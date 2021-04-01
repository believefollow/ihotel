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
 * Criteria class for the {@link ihotel.app.domain.Cardysq} entity. This class is used
 * in {@link ihotel.app.web.rest.CardysqResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /cardysqs?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class CardysqCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter roomn;

    private StringFilter guestname;

    private StringFilter account;

    private InstantFilter rq;

    private StringFilter cardid;

    private StringFilter djh;

    private StringFilter sqh;

    private StringFilter empn;

    private StringFilter sign;

    private InstantFilter hoteltime;

    private InstantFilter yxrq;

    private BigDecimalFilter je;

    private StringFilter ysqmemo;

    public CardysqCriteria() {}

    public CardysqCriteria(CardysqCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.roomn = other.roomn == null ? null : other.roomn.copy();
        this.guestname = other.guestname == null ? null : other.guestname.copy();
        this.account = other.account == null ? null : other.account.copy();
        this.rq = other.rq == null ? null : other.rq.copy();
        this.cardid = other.cardid == null ? null : other.cardid.copy();
        this.djh = other.djh == null ? null : other.djh.copy();
        this.sqh = other.sqh == null ? null : other.sqh.copy();
        this.empn = other.empn == null ? null : other.empn.copy();
        this.sign = other.sign == null ? null : other.sign.copy();
        this.hoteltime = other.hoteltime == null ? null : other.hoteltime.copy();
        this.yxrq = other.yxrq == null ? null : other.yxrq.copy();
        this.je = other.je == null ? null : other.je.copy();
        this.ysqmemo = other.ysqmemo == null ? null : other.ysqmemo.copy();
    }

    @Override
    public CardysqCriteria copy() {
        return new CardysqCriteria(this);
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

    public InstantFilter getRq() {
        return rq;
    }

    public InstantFilter rq() {
        if (rq == null) {
            rq = new InstantFilter();
        }
        return rq;
    }

    public void setRq(InstantFilter rq) {
        this.rq = rq;
    }

    public StringFilter getCardid() {
        return cardid;
    }

    public StringFilter cardid() {
        if (cardid == null) {
            cardid = new StringFilter();
        }
        return cardid;
    }

    public void setCardid(StringFilter cardid) {
        this.cardid = cardid;
    }

    public StringFilter getDjh() {
        return djh;
    }

    public StringFilter djh() {
        if (djh == null) {
            djh = new StringFilter();
        }
        return djh;
    }

    public void setDjh(StringFilter djh) {
        this.djh = djh;
    }

    public StringFilter getSqh() {
        return sqh;
    }

    public StringFilter sqh() {
        if (sqh == null) {
            sqh = new StringFilter();
        }
        return sqh;
    }

    public void setSqh(StringFilter sqh) {
        this.sqh = sqh;
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

    public InstantFilter getYxrq() {
        return yxrq;
    }

    public InstantFilter yxrq() {
        if (yxrq == null) {
            yxrq = new InstantFilter();
        }
        return yxrq;
    }

    public void setYxrq(InstantFilter yxrq) {
        this.yxrq = yxrq;
    }

    public BigDecimalFilter getJe() {
        return je;
    }

    public BigDecimalFilter je() {
        if (je == null) {
            je = new BigDecimalFilter();
        }
        return je;
    }

    public void setJe(BigDecimalFilter je) {
        this.je = je;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final CardysqCriteria that = (CardysqCriteria) o;
        return (
            Objects.equals(id, that.id) &&
            Objects.equals(roomn, that.roomn) &&
            Objects.equals(guestname, that.guestname) &&
            Objects.equals(account, that.account) &&
            Objects.equals(rq, that.rq) &&
            Objects.equals(cardid, that.cardid) &&
            Objects.equals(djh, that.djh) &&
            Objects.equals(sqh, that.sqh) &&
            Objects.equals(empn, that.empn) &&
            Objects.equals(sign, that.sign) &&
            Objects.equals(hoteltime, that.hoteltime) &&
            Objects.equals(yxrq, that.yxrq) &&
            Objects.equals(je, that.je) &&
            Objects.equals(ysqmemo, that.ysqmemo)
        );
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, roomn, guestname, account, rq, cardid, djh, sqh, empn, sign, hoteltime, yxrq, je, ysqmemo);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "CardysqCriteria{" +
            (id != null ? "id=" + id + ", " : "") +
            (roomn != null ? "roomn=" + roomn + ", " : "") +
            (guestname != null ? "guestname=" + guestname + ", " : "") +
            (account != null ? "account=" + account + ", " : "") +
            (rq != null ? "rq=" + rq + ", " : "") +
            (cardid != null ? "cardid=" + cardid + ", " : "") +
            (djh != null ? "djh=" + djh + ", " : "") +
            (sqh != null ? "sqh=" + sqh + ", " : "") +
            (empn != null ? "empn=" + empn + ", " : "") +
            (sign != null ? "sign=" + sign + ", " : "") +
            (hoteltime != null ? "hoteltime=" + hoteltime + ", " : "") +
            (yxrq != null ? "yxrq=" + yxrq + ", " : "") +
            (je != null ? "je=" + je + ", " : "") +
            (ysqmemo != null ? "ysqmemo=" + ysqmemo + ", " : "") +
            "}";
    }
}
