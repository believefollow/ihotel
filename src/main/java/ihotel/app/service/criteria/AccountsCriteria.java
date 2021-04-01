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
 * Criteria class for the {@link ihotel.app.domain.Accounts} entity. This class is used
 * in {@link ihotel.app.web.rest.AccountsResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /accounts?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class AccountsCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private StringFilter account;

    private InstantFilter consumetime;

    private InstantFilter hoteltime;

    private LongFilter feenum;

    private BigDecimalFilter money;

    private StringFilter memo;

    private StringFilter empn;

    private BigDecimalFilter imprest;

    private StringFilter propertiy;

    private LongFilter earntypen;

    private LongFilter payment;

    private StringFilter roomn;

    private LongFilter id;

    private StringFilter ulogogram;

    private LongFilter lk;

    private StringFilter acc;

    private LongFilter jzSign;

    private LongFilter jzflag;

    private StringFilter sign;

    private LongFilter bs;

    private InstantFilter jzhotel;

    private StringFilter jzempn;

    private InstantFilter jztime;

    private LongFilter chonghong;

    private StringFilter billno;

    private LongFilter printcount;

    private BigDecimalFilter vipjf;

    private StringFilter hykh;

    private BigDecimalFilter sl;

    private StringFilter sgdjh;

    private StringFilter hoteldm;

    private LongFilter isnew;

    private DoubleFilter guestId;

    private StringFilter yhkh;

    private StringFilter djq;

    private BigDecimalFilter ysje;

    private StringFilter bj;

    private StringFilter bjempn;

    private InstantFilter bjtime;

    private StringFilter paper2;

    private StringFilter bc;

    private StringFilter auto;

    private StringFilter xsy;

    private StringFilter djkh;

    private StringFilter djsign;

    private StringFilter classname;

    private StringFilter iscy;

    private StringFilter bsign;

    private StringFilter fx;

    private StringFilter djlx;

    private LongFilter isup;

    private BigDecimalFilter yongjin;

    private StringFilter czpc;

    private LongFilter cxflag;

    private StringFilter pmemo;

    private StringFilter czbillno;

    private StringFilter djqbz;

    private StringFilter ysqmemo;

    private StringFilter transactionId;

    private StringFilter outTradeNo;

    private StringFilter gsname;

    private InstantFilter rz;

    private InstantFilter gz;

    private LongFilter ts;

    private StringFilter ky;

    private StringFilter xy;

    private StringFilter roomtype;

    private LongFilter bkid;

    public AccountsCriteria() {}

    public AccountsCriteria(AccountsCriteria other) {
        this.account = other.account == null ? null : other.account.copy();
        this.consumetime = other.consumetime == null ? null : other.consumetime.copy();
        this.hoteltime = other.hoteltime == null ? null : other.hoteltime.copy();
        this.feenum = other.feenum == null ? null : other.feenum.copy();
        this.money = other.money == null ? null : other.money.copy();
        this.memo = other.memo == null ? null : other.memo.copy();
        this.empn = other.empn == null ? null : other.empn.copy();
        this.imprest = other.imprest == null ? null : other.imprest.copy();
        this.propertiy = other.propertiy == null ? null : other.propertiy.copy();
        this.earntypen = other.earntypen == null ? null : other.earntypen.copy();
        this.payment = other.payment == null ? null : other.payment.copy();
        this.roomn = other.roomn == null ? null : other.roomn.copy();
        this.id = other.id == null ? null : other.id.copy();
        this.ulogogram = other.ulogogram == null ? null : other.ulogogram.copy();
        this.lk = other.lk == null ? null : other.lk.copy();
        this.acc = other.acc == null ? null : other.acc.copy();
        this.jzSign = other.jzSign == null ? null : other.jzSign.copy();
        this.jzflag = other.jzflag == null ? null : other.jzflag.copy();
        this.sign = other.sign == null ? null : other.sign.copy();
        this.bs = other.bs == null ? null : other.bs.copy();
        this.jzhotel = other.jzhotel == null ? null : other.jzhotel.copy();
        this.jzempn = other.jzempn == null ? null : other.jzempn.copy();
        this.jztime = other.jztime == null ? null : other.jztime.copy();
        this.chonghong = other.chonghong == null ? null : other.chonghong.copy();
        this.billno = other.billno == null ? null : other.billno.copy();
        this.printcount = other.printcount == null ? null : other.printcount.copy();
        this.vipjf = other.vipjf == null ? null : other.vipjf.copy();
        this.hykh = other.hykh == null ? null : other.hykh.copy();
        this.sl = other.sl == null ? null : other.sl.copy();
        this.sgdjh = other.sgdjh == null ? null : other.sgdjh.copy();
        this.hoteldm = other.hoteldm == null ? null : other.hoteldm.copy();
        this.isnew = other.isnew == null ? null : other.isnew.copy();
        this.guestId = other.guestId == null ? null : other.guestId.copy();
        this.yhkh = other.yhkh == null ? null : other.yhkh.copy();
        this.djq = other.djq == null ? null : other.djq.copy();
        this.ysje = other.ysje == null ? null : other.ysje.copy();
        this.bj = other.bj == null ? null : other.bj.copy();
        this.bjempn = other.bjempn == null ? null : other.bjempn.copy();
        this.bjtime = other.bjtime == null ? null : other.bjtime.copy();
        this.paper2 = other.paper2 == null ? null : other.paper2.copy();
        this.bc = other.bc == null ? null : other.bc.copy();
        this.auto = other.auto == null ? null : other.auto.copy();
        this.xsy = other.xsy == null ? null : other.xsy.copy();
        this.djkh = other.djkh == null ? null : other.djkh.copy();
        this.djsign = other.djsign == null ? null : other.djsign.copy();
        this.classname = other.classname == null ? null : other.classname.copy();
        this.iscy = other.iscy == null ? null : other.iscy.copy();
        this.bsign = other.bsign == null ? null : other.bsign.copy();
        this.fx = other.fx == null ? null : other.fx.copy();
        this.djlx = other.djlx == null ? null : other.djlx.copy();
        this.isup = other.isup == null ? null : other.isup.copy();
        this.yongjin = other.yongjin == null ? null : other.yongjin.copy();
        this.czpc = other.czpc == null ? null : other.czpc.copy();
        this.cxflag = other.cxflag == null ? null : other.cxflag.copy();
        this.pmemo = other.pmemo == null ? null : other.pmemo.copy();
        this.czbillno = other.czbillno == null ? null : other.czbillno.copy();
        this.djqbz = other.djqbz == null ? null : other.djqbz.copy();
        this.ysqmemo = other.ysqmemo == null ? null : other.ysqmemo.copy();
        this.transactionId = other.transactionId == null ? null : other.transactionId.copy();
        this.outTradeNo = other.outTradeNo == null ? null : other.outTradeNo.copy();
        this.gsname = other.gsname == null ? null : other.gsname.copy();
        this.rz = other.rz == null ? null : other.rz.copy();
        this.gz = other.gz == null ? null : other.gz.copy();
        this.ts = other.ts == null ? null : other.ts.copy();
        this.ky = other.ky == null ? null : other.ky.copy();
        this.xy = other.xy == null ? null : other.xy.copy();
        this.roomtype = other.roomtype == null ? null : other.roomtype.copy();
        this.bkid = other.bkid == null ? null : other.bkid.copy();
    }

    @Override
    public AccountsCriteria copy() {
        return new AccountsCriteria(this);
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

    public InstantFilter getConsumetime() {
        return consumetime;
    }

    public InstantFilter consumetime() {
        if (consumetime == null) {
            consumetime = new InstantFilter();
        }
        return consumetime;
    }

    public void setConsumetime(InstantFilter consumetime) {
        this.consumetime = consumetime;
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

    public LongFilter getFeenum() {
        return feenum;
    }

    public LongFilter feenum() {
        if (feenum == null) {
            feenum = new LongFilter();
        }
        return feenum;
    }

    public void setFeenum(LongFilter feenum) {
        this.feenum = feenum;
    }

    public BigDecimalFilter getMoney() {
        return money;
    }

    public BigDecimalFilter money() {
        if (money == null) {
            money = new BigDecimalFilter();
        }
        return money;
    }

    public void setMoney(BigDecimalFilter money) {
        this.money = money;
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

    public BigDecimalFilter getImprest() {
        return imprest;
    }

    public BigDecimalFilter imprest() {
        if (imprest == null) {
            imprest = new BigDecimalFilter();
        }
        return imprest;
    }

    public void setImprest(BigDecimalFilter imprest) {
        this.imprest = imprest;
    }

    public StringFilter getPropertiy() {
        return propertiy;
    }

    public StringFilter propertiy() {
        if (propertiy == null) {
            propertiy = new StringFilter();
        }
        return propertiy;
    }

    public void setPropertiy(StringFilter propertiy) {
        this.propertiy = propertiy;
    }

    public LongFilter getEarntypen() {
        return earntypen;
    }

    public LongFilter earntypen() {
        if (earntypen == null) {
            earntypen = new LongFilter();
        }
        return earntypen;
    }

    public void setEarntypen(LongFilter earntypen) {
        this.earntypen = earntypen;
    }

    public LongFilter getPayment() {
        return payment;
    }

    public LongFilter payment() {
        if (payment == null) {
            payment = new LongFilter();
        }
        return payment;
    }

    public void setPayment(LongFilter payment) {
        this.payment = payment;
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

    public StringFilter getUlogogram() {
        return ulogogram;
    }

    public StringFilter ulogogram() {
        if (ulogogram == null) {
            ulogogram = new StringFilter();
        }
        return ulogogram;
    }

    public void setUlogogram(StringFilter ulogogram) {
        this.ulogogram = ulogogram;
    }

    public LongFilter getLk() {
        return lk;
    }

    public LongFilter lk() {
        if (lk == null) {
            lk = new LongFilter();
        }
        return lk;
    }

    public void setLk(LongFilter lk) {
        this.lk = lk;
    }

    public StringFilter getAcc() {
        return acc;
    }

    public StringFilter acc() {
        if (acc == null) {
            acc = new StringFilter();
        }
        return acc;
    }

    public void setAcc(StringFilter acc) {
        this.acc = acc;
    }

    public LongFilter getJzSign() {
        return jzSign;
    }

    public LongFilter jzSign() {
        if (jzSign == null) {
            jzSign = new LongFilter();
        }
        return jzSign;
    }

    public void setJzSign(LongFilter jzSign) {
        this.jzSign = jzSign;
    }

    public LongFilter getJzflag() {
        return jzflag;
    }

    public LongFilter jzflag() {
        if (jzflag == null) {
            jzflag = new LongFilter();
        }
        return jzflag;
    }

    public void setJzflag(LongFilter jzflag) {
        this.jzflag = jzflag;
    }

    public StringFilter getSign() {
        return sign;
    }

    public StringFilter sign() {
        if (sign == null) {
            sign = new StringFilter();
        }
        return sign;
    }

    public void setSign(StringFilter sign) {
        this.sign = sign;
    }

    public LongFilter getBs() {
        return bs;
    }

    public LongFilter bs() {
        if (bs == null) {
            bs = new LongFilter();
        }
        return bs;
    }

    public void setBs(LongFilter bs) {
        this.bs = bs;
    }

    public InstantFilter getJzhotel() {
        return jzhotel;
    }

    public InstantFilter jzhotel() {
        if (jzhotel == null) {
            jzhotel = new InstantFilter();
        }
        return jzhotel;
    }

    public void setJzhotel(InstantFilter jzhotel) {
        this.jzhotel = jzhotel;
    }

    public StringFilter getJzempn() {
        return jzempn;
    }

    public StringFilter jzempn() {
        if (jzempn == null) {
            jzempn = new StringFilter();
        }
        return jzempn;
    }

    public void setJzempn(StringFilter jzempn) {
        this.jzempn = jzempn;
    }

    public InstantFilter getJztime() {
        return jztime;
    }

    public InstantFilter jztime() {
        if (jztime == null) {
            jztime = new InstantFilter();
        }
        return jztime;
    }

    public void setJztime(InstantFilter jztime) {
        this.jztime = jztime;
    }

    public LongFilter getChonghong() {
        return chonghong;
    }

    public LongFilter chonghong() {
        if (chonghong == null) {
            chonghong = new LongFilter();
        }
        return chonghong;
    }

    public void setChonghong(LongFilter chonghong) {
        this.chonghong = chonghong;
    }

    public StringFilter getBillno() {
        return billno;
    }

    public StringFilter billno() {
        if (billno == null) {
            billno = new StringFilter();
        }
        return billno;
    }

    public void setBillno(StringFilter billno) {
        this.billno = billno;
    }

    public LongFilter getPrintcount() {
        return printcount;
    }

    public LongFilter printcount() {
        if (printcount == null) {
            printcount = new LongFilter();
        }
        return printcount;
    }

    public void setPrintcount(LongFilter printcount) {
        this.printcount = printcount;
    }

    public BigDecimalFilter getVipjf() {
        return vipjf;
    }

    public BigDecimalFilter vipjf() {
        if (vipjf == null) {
            vipjf = new BigDecimalFilter();
        }
        return vipjf;
    }

    public void setVipjf(BigDecimalFilter vipjf) {
        this.vipjf = vipjf;
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

    public BigDecimalFilter getSl() {
        return sl;
    }

    public BigDecimalFilter sl() {
        if (sl == null) {
            sl = new BigDecimalFilter();
        }
        return sl;
    }

    public void setSl(BigDecimalFilter sl) {
        this.sl = sl;
    }

    public StringFilter getSgdjh() {
        return sgdjh;
    }

    public StringFilter sgdjh() {
        if (sgdjh == null) {
            sgdjh = new StringFilter();
        }
        return sgdjh;
    }

    public void setSgdjh(StringFilter sgdjh) {
        this.sgdjh = sgdjh;
    }

    public StringFilter getHoteldm() {
        return hoteldm;
    }

    public StringFilter hoteldm() {
        if (hoteldm == null) {
            hoteldm = new StringFilter();
        }
        return hoteldm;
    }

    public void setHoteldm(StringFilter hoteldm) {
        this.hoteldm = hoteldm;
    }

    public LongFilter getIsnew() {
        return isnew;
    }

    public LongFilter isnew() {
        if (isnew == null) {
            isnew = new LongFilter();
        }
        return isnew;
    }

    public void setIsnew(LongFilter isnew) {
        this.isnew = isnew;
    }

    public DoubleFilter getGuestId() {
        return guestId;
    }

    public DoubleFilter guestId() {
        if (guestId == null) {
            guestId = new DoubleFilter();
        }
        return guestId;
    }

    public void setGuestId(DoubleFilter guestId) {
        this.guestId = guestId;
    }

    public StringFilter getYhkh() {
        return yhkh;
    }

    public StringFilter yhkh() {
        if (yhkh == null) {
            yhkh = new StringFilter();
        }
        return yhkh;
    }

    public void setYhkh(StringFilter yhkh) {
        this.yhkh = yhkh;
    }

    public StringFilter getDjq() {
        return djq;
    }

    public StringFilter djq() {
        if (djq == null) {
            djq = new StringFilter();
        }
        return djq;
    }

    public void setDjq(StringFilter djq) {
        this.djq = djq;
    }

    public BigDecimalFilter getYsje() {
        return ysje;
    }

    public BigDecimalFilter ysje() {
        if (ysje == null) {
            ysje = new BigDecimalFilter();
        }
        return ysje;
    }

    public void setYsje(BigDecimalFilter ysje) {
        this.ysje = ysje;
    }

    public StringFilter getBj() {
        return bj;
    }

    public StringFilter bj() {
        if (bj == null) {
            bj = new StringFilter();
        }
        return bj;
    }

    public void setBj(StringFilter bj) {
        this.bj = bj;
    }

    public StringFilter getBjempn() {
        return bjempn;
    }

    public StringFilter bjempn() {
        if (bjempn == null) {
            bjempn = new StringFilter();
        }
        return bjempn;
    }

    public void setBjempn(StringFilter bjempn) {
        this.bjempn = bjempn;
    }

    public InstantFilter getBjtime() {
        return bjtime;
    }

    public InstantFilter bjtime() {
        if (bjtime == null) {
            bjtime = new InstantFilter();
        }
        return bjtime;
    }

    public void setBjtime(InstantFilter bjtime) {
        this.bjtime = bjtime;
    }

    public StringFilter getPaper2() {
        return paper2;
    }

    public StringFilter paper2() {
        if (paper2 == null) {
            paper2 = new StringFilter();
        }
        return paper2;
    }

    public void setPaper2(StringFilter paper2) {
        this.paper2 = paper2;
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

    public StringFilter getAuto() {
        return auto;
    }

    public StringFilter auto() {
        if (auto == null) {
            auto = new StringFilter();
        }
        return auto;
    }

    public void setAuto(StringFilter auto) {
        this.auto = auto;
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

    public StringFilter getDjkh() {
        return djkh;
    }

    public StringFilter djkh() {
        if (djkh == null) {
            djkh = new StringFilter();
        }
        return djkh;
    }

    public void setDjkh(StringFilter djkh) {
        this.djkh = djkh;
    }

    public StringFilter getDjsign() {
        return djsign;
    }

    public StringFilter djsign() {
        if (djsign == null) {
            djsign = new StringFilter();
        }
        return djsign;
    }

    public void setDjsign(StringFilter djsign) {
        this.djsign = djsign;
    }

    public StringFilter getClassname() {
        return classname;
    }

    public StringFilter classname() {
        if (classname == null) {
            classname = new StringFilter();
        }
        return classname;
    }

    public void setClassname(StringFilter classname) {
        this.classname = classname;
    }

    public StringFilter getIscy() {
        return iscy;
    }

    public StringFilter iscy() {
        if (iscy == null) {
            iscy = new StringFilter();
        }
        return iscy;
    }

    public void setIscy(StringFilter iscy) {
        this.iscy = iscy;
    }

    public StringFilter getBsign() {
        return bsign;
    }

    public StringFilter bsign() {
        if (bsign == null) {
            bsign = new StringFilter();
        }
        return bsign;
    }

    public void setBsign(StringFilter bsign) {
        this.bsign = bsign;
    }

    public StringFilter getFx() {
        return fx;
    }

    public StringFilter fx() {
        if (fx == null) {
            fx = new StringFilter();
        }
        return fx;
    }

    public void setFx(StringFilter fx) {
        this.fx = fx;
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

    public BigDecimalFilter getYongjin() {
        return yongjin;
    }

    public BigDecimalFilter yongjin() {
        if (yongjin == null) {
            yongjin = new BigDecimalFilter();
        }
        return yongjin;
    }

    public void setYongjin(BigDecimalFilter yongjin) {
        this.yongjin = yongjin;
    }

    public StringFilter getCzpc() {
        return czpc;
    }

    public StringFilter czpc() {
        if (czpc == null) {
            czpc = new StringFilter();
        }
        return czpc;
    }

    public void setCzpc(StringFilter czpc) {
        this.czpc = czpc;
    }

    public LongFilter getCxflag() {
        return cxflag;
    }

    public LongFilter cxflag() {
        if (cxflag == null) {
            cxflag = new LongFilter();
        }
        return cxflag;
    }

    public void setCxflag(LongFilter cxflag) {
        this.cxflag = cxflag;
    }

    public StringFilter getPmemo() {
        return pmemo;
    }

    public StringFilter pmemo() {
        if (pmemo == null) {
            pmemo = new StringFilter();
        }
        return pmemo;
    }

    public void setPmemo(StringFilter pmemo) {
        this.pmemo = pmemo;
    }

    public StringFilter getCzbillno() {
        return czbillno;
    }

    public StringFilter czbillno() {
        if (czbillno == null) {
            czbillno = new StringFilter();
        }
        return czbillno;
    }

    public void setCzbillno(StringFilter czbillno) {
        this.czbillno = czbillno;
    }

    public StringFilter getDjqbz() {
        return djqbz;
    }

    public StringFilter djqbz() {
        if (djqbz == null) {
            djqbz = new StringFilter();
        }
        return djqbz;
    }

    public void setDjqbz(StringFilter djqbz) {
        this.djqbz = djqbz;
    }

    public StringFilter getYsqmemo() {
        return ysqmemo;
    }

    public StringFilter ysqmemo() {
        if (ysqmemo == null) {
            ysqmemo = new StringFilter();
        }
        return ysqmemo;
    }

    public void setYsqmemo(StringFilter ysqmemo) {
        this.ysqmemo = ysqmemo;
    }

    public StringFilter getTransactionId() {
        return transactionId;
    }

    public StringFilter transactionId() {
        if (transactionId == null) {
            transactionId = new StringFilter();
        }
        return transactionId;
    }

    public void setTransactionId(StringFilter transactionId) {
        this.transactionId = transactionId;
    }

    public StringFilter getOutTradeNo() {
        return outTradeNo;
    }

    public StringFilter outTradeNo() {
        if (outTradeNo == null) {
            outTradeNo = new StringFilter();
        }
        return outTradeNo;
    }

    public void setOutTradeNo(StringFilter outTradeNo) {
        this.outTradeNo = outTradeNo;
    }

    public StringFilter getGsname() {
        return gsname;
    }

    public StringFilter gsname() {
        if (gsname == null) {
            gsname = new StringFilter();
        }
        return gsname;
    }

    public void setGsname(StringFilter gsname) {
        this.gsname = gsname;
    }

    public InstantFilter getRz() {
        return rz;
    }

    public InstantFilter rz() {
        if (rz == null) {
            rz = new InstantFilter();
        }
        return rz;
    }

    public void setRz(InstantFilter rz) {
        this.rz = rz;
    }

    public InstantFilter getGz() {
        return gz;
    }

    public InstantFilter gz() {
        if (gz == null) {
            gz = new InstantFilter();
        }
        return gz;
    }

    public void setGz(InstantFilter gz) {
        this.gz = gz;
    }

    public LongFilter getTs() {
        return ts;
    }

    public LongFilter ts() {
        if (ts == null) {
            ts = new LongFilter();
        }
        return ts;
    }

    public void setTs(LongFilter ts) {
        this.ts = ts;
    }

    public StringFilter getKy() {
        return ky;
    }

    public StringFilter ky() {
        if (ky == null) {
            ky = new StringFilter();
        }
        return ky;
    }

    public void setKy(StringFilter ky) {
        this.ky = ky;
    }

    public StringFilter getXy() {
        return xy;
    }

    public StringFilter xy() {
        if (xy == null) {
            xy = new StringFilter();
        }
        return xy;
    }

    public void setXy(StringFilter xy) {
        this.xy = xy;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final AccountsCriteria that = (AccountsCriteria) o;
        return (
            Objects.equals(account, that.account) &&
            Objects.equals(consumetime, that.consumetime) &&
            Objects.equals(hoteltime, that.hoteltime) &&
            Objects.equals(feenum, that.feenum) &&
            Objects.equals(money, that.money) &&
            Objects.equals(memo, that.memo) &&
            Objects.equals(empn, that.empn) &&
            Objects.equals(imprest, that.imprest) &&
            Objects.equals(propertiy, that.propertiy) &&
            Objects.equals(earntypen, that.earntypen) &&
            Objects.equals(payment, that.payment) &&
            Objects.equals(roomn, that.roomn) &&
            Objects.equals(id, that.id) &&
            Objects.equals(ulogogram, that.ulogogram) &&
            Objects.equals(lk, that.lk) &&
            Objects.equals(acc, that.acc) &&
            Objects.equals(jzSign, that.jzSign) &&
            Objects.equals(jzflag, that.jzflag) &&
            Objects.equals(sign, that.sign) &&
            Objects.equals(bs, that.bs) &&
            Objects.equals(jzhotel, that.jzhotel) &&
            Objects.equals(jzempn, that.jzempn) &&
            Objects.equals(jztime, that.jztime) &&
            Objects.equals(chonghong, that.chonghong) &&
            Objects.equals(billno, that.billno) &&
            Objects.equals(printcount, that.printcount) &&
            Objects.equals(vipjf, that.vipjf) &&
            Objects.equals(hykh, that.hykh) &&
            Objects.equals(sl, that.sl) &&
            Objects.equals(sgdjh, that.sgdjh) &&
            Objects.equals(hoteldm, that.hoteldm) &&
            Objects.equals(isnew, that.isnew) &&
            Objects.equals(guestId, that.guestId) &&
            Objects.equals(yhkh, that.yhkh) &&
            Objects.equals(djq, that.djq) &&
            Objects.equals(ysje, that.ysje) &&
            Objects.equals(bj, that.bj) &&
            Objects.equals(bjempn, that.bjempn) &&
            Objects.equals(bjtime, that.bjtime) &&
            Objects.equals(paper2, that.paper2) &&
            Objects.equals(bc, that.bc) &&
            Objects.equals(auto, that.auto) &&
            Objects.equals(xsy, that.xsy) &&
            Objects.equals(djkh, that.djkh) &&
            Objects.equals(djsign, that.djsign) &&
            Objects.equals(classname, that.classname) &&
            Objects.equals(iscy, that.iscy) &&
            Objects.equals(bsign, that.bsign) &&
            Objects.equals(fx, that.fx) &&
            Objects.equals(djlx, that.djlx) &&
            Objects.equals(isup, that.isup) &&
            Objects.equals(yongjin, that.yongjin) &&
            Objects.equals(czpc, that.czpc) &&
            Objects.equals(cxflag, that.cxflag) &&
            Objects.equals(pmemo, that.pmemo) &&
            Objects.equals(czbillno, that.czbillno) &&
            Objects.equals(djqbz, that.djqbz) &&
            Objects.equals(ysqmemo, that.ysqmemo) &&
            Objects.equals(transactionId, that.transactionId) &&
            Objects.equals(outTradeNo, that.outTradeNo) &&
            Objects.equals(gsname, that.gsname) &&
            Objects.equals(rz, that.rz) &&
            Objects.equals(gz, that.gz) &&
            Objects.equals(ts, that.ts) &&
            Objects.equals(ky, that.ky) &&
            Objects.equals(xy, that.xy) &&
            Objects.equals(roomtype, that.roomtype) &&
            Objects.equals(bkid, that.bkid)
        );
    }

    @Override
    public int hashCode() {
        return Objects.hash(
            account,
            consumetime,
            hoteltime,
            feenum,
            money,
            memo,
            empn,
            imprest,
            propertiy,
            earntypen,
            payment,
            roomn,
            id,
            ulogogram,
            lk,
            acc,
            jzSign,
            jzflag,
            sign,
            bs,
            jzhotel,
            jzempn,
            jztime,
            chonghong,
            billno,
            printcount,
            vipjf,
            hykh,
            sl,
            sgdjh,
            hoteldm,
            isnew,
            guestId,
            yhkh,
            djq,
            ysje,
            bj,
            bjempn,
            bjtime,
            paper2,
            bc,
            auto,
            xsy,
            djkh,
            djsign,
            classname,
            iscy,
            bsign,
            fx,
            djlx,
            isup,
            yongjin,
            czpc,
            cxflag,
            pmemo,
            czbillno,
            djqbz,
            ysqmemo,
            transactionId,
            outTradeNo,
            gsname,
            rz,
            gz,
            ts,
            ky,
            xy,
            roomtype,
            bkid
        );
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "AccountsCriteria{" +
            (account != null ? "account=" + account + ", " : "") +
            (consumetime != null ? "consumetime=" + consumetime + ", " : "") +
            (hoteltime != null ? "hoteltime=" + hoteltime + ", " : "") +
            (feenum != null ? "feenum=" + feenum + ", " : "") +
            (money != null ? "money=" + money + ", " : "") +
            (memo != null ? "memo=" + memo + ", " : "") +
            (empn != null ? "empn=" + empn + ", " : "") +
            (imprest != null ? "imprest=" + imprest + ", " : "") +
            (propertiy != null ? "propertiy=" + propertiy + ", " : "") +
            (earntypen != null ? "earntypen=" + earntypen + ", " : "") +
            (payment != null ? "payment=" + payment + ", " : "") +
            (roomn != null ? "roomn=" + roomn + ", " : "") +
            (id != null ? "id=" + id + ", " : "") +
            (ulogogram != null ? "ulogogram=" + ulogogram + ", " : "") +
            (lk != null ? "lk=" + lk + ", " : "") +
            (acc != null ? "acc=" + acc + ", " : "") +
            (jzSign != null ? "jzSign=" + jzSign + ", " : "") +
            (jzflag != null ? "jzflag=" + jzflag + ", " : "") +
            (sign != null ? "sign=" + sign + ", " : "") +
            (bs != null ? "bs=" + bs + ", " : "") +
            (jzhotel != null ? "jzhotel=" + jzhotel + ", " : "") +
            (jzempn != null ? "jzempn=" + jzempn + ", " : "") +
            (jztime != null ? "jztime=" + jztime + ", " : "") +
            (chonghong != null ? "chonghong=" + chonghong + ", " : "") +
            (billno != null ? "billno=" + billno + ", " : "") +
            (printcount != null ? "printcount=" + printcount + ", " : "") +
            (vipjf != null ? "vipjf=" + vipjf + ", " : "") +
            (hykh != null ? "hykh=" + hykh + ", " : "") +
            (sl != null ? "sl=" + sl + ", " : "") +
            (sgdjh != null ? "sgdjh=" + sgdjh + ", " : "") +
            (hoteldm != null ? "hoteldm=" + hoteldm + ", " : "") +
            (isnew != null ? "isnew=" + isnew + ", " : "") +
            (guestId != null ? "guestId=" + guestId + ", " : "") +
            (yhkh != null ? "yhkh=" + yhkh + ", " : "") +
            (djq != null ? "djq=" + djq + ", " : "") +
            (ysje != null ? "ysje=" + ysje + ", " : "") +
            (bj != null ? "bj=" + bj + ", " : "") +
            (bjempn != null ? "bjempn=" + bjempn + ", " : "") +
            (bjtime != null ? "bjtime=" + bjtime + ", " : "") +
            (paper2 != null ? "paper2=" + paper2 + ", " : "") +
            (bc != null ? "bc=" + bc + ", " : "") +
            (auto != null ? "auto=" + auto + ", " : "") +
            (xsy != null ? "xsy=" + xsy + ", " : "") +
            (djkh != null ? "djkh=" + djkh + ", " : "") +
            (djsign != null ? "djsign=" + djsign + ", " : "") +
            (classname != null ? "classname=" + classname + ", " : "") +
            (iscy != null ? "iscy=" + iscy + ", " : "") +
            (bsign != null ? "bsign=" + bsign + ", " : "") +
            (fx != null ? "fx=" + fx + ", " : "") +
            (djlx != null ? "djlx=" + djlx + ", " : "") +
            (isup != null ? "isup=" + isup + ", " : "") +
            (yongjin != null ? "yongjin=" + yongjin + ", " : "") +
            (czpc != null ? "czpc=" + czpc + ", " : "") +
            (cxflag != null ? "cxflag=" + cxflag + ", " : "") +
            (pmemo != null ? "pmemo=" + pmemo + ", " : "") +
            (czbillno != null ? "czbillno=" + czbillno + ", " : "") +
            (djqbz != null ? "djqbz=" + djqbz + ", " : "") +
            (ysqmemo != null ? "ysqmemo=" + ysqmemo + ", " : "") +
            (transactionId != null ? "transactionId=" + transactionId + ", " : "") +
            (outTradeNo != null ? "outTradeNo=" + outTradeNo + ", " : "") +
            (gsname != null ? "gsname=" + gsname + ", " : "") +
            (rz != null ? "rz=" + rz + ", " : "") +
            (gz != null ? "gz=" + gz + ", " : "") +
            (ts != null ? "ts=" + ts + ", " : "") +
            (ky != null ? "ky=" + ky + ", " : "") +
            (xy != null ? "xy=" + xy + ", " : "") +
            (roomtype != null ? "roomtype=" + roomtype + ", " : "") +
            (bkid != null ? "bkid=" + bkid + ", " : "") +
            "}";
    }
}
