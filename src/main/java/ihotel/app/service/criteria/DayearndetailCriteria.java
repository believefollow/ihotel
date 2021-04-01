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
 * Criteria class for the {@link ihotel.app.domain.Dayearndetail} entity. This class is used
 * in {@link ihotel.app.web.rest.DayearndetailResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /dayearndetails?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class DayearndetailCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private InstantFilter earndate;

    private LongFilter salespotn;

    private BigDecimalFilter money;

    public DayearndetailCriteria() {}

    public DayearndetailCriteria(DayearndetailCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.earndate = other.earndate == null ? null : other.earndate.copy();
        this.salespotn = other.salespotn == null ? null : other.salespotn.copy();
        this.money = other.money == null ? null : other.money.copy();
    }

    @Override
    public DayearndetailCriteria copy() {
        return new DayearndetailCriteria(this);
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

    public InstantFilter getEarndate() {
        return earndate;
    }

    public InstantFilter earndate() {
        if (earndate == null) {
            earndate = new InstantFilter();
        }
        return earndate;
    }

    public void setEarndate(InstantFilter earndate) {
        this.earndate = earndate;
    }

    public LongFilter getSalespotn() {
        return salespotn;
    }

    public LongFilter salespotn() {
        if (salespotn == null) {
            salespotn = new LongFilter();
        }
        return salespotn;
    }

    public void setSalespotn(LongFilter salespotn) {
        this.salespotn = salespotn;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final DayearndetailCriteria that = (DayearndetailCriteria) o;
        return (
            Objects.equals(id, that.id) &&
            Objects.equals(earndate, that.earndate) &&
            Objects.equals(salespotn, that.salespotn) &&
            Objects.equals(money, that.money)
        );
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, earndate, salespotn, money);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "DayearndetailCriteria{" +
            (id != null ? "id=" + id + ", " : "") +
            (earndate != null ? "earndate=" + earndate + ", " : "") +
            (salespotn != null ? "salespotn=" + salespotn + ", " : "") +
            (money != null ? "money=" + money + ", " : "") +
            "}";
    }
}
