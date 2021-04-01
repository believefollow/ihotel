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
 * Criteria class for the {@link ihotel.app.domain.Code1} entity. This class is used
 * in {@link ihotel.app.web.rest.Code1Resource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /code-1-s?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class Code1Criteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter code1;

    private StringFilter code2;

    public Code1Criteria() {}

    public Code1Criteria(Code1Criteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.code1 = other.code1 == null ? null : other.code1.copy();
        this.code2 = other.code2 == null ? null : other.code2.copy();
    }

    @Override
    public Code1Criteria copy() {
        return new Code1Criteria(this);
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

    public StringFilter getCode1() {
        return code1;
    }

    public StringFilter code1() {
        if (code1 == null) {
            code1 = new StringFilter();
        }
        return code1;
    }

    public void setCode1(StringFilter code1) {
        this.code1 = code1;
    }

    public StringFilter getCode2() {
        return code2;
    }

    public StringFilter code2() {
        if (code2 == null) {
            code2 = new StringFilter();
        }
        return code2;
    }

    public void setCode2(StringFilter code2) {
        this.code2 = code2;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final Code1Criteria that = (Code1Criteria) o;
        return Objects.equals(id, that.id) && Objects.equals(code1, that.code1) && Objects.equals(code2, that.code2);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, code1, code2);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Code1Criteria{" +
            (id != null ? "id=" + id + ", " : "") +
            (code1 != null ? "code1=" + code1 + ", " : "") +
            (code2 != null ? "code2=" + code2 + ", " : "") +
            "}";
    }
}
