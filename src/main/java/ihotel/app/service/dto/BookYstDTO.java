package ihotel.app.service.dto;

import java.io.Serializable;
import java.util.Objects;
import javax.validation.constraints.*;

/**
 * A DTO for the {@link ihotel.app.domain.BookYst} entity.
 */
public class BookYstDTO implements Serializable {

    private Long id;

    @Size(max = 100)
    private String roomcode;

    @NotNull
    @Size(max = 100)
    private String roomname;

    @Size(max = 100)
    private String roomnum;

    @Size(max = 100)
    private String roomseparatenum;

    @Size(max = 200)
    private String bedids;

    @Size(max = 100)
    private String bedsimpledesc;

    @Size(max = 200)
    private String bednum;

    @Size(max = 200)
    private String roomsize;

    @Size(max = 200)
    private String roomfloor;

    @Size(max = 200)
    private String netservice;

    @Size(max = 200)
    private String nettype;

    @Size(max = 200)
    private String iswindow;

    @Size(max = 200)
    private String remark;

    @Size(max = 200)
    private String sortid;

    @Size(max = 200)
    private String roomstate;

    @Size(max = 100)
    private String source;

    @Size(max = 100)
    private String roomamenities;

    @Size(max = 200)
    private String maxguestnums;

    @Size(max = 100)
    private String roomdistribution;

    @Size(max = 100)
    private String conditionbeforedays;

    @Size(max = 100)
    private String conditionleastdays;

    @Size(max = 100)
    private String conditionleastroomnum;

    @Size(max = 100)
    private String paymentype;

    @Size(max = 200)
    private String rateplandesc;

    @Size(max = 100)
    private String rateplanname;

    @Size(max = 100)
    private String rateplanstate;

    @Size(max = 100)
    private String addvaluebednum;

    @Size(max = 200)
    private String addvaluebedprice;

    @Size(max = 200)
    private String addvaluebreakfastnum;

    @Size(max = 200)
    private String addvaluebreakfastprice;

    @Size(max = 200)
    private String baseprice;

    @Size(max = 200)
    private String saleprice;

    @Size(max = 200)
    private String marketprice;

    @Size(max = 100)
    private String hotelproductservice;

    @Size(max = 500)
    private String hotelproductservicedesc;

    @Size(max = 50)
    private String hotelproductid;

    @Size(max = 50)
    private String roomid;

    @Size(max = 50)
    private String hotelid;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getRoomcode() {
        return roomcode;
    }

    public void setRoomcode(String roomcode) {
        this.roomcode = roomcode;
    }

    public String getRoomname() {
        return roomname;
    }

    public void setRoomname(String roomname) {
        this.roomname = roomname;
    }

    public String getRoomnum() {
        return roomnum;
    }

    public void setRoomnum(String roomnum) {
        this.roomnum = roomnum;
    }

    public String getRoomseparatenum() {
        return roomseparatenum;
    }

    public void setRoomseparatenum(String roomseparatenum) {
        this.roomseparatenum = roomseparatenum;
    }

    public String getBedids() {
        return bedids;
    }

    public void setBedids(String bedids) {
        this.bedids = bedids;
    }

    public String getBedsimpledesc() {
        return bedsimpledesc;
    }

    public void setBedsimpledesc(String bedsimpledesc) {
        this.bedsimpledesc = bedsimpledesc;
    }

    public String getBednum() {
        return bednum;
    }

    public void setBednum(String bednum) {
        this.bednum = bednum;
    }

    public String getRoomsize() {
        return roomsize;
    }

    public void setRoomsize(String roomsize) {
        this.roomsize = roomsize;
    }

    public String getRoomfloor() {
        return roomfloor;
    }

    public void setRoomfloor(String roomfloor) {
        this.roomfloor = roomfloor;
    }

    public String getNetservice() {
        return netservice;
    }

    public void setNetservice(String netservice) {
        this.netservice = netservice;
    }

    public String getNettype() {
        return nettype;
    }

    public void setNettype(String nettype) {
        this.nettype = nettype;
    }

    public String getIswindow() {
        return iswindow;
    }

