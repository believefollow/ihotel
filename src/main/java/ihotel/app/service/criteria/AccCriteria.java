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
 * Criteria class for the {@link ihotel.app.domain.Acc} entity. This class is used
 * in {@link ihotel.app.web.rest.AccResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /accs?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class AccCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter acc;

    public AccCriteria() {}

    public AccCriteria(AccCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.acc = other.acc == null ? null : other.acc.copy();
    }

    @Override
    public AccCriteria copy() {
        return new AccCriteria(this);
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

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final AccCriteria that = (AccCriteria) o;
        return Objects.equals(id, that.id) && Objects.equals(acc, that.acc);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, acc);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "AccCriteria{" +
            (id != null ? "id=" + id + ", " : "") +
            (acc != null ? "acc=" + acc + ", " : "") +
            "}";
    }
}
