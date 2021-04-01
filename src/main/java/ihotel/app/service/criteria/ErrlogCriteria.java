package ihotel.app.service.criteria;

import java.io.Serializable;
import java.util.Objects;
import tech.jhipster.service.Criteria;
import tech.jhipster.service.filter.BooleanFilter;
import tech.jhipster.service.filter.DoubleFilter;
import tech.jhipster.service.filter.Filter;
import tech.jhipster.service.filter.FloatFilter;
import tech.jhipster.service.filter.InstantFilter;
import tech.jhipster.service.filter.IntegerFilter;
import tech.jhipster.service.filter.LongFilter;
import tech.jhipster.service.filter.StringFilter;

/**
 * Criteria class for the {@link ihotel.app.domain.Errlog} entity. This class is used
 * in {@link ihotel.app.web.rest.ErrlogResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /errlogs?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class ErrlogCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private LongFilter iderrlog;

    private LongFilter errnumber;

    private StringFilter errtext;

    private StringFilter errwindowmenu;

    private StringFilter errobject;

    private StringFilter errevent;

    private LongFilter errline;

    private InstantFilter errtime;

    private BooleanFilter sumbitsign;

    private StringFilter bmpfile;

    public ErrlogCriteria() {}

    public ErrlogCriteria(ErrlogCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.iderrlog = other.iderrlog == null ? null : other.iderrlog.copy();
        this.errnumber = other.errnumber == null ? null : other.errnumber.copy();
        this.errtext = other.errtext == null ? null : other.errtext.copy();
        this.errwindowmenu = other.errwindowmenu == null ? null : other.errwindowmenu.copy();
        this.errobject = other.errobject == null ? null : other.errobject.copy();
        this.errevent = other.errevent == null ? null : other.errevent.copy();
        this.errline = other.errline == null ? null : other.errline.copy();
        this.errtime = other.errtime == null ? null : other.errtime.copy();
        this.sumbitsign = other.sumbitsign == null ? null : other.sumbitsign.copy();
        this.bmpfile = other.bmpfile == null ? null : other.bmpfile.copy();
    }

    @Override
    public ErrlogCriteria copy() {
        return new ErrlogCriteria(this);
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

    public LongFilter getIderrlog() {
        return iderrlog;
    }

    public LongFilter iderrlog() {
        if (iderrlog == null) {
            iderrlog = new LongFilter();
        }
        return iderrlog;
    }

    public void setIderrlog(LongFilter iderrlog) {
        this.iderrlog = iderrlog;
    }

    public LongFilter getErrnumber() {
        return errnumber;
    }

    public LongFilter errnumber() {
        if (errnumber == null) {
            errnumber = new LongFilter();
        }
        return errnumber;
    }

    public void setErrnumber(LongFilter errnumber) {
        this.errnumber = errnumber;
    }

    public StringFilter getErrtext() {
        return errtext;
    }

    public StringFilter errtext() {
        if (errtext == null) {
            errtext = new StringFilter();
        }
        return errtext;
    }

    public void setErrtext(StringFilter errtext) {
        this.errtext = errtext;
    }

    public StringFilter getErrwindowmenu() {
        return errwindowmenu;
    }

    public StringFilter errwindowmenu() {
        if (errwindowmenu == null) {
            errwindowmenu = new StringFilter();
        }
        return errwindowmenu;
    }

    public void setErrwindowmenu(StringFilter errwindowmenu) {
        this.errwindowmenu = errwindowmenu;
    }

    public StringFilter getErrobject() {
        return errobject;
    }

    public StringFilter errobject() {
        if (errobject == null) {
            errobject = new StringFilter();
        }
        return errobject;
    }

    public void setErrobject(StringFilter errobject) {
        this.errobject = errobject;
    }

    public StringFilter getErrevent() {
        return errevent;
    }

    public StringFilter errevent() {
        if (errevent == null) {
            errevent = new StringFilter();
        }
        return errevent;
    }

    public void setErrevent(StringFilter errevent) {
        this.errevent = errevent;
    }

    public LongFilter getErrline() {
        return errline;
    }

    public LongFilter errline() {
        if (errline == null) {
            errline = new LongFilter();
        }
        return errline;
    }

    public void setErrline(LongFilter errline) {
        this.errline = errline;
    }

    public InstantFilter getErrtime() {
        return errtime;
    }

    public InstantFilter errtime() {
        if (errtime == null) {
            errtime = new InstantFilter();
        }
        return errtime;
    }

    public void setErrtime(InstantFilter errtime) {
        this.errtime = errtime;
    }

    public BooleanFilter getSumbitsign() {
        return sumbitsign;
    }

    public BooleanFilter sumbitsign() {
        if (sumbitsign == null) {
            sumbitsign = new BooleanFilter();
        }
        return sumbitsign;
    }

    public void setSumbitsign(BooleanFilter sumbitsign) {
        this.sumbitsign = sumbitsign;
    }

    public StringFilter getBmpfile() {
        return bmpfile;
    }

    public StringFilter bmpfile() {
        if (bmpfile == null) {
            bmpfile = new StringFilter();
        }
        return bmpfile;
    }

    public void setBmpfile(StringFilter bmpfile) {
        this.bmpfile = bmpfile;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final ErrlogCriteria that = (ErrlogCriteria) o;
        return (
            Objects.equals(id, that.id) &&
            Objects.equals(iderrlog, that.iderrlog) &&
            Objects.equals(errnumber, that.errnumber) &&
            Objects.equals(errtext, that.errtext) &&
            Objects.equals(errwindowmenu, that.errwindowmenu) &&
            Objects.equals(errobject, that.errobject) &&
            Objects.equals(errevent, that.errevent) &&
            Objects.equals(errline, that.errline) &&
            Objects.equals(errtime, that.errtime) &&
            Objects.equals(sumbitsign, that.sumbitsign) &&
            Objects.equals(bmpfile, that.bmpfile)
        );
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, iderrlog, errnumber, errtext, errwindowmenu, errobject, errevent, errline, errtime, sumbitsign, bmpfile);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ErrlogCriteria{" +
            (id != null ? "id=" + id + ", " : "") +
            (iderrlog != null ? "iderrlog=" + iderrlog + ", " : "") +
            (errnumber != null ? "errnumber=" + errnumber + ", " : "") +
            (errtext != null ? "errtext=" + errtext + ", " : "") +
            (errwindowmenu != null ? "errwindowmenu=" + errwindowmenu + ", " : "") +
            (errobject != null ? "errobject=" + errobject + ", " : "") +
            (errevent != null ? "errevent=" + errevent + ", " : "") +
            (errline != null ? "errline=" + errline + ", " : "") +
            (errtime != null ? "errtime=" + errtime + ", " : "") +
            (sumbitsign != null ? "sumbitsign=" + sumbitsign + ", " : "") +
            (bmpfile != null ? "bmpfile=" + bmpfile + ", " : "") +
            "}";
    }
}
