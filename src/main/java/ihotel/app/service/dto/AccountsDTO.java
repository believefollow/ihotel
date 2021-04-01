package ihotel.app.service.dto;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.Instant;
import java.util.Objects;
import javax.validation.constraints.*;

/**
 * A DTO for the {@link ihotel.app.domain.Accounts} entity.
 */
public class AccountsDTO implements Serializable {

    @NotNull
    @Size(max = 30)
    private String account;

    @NotNull
    private Instant consumetime;

    private Instant hoteltime;

    private Long feenum;

    private BigDecimal money;

    @Size(max = 100)
    private String memo;

    @Size(max = 10)
    private String empn;

    private BigDecimal imprest;

    @Size(max = 100)
    private String propertiy;

    private Long earntypen;

    private Long payment;

    @Size(max = 10)
    private String roomn;

    @NotNull
    private Long id;

    @Size(max = 30)
    private String ulogogram;

    private Long lk;

    @Size(max = 50)
    private String acc;

    private Long jzSign;

    private Long jzflag;

    @Size(max = 4)
    private String sign;

    private Long bs;

    private Instant jzhotel;

    @Size(max = 50)
    private String jzempn;

    private Instant jztime;

    private Long chonghong;

    @Size(max = 10)
    private String billno;

    private Long printcount;

    private BigDecimal vipjf;

    @Size(max = 20)
    private String hykh;

    private BigDecimal sl;

    @Size(max = 20)
    private String sgdjh;

    @Size(max = 20)
    private String hoteldm;

    private Long isnew;

    private Double guestId;

    @Size(max = 50)
    private String yhkh;

    @Size(max = 100)
    private String djq;

    private BigDecimal ysje;

    @Size(max = 2)
    private String bj;

    @Size(max = 50)
    private String bjempn;

    private Instant bjtime;

    @Size(max = 50)
    private String paper2;

    @Size(max = 20)
    private String bc;

    @Size(max = 2)
    private String auto;

    @Size(max = 50)
    private String xsy;

    @Size(max = 50)
    private String djkh;

    @Size(max = 2)
    private String djsign;

    @Size(max = 50)
    private String classname;

    @Size(max = 20)
    private String iscy;

    @Size(max = 2)
    private String bsign;

    @Size(max = 2)
    private String fx;

    @Size(max = 50)
    private String djlx;

    private Long isup;

    private BigDecimal yongjin;

    @Size(max = 50)
    private String czpc;

    private Long cxflag;

    @Size(max = 200)
    private String pmemo;

    @Size(max = 50)
    private String czbillno;

    @Size(max = 50)
    private String djqbz;

    @Size(max = 100)
    private String ysqmemo;

    @Size(max = 100)
    private String transactionId;

    @Size(max = 100)
    private String outTradeNo;

    @Size(max = 200)
    private String gsname;

    private Instant rz;

    private Instant gz;

    private Long ts;

    @Size(max = 50)
    private String ky;

    @Size(max = 50)
    private String xy;

    @Size(max = 50)
    private String roomtype;

    private Long bkid;

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public Instant getConsumetime() {
        return consumetime;
    }

    public void setConsumetime(Instant consumetime) {
        this.consumetime = consumetime;
    }

    public Instant getHoteltime() {
        return hoteltime;
    }

    public void setHoteltime(Instant hoteltime) {
        this.hoteltime = hoteltime;
    }

    public Long getFeenum() {
        return feenum;
    }

    public void setFeenum(Long feenum) {
        this.feenum = feenum;
    }

    public BigDecimal getMoney() {
        return money;
    }

    public void setMoney(BigDecimal money) {
        this.money = money;
    }

    public String getMemo() {
        return memo;
    }

    public void setMemo(String memo) {
        this.memo = memo;
    }

    public String getEmpn() {
        return empn;
    }

    public void setEmpn(String empn) {
        this.empn = empn;
    }

    public BigDecimal getImprest() {
        return imprest;
    }

    public void setImprest(BigDecimal imprest) {
        this.imprest = imprest;
    }

    public String getPropertiy() {
        return propertiy;
    }

    public void setPropertiy(String propertiy) {
        this.propertiy = propertiy;
    }

    public Long getEarntypen() {
        return earntypen;
    }

    public void setEarntypen(Long earntypen) {
        this.earntypen = earntypen;
    }

    public Long getPayment() {
        return payment;
    }

    public void setPayment(Long payment) {
        this.payment = payment;
    }

    public String getRoomn() {
        return roomn;
    }

