package ihotel.app.domain;

import java.io.Serializable;
import javax.persistence.*;
import javax.validation.constraints.*;
import org.springframework.data.elasticsearch.annotations.FieldType;

/**
 * A BookYst.
 */
@Entity
@Table(name = "book_yst")
@org.springframework.data.elasticsearch.annotations.Document(indexName = "bookyst")
public class BookYst implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Size(max = 100)
    @Column(name = "roomcode", length = 100)
    private String roomcode;

    @NotNull
    @Size(max = 100)
    @Column(name = "roomname", length = 100, nullable = false)
    private String roomname;

    @Size(max = 100)
    @Column(name = "roomnum", length = 100)
    private String roomnum;

    @Size(max = 100)
    @Column(name = "roomseparatenum", length = 100)
    private String roomseparatenum;

    @Size(max = 200)
    @Column(name = "bedids", length = 200)
    private String bedids;

    @Size(max = 100)
    @Column(name = "bedsimpledesc", length = 100)
    private String bedsimpledesc;

    @Size(max = 200)
    @Column(name = "bednum", length = 200)
    private String bednum;

    @Size(max = 200)
    @Column(name = "roomsize", length = 200)
    private String roomsize;

    @Size(max = 200)
    @Column(name = "roomfloor", length = 200)
    private String roomfloor;

    @Size(max = 200)
    @Column(name = "netservice", length = 200)
    private String netservice;

    @Size(max = 200)
    @Column(name = "nettype", length = 200)
    private String nettype;

    @Size(max = 200)
    @Column(name = "iswindow", length = 200)
    private String iswindow;

    @Size(max = 200)
    @Column(name = "remark", length = 200)
    private String remark;

    @Size(max = 200)
    @Column(name = "sortid", length = 200)
    private String sortid;

    @Size(max = 200)
    @Column(name = "roomstate", length = 200)
    private String roomstate;

    @Size(max = 100)
    @Column(name = "source", length = 100)
    private String source;

    @Size(max = 100)
    @Column(name = "roomamenities", length = 100)
    private String roomamenities;

    @Size(max = 200)
    @Column(name = "maxguestnums", length = 200)
    private String maxguestnums;

    @Size(max = 100)
    @Column(name = "roomdistribution", length = 100)
    private String roomdistribution;

    @Size(max = 100)
    @Column(name = "conditionbeforedays", length = 100)
    private String conditionbeforedays;

    @Size(max = 100)
    @Column(name = "conditionleastdays", length = 100)
    private String conditionleastdays;

    @Size(max = 100)
    @Column(name = "conditionleastroomnum", length = 100)
    private String conditionleastroomnum;

    @Size(max = 100)
    @Column(name = "paymentype", length = 100)
    private String paymentype;

    @Size(max = 200)
    @Column(name = "rateplandesc", length = 200)
    private String rateplandesc;

    @Size(max = 100)
    @Column(name = "rateplanname", length = 100)
    private String rateplanname;

    @Size(max = 100)
    @Column(name = "rateplanstate", length = 100)
    private String rateplanstate;

    @Size(max = 100)
    @Column(name = "addvaluebednum", length = 100)
    private String addvaluebednum;

    @Size(max = 200)
    @Column(name = "addvaluebedprice", length = 200)
    private String addvaluebedprice;

    @Size(max = 200)
    @Column(name = "addvaluebreakfastnum", length = 200)
    private String addvaluebreakfastnum;

    @Size(max = 200)
    @Column(name = "addvaluebreakfastprice", length = 200)
    private String addvaluebreakfastprice;

    @Size(max = 200)
    @Column(name = "baseprice", length = 200)
    private String baseprice;

    @Size(max = 200)
    @Column(name = "saleprice", length = 200)
    private String saleprice;

    @Size(max = 200)
    @Column(name = "marketprice", length = 200)
    private String marketprice;

    @Size(max = 100)
    @Column(name = "hotelproductservice", length = 100)
    private String hotelproductservice;

    @Size(max = 500)
    @Column(name = "hotelproductservicedesc", length = 500)
    private String hotelproductservicedesc;

    @Size(max = 50)
    @Column(name = "hotelproductid", length = 50)
    private String hotelproductid;

    @Size(max = 50)
    @Column(name = "roomid", length = 50)
    private String roomid;

    @Size(max = 50)
    @Column(name = "hotelid", length = 50)
    private String hotelid;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public BookYst id(Long id) {
        this.id = id;
        return this;
    }

    public String getRoomcode() {
        return this.roomcode;
    }

    public BookYst roomcode(String roomcode) {
        this.roomcode = roomcode;
        return this;
    }

    public void setRoomcode(String roomcode) {
        this.roomcode = roomcode;
    }

    public String getRoomname() {
        return this.roomname;
    }

    public BookYst roomname(String roomname) {
        this.roomname = roomname;
        return this;
    }

    public void setRoomname(String roomname) {
        this.roomname = roomname;
    }

    public String getRoomnum() {
        return this.roomnum;
    }

    public BookYst roomnum(String roomnum) {
        this.roomnum = roomnum;
        return this;
    }

    public void setRoomnum(String roomnum) {
        this.roomnum = roomnum;
    }

    public String getRoomseparatenum() {
        return this.roomseparatenum;
    }

    public BookYst roomseparatenum(String roomseparatenum) {
        this.roomseparatenum = roomseparatenum;
        return this;
    }

    public void setRoomseparatenum(String roomseparatenum) {
        this.roomseparatenum = roomseparatenum;
    }

    public String getBedids() {
        return this.bedids;
    }

    public BookYst bedids(String bedids) {
        this.bedids = bedids;
        return this;
    }

    public void setBedids(String bedids) {
        this.bedids = bedids;
    }

    public String getBedsimpledesc() {
        return this.bedsimpledesc;
    }

    public BookYst bedsimpledesc(String bedsimpledesc) {
        this.bedsimpledesc = bedsimpledesc;
        return this;
    }

    public void setBedsimpledesc(String bedsimpledesc) {
        this.bedsimpledesc = bedsimpledesc;
    }

    public String getBednum() {
        return this.bednum;
    }

    public BookYst bednum(String bednum) {
        this.bednum = bednum;
        return this;
    }

    public void setBednum(String bednum) {
        this.bednum = bednum;
    }

    public String getRoomsize() {
        return this.roomsize;
    }

    public BookYst roomsize(String roomsize) {
        this.roomsize = roomsize;
        return this;
    }

    public void setRoomsize(String roomsize) {
        this.roomsize = roomsize;
    }

    public String getRoomfloor() {
        return this.roomfloor;
    }

    public BookYst roomfloor(String roomfloor) {
        this.roomfloor = roomfloor;
        return this;
    }

    public void setRoomfloor(String roomfloor) {
        this.roomfloor = roomfloor;
    }

    public String getNetservice() {
        return this.netservice;
    }

    public BookYst netservice(String netservice) {
        this.netservice = netservice;
        return this;
    }

    public void setNetservice(String netservice) {
        this.netservice = netservice;
    }

    public String getNettype() {
        return this.nettype;
    }

    public BookYst nettype(String nettype) {
        this.nettype = nettype;
        return this;
    }

    public void setNettype(String nettype) {
        this.nettype = nettype;
    }

    public String getIswindow() {
        return this.iswindow;
    }

    public BookYst iswindow(String iswindow) {
        this.iswindow = iswindow;
        return this;
    }

    public void setIswindow(String iswindow) {
        this.iswindow = iswindow;
    }

    public String getRemark() {
        return this.remark;
    }

    public BookYst remark(String remark) {
        this.remark = remark;
        return this;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getSortid() {
        return this.sortid;
    }

    public BookYst sortid(String sortid) {
        this.sortid = sortid;
        return this;
    }

    public void setSortid(String sortid) {
        this.sortid = sortid;
    }

    public String getRoomstate() {
        return this.roomstate;
    }

    public BookYst roomstate(String roomstate) {
        this.roomstate = roomstate;
        return this;
    }

    public void setRoomstate(String roomstate) {
        this.roomstate = roomstate;
    }

    public String getSource() {
        return this.source;
    }

    public BookYst source(String source) {
        this.source = source;
        return this;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String getRoomamenities() {
        return this.roomamenities;
    }

    public BookYst roomamenities(String roomamenities) {
        this.roomamenities = roomamenities;
        return this;
    }

    public void setRoomamenities(String roomamenities) {
        this.roomamenities = roomamenities;
    }

    public String getMaxguestnums() {
        return this.maxguestnums;
    }

    public BookYst maxguestnums(String maxguestnums) {
        this.maxguestnums = maxguestnums;
        return this;
    }

    public void setMaxguestnums(String maxguestnums) {
        this.maxguestnums = maxguestnums;
    }

    public String getRoomdistribution() {
        return this.roomdistribution;
    }

    public BookYst roomdistribution(String roomdistribution) {
        this.roomdistribution = roomdistribution;
        return this;
    }

    public void setRoomdistribution(String roomdistribution) {
        this.roomdistribution = roomdistribution;
    }

    public String getConditionbeforedays() {
        return this.conditionbeforedays;
    }

    public BookYst conditionbeforedays(String conditionbeforedays) {
        this.conditionbeforedays = conditionbeforedays;
        return this;
    }

    public void setConditionbeforedays(String conditionbeforedays) {
        this.conditionbeforedays = conditionbeforedays;
    }

    public String getConditionleastdays() {
        return this.conditionleastdays;
    }

    public BookYst conditionleastdays(String conditionleastdays) {
        this.conditionleastdays = conditionleastdays;
        return this;
    }

    public void setConditionleastdays(String conditionleastdays) {
        this.conditionleastdays = conditionleastdays;
    }

    public String getConditionleastroomnum() {
        return this.conditionleastroomnum;
    }

    public BookYst conditionleastroomnum(String conditionleastroomnum) {
        this.conditionleastroomnum = conditionleastroomnum;
        return this;
    }

    public void setConditionleastroomnum(String conditionleastroomnum) {
        this.conditionleastroomnum = conditionleastroomnum;
    }

    public String getPaymentype() {
        return this.paymentype;
    }

    public BookYst paymentype(String paymentype) {
        this.paymentype = paymentype;
        return this;
    }

    public void setPaymentype(String paymentype) {
        this.paymentype = paymentype;
    }

    public String getRateplandesc() {
        return this.rateplandesc;
    }

    public BookYst rateplandesc(String rateplandesc) {
        this.rateplandesc = rateplandesc;
        return this;
    }

    public void setRateplandesc(String rateplandesc) {
        this.rateplandesc = rateplandesc;
    }

    public String getRateplanname() {
        return this.rateplanname;
    }

    public BookYst rateplanname(String rateplanname) {
        this.rateplanname = rateplanname;
        return this;
    }

    public void setRateplanname(String rateplanname) {
        this.rateplanname = rateplanname;
    }

    public String getRateplanstate() {
        return this.rateplanstate;
    }

    public BookYst rateplanstate(String rateplanstate) {
        this.rateplanstate = rateplanstate;
        return this;
    }

    public void setRateplanstate(String rateplanstate) {
        this.rateplanstate = rateplanstate;
    }

    public String getAddvaluebednum() {
        return this.addvaluebednum;
    }

    public BookYst addvaluebednum(String addvaluebednum) {
        this.addvaluebednum = addvaluebednum;
        return this;
    }

    public void setAddvaluebednum(String addvaluebednum) {
        this.addvaluebednum = addvaluebednum;
    }

    public String getAddvaluebedprice() {
        return this.addvaluebedprice;
    }

    public BookYst addvaluebedprice(String addvaluebedprice) {
        this.addvaluebedprice = addvaluebedprice;
        return this;
    }

    public void setAddvaluebedprice(String addvaluebedprice) {
        this.addvaluebedprice = addvaluebedprice;
    }

    public String getAddvaluebreakfastnum() {
        return this.addvaluebreakfastnum;
    }

    public BookYst addvaluebreakfastnum(String addvaluebreakfastnum) {
        this.addvaluebreakfastnum = addvaluebreakfastnum;
        return this;
    }

    public void setAddvaluebreakfastnum(String addvaluebreakfastnum) {
        this.addvaluebreakfastnum = addvaluebreakfastnum;
    }

    public String getAddvaluebreakfastprice() {
        return this.addvaluebreakfastprice;
    }

    public BookYst addvaluebreakfastprice(String addvaluebreakfastprice) {
        this.addvaluebreakfastprice = addvaluebreakfastprice;
        return this;
    }

    public void setAddvaluebreakfastprice(String addvaluebreakfastprice) {
        this.addvaluebreakfastprice = addvaluebreakfastprice;
    }

    public String getBaseprice() {
        return this.baseprice;
    }

    public BookYst baseprice(String baseprice) {
        this.baseprice = baseprice;
        return this;
    }

    public void setBaseprice(String baseprice) {
        this.baseprice = baseprice;
    }

    public String getSaleprice() {
        return this.saleprice;
    }

    public BookYst saleprice(String saleprice) {
        this.saleprice = saleprice;
        return this;
    }

    public void setSaleprice(String saleprice) {
        this.saleprice = saleprice;
    }

    public String getMarketprice() {
        return this.marketprice;
    }

    public BookYst marketprice(String marketprice) {
        this.marketprice = marketprice;
        return this;
    }

    public void setMarketprice(String marketprice) {
        this.marketprice = marketprice;
    }

    public String getHotelproductservice() {
        return this.hotelproductservice;
    }

    public BookYst hotelproductservice(String hotelproductservice) {
        this.hotelproductservice = hotelproductservice;
        return this;
    }

    public void setHotelproductservice(String hotelproductservice) {
        this.hotelproductservice = hotelproductservice;
    }

    public String getHotelproductservicedesc() {
        return this.hotelproductservicedesc;
    }

    public BookYst hotelproductservicedesc(String hotelproductservicedesc) {
        this.hotelproductservicedesc = hotelproductservicedesc;
        return this;
    }

    public void setHotelproductservicedesc(String hotelproductservicedesc) {
        this.hotelproductservicedesc = hotelproductservicedesc;
    }

    public String getHotelproductid() {
        return this.hotelproductid;
    }

    public BookYst hotelproductid(String hotelproductid) {
        this.hotelproductid = hotelproductid;
        return this;
    }

    public void setHotelproductid(String hotelproductid) {
        this.hotelproductid = hotelproductid;
    }

    public String getRoomid() {
        return this.roomid;
    }

    public BookYst roomid(String roomid) {
        this.roomid = roomid;
        return this;
    }

    public void setRoomid(String roomid) {
        this.roomid = roomid;
    }

    public String getHotelid() {
        return this.hotelid;
    }

    public BookYst hotelid(String hotelid) {
        this.hotelid = hotelid;
        return this;
    }

    public void setHotelid(String hotelid) {
        this.hotelid = hotelid;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof BookYst)) {
            return false;
        }
        return id != null && id.equals(((BookYst) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "BookYst{" +
            "id=" + getId() +
            ", roomcode='" + getRoomcode() + "'" +
            ", roomname='" + getRoomname() + "'" +
            ", roomnum='" + getRoomnum() + "'" +
            ", roomseparatenum='" + getRoomseparatenum() + "'" +
            ", bedids='" + getBedids() + "'" +
            ", bedsimpledesc='" + getBedsimpledesc() + "'" +
            ", bednum='" + getBednum() + "'" +
            ", roomsize='" + getRoomsize() + "'" +
            ", roomfloor='" + getRoomfloor() + "'" +
            ", netservice='" + getNetservice() + "'" +
            ", nettype='" + getNettype() + "'" +
            ", iswindow='" + getIswindow() + "'" +
            ", remark='" + getRemark() + "'" +
            ", sortid='" + getSortid() + "'" +
            ", roomstate='" + getRoomstate() + "'" +
            ", source='" + getSource() + "'" +
            ", roomamenities='" + getRoomamenities() + "'" +
            ", maxguestnums='" + getMaxguestnums() + "'" +
            ", roomdistribution='" + getRoomdistribution() + "'" +
            ", conditionbeforedays='" + getConditionbeforedays() + "'" +
            ", conditionleastdays='" + getConditionleastdays() + "'" +
            ", conditionleastroomnum='" + getConditionleastroomnum() + "'" +
            ", paymentype='" + getPaymentype() + "'" +
            ", rateplandesc='" + getRateplandesc() + "'" +
            ", rateplanname='" + getRateplanname() + "'" +
            ", rateplanstate='" + getRateplanstate() + "'" +
            ", addvaluebednum='" + getAddvaluebednum() + "'" +
            ", addvaluebedprice='" + getAddvaluebedprice() + "'" +
            ", addvaluebreakfastnum='" + getAddvaluebreakfastnum() + "'" +
            ", addvaluebreakfastprice='" + getAddvaluebreakfastprice() + "'" +
            ", baseprice='" + getBaseprice() + "'" +
            ", saleprice='" + getSaleprice() + "'" +
            ", marketprice='" + getMarketprice() + "'" +
            ", hotelproductservice='" + getHotelproductservice() + "'" +
            ", hotelproductservicedesc='" + getHotelproductservicedesc() + "'" +
            ", hotelproductid='" + getHotelproductid() + "'" +
            ", roomid='" + getRoomid() + "'" +
            ", hotelid='" + getHotelid() + "'" +
            "}";
    }
}
