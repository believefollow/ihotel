package ihotel.app.service.criteria;

import java.io.Serializable;
import java.util.Objects;
import tech.jhipster.service.Criteria;
import tech.jhipster.service.filter.BooleanFilter;
import tech.jhipster.service.filter.DoubleFilter;
import tech.jhipster.service.filter.Filter;
import tech.jhipster.service.filter.FloatFilter;
import tech.jhipster.service.filter.IntegerFilter;
import tech.jhipster.service.filter.LongFilter;
import tech.jhipster.service.filter.StringFilter;

/**
 * Criteria class for the {@link ihotel.app.domain.BookYst} entity. This class is used
 * in {@link ihotel.app.web.rest.BookYstResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /book-ysts?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class BookYstCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter roomcode;

    private StringFilter roomname;

    private StringFilter roomnum;

    private StringFilter roomseparatenum;

    private StringFilter bedids;

    private StringFilter bedsimpledesc;

    private StringFilter bednum;

    private StringFilter roomsize;

    private StringFilter roomfloor;

    private StringFilter netservice;

    private StringFilter nettype;

    private StringFilter iswindow;

    private StringFilter remark;

    private StringFilter sortid;

    private StringFilter roomstate;

    private StringFilter source;

    private StringFilter roomamenities;

    private StringFilter maxguestnums;

    private StringFilter roomdistribution;

    private StringFilter conditionbeforedays;

    private StringFilter conditionleastdays;

    private StringFilter conditionleastroomnum;

    private StringFilter paymentype;

    private StringFilter rateplandesc;

    private StringFilter rateplanname;

    private StringFilter rateplanstate;

    private StringFilter addvaluebednum;

    private StringFilter addvaluebedprice;

    private StringFilter addvaluebreakfastnum;

    private StringFilter addvaluebreakfastprice;

    private StringFilter baseprice;

    private StringFilter saleprice;

    private StringFilter marketprice;

    private StringFilter hotelproductservice;

    private StringFilter hotelproductservicedesc;

    private StringFilter hotelproductid;

    private StringFilter roomid;

    private StringFilter hotelid;

    public BookYstCriteria() {}

    public BookYstCriteria(BookYstCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.roomcode = other.roomcode == null ? null : other.roomcode.copy();
        this.roomname = other.roomname == null ? null : other.roomname.copy();
        this.roomnum = other.roomnum == null ? null : other.roomnum.copy();
        this.roomseparatenum = other.roomseparatenum == null ? null : other.roomseparatenum.copy();
        this.bedids = other.bedids == null ? null : other.bedids.copy();
        this.bedsimpledesc = other.bedsimpledesc == null ? null : other.bedsimpledesc.copy();
        this.bednum = other.bednum == null ? null : other.bednum.copy();
        this.roomsize = other.roomsize == null ? null : other.roomsize.copy();
        this.roomfloor = other.roomfloor == null ? null : other.roomfloor.copy();
        this.netservice = other.netservice == null ? null : other.netservice.copy();
        this.nettype = other.nettype == null ? null : other.nettype.copy();
        this.iswindow = other.iswindow == null ? null : other.iswindow.copy();
        this.remark = other.remark == null ? null : other.remark.copy();
        this.sortid = other.sortid == null ? null : other.sortid.copy();
        this.roomstate = other.roomstate == null ? null : other.roomstate.copy();
        this.source = other.source == null ? null : other.source.copy();
        this.roomamenities = other.roomamenities == null ? null : other.roomamenities.copy();
        this.maxguestnums = other.maxguestnums == null ? null : other.maxguestnums.copy();
        this.roomdistribution = other.roomdistribution == null ? null : other.roomdistribution.copy();
        this.conditionbeforedays = other.conditionbeforedays == null ? null : other.conditionbeforedays.copy();
        this.conditionleastdays = other.conditionleastdays == null ? null : other.conditionleastdays.copy();
        this.conditionleastroomnum = other.conditionleastroomnum == null ? null : other.conditionleastroomnum.copy();
        this.paymentype = other.paymentype == null ? null : other.paymentype.copy();
        this.rateplandesc = other.rateplandesc == null ? null : other.rateplandesc.copy();
        this.rateplanname = other.rateplanname == null ? null : other.rateplanname.copy();
        this.rateplanstate = other.rateplanstate == null ? null : other.rateplanstate.copy();
        this.addvaluebednum = other.addvaluebednum == null ? null : other.addvaluebednum.copy();
        this.addvaluebedprice = other.addvaluebedprice == null ? null : other.addvaluebedprice.copy();
        this.addvaluebreakfastnum = other.addvaluebreakfastnum == null ? null : other.addvaluebreakfastnum.copy();
        this.addvaluebreakfastprice = other.addvaluebreakfastprice == null ? null : other.addvaluebreakfastprice.copy();
        this.baseprice = other.baseprice == null ? null : other.baseprice.copy();
        this.saleprice = other.saleprice == null ? null : other.saleprice.copy();
        this.marketprice = other.marketprice == null ? null : other.marketprice.copy();
        this.hotelproductservice = other.hotelproductservice == null ? null : other.hotelproductservice.copy();
        this.hotelproductservicedesc = other.hotelproductservicedesc == null ? null : other.hotelproductservicedesc.copy();
        this.hotelproductid = other.hotelproductid == null ? null : other.hotelproductid.copy();
        this.roomid = other.roomid == null ? null : other.roomid.copy();
        this.hotelid = other.hotelid == null ? null : other.hotelid.copy();
    }

    @Override
    public BookYstCriteria copy() {
        return new BookYstCriteria(this);
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

    public StringFilter getRoomcode() {
        return roomcode;
    }

    public StringFilter roomcode() {
        if (roomcode == null) {
            roomcode = new StringFilter();
        }
        return roomcode;
    }

    public void setRoomcode(StringFilter roomcode) {
        this.roomcode = roomcode;
    }

    public StringFilter getRoomname() {
        return roomname;
    }

    public StringFilter roomname() {
        if (roomname == null) {
            roomname = new StringFilter();
        }
        return roomname;
    }

    public void setRoomname(StringFilter roomname) {
        this.roomname = roomname;
    }

    public StringFilter getRoomnum() {
        return roomnum;
    }

    public StringFilter roomnum() {
        if (roomnum == null) {
            roomnum = new StringFilter();
        }
        return roomnum;
    }

    public void setRoomnum(StringFilter roomnum) {
        this.roomnum = roomnum;
    }

    public StringFilter getRoomseparatenum() {
        return roomseparatenum;
    }

    public StringFilter roomseparatenum() {
        if (roomseparatenum == null) {
            roomseparatenum = new StringFilter();
        }
        return roomseparatenum;
    }

    public void setRoomseparatenum(StringFilter roomseparatenum) {
        this.roomseparatenum = roomseparatenum;
    }

    public StringFilter getBedids() {
        return bedids;
    }

    public StringFilter bedids() {
        if (bedids == null) {
            bedids = new StringFilter();
        }
        return bedids;
    }

    public void setBedids(StringFilter bedids) {
        this.bedids = bedids;
    }

    public StringFilter getBedsimpledesc() {
        return bedsimpledesc;
    }

    public StringFilter bedsimpledesc() {
        if (bedsimpledesc == null) {
            bedsimpledesc = new StringFilter();
        }
        return bedsimpledesc;
    }

    public void setBedsimpledesc(StringFilter bedsimpledesc) {
        this.bedsimpledesc = bedsimpledesc;
    }

    public StringFilter getBednum() {
        return bednum;
    }

    public StringFilter bednum() {
        if (bednum == null) {
            bednum = new StringFilter();
        }
        return bednum;
    }

    public void setBednum(StringFilter bednum) {
        this.bednum = bednum;
    }

    public StringFilter getRoomsize() {
        return roomsize;
    }

    public StringFilter roomsize() {
        if (roomsize == null) {
            roomsize = new StringFilter();
        }
        return roomsize;
    }

    public void setRoomsize(StringFilter roomsize) {
        this.roomsize = roomsize;
    }

    public StringFilter getRoomfloor() {
        return roomfloor;
    }

    public StringFilter roomfloor() {
        if (roomfloor == null) {
            roomfloor = new StringFilter();
        }
        return roomfloor;
    }

    public void setRoomfloor(StringFilter roomfloor) {
        this.roomfloor = roomfloor;
    }

    public StringFilter getNetservice() {
        return netservice;
    }

    public StringFilter netservice() {
        if (netservice == null) {
            netservice = new StringFilter();
        }
        return netservice;
    }

    public void setNetservice(StringFilter netservice) {
        this.netservice = netservice;
    }

    public StringFilter getNettype() {
        return nettype;
    }

    public StringFilter nettype() {
        if (nettype == null) {
            nettype = new StringFilter();
        }
        return nettype;
    }

    public void setNettype(StringFilter nettype) {
        this.nettype = nettype;
    }

    public StringFilter getIswindow() {
        return iswindow;
    }

    public StringFilter iswindow() {
        if (iswindow == null) {
            iswindow = new StringFilter();
        }
        return iswindow;
    }

    public void setIswindow(StringFilter iswindow) {
        this.iswindow = iswindow;
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

    public StringFilter getSortid() {
        return sortid;
    }

    public StringFilter sortid() {
        if (sortid == null) {
            sortid = new StringFilter();
        }
        return sortid;
    }

    public void setSortid(StringFilter sortid) {
        this.sortid = sortid;
    }

    public StringFilter getRoomstate() {
        return roomstate;
    }

    public StringFilter roomstate() {
        if (roomstate == null) {
            roomstate = new StringFilter();
        }
        return roomstate;
    }

    public void setRoomstate(StringFilter roomstate) {
        this.roomstate = roomstate;
    }

    public StringFilter getSource() {
        return source;
    }

    public StringFilter source() {
        if (source == null) {
            source = new StringFilter();
        }
        return source;
    }

    public void setSource(StringFilter source) {
        this.source = source;
    }

    public StringFilter getRoomamenities() {
        return roomamenities;
    }

    public StringFilter roomamenities() {
        if (roomamenities == null) {
            roomamenities = new StringFilter();
        }
        return roomamenities;
    }

    public void setRoomamenities(StringFilter roomamenities) {
        this.roomamenities = roomamenities;
    }

    public StringFilter getMaxguestnums() {
        return maxguestnums;
    }

    public StringFilter maxguestnums() {
        if (maxguestnums == null) {
            maxguestnums = new StringFilter();
        }
        return maxguestnums;
    }

    public void setMaxguestnums(StringFilter maxguestnums) {
        this.maxguestnums = maxguestnums;
    }

    public StringFilter getRoomdistribution() {
        return roomdistribution;
    }

    public StringFilter roomdistribution() {
        if (roomdistribution == null) {
            roomdistribution = new StringFilter();
        }
        return roomdistribution;
    }

    public void setRoomdistribution(StringFilter roomdistribution) {
        this.roomdistribution = roomdistribution;
    }

    public StringFilter getConditionbeforedays() {
        return conditionbeforedays;
    }

    public StringFilter conditionbeforedays() {
        if (conditionbeforedays == null) {
            conditionbeforedays = new StringFilter();
        }
        return conditionbeforedays;
    }

    public void setConditionbeforedays(StringFilter conditionbeforedays) {
        this.conditionbeforedays = conditionbeforedays;
    }

    public StringFilter getConditionleastdays() {
        return conditionleastdays;
    }

    public StringFilter conditionleastdays() {
        if (conditionleastdays == null) {
            conditionleastdays = new StringFilter();
        }
        return conditionleastdays;
    }

    public void setConditionleastdays(StringFilter conditionleastdays) {
        this.conditionleastdays = conditionleastdays;
    }

    public StringFilter getConditionleastroomnum() {
        return conditionleastroomnum;
    }

    public StringFilter conditionleastroomnum() {
        if (conditionleastroomnum == null) {
            conditionleastroomnum = new StringFilter();
        }
        return conditionleastroomnum;
    }

    public void setConditionleastroomnum(StringFilter conditionleastroomnum) {
        this.conditionleastroomnum = conditionleastroomnum;
    }

    public StringFilter getPaymentype() {
        return paymentype;
    }

    public StringFilter paymentype() {
        if (paymentype == null) {
            paymentype = new StringFilter();
        }
        return paymentype;
    }

    public void setPaymentype(StringFilter paymentype) {
        this.paymentype = paymentype;
    }

    public StringFilter getRateplandesc() {
        return rateplandesc;
    }

    public StringFilter rateplandesc() {
        if (rateplandesc == null) {
            rateplandesc = new StringFilter();
        }
        return rateplandesc;
    }

    public void setRateplandesc(StringFilter rateplandesc) {
        this.rateplandesc = rateplandesc;
    }

    public StringFilter getRateplanname() {
        return rateplanname;
    }

    public StringFilter rateplanname() {
        if (rateplanname == null) {
            rateplanname = new StringFilter();
        }
        return rateplanname;
    }

    public void setRateplanname(StringFilter rateplanname) {
        this.rateplanname = rateplanname;
    }

    public StringFilter getRateplanstate() {
        return rateplanstate;
    }

    public StringFilter rateplanstate() {
        if (rateplanstate == null) {
            rateplanstate = new StringFilter();
        }
        return rateplanstate;
    }

    public void setRateplanstate(StringFilter rateplanstate) {
        this.rateplanstate = rateplanstate;
    }

    public StringFilter getAddvaluebednum() {
        return addvaluebednum;
    }

    public StringFilter addvaluebednum() {
        if (addvaluebednum == null) {
            addvaluebednum = new StringFilter();
        }
        return addvaluebednum;
    }

    public void setAddvaluebednum(StringFilter addvaluebednum) {
        this.addvaluebednum = addvaluebednum;
    }

    public StringFilter getAddvaluebedprice() {
        return addvaluebedprice;
    }

    public StringFilter addvaluebedprice() {
        if (addvaluebedprice == null) {
            addvaluebedprice = new StringFilter();
        }
        return addvaluebedprice;
    }

    public void setAddvaluebedprice(StringFilter addvaluebedprice) {
        this.addvaluebedprice = addvaluebedprice;
    }

    public StringFilter getAddvaluebreakfastnum() {
        return addvaluebreakfastnum;
    }

    public StringFilter addvaluebreakfastnum() {
        if (addvaluebreakfastnum == null) {
            addvaluebreakfastnum = new StringFilter();
        }
        return addvaluebreakfastnum;
    }

    public void setAddvaluebreakfastnum(StringFilter addvaluebreakfastnum) {
        this.addvaluebreakfastnum = addvaluebreakfastnum;
    }

    public StringFilter getAddvaluebreakfastprice() {
        return addvaluebreakfastprice;
    }

    public StringFilter addvaluebreakfastprice() {
        if (addvaluebreakfastprice == null) {
            addvaluebreakfastprice = new StringFilter();
        }
        return addvaluebreakfastprice;
    }

    public void setAddvaluebreakfastprice(StringFilter addvaluebreakfastprice) {
        this.addvaluebreakfastprice = addvaluebreakfastprice;
    }

    public StringFilter getBaseprice() {
        return baseprice;
    }

    public StringFilter baseprice() {
        if (baseprice == null) {
            baseprice = new StringFilter();
        }
        return baseprice;
    }

    public void setBaseprice(StringFilter baseprice) {
        this.baseprice = baseprice;
    }

    public StringFilter getSaleprice() {
        return saleprice;
    }

    public StringFilter saleprice() {
        if (saleprice == null) {
            saleprice = new StringFilter();
        }
        return saleprice;
    }

    public void setSaleprice(StringFilter saleprice) {
        this.saleprice = saleprice;
    }

    public StringFilter getMarketprice() {
        return marketprice;
    }

    public StringFilter marketprice() {
        if (marketprice == null) {
            marketprice = new StringFilter();
        }
        return marketprice;
    }

    public void setMarketprice(StringFilter marketprice) {
        this.marketprice = marketprice;
    }

    public StringFilter getHotelproductservice() {
        return hotelproductservice;
    }

    public StringFilter hotelproductservice() {
        if (hotelproductservice == null) {
            hotelproductservice = new StringFilter();
        }
        return hotelproductservice;
    }

    public void setHotelproductservice(StringFilter hotelproductservice) {
        this.hotelproductservice = hotelproductservice;
    }

    public StringFilter getHotelproductservicedesc() {
        return hotelproductservicedesc;
    }

    public StringFilter hotelproductservicedesc() {
        if (hotelproductservicedesc == null) {
            hotelproductservicedesc = new StringFilter();
        }
        return hotelproductservicedesc;
    }

    public void setHotelproductservicedesc(StringFilter hotelproductservicedesc) {
        this.hotelproductservicedesc = hotelproductservicedesc;
    }

    public StringFilter getHotelproductid() {
        return hotelproductid;
    }

    public StringFilter hotelproductid() {
        if (hotelproductid == null) {
            hotelproductid = new StringFilter();
        }
        return hotelproductid;
    }

    public void setHotelproductid(StringFilter hotelproductid) {
        this.hotelproductid = hotelproductid;
    }

    public StringFilter getRoomid() {
        return roomid;
    }

    public StringFilter roomid() {
        if (roomid == null) {
            roomid = new StringFilter();
        }
        return roomid;
    }

    public void setRoomid(StringFilter roomid) {
        this.roomid = roomid;
    }

    public StringFilter getHotelid() {
        return hotelid;
    }

    public StringFilter hotelid() {
        if (hotelid == null) {
            hotelid = new StringFilter();
        }
        return hotelid;
    }

    public void setHotelid(StringFilter hotelid) {
        this.hotelid = hotelid;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final BookYstCriteria that = (BookYstCriteria) o;
        return (
            Objects.equals(id, that.id) &&
            Objects.equals(roomcode, that.roomcode) &&
            Objects.equals(roomname, that.roomname) &&
            Objects.equals(roomnum, that.roomnum) &&
            Objects.equals(roomseparatenum, that.roomseparatenum) &&
            Objects.equals(bedids, that.bedids) &&
            Objects.equals(bedsimpledesc, that.bedsimpledesc) &&
            Objects.equals(bednum, that.bednum) &&
            Objects.equals(roomsize, that.roomsize) &&
            Objects.equals(roomfloor, that.roomfloor) &&
            Objects.equals(netservice, that.netservice) &&
            Objects.equals(nettype, that.nettype) &&
            Objects.equals(iswindow, that.iswindow) &&
            Objects.equals(remark, that.remark) &&
            Objects.equals(sortid, that.sortid) &&
            Objects.equals(roomstate, that.roomstate) &&
            Objects.equals(source, that.source) &&
            Objects.equals(roomamenities, that.roomamenities) &&
            Objects.equals(maxguestnums, that.maxguestnums) &&
            Objects.equals(roomdistribution, that.roomdistribution) &&
            Objects.equals(conditionbeforedays, that.conditionbeforedays) &&
            Objects.equals(conditionleastdays, that.conditionleastdays) &&
            Objects.equals(conditionleastroomnum, that.conditionleastroomnum) &&
            Objects.equals(paymentype, that.paymentype) &&
            Objects.equals(rateplandesc, that.rateplandesc) &&
            Objects.equals(rateplanname, that.rateplanname) &&
            Objects.equals(rateplanstate, that.rateplanstate) &&
            Objects.equals(addvaluebednum, that.addvaluebednum) &&
            Objects.equals(addvaluebedprice, that.addvaluebedprice) &&
            Objects.equals(addvaluebreakfastnum, that.addvaluebreakfastnum) &&
            Objects.equals(addvaluebreakfastprice, that.addvaluebreakfastprice) &&
            Objects.equals(baseprice, that.baseprice) &&
            Objects.equals(saleprice, that.saleprice) &&
            Objects.equals(marketprice, that.marketprice) &&
            Objects.equals(hotelproductservice, that.hotelproductservice) &&
            Objects.equals(hotelproductservicedesc, that.hotelproductservicedesc) &&
            Objects.equals(hotelproductid, that.hotelproductid) &&
            Objects.equals(roomid, that.roomid) &&
            Objects.equals(hotelid, that.hotelid)
        );
    }

    @Override
    public int hashCode() {
        return Objects.hash(
            id,
            roomcode,
            roomname,
            roomnum,
            roomseparatenum,
            bedids,
            bedsimpledesc,
            bednum,
            roomsize,
            roomfloor,
            netservice,
            nettype,
            iswindow,
            remark,
            sortid,
            roomstate,
            source,
            roomamenities,
            maxguestnums,
            roomdistribution,
            conditionbeforedays,
            conditionleastdays,
            conditionleastroomnum,
            paymentype,
            rateplandesc,
            rateplanname,
            rateplanstate,
            addvaluebednum,
            addvaluebedprice,
            addvaluebreakfastnum,
            addvaluebreakfastprice,
            baseprice,
            saleprice,
            marketprice,
            hotelproductservice,
            hotelproductservicedesc,
            hotelproductid,
            roomid,
            hotelid
        );
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "BookYstCriteria{" +
            (id != null ? "id=" + id + ", " : "") +
            (roomcode != null ? "roomcode=" + roomcode + ", " : "") +
            (roomname != null ? "roomname=" + roomname + ", " : "") +
            (roomnum != null ? "roomnum=" + roomnum + ", " : "") +
            (roomseparatenum != null ? "roomseparatenum=" + roomseparatenum + ", " : "") +
            (bedids != null ? "bedids=" + bedids + ", " : "") +
            (bedsimpledesc != null ? "bedsimpledesc=" + bedsimpledesc + ", " : "") +
            (bednum != null ? "bednum=" + bednum + ", " : "") +
            (roomsize != null ? "roomsize=" + roomsize + ", " : "") +
            (roomfloor != null ? "roomfloor=" + roomfloor + ", " : "") +
            (netservice != null ? "netservice=" + netservice + ", " : "") +
            (nettype != null ? "nettype=" + nettype + ", " : "") +
            (iswindow != null ? "iswindow=" + iswindow + ", " : "") +
            (remark != null ? "remark=" + remark + ", " : "") +
            (sortid != null ? "sortid=" + sortid + ", " : "") +
            (roomstate != null ? "roomstate=" + roomstate + ", " : "") +
            (source != null ? "source=" + source + ", " : "") +
            (roomamenities != null ? "roomamenities=" + roomamenities + ", " : "") +
            (maxguestnums != null ? "maxguestnums=" + maxguestnums + ", " : "") +
            (roomdistribution != null ? "roomdistribution=" + roomdistribution + ", " : "") +
            (conditionbeforedays != null ? "conditionbeforedays=" + conditionbeforedays + ", " : "") +
            (conditionleastdays != null ? "conditionleastdays=" + conditionleastdays + ", " : "") +
            (conditionleastroomnum != null ? "conditionleastroomnum=" + conditionleastroomnum + ", " : "") +
            (paymentype != null ? "paymentype=" + paymentype + ", " : "") +
            (rateplandesc != null ? "rateplandesc=" + rateplandesc + ", " : "") +
            (rateplanname != null ? "rateplanname=" + rateplanname + ", " : "") +
            (rateplanstate != null ? "rateplanstate=" + rateplanstate + ", " : "") +
            (addvaluebednum != null ? "addvaluebednum=" + addvaluebednum + ", " : "") +
            (addvaluebedprice != null ? "addvaluebedprice=" + addvaluebedprice + ", " : "") +
            (addvaluebreakfastnum != null ? "addvaluebreakfastnum=" + addvaluebreakfastnum + ", " : "") +
            (addvaluebreakfastprice != null ? "addvaluebreakfastprice=" + addvaluebreakfastprice + ", " : "") +
            (baseprice != null ? "baseprice=" + baseprice + ", " : "") +
            (saleprice != null ? "saleprice=" + saleprice + ", " : "") +
            (marketprice != null ? "marketprice=" + marketprice + ", " : "") +
            (hotelproductservice != null ? "hotelproductservice=" + hotelproductservice + ", " : "") +
            (hotelproductservicedesc != null ? "hotelproductservicedesc=" + hotelproductservicedesc + ", " : "") +
            (hotelproductid != null ? "hotelproductid=" + hotelproductid + ", " : "") +
            (roomid != null ? "roomid=" + roomid + ", " : "") +
            (hotelid != null ? "hotelid=" + hotelid + ", " : "") +
            "}";
    }
}
