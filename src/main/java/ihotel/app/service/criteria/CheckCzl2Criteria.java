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
 * Criteria class for the {@link ihotel.app.domain.CheckCzl2} entity. This class is used
 * in {@link ihotel.app.web.rest.CheckCzl2Resource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /check-czl-2-s?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class CheckCzl2Criteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private InstantFilter hoteltime;

    private StringFilter protocol;

    private LongFilter rnum;

    private BigDecimalFilter czl;

    private BigDecimalFilter chagrge;

    private BigDecimalFilter chagrgeAvg;

    private StringFilter empn;

    private InstantFilter entertime;

    public CheckCzl2Criteria() {}

    public CheckCzl2Criteria(CheckCzl2Criteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.hoteltime = other.hoteltime == null ? null : other.hoteltime.copy();
        this.protocol = other.protocol == null ? null : other.protocol.copy();
        this.rnum = other.rnum == null ? null : other.rnum.copy();
        this.czl = other.czl == null ? null : other.czl.copy();
        this.chagrge = other.chagrge == null ? null : other.chagrge.copy();
        this.chagrgeAvg = other.chagrgeAvg == null ? null : other.chagrgeAvg.copy();
        this.empn = other.empn == null ? null : other.empn.copy();
        this.entertime = other.entertime == null ? null : other.entertime.copy();
    }

    @Override
    public CheckCzl2Criteria copy() {
        return new CheckCzl2Criteria(this);
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

    public StringFilter getProtocol() {
        return protocol;
    }

    public StringFilter protocol() {
        if (protocol == null) {
            protocol = new StringFilter();
        }
        return protocol;
    }

    public void setProtocol(StringFilter protocol) {
        this.protocol = protocol;
    }

    public LongFilter getRnum() {
        return rnum;
    }

    public LongFilter rnum() {
        if (rnum == null) {
            rnum = new LongFilter();
        }
        return rnum;
    }

    public void setRnum(LongFilter rnum) {
        this.rnum = rnum;
    }

    public BigDecimalFilter getCzl() {
        return czl;
    }

    public BigDecimalFilter czl() {
        if (czl == null) {
            czl = new BigDecimalFilter();
        }
        return czl;
    }

    public void setCzl(BigDecimalFilter czl) {
        this.czl = czl;
    }

    public BigDecimalFilter getChagrge() {
        return chagrge;
    }

    public BigDecimalFilter chagrge() {
        if (chagrge == null) {
            chagrge = new BigDecimalFilter();
        }
        return chagrge;
    }

    public void setChagrge(BigDecimalFilter chagrge) {
        this.chagrge = chagrge;
    }

    public BigDecimalFilter getChagrgeAvg() {
        return chagrgeAvg;
    }

    public BigDecimalFilter chagrgeAvg() {
        if (chagrgeAvg == null) {
            chagrgeAvg = new BigDecimalFilter();
        }
        return chagrgeAvg;
    }

    public void setChagrgeAvg(BigDecimalFilter chagrgeAvg) {
        this.chagrgeAvg = chagrgeAvg;
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

    public InstantFilter getEntertime() {
        return entertime;
    }

    public InstantFilter entertime() {
        if (entertime == null) {
            entertime = new InstantFilter();
        }
        return entertime;
    }

    public void setEntertime(InstantFilter entertime) {
        this.entertime = entertime;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final CheckCzl2Criteria that = (CheckCzl2Criteria) o;
        return (
            Objects.equals(id, that.id) &&
            Objects.equals(hoteltime, that.hoteltime) &&
            Objects.equals(protocol, that.protocol) &&
            Objects.equals(rnum, that.rnum) &&
            Objects.equals(czl, that.czl) &&
            Objects.equals(chagrge, that.chagrge) &&
            Objects.equals(chagrgeAvg, that.chagrgeAvg) &&
            Objects.equals(empn, that.empn) &&
            Objects.equals(entertime, that.entertime)
        );
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, hoteltime, protocol, rnum, czl, chagrge, chagrgeAvg, empn, entertime);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "CheckCzl2Criteria{" +
            (id != null ? "id=" + id + ", " : "") +
            (hoteltime != null ? "hoteltime=" + hoteltime + ", " : "") +
            (protocol != null ? "protocol=" + protocol + ", " : "") +
            (rnum != null ? "rnum=" + rnum + ", " : "") +
            (czl != null ? "czl=" + czl + ", " : "") +
            (chagrge != null ? "chagrge=" + chagrge + ", " : "") +
            (chagrgeAvg != null ? "chagrgeAvg=" + chagrgeAvg + ", " : "") +
            (empn != null ? "empn=" + empn + ", " : "") +
            (entertime != null ? "entertime=" + entertime + ", " : "") +
            "}";
    }
}