    public void setRoomn(String roomn) {
        this.roomn = roomn;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUlogogram() {
        return ulogogram;
    }

    public void setUlogogram(String ulogogram) {
        this.ulogogram = ulogogram;
    }

    public Long getLk() {
        return lk;
    }

    public void setLk(Long lk) {
        this.lk = lk;
    }

    public String getAcc() {
        return acc;
    }

    public void setAcc(String acc) {
        this.acc = acc;
    }

    public Long getJzSign() {
        return jzSign;
    }

    public void setJzSign(Long jzSign) {
        this.jzSign = jzSign;
    }

    public Long getJzflag() {
        return jzflag;
    }

    public void setJzflag(Long jzflag) {
        this.jzflag = jzflag;
    }

    public String getSign() {
        return sign;
    }

    public void setSign(String sign) {
        this.sign = sign;
    }

    public Long getBs() {
        return bs;
    }

    public void setBs(Long bs) {
        this.bs = bs;
    }

    public Instant getJzhotel() {
        return jzhotel;
    }

    public void setJzhotel(Instant jzhotel) {
        this.jzhotel = jzhotel;
    }

    public String getJzempn() {
        return jzempn;
    }

    public void setJzempn(String jzempn) {
        this.jzempn = jzempn;
    }

    public Instant getJztime() {
        return jztime;
    }

    public void setJztime(Instant jztime) {
        this.jztime = jztime;
    }

    public Long getChonghong() {
        return chonghong;
    }

    public void setChonghong(Long chonghong) {
        this.chonghong = chonghong;
    }

    public String getBillno() {
        return billno;
    }

    public void setBillno(String billno) {
        this.billno = billno;
    }

    public Long getPrintcount() {
        return printcount;
    }

    public void setPrintcount(Long printcount) {
        this.printcount = printcount;
    }

    public BigDecimal getVipjf() {
        return vipjf;
    }

    public void setVipjf(BigDecimal vipjf) {
        this.vipjf = vipjf;
    }

    public String getHykh() {
        return hykh;
    }

    public void setHykh(String hykh) {
        this.hykh = hykh;
    }

    public BigDecimal getSl() {
        return sl;
    }

    public void setSl(BigDecimal sl) {
        this.sl = sl;
    }

    public String getSgdjh() {
        return sgdjh;
    }

    public void setSgdjh(String sgdjh) {
        this.sgdjh = sgdjh;
    }

    public String getHoteldm() {
        return hoteldm;
    }

    public void setHoteldm(String hoteldm) {
        this.hoteldm = hoteldm;
    }

    public Long getIsnew() {
        return isnew;
    }

    public void setIsnew(Long isnew) {
        this.isnew = isnew;
    }

    public Double getGuestId() {
        return guestId;
    }

    public void setGuestId(Double guestId) {
        this.guestId = guestId;
    }

    public String getYhkh() {
        return yhkh;
    }

    public void setYhkh(String yhkh) {
        this.yhkh = yhkh;
    }

    public String getDjq() {
        return djq;
    }

    public void setDjq(String djq) {
        this.djq = djq;
    }

    public BigDecimal getYsje() {
        return ysje;
    }

    public void setYsje(BigDecimal ysje) {
        this.ysje = ysje;
    }

    public String getBj() {
        return bj;
    }

    public void setBj(String bj) {
        this.bj = bj;
    }

    public String getBjempn() {
        return bjempn;
    }

    public void setBjempn(String bjempn) {
        this.bjempn = bjempn;
    }

    public Instant getBjtime() {
        return bjtime;
    }

    public void setBjtime(Instant bjtime) {
        this.bjtime = bjtime;
    }

    public String getPaper2() {
        return paper2;
    }

    public void setPaper2(String paper2) {
        this.paper2 = paper2;
    }

    public String getBc() {
        return bc;
    }

    public void setBc(String bc) {
        this.bc = bc;
    }

    public String getAuto() {
        return auto;
    }

    public void setAuto(String auto) {
        this.auto = auto;
    }

    public String getXsy() {
        return xsy;
    }

    public void setXsy(String xsy) {
        this.xsy = xsy;
    }

    public String getDjkh() {
        return djkh;
    }

    public void setDjkh(String djkh) {
        this.djkh = djkh;
    }

    public String getDjsign() {
        return djsign;
    }

    public void setDjsign(String djsign) {
        this.djsign = djsign;
    }

    public String getClassname() {
        return classname;
    }

    public void setClassname(String classname) {
        this.classname = classname;
    }

    public String getIscy() {
        return iscy;
    }

    public void setIscy(String iscy) {
        this.iscy = iscy;
    }

    public String getBsign() {
        return bsign;
    }

    public void setBsign(String bsign) {
        this.bsign = bsign;
    }

    public String getFx() {
        return fx;
    }

    public void setFx(String fx) {
        this.fx = fx;
    }

    public String getDjlx() {
        return djlx;
    }

    public void setDjlx(String djlx) {
        this.djlx = djlx;
    }

    public Long getIsup() {
        return isup;
    }

    public void setIsup(Long isup) {
        this.isup = isup;
    }

    public BigDecimal getYongjin() {
        return yongjin;
    }

    public void setYongjin(BigDecimal yongjin) {
        this.yongjin = yongjin;
    }

    public String getCzpc() {
        return czpc;
    }

    public void setCzpc(String czpc) {
        this.czpc = czpc;
    }

    public Long getCxflag() {
        return cxflag;
    }

    public void setCxflag(Long cxflag) {
        this.cxflag = cxflag;
    }

    public String getPmemo() {
        return pmemo;
    }

    public void setPmemo(String pmemo) {
        this.pmemo = pmemo;
    }

    public String getCzbillno() {
        return czbillno;
    }

    public void setCzbillno(String czbillno) {
        this.czbillno = czbillno;
    }

    public String getDjqbz() {
        return djqbz;
    }

    public void setDjqbz(String djqbz) {
        this.djqbz = djqbz;
    }

    public String getYsqmemo() {
        return ysqmemo;
    }

    public void setYsqmemo(String ysqmemo) {
        this.ysqmemo = ysqmemo;
    }

    public String getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(String transactionId) {
        this.transactionId = transactionId;
    }

    public String getOutTradeNo() {
        return outTradeNo;
    }

    public void setOutTradeNo(String outTradeNo) {
        this.outTradeNo = outTradeNo;
    }

    public String getGsname() {
        return gsname;
    }

    public void setGsname(String gsname) {
        this.gsname = gsname;
    }

    public Instant getRz() {
        return rz;
    }

    public void setRz(Instant rz) {
        this.rz = rz;
    }

    public Instant getGz() {
        return gz;
    }

    public void setGz(Instant gz) {
        this.gz = gz;
    }

    public Long getTs() {
        return ts;
    }

    public void setTs(Long ts) {
        this.ts = ts;
    }

    public String getKy() {
        return ky;
    }

    public void setKy(String ky) {
        this.ky = ky;
    }

    public String getXy() {
        return xy;
    }

    public void setXy(String xy) {
        this.xy = xy;
    }

    public String getRoomtype() {
        return roomtype;
    }

    public void setRoomtype(String roomtype) {
        this.roomtype = roomtype;
    }

    public Long getBkid() {
        return bkid;
    }

    public void setBkid(Long bkid) {
        this.bkid = bkid;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof AccountsDTO)) {
            return false;
        }

