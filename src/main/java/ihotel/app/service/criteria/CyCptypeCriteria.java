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
 * Criteria class for the {@link ihotel.app.domain.CyCptype} entity. This class is used
 * in {@link ihotel.app.web.rest.CyCptypeResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /cy-cptypes?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class CyCptypeCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter cptdm;

    private StringFilter cptname;

    private BooleanFilter printsign;

    private StringFilter printer;

    private LongFilter printnum;

    private LongFilter printcut;

    private BooleanFilter syssign;

    private StringFilter typesign;

    private StringFilter qy;

    public CyCptypeCriteria() {}

    public CyCptypeCriteria(CyCptypeCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.cptdm = other.cptdm == null ? null : other.cptdm.copy();
        this.cptname = other.cptname == null ? null : other.cptname.copy();
        this.printsign = other.printsign == null ? null : other.printsign.copy();
        this.printer = other.printer == null ? null : other.printer.copy();
        this.printnum = other.printnum == null ? null : other.printnum.copy();
        this.printcut = other.printcut == null ? null : other.printcut.copy();
        this.syssign = other.syssign == null ? null : other.syssign.copy();
        this.typesign = other.typesign == null ? null : other.typesign.copy();
        this.qy = other.qy == null ? null : other.qy.copy();
    }

    @Override
    public CyCptypeCriteria copy() {
        return new CyCptypeCriteria(this);
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

    public StringFilter getCptdm() {
        return cptdm;
    }

    public StringFilter cptdm() {
        if (cptdm == null) {
            cptdm = new StringFilter();
        }
        return cptdm;
    }

    public void setCptdm(StringFilter cptdm) {
        this.cptdm = cptdm;
    }

    public StringFilter getCptname() {
        return cptname;
    }

    public StringFilter cptname() {
        if (cptname == null) {
            cptname = new StringFilter();
        }
        return cptname;
    }

    public void setCptname(StringFilter cptname) {
        this.cptname = cptname;
    }

    public BooleanFilter getPrintsign() {
        return printsign;
    }

    public BooleanFilter printsign() {
        if (printsign == null) {
            printsign = new BooleanFilter();
        }
        return printsign;
    }

    public void setPrintsign(BooleanFilter printsign) {
        this.printsign = printsign;
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

    public LongFilter getPrintcut() {
        return printcut;
    }

    public LongFilter printcut() {
        if (printcut == null) {
            printcut = new LongFilter();
        }
        return printcut;
    }

    public void setPrintcut(LongFilter printcut) {
        this.printcut = printcut;
    }

    public BooleanFilter getSyssign() {
        return syssign;
    }

    public BooleanFilter syssign() {
        if (syssign == null) {
            syssign = new BooleanFilter();
        }
        return syssign;
    }

    public void setSyssign(BooleanFilter syssign) {
        this.syssign = syssign;
    }

    public StringFilter getTypesign() {
        return typesign;
    }

    public StringFilter typesign() {
        if (typesign == null) {
            typesign = new StringFilter();
        }
        return typesign;
    }

    public void setTypesign(StringFilter typesign) {
        this.typesign = typesign;
    }

    public StringFilter getQy() {
        return qy;
    }

    public StringFilter qy() {
        if (qy == null) {
            qy = new StringFilter();
        }
        return qy;
    }

    public void setQy(StringFilter qy) {
        this.qy = qy;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final CyCptypeCriteria that = (CyCptypeCriteria) o;
        return (
            Objects.equals(id, that.id) &&
            Objects.equals(cptdm, that.cptdm) &&
            Objects.equals(cptname, that.cptname) &&
            Objects.equals(printsign, that.printsign) &&
            Objects.equals(printer, that.printer) &&
            Objects.equals(printnum, that.printnum) &&
            Objects.equals(printcut, that.printcut) &&
            Objects.equals(syssign, that.syssign) &&
            Objects.equals(typesign, that.typesign) &&
            Objects.equals(qy, that.qy)
        );
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, cptdm, cptname, printsign, printer, printnum, printcut, syssign, typesign, qy);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "CyCptypeCriteria{" +
            (id != null ? "id=" + id + ", " : "") +
            (cptdm != null ? "cptdm=" + cptdm + ", " : "") +
            (cptname != null ? "cptname=" + cptname + ", " : "") +
            (printsign != null ? "printsign=" + printsign + ", " : "") +
            (printer != null ? "printer=" + printer + ", " : "") +
            (printnum != null ? "printnum=" + printnum + ", " : "") +
            (printcut != null ? "printcut=" + printcut + ", " : "") +
            (syssign != null ? "syssign=" + syssign + ", " : "") +
            (typesign != null ? "typesign=" + typesign + ", " : "") +
            (qy != null ? "qy=" + qy + ", " : "") +
            "}";
    }
}
