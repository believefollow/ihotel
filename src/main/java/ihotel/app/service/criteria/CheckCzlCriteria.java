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
 * Criteria class for the {@link ihotel.app.domain.CheckCzl} entity. This class is used
 * in {@link ihotel.app.web.rest.CheckCzlResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /check-czls?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class CheckCzlCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private InstantFilter hoteltime;

    private StringFilter rtype;

    private LongFilter rnum;

    private LongFilter rOutnum;

    private BigDecimalFilter czl;

    private BigDecimalFilter chagrge;

    private BigDecimalFilter chagrgeAvg;

    private StringFilter empn;

    private InstantFilter entertime;

    public CheckCzlCriteria() {}

    public CheckCzlCriteria(CheckCzlCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.hoteltime = other.hoteltime == null ? null : other.hoteltime.copy();
        this.rtype = other.rtype == null ? null : other.rtype.copy();
        this.rnum = other.rnum == null ? null : other.rnum.copy();
        this.rOutnum = other.rOutnum == null ? null : other.rOutnum.copy();
        this.czl = other.czl == null ? null : other.czl.copy();
        this.chagrge = other.chagrge == null ? null : other.chagrge.copy();
        this.chagrgeAvg = other.chagrgeAvg == null ? null : other.chagrgeAvg.copy();
        this.empn = other.empn == null ? null : other.empn.copy();
        this.entertime = other.entertime == null ? null : other.entertime.copy();
    }

    @Override
    public CheckCzlCriteria copy() {
        return new CheckCzlCriteria(this);
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

    public StringFilter getRtype() {
        return rtype;
    }

    public StringFilter rtype() {
        if (rtype == null) {
            rtype = new StringFilter();
        }
        return rtype;
    }

    public void setRtype(StringFilter rtype) {
        this.rtype = rtype;
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

    public LongFilter getrOutnum() {
        return rOutnum;
    }

    public LongFilter rOutnum() {
        if (rOutnum == null) {
            rOutnum = new LongFilter();
        }
        return rOutnum;
    }

    public void setrOutnum(LongFilter rOutnum) {
        this.rOutnum = rOutnum;
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
        final CheckCzlCriteria that = (CheckCzlCriteria) o;
        return (
            Objects.equals(id, that.id) &&
            Objects.equals(hoteltime, that.hoteltime) &&
            Objects.equals(rtype, that.rtype) &&
            Objects.equals(rnum, that.rnum) &&
            Objects.equals(rOutnum, that.rOutnum) &&
            Objects.equals(czl, that.czl) &&
            Objects.equals(chagrge, that.chagrge) &&
            Objects.equals(chagrgeAvg, that.chagrgeAvg) &&
            Objects.equals(empn, that.empn) &&
            Objects.equals(entertime, that.entertime)
        );
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, hoteltime, rtype, rnum, rOutnum, czl, chagrge, chagrgeAvg, empn, entertime);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "CheckCzlCriteria{" +
            (id != null ? "id=" + id + ", " : "") +
            (hoteltime != null ? "hoteltime=" + hoteltime + ", " : "") +
            (rtype != null ? "rtype=" + rtype + ", " : "") +
            (rnum != null ? "rnum=" + rnum + ", " : "") +
            (rOutnum != null ? "rOutnum=" + rOutnum + ", " : "") +
            (czl != null ? "czl=" + czl + ", " : "") +
            (chagrge != null ? "chagrge=" + chagrge + ", " : "") +
            (chagrgeAvg != null ? "chagrgeAvg=" + chagrgeAvg + ", " : "") +
            (empn != null ? "empn=" + empn + ", " : "") +
            (entertime != null ? "entertime=" + entertime + ", " : "") +
            "}";
    }
}