        AccountsDTO accountsDTO = (AccountsDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, accountsDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "AccountsDTO{" +
            "account='" + getAccount() + "'" +
            ", consumetime='" + getConsumetime() + "'" +
            ", hoteltime='" + getHoteltime() + "'" +
            ", feenum=" + getFeenum() +
            ", money=" + getMoney() +
            ", memo='" + getMemo() + "'" +
            ", empn='" + getEmpn() + "'" +
            ", imprest=" + getImprest() +
            ", propertiy='" + getPropertiy() + "'" +
            ", earntypen=" + getEarntypen() +
            ", payment=" + getPayment() +
            ", roomn='" + getRoomn() + "'" +
            ", id=" + getId() +
            ", ulogogram='" + getUlogogram() + "'" +
            ", lk=" + getLk() +
            ", acc='" + getAcc() + "'" +
            ", jzSign=" + getJzSign() +
            ", jzflag=" + getJzflag() +
            ", sign='" + getSign() + "'" +
            ", bs=" + getBs() +
            ", jzhotel='" + getJzhotel() + "'" +
            ", jzempn='" + getJzempn() + "'" +
            ", jztime='" + getJztime() + "'" +
            ", chonghong=" + getChonghong() +
            ", billno='" + getBillno() + "'" +
            ", printcount=" + getPrintcount() +
            ", vipjf=" + getVipjf() +
            ", hykh='" + getHykh() + "'" +
            ", sl=" + getSl() +
            ", sgdjh='" + getSgdjh() + "'" +
            ", hoteldm='" + getHoteldm() + "'" +
            ", isnew=" + getIsnew() +
            ", guestId=" + getGuestId() +
            ", yhkh='" + getYhkh() + "'" +
            ", djq='" + getDjq() + "'" +
            ", ysje=" + getYsje() +
            ", bj='" + getBj() + "'" +
            ", bjempn='" + getBjempn() + "'" +
            ", bjtime='" + getBjtime() + "'" +
            ", paper2='" + getPaper2() + "'" +
            ", bc='" + getBc() + "'" +
            ", auto='" + getAuto() + "'" +
            ", xsy='" + getXsy() + "'" +
            ", djkh='" + getDjkh() + "'" +
            ", djsign='" + getDjsign() + "'" +
            ", classname='" + getClassname() + "'" +
            ", iscy='" + getIscy() + "'" +
            ", bsign='" + getBsign() + "'" +
            ", fx='" + getFx() + "'" +
            ", djlx='" + getDjlx() + "'" +
            ", isup=" + getIsup() +
            ", yongjin=" + getYongjin() +
            ", czpc='" + getCzpc() + "'" +
            ", cxflag=" + getCxflag() +
            ", pmemo='" + getPmemo() + "'" +
            ", czbillno='" + getCzbillno() + "'" +
            ", djqbz='" + getDjqbz() + "'" +
            ", ysqmemo='" + getYsqmemo() + "'" +
            ", transactionId='" + getTransactionId() + "'" +
            ", outTradeNo='" + getOutTradeNo() + "'" +
            ", gsname='" + getGsname() + "'" +
            ", rz='" + getRz() + "'" +
            ", gz='" + getGz() + "'" +
            ", ts=" + getTs() +
            ", ky='" + getKy() + "'" +
            ", xy='" + getXy() + "'" +
            ", roomtype='" + getRoomtype() + "'" +
            ", bkid=" + getBkid() +
            "}";
    }
}