    public void setIswindow(String iswindow) {
        this.iswindow = iswindow;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getSortid() {
        return sortid;
    }

    public void setSortid(String sortid) {
        this.sortid = sortid;
    }

    public String getRoomstate() {
        return roomstate;
    }

    public void setRoomstate(String roomstate) {
        this.roomstate = roomstate;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String getRoomamenities() {
        return roomamenities;
    }

    public void setRoomamenities(String roomamenities) {
        this.roomamenities = roomamenities;
    }

    public String getMaxguestnums() {
        return maxguestnums;
    }

    public void setMaxguestnums(String maxguestnums) {
        this.maxguestnums = maxguestnums;
    }

    public String getRoomdistribution() {
        return roomdistribution;
    }

    public void setRoomdistribution(String roomdistribution) {
        this.roomdistribution = roomdistribution;
    }

    public String getConditionbeforedays() {
        return conditionbeforedays;
    }

    public void setConditionbeforedays(String conditionbeforedays) {
        this.conditionbeforedays = conditionbeforedays;
    }

    public String getConditionleastdays() {
        return conditionleastdays;
    }

    public void setConditionleastdays(String conditionleastdays) {
        this.conditionleastdays = conditionleastdays;
    }

    public String getConditionleastroomnum() {
        return conditionleastroomnum;
    }

    public void setConditionleastroomnum(String conditionleastroomnum) {
        this.conditionleastroomnum = conditionleastroomnum;
    }

    public String getPaymentype() {
        return paymentype;
    }

    public void setPaymentype(String paymentype) {
        this.paymentype = paymentype;
    }

    public String getRateplandesc() {
        return rateplandesc;
    }

    public void setRateplandesc(String rateplandesc) {
        this.rateplandesc = rateplandesc;
    }

    public String getRateplanname() {
        return rateplanname;
    }

    public void setRateplanname(String rateplanname) {
        this.rateplanname = rateplanname;
    }

    public String getRateplanstate() {
        return rateplanstate;
    }

    public void setRateplanstate(String rateplanstate) {
        this.rateplanstate = rateplanstate;
    }

    public String getAddvaluebednum() {
        return addvaluebednum;
    }

    public void setAddvaluebednum(String addvaluebednum) {
        this.addvaluebednum = addvaluebednum;
    }

    public String getAddvaluebedprice() {
        return addvaluebedprice;
    }

    public void setAddvaluebedprice(String addvaluebedprice) {
        this.addvaluebedprice = addvaluebedprice;
    }

    public String getAddvaluebreakfastnum() {
        return addvaluebreakfastnum;
    }

    public void setAddvaluebreakfastnum(String addvaluebreakfastnum) {
        this.addvaluebreakfastnum = addvaluebreakfastnum;
    }

    public String getAddvaluebreakfastprice() {
        return addvaluebreakfastprice;
    }

    public void setAddvaluebreakfastprice(String addvaluebreakfastprice) {
        this.addvaluebreakfastprice = addvaluebreakfastprice;
    }

    public String getBaseprice() {
        return baseprice;
    }

    public void setBaseprice(String baseprice) {
        this.baseprice = baseprice;
    }

    public String getSaleprice() {
        return saleprice;
    }

    public void setSaleprice(String saleprice) {
        this.saleprice = saleprice;
    }

    public String getMarketprice() {
        return marketprice;
    }

    public void setMarketprice(String marketprice) {
        this.marketprice = marketprice;
    }

    public String getHotelproductservice() {
        return hotelproductservice;
    }

    public void setHotelproductservice(String hotelproductservice) {
        this.hotelproductservice = hotelproductservice;
    }

    public String getHotelproductservicedesc() {
        return hotelproductservicedesc;
    }

    public void setHotelproductservicedesc(String hotelproductservicedesc) {
        this.hotelproductservicedesc = hotelproductservicedesc;
    }

    public String getHotelproductid() {
        return hotelproductid;
    }

    public void setHotelproductid(String hotelproductid) {
        this.hotelproductid = hotelproductid;
    }

    public String getRoomid() {
        return roomid;
    }

    public void setRoomid(String roomid) {
        this.roomid = roomid;
    }

    public String getHotelid() {
        return hotelid;
    }

    public void setHotelid(String hotelid) {
        this.hotelid = hotelid;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof BookYstDTO)) {
            return false;
        }

        BookYstDTO bookYstDTO = (BookYstDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, bookYstDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "BookYstDTO{" +
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
