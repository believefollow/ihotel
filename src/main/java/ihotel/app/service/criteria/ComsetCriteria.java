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
 * Criteria class for the {@link ihotel.app.domain.Comset} entity. This class is used
 * in {@link ihotel.app.web.rest.ComsetResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /comsets?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class ComsetCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter comNum;

    private StringFilter comBytes;

    private StringFilter comDatabit;

    private StringFilter comParitycheck;

    private StringFilter comStopbit;

    private LongFilter comFunction;

    public ComsetCriteria() {}

    public ComsetCriteria(ComsetCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.comNum = other.comNum == null ? null : other.comNum.copy();
        this.comBytes = other.comBytes == null ? null : other.comBytes.copy();
        this.comDatabit = other.comDatabit == null ? null : other.comDatabit.copy();
        this.comParitycheck = other.comParitycheck == null ? null : other.comParitycheck.copy();
        this.comStopbit = other.comStopbit == null ? null : other.comStopbit.copy();
        this.comFunction = other.comFunction == null ? null : other.comFunction.copy();
    }

    @Override
    public ComsetCriteria copy() {
        return new ComsetCriteria(this);
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

    public StringFilter getComNum() {
        return comNum;
    }

    public StringFilter comNum() {
        if (comNum == null) {
            comNum = new StringFilter();
        }
        return comNum;
    }

    public void setComNum(StringFilter comNum) {
        this.comNum = comNum;
    }

    public StringFilter getComBytes() {
        return comBytes;
    }

    public StringFilter comBytes() {
        if (comBytes == null) {
            comBytes = new StringFilter();
        }
        return comBytes;
    }

    public void setComBytes(StringFilter comBytes) {
        this.comBytes = comBytes;
    }

    public StringFilter getComDatabit() {
        return comDatabit;
    }

    public StringFilter comDatabit() {
        if (comDatabit == null) {
            comDatabit = new StringFilter();
        }
        return comDatabit;
    }

    public void setComDatabit(StringFilter comDatabit) {
        this.comDatabit = comDatabit;
    }

    public StringFilter getComParitycheck() {
        return comParitycheck;
    }

    public StringFilter comParitycheck() {
        if (comParitycheck == null) {
            comParitycheck = new StringFilter();
        }
        return comParitycheck;
    }

    public void setComParitycheck(StringFilter comParitycheck) {
        this.comParitycheck = comParitycheck;
    }

    public StringFilter getComStopbit() {
        return comStopbit;
    }

    public StringFilter comStopbit() {
        if (comStopbit == null) {
            comStopbit = new StringFilter();
        }
        return comStopbit;
    }

    public void setComStopbit(StringFilter comStopbit) {
        this.comStopbit = comStopbit;
    }

    public LongFilter getComFunction() {
        return comFunction;
    }

    public LongFilter comFunction() {
        if (comFunction == null) {
            comFunction = new LongFilter();
        }
        return comFunction;
    }

    public void setComFunction(LongFilter comFunction) {
        this.comFunction = comFunction;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final ComsetCriteria that = (ComsetCriteria) o;
        return (
            Objects.equals(id, that.id) &&
            Objects.equals(comNum, that.comNum) &&
            Objects.equals(comBytes, that.comBytes) &&
            Objects.equals(comDatabit, that.comDatabit) &&
            Objects.equals(comParitycheck, that.comParitycheck) &&
            Objects.equals(comStopbit, that.comStopbit) &&
            Objects.equals(comFunction, that.comFunction)
        );
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, comNum, comBytes, comDatabit, comParitycheck, comStopbit, comFunction);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ComsetCriteria{" +
            (id != null ? "id=" + id + ", " : "") +
            (comNum != null ? "comNum=" + comNum + ", " : "") +
            (comBytes != null ? "comBytes=" + comBytes + ", " : "") +
            (comDatabit != null ? "comDatabit=" + comDatabit + ", " : "") +
            (comParitycheck != null ? "comParitycheck=" + comParitycheck + ", " : "") +
            (comStopbit != null ? "comStopbit=" + comStopbit + ", " : "") +
            (comFunction != null ? "comFunction=" + comFunction + ", " : "") +
            "}";
    }
}
