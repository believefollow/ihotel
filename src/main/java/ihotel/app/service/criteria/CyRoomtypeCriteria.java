package ihotel.app.service.criteria;

import java.io.Serializable;
import java.util.Objects;
import tech.jhipster.service.Criteria;
import tech.jhipster.service.filter.BigDecimalFilter;
import tech.jhipster.service.filter.BooleanFilter;
import tech.jhipster.service.filter.DoubleFilter;
import tech.jhipster.service.filter.Filter;
import tech.jhipster.service.filter.FloatFilter;
import tech.jhipster.service.filter.IntegerFilter;
import tech.jhipster.service.filter.LongFilter;
import tech.jhipster.service.filter.StringFilter;

/**
 * Criteria class for the {@link ihotel.app.domain.CyRoomtype} entity. This class is used
 * in {@link ihotel.app.web.rest.CyRoomtypeResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /cy-roomtypes?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class CyRoomtypeCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter rtdm;

    private BigDecimalFilter minc;

    private BigDecimalFilter servicerate;

    private StringFilter printer;

    private LongFilter printnum;

    public CyRoomtypeCriteria() {}

    public CyRoomtypeCriteria(CyRoomtypeCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.rtdm = other.rtdm == null ? null : other.rtdm.copy();
        this.minc = other.minc == null ? null : other.minc.copy();
        this.servicerate = other.servicerate == null ? null : other.servicerate.copy();
        this.printer = other.printer == null ? null : other.printer.copy();
        this.printnum = other.printnum == null ? null : other.printnum.copy();
    }

    @Override
    public CyRoomtypeCriteria copy() {
        return new CyRoomtypeCriteria(this);
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

    public StringFilter getRtdm() {
        return rtdm;
    }

    public StringFilter rtdm() {
        if (rtdm == null) {
            rtdm = new StringFilter();
        }
        return rtdm;
    }

    public void setRtdm(StringFilter rtdm) {
        this.rtdm = rtdm;
    }

    public BigDecimalFilter getMinc() {
        return minc;
    }

    public BigDecimalFilter minc() {
        if (minc == null) {
            minc = new BigDecimalFilter();
        }
        return minc;
    }

    public void setMinc(BigDecimalFilter minc) {
        this.minc = minc;
    }

    public BigDecimalFilter getServicerate() {
        return servicerate;
    }

    public BigDecimalFilter servicerate() {
        if (servicerate == null) {
            servicerate = new BigDecimalFilter();
        }
        return servicerate;
    }

    public void setServicerate(BigDecimalFilter servicerate) {
        this.servicerate = servicerate;
    }

    public StringFilter getPrinter() {
        return printer;
    }

    public StringFilter printer() {
        if (printer == null) {
            printer = new StringFilter();
        }
        return printer;
    }

    public void setPrinter(StringFilter printer) {
        this.printer = printer;
    }

    public LongFilter getPrintnum() {
        return printnum;
    }

    public LongFilter printnum() {
        if (printnum == null) {
            printnum = new LongFilter();
        }
        return printnum;
    }

    public void setPrintnum(LongFilter printnum) {
        this.printnum = printnum;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final CyRoomtypeCriteria that = (CyRoomtypeCriteria) o;
        return (
            Objects.equals(id, that.id) &&
            Objects.equals(rtdm, that.rtdm) &&
            Objects.equals(minc, that.minc) &&
            Objects.equals(servicerate, that.servicerate) &&
            Objects.equals(printer, that.printer) &&
            Objects.equals(printnum, that.printnum)
        );
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, rtdm, minc, servicerate, printer, printnum);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "CyRoomtypeCriteria{" +
            (id != null ? "id=" + id + ", " : "") +
            (rtdm != null ? "rtdm=" + rtdm + ", " : "") +
            (minc != null ? "minc=" + minc + ", " : "") +
            (servicerate != null ? "servicerate=" + servicerate + ", " : "") +
            (printer != null ? "printer=" + printer + ", " : "") +
            (printnum != null ? "printnum=" + printnum + ", " : "") +
            "}";
    }
}
