package ihotel.app.domain;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.Instant;
import javax.persistence.*;
import javax.validation.constraints.*;
import org.springframework.data.elasticsearch.annotations.FieldType;

/**
 * A Accounts.
 */
@Entity
@Table(name = "accounts")
@org.springframework.data.elasticsearch.annotations.Document(indexName = "accounts")
public class Accounts implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Size(max = 30)
    @Column(name = "account", length = 30, nullable = false)
    private String account;

    @NotNull
    @Column(name = "consumetime", nullable = false)
    private Instant consumetime;

    @Column(name = "hoteltime")
    private Instant hoteltime;

    @Column(name = "feenum")
    private Long feenum;

    @Column(name = "money", precision = 21, scale = 2)
    private BigDecimal money;

    @Size(max = 100)
    @Column(name = "memo", length = 100)
    private String memo;

    @Size(max = 10)
    @Column(name = "empn", length = 10)
    private String empn;

    @Column(name = "imprest", precision = 21, scale = 2)
    private BigDecimal imprest;

    @Size(max = 100)
    @Column(name = "propertiy", length = 100)
    private String propertiy;

    @Column(name = "earntypen")
    private Long earntypen;

    @Column(name = "payment")
    private Long payment;

    @Size(max = 10)
    @Column(name = "roomn", length = 10)
    private String roomn;

    @Size(max = 30)
    @Column(name = "ulogogram", length = 30)
    private String ulogogram;

    @Column(name = "lk")
    private Long lk;

    @Size(max = 50)
    @Column(name = "acc", length = 50)
    private String acc;

    @Column(name = "jz_sign")
    private Long jzSign;

    @Column(name = "jzflag")
    private Long jzflag;

    @Size(max = 4)
    @Column(name = "sign", length = 4)
    private String sign;

    @Column(name = "bs")
    private Long bs;

    @Column(name = "jzhotel")
    private Instant jzhotel;

    @Size(max = 50)
    @Column(name = "jzempn", length = 50)
    private String jzempn;

    @Column(name = "jztime")
    private Instant jztime;

    @Column(name = "chonghong")
    private Long chonghong;

    @Size(max = 10)
    @Column(name = "billno", length = 10)
    private String billno;

    @Column(name = "printcount")
    private Long printcount;

    @Column(name = "vipjf", precision = 21, scale = 2)
    private BigDecimal vipjf;

    @Size(max = 20)
    @Column(name = "hykh", length = 20)
    private String hykh;

    @Column(name = "sl", precision = 21, scale = 2)
    private BigDecimal sl;

    @Size(max = 20)
    @Column(name = "sgdjh", length = 20)
    private String sgdjh;

    @Size(max = 20)
    @Column(name = "hoteldm", length = 20)
    private String hoteldm;

    @Column(name = "isnew")
    private Long isnew;

    @Column(name = "guest_id")
    private Double guestId;

    @Size(max = 50)
    @Column(name = "yhkh", length = 50)
    private String yhkh;

    @Size(max = 100)
    @Column(name = "djq", length = 100)
    private String djq;

    @Column(name = "ysje", precision = 21, scale = 2)
    private BigDecimal ysje;

    @Size(max = 2)
    @Column(name = "bj", length = 2)
    private String bj;

    @Size(max = 50)
    @Column(name = "bjempn", length = 50)
    private String bjempn;

    @Column(name = "bjtime")
    private Instant bjtime;

    @Size(max = 50)
    @Column(name = "paper_2", length = 50)
    private String paper2;

    @Size(max = 20)
    @Column(name = "bc", length = 20)
    private String bc;

    @Size(max = 2)
    @Column(name = "auto", length = 2)
    private String auto;

    @Size(max = 50)
    @Column(name = "xsy", length = 50)
    private String xsy;

    @Size(max = 50)
    @Column(name = "djkh", length = 50)
    private String djkh;

    @Size(max = 2)
    @Column(name = "djsign", length = 2)
    private String djsign;

    @Size(max = 50)
    @Column(name = "classname", length = 50)
    private String classname;

    @Size(max = 20)
    @Column(name = "iscy", length = 20)
    private String iscy;

    @Size(max = 2)
    @Column(name = "bsign", length = 2)
    private String bsign;

    @Size(max = 2)
    @Column(name = "fx", length = 2)
    private String fx;

    @Size(max = 50)
    @Column(name = "djlx", length = 50)
    private String djlx;

    @Column(name = "isup")
    private Long isup;

    @Column(name = "yongjin", precision = 21, scale = 2)
    private BigDecimal yongjin;

    @Size(max = 50)
    @Column(name = "czpc", length = 50)
    private String czpc;

    @Column(name = "cxflag")
    private Long cxflag;

    @Size(max = 200)
    @Column(name = "pmemo", length = 200)
    private String pmemo;

    @Size(max = 50)
    @Column(name = "czbillno", length = 50)
    private String czbillno;

    @Size(max = 50)
    @Column(name = "djqbz", length = 50)
    private String djqbz;

    @Size(max = 100)
    @Column(name = "ysqmemo", length = 100)
    private String ysqmemo;

    @Size(max = 100)
    @Column(name = "transaction_id", length = 100)
    private String transactionId;

    @Size(max = 100)
    @Column(name = "out_trade_no", length = 100)
    private String outTradeNo;

    @Size(max = 200)
    @Column(name = "gsname", length = 200)
    private String gsname;

    @Column(name = "rz")
    private Instant rz;

    @Column(name = "gz")
    private Instant gz;

    @Column(name = "ts")
    private Long ts;

    @Size(max = 50)
    @Column(name = "ky", length = 50)
    private String ky;

    @Size(max = 50)
    @Column(name = "xy", length = 50)
    private String xy;

    @Size(max = 50)
    @Column(name = "roomtype", length = 50)
    private String roomtype;

    @Column(name = "bkid")
    private Long bkid;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Accounts id(Long id) {
        this.id = id;
        return this;
    }

    public String getAccount() {
        return this.account;
    }

    public Accounts account(String account) {
        this.account = account;
        return this;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public Instant getConsumetime() {
        return this.consumetime;
    }

    public Accounts consumetime(Instant consumetime) {
        this.consumetime = consumetime;
        return this;
    }

    public void setConsumetime(Instant consumetime) {
        this.consumetime = consumetime;
    }

    public Instant getHoteltime() {
        return this.hoteltime;
    }

    public Accounts hoteltime(Instant hoteltime) {
        this.hoteltime = hoteltime;
        return this;
    }

    public void setHoteltime(Instant hoteltime) {
        this.hoteltime = hoteltime;
    }

    public Long getFeenum() {
        return this.feenum;
    }

    public Accounts feenum(Long feenum) {
        this.feenum = feenum;
        return this;
    }

    public void setFeenum(Long feenum) {
        this.feenum = feenum;
    }

    public BigDecimal getMoney() {
        return this.money;
    }

    public Accounts money(BigDecimal money) {
        this.money = money;
        return this;
    }

    public void setMoney(BigDecimal money) {
        this.money = money;
    }

    public String getMemo() {
        return this.memo;
    }

    public Accounts memo(String memo) {
        this.memo = memo;
        return this;
    }

    public void setMemo(String memo) {
        this.memo = memo;
    }

    public String getEmpn() {
        return this.empn;
    }

    public Accounts empn(String empn) {
        this.empn = empn;
        return this;
    }

    public void setEmpn(String empn) {
        this.empn = empn;
    }

    public BigDecimal getImprest() {
        return this.imprest;
    }

    public Accounts imprest(BigDecimal imprest) {
        this.imprest = imprest;
        return this;
    }

    public void setImprest(BigDecimal imprest) {
        this.imprest = imprest;
    }

    public String getPropertiy() {
        return this.propertiy;
    }

    public Accounts propertiy(String propertiy) {
        this.propertiy = propertiy;
        return this;
    }

    public void setPropertiy(String propertiy) {
        this.propertiy = propertiy;
    }

    public Long getEarntypen() {
        return this.earntypen;
    }

    public Accounts earntypen(Long earntypen) {
        this.earntypen = earntypen;
        return this;
    }

    public void setEarntypen(Long earntypen) {
        this.earntypen = earntypen;
    }

    public Long getPayment() {
        return this.payment;
    }

    public Accounts payment(Long payment) {
        this.payment = payment;
        return this;
    }

    public void setPayment(Long payment) {
        this.payment = payment;
    }

    public String getRoomn() {
        return this.roomn;
    }

    public Accounts roomn(String roomn) {
        this.roomn = roomn;
        return this;
    }

    public void setRoomn(String roomn) {
        this.roomn = roomn;
    }

    public String getUlogogram() {
        return this.ulogogram;
    }

    public Accounts ulogogram(String ulogogram) {
        this.ulogogram = ulogogram;
        return this;
    }

    public void setUlogogram(String ulogogram) {
        this.ulogogram = ulogogram;
    }

    public Long getLk() {
        return this.lk;
    }

    public Accounts lk(Long lk) {
        this.lk = lk;
        return this;
    }

    public void setLk(Long lk) {
        this.lk = lk;
    }

    public String getAcc() {
        return this.acc;
    }

    public Accounts acc(String acc) {
        this.acc = acc;
        return this;
    }

    public void setAcc(String acc) {
        this.acc = acc;
    }

    public Long getJzSign() {
        return this.jzSign;
    }

    public Accounts jzSign(Long jzSign) {
        this.jzSign = jzSign;
        return this;
    }

    public void setJzSign(Long jzSign) {
        this.jzSign = jzSign;
    }

    public Long getJzflag() {
        return this.jzflag;
    }

    public Accounts jzflag(Long jzflag) {
        this.jzflag = jzflag;
        return this;
    }

    public void setJzflag(Long jzflag) {
        this.jzflag = jzflag;
    }

    public String getSign() {
        return this.sign;
    }

    public Accounts sign(String sign) {
        this.sign = sign;
        return this;
    }

    public void setSign(String sign) {
        this.sign = sign;
    }

    public Long getBs() {
        return this.bs;
    }

    public Accounts bs(Long bs) {
        this.bs = bs;
        return this;
    }

    public void setBs(Long bs) {
        this.bs = bs;
    }

    public Instant getJzhotel() {
        return this.jzhotel;
    }

    public Accounts jzhotel(Instant jzhotel) {
        this.jzhotel = jzhotel;
        return this;
    }

    public void setJzhotel(Instant jzhotel) {
        this.jzhotel = jzhotel;
    }

    public String getJzempn() {
        return this.jzempn;
    }

    public Accounts jzempn(String jzempn) {
        this.jzempn = jzempn;
        return this;
    }

    public void setJzempn(String jzempn) {
        this.jzempn = jzempn;
    }

    public Instant getJztime() {
        return this.jztime;
    }

    public Accounts jztime(Instant jztime) {
        this.jztime = jztime;
        return this;
    }

    public void setJztime(Instant jztime) {
        this.jztime = jztime;
    }

    public Long getChonghong() {
        return this.chonghong;
    }

    public Accounts chonghong(Long chonghong) {
        this.chonghong = chonghong;
        return this;
    }

    public void setChonghong(Long chonghong) {
        this.chonghong = chonghong;
    }

    public String getBillno() {
        return this.billno;
    }

    public Accounts billno(String billno) {
        this.billno = billno;
        return this;
    }

    public void setBillno(String billno) {
        this.billno = billno;
    }

    public Long getPrintcount() {
        return this.printcount;
    }

    public Accounts printcount(Long printcount) {
        this.printcount = printcount;
        return this;
    }

    public void setPrintcount(Long printcount) {
        this.printcount = printcount;
    }

    public BigDecimal getVipjf() {
        return this.vipjf;
    }

    public Accounts vipjf(BigDecimal vipjf) {
        this.vipjf = vipjf;
        return this;
    }

    public void setVipjf(BigDecimal vipjf) {
        this.vipjf = vipjf;
    }

    public String getHykh() {
        return this.hykh;
    }

    public Accounts hykh(String hykh) {
        this.hykh = hykh;
        return this;
    }

    public void setHykh(String hykh) {
        this.hykh = hykh;
    }

    public BigDecimal getSl() {
        return this.sl;
    }

    public Accounts sl(BigDecimal sl) {
        this.sl = sl;
        return this;
    }

    public void setSl(BigDecimal sl) {
        this.sl = sl;
    }

    public String getSgdjh() {
        return this.sgdjh;
    }

    public Accounts sgdjh(String sgdjh) {
        this.sgdjh = sgdjh;
        return this;
    }

    public void setSgdjh(String sgdjh) {
        this.sgdjh = sgdjh;
    }

    public String getHoteldm() {
        return this.hoteldm;
    }

    public Accounts hoteldm(String hoteldm) {
        this.hoteldm = hoteldm;
        return this;
    }

    public void setHoteldm(String hoteldm) {
        this.hoteldm = hoteldm;
    }

    public Long getIsnew() {
        return this.isnew;
    }

    public Accounts isnew(Long isnew) {
        this.isnew = isnew;
        return this;
    }

    public void setIsnew(Long isnew) {
        this.isnew = isnew;
    }

    public Double getGuestId() {
        return this.guestId;
    }

    public Accounts guestId(Double guestId) {
        this.guestId = guestId;
        return this;
    }

    public void setGuestId(Double guestId) {
        this.guestId = guestId;
    }

    public String getYhkh() {
        return this.yhkh;
    }

    public Accounts yhkh(String yhkh) {
        this.yhkh = yhkh;
        return this;
    }

    public void setYhkh(String yhkh) {
        this.yhkh = yhkh;
    }

    public String getDjq() {
        return this.djq;
    }

    public Accounts djq(String djq) {
        this.djq = djq;
        return this;
    }

    public void setDjq(String djq) {
        this.djq = djq;
    }

    public BigDecimal getYsje() {
        return this.ysje;
    }

    public Accounts ysje(BigDecimal ysje) {
        this.ysje = ysje;
        return this;
    }

    public void setYsje(BigDecimal ysje) {
        this.ysje = ysje;
    }

    public String getBj() {
        return this.bj;
    }

    public Accounts bj(String bj) {
        this.bj = bj;
        return this;
    }

    public void setBj(String bj) {
        this.bj = bj;
    }

    public String getBjempn() {
        return this.bjempn;
    }

    public Accounts bjempn(String bjempn) {
        this.bjempn = bjempn;
        return this;
    }

    public void setBjempn(String bjempn) {
        this.bjempn = bjempn;
    }

    public Instant getBjtime() {
        return this.bjtime;
    }

    public Accounts bjtime(Instant bjtime) {
        this.bjtime = bjtime;
        return this;
    }

    public void setBjtime(Instant bjtime) {
        this.bjtime = bjtime;
    }

    public String getPaper2() {
        return this.paper2;
    }

    public Accounts paper2(String paper2) {
        this.paper2 = paper2;
        return this;
    }

    public void setPaper2(String paper2) {
        this.paper2 = paper2;
    }

    public String getBc() {
        return this.bc;
    }

    public Accounts bc(String bc) {
        this.bc = bc;
        return this;
    }

    public void setBc(String bc) {
        this.bc = bc;
    }

    public String getAuto() {
        return this.auto;
    }

    public Accounts auto(String auto) {
        this.auto = auto;
        return this;
    }

    public void setAuto(String auto) {
        this.auto = auto;
    }

    public String getXsy() {
        return this.xsy;
    }

    public Accounts xsy(String xsy) {
        this.xsy = xsy;
        return this;
    }

    public void setXsy(String xsy) {
        this.xsy = xsy;
    }

    public String getDjkh() {
        return this.djkh;
    }

    public Accounts djkh(String djkh) {
        this.djkh = djkh;
        return this;
    }

    public void setDjkh(String djkh) {
        this.djkh = djkh;
    }

    public String getDjsign() {
        return this.djsign;
    }

    public Accounts djsign(String djsign) {
        this.djsign = djsign;
        return this;
    }

    public void setDjsign(String djsign) {
        this.djsign = djsign;
    }

    public String getClassname() {
        return this.classname;
    }

    public Accounts classname(String classname) {
        this.classname = classname;
        return this;
    }

    public void setClassname(String classname) {
        this.classname = classname;
    }

    public String getIscy() {
        return this.iscy;
    }

    public Accounts iscy(String iscy) {
        this.iscy = iscy;
        return this;
    }

    public void setIscy(String iscy) {
        this.iscy = iscy;
    }

    public String getBsign() {
        return this.bsign;
    }

    public Accounts bsign(String bsign) {
        this.bsign = bsign;
        return this;
    }

    public void setBsign(String bsign) {
        this.bsign = bsign;
    }

    public String getFx() {
        return this.fx;
    }

    public Accounts fx(String fx) {
        this.fx = fx;
        return this;
    }

    public void setFx(String fx) {
        this.fx = fx;
    }

    public String getDjlx() {
        return this.djlx;
    }

    public Accounts djlx(String djlx) {
        this.djlx = djlx;
        return this;
    }

    public void setDjlx(String djlx) {
        this.djlx = djlx;
    }

    public Long getIsup() {
        return this.isup;
    }

    public Accounts isup(Long isup) {
        this.isup = isup;
        return this;
    }

    public void setIsup(Long isup) {
        this.isup = isup;
    }

    public BigDecimal getYongjin() {
        return this.yongjin;
    }

    public Accounts yongjin(BigDecimal yongjin) {
        this.yongjin = yongjin;
        return this;
    }

    public void setYongjin(BigDecimal yongjin) {
        this.yongjin = yongjin;
    }

    public String getCzpc() {
        return this.czpc;
    }

    public Accounts czpc(String czpc) {
        this.czpc = czpc;
        return this;
    }

    public void setCzpc(String czpc) {
        this.czpc = czpc;
    }

    public Long getCxflag() {
        return this.cxflag;
    }

    public Accounts cxflag(Long cxflag) {
        this.cxflag = cxflag;
        return this;
    }

    public void setCxflag(Long cxflag) {
        this.cxflag = cxflag;
    }

    public String getPmemo() {
        return this.pmemo;
    }

    public Accounts pmemo(String pmemo) {
        this.pmemo = pmemo;
        return this;
    }

    public void setPmemo(String pmemo) {
        this.pmemo = pmemo;
    }

    public String getCzbillno() {
        return this.czbillno;
    }

    public Accounts czbillno(String czbillno) {
        this.czbillno = czbillno;
        return this;
    }

    public void setCzbillno(String czbillno) {
        this.czbillno = czbillno;
    }

    public String getDjqbz() {
        return this.djqbz;
    }

    public Accounts djqbz(String djqbz) {
        this.djqbz = djqbz;
        return this;
    }

    public void setDjqbz(String djqbz) {
        this.djqbz = djqbz;
    }

    public String getYsqmemo() {
        return this.ysqmemo;
    }

    public Accounts ysqmemo(String ysqmemo) {
        this.ysqmemo = ysqmemo;
        return this;
    }

    public void setYsqmemo(String ysqmemo) {
        this.ysqmemo = ysqmemo;
    }

    public String getTransactionId() {
        return this.transactionId;
    }

    public Accounts transactionId(String transactionId) {
        this.transactionId = transactionId;
        return this;
    }

    public void setTransactionId(String transactionId) {
        this.transactionId = transactionId;
    }

    public String getOutTradeNo() {
        return this.outTradeNo;
    }

    public Accounts outTradeNo(String outTradeNo) {
        this.outTradeNo = outTradeNo;
        return this;
    }

    public void setOutTradeNo(String outTradeNo) {
        this.outTradeNo = outTradeNo;
    }

    public String getGsname() {
        return this.gsname;
    }

    public Accounts gsname(String gsname) {
        this.gsname = gsname;
        return this;
    }

    public void setGsname(String gsname) {
        this.gsname = gsname;
    }

    public Instant getRz() {
        return this.rz;
    }

    public Accounts rz(Instant rz) {
        this.rz = rz;
        return this;
    }

    public void setRz(Instant rz) {
        this.rz = rz;
    }

    public Instant getGz() {
        return this.gz;
    }

    public Accounts gz(Instant gz) {
        this.gz = gz;
        return this;
    }

    public void setGz(Instant gz) {
        this.gz = gz;
    }

    public Long getTs() {
        return this.ts;
    }

    public Accounts ts(Long ts) {
        this.ts = ts;
        return this;
    }

    public void setTs(Long ts) {
        this.ts = ts;
    }

    public String getKy() {
        return this.ky;
    }

    public Accounts ky(String ky) {
        this.ky = ky;
        return this;
    }

    public void setKy(String ky) {
        this.ky = ky;
    }

    public String getXy() {
        return this.xy;
    }

    public Accounts xy(String xy) {
        this.xy = xy;
        return this;
    }

    public void setXy(String xy) {
        this.xy = xy;
    }

    public String getRoomtype() {
        return this.roomtype;
    }

    public Accounts roomtype(String roomtype) {
        this.roomtype = roomtype;
        return this;
    }

    public void setRoomtype(String roomtype) {
        this.roomtype = roomtype;
    }

    public Long getBkid() {
        return this.bkid;
    }

    public Accounts bkid(Long bkid) {
        this.bkid = bkid;
        return this;
    }

    public void setBkid(Long bkid) {
        this.bkid = bkid;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Accounts)) {
            return false;
        }
        return id != null && id.equals(((Accounts) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Accounts{" +
            "id=" + getId() +
            ", account='" + getAccount() + "'" +
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
