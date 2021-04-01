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
 * Criteria class for the {@link ihotel.app.domain.CzCzl2} entity. This class is used
 * in {@link ihotel.app.web.rest.CzCzl2Resource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /cz-czl-2-s?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class CzCzl2Criteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private InstantFilter dr;

    private StringFilter type;

    private LongFilter fs;

    private BigDecimalFilter kfl;

    private BigDecimalFilter fzsr;

    private BigDecimalFilter pjz;

    private LongFilter fsM;

    private BigDecimalFilter kflM;

    private BigDecimalFilter fzsrM;

    private BigDecimalFilter pjzM;

    private LongFilter fsY;

    private BigDecimalFilter kflY;

    private BigDecimalFilter fzsrY;

    private BigDecimalFilter pjzY;

    private LongFilter fsQ;

    private BigDecimalFilter kflQ;

    private BigDecimalFilter fzsrQ;

    private BigDecimalFilter pjzQ;

    private StringFilter dateY;

    private InstantFilter dqdate;

    private StringFilter empn;

    private LongFilter number;

    private LongFilter numberM;

    private LongFilter numberY;

    private StringFilter hoteldm;

    private LongFilter isnew;

    public CzCzl2Criteria() {}

    public CzCzl2Criteria(CzCzl2Criteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.dr = other.dr == null ? null : other.dr.copy();
        this.type = other.type == null ? null : other.type.copy();
        this.fs = other.fs == null ? null : other.fs.copy();
        this.kfl = other.kfl == null ? null : other.kfl.copy();
        this.fzsr = other.fzsr == null ? null : other.fzsr.copy();
        this.pjz = other.pjz == null ? null : other.pjz.copy();
        this.fsM = other.fsM == null ? null : other.fsM.copy();
        this.kflM = other.kflM == null ? null : other.kflM.copy();
        this.fzsrM = other.fzsrM == null ? null : other.fzsrM.copy();
        this.pjzM = other.pjzM == null ? null : other.pjzM.copy();
        this.fsY = other.fsY == null ? null : other.fsY.copy();
        this.kflY = other.kflY == null ? null : other.kflY.copy();
        this.fzsrY = other.fzsrY == null ? null : other.fzsrY.copy();
        this.pjzY = other.pjzY == null ? null : other.pjzY.copy();
        this.fsQ = other.fsQ == null ? null : other.fsQ.copy();
        this.kflQ = other.kflQ == null ? null : other.kflQ.copy();
        this.fzsrQ = other.fzsrQ == null ? null : other.fzsrQ.copy();
        this.pjzQ = other.pjzQ == null ? null : other.pjzQ.copy();
        this.dateY = other.dateY == null ? null : other.dateY.copy();
        this.dqdate = other.dqdate == null ? null : other.dqdate.copy();
        this.empn = other.empn == null ? null : other.empn.copy();
        this.number = other.number == null ? null : other.number.copy();
        this.numberM = other.numberM == null ? null : other.numberM.copy();
        this.numberY = other.numberY == null ? null : other.numberY.copy();
        this.hoteldm = other.hoteldm == null ? null : other.hoteldm.copy();
        this.isnew = other.isnew == null ? null : other.isnew.copy();
    }

    @Override
    public CzCzl2Criteria copy() {
        return new CzCzl2Criteria(this);
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

    public InstantFilter getDr() {
        return dr;
    }

    public InstantFilter dr() {
        if (dr == null) {
            dr = new InstantFilter();
        }
        return dr;
    }

    public void setDr(InstantFilter dr) {
        this.dr = dr;
    }

    public StringFilter getType() {
        return type;
    }

    public StringFilter type() {
        if (type == null) {
            type = new StringFilter();
        }
        return type;
    }

    public void setType(StringFilter type) {
        this.type = type;
    }

    public LongFilter getFs() {
        return fs;
    }

    public LongFilter fs() {
        if (fs == null) {
            fs = new LongFilter();
        }
        return fs;
    }

    public void setFs(LongFilter fs) {
        this.fs = fs;
    }

    public BigDecimalFilter getKfl() {
        return kfl;
    }

    public BigDecimalFilter kfl() {
        if (kfl == null) {
            kfl = new BigDecimalFilter();
        }
        return kfl;
    }

    public void setKfl(BigDecimalFilter kfl) {
        this.kfl = kfl;
    }

    public BigDecimalFilter getFzsr() {
        return fzsr;
    }

    public BigDecimalFilter fzsr() {
        if (fzsr == null) {
            fzsr = new BigDecimalFilter();
        }
        return fzsr;
    }

    public void setFzsr(BigDecimalFilter fzsr) {
        this.fzsr = fzsr;
    }

    public BigDecimalFilter getPjz() {
        return pjz;
    }

    public BigDecimalFilter pjz() {
        if (pjz == null) {
            pjz = new BigDecimalFilter();
        }
        return pjz;
    }

    public void setPjz(BigDecimalFilter pjz) {
        this.pjz = pjz;
    }

    public LongFilter getFsM() {
        return fsM;
    }

    public LongFilter fsM() {
        if (fsM == null) {
            fsM = new LongFilter();
        }
        return fsM;
    }

    public void setFsM(LongFilter fsM) {
        this.fsM = fsM;
    }

    public BigDecimalFilter getKflM() {
        return kflM;
    }

    public BigDecimalFilter kflM() {
        if (kflM == null) {
            kflM = new BigDecimalFilter();
        }
        return kflM;
    }

    public void setKflM(BigDecimalFilter kflM) {
        this.kflM = kflM;
    }

    public BigDecimalFilter getFzsrM() {
        return fzsrM;
    }

    public BigDecimalFilter fzsrM() {
        if (fzsrM == null) {
            fzsrM = new BigDecimalFilter();
        }
        return fzsrM;
    }

    public void setFzsrM(BigDecimalFilter fzsrM) {
        this.fzsrM = fzsrM;
    }

    public BigDecimalFilter getPjzM() {
        return pjzM;
    }

    public BigDecimalFilter pjzM() {
        if (pjzM == null) {
            pjzM = new BigDecimalFilter();
        }
        return pjzM;
    }

    public void setPjzM(BigDecimalFilter pjzM) {
        this.pjzM = pjzM;
    }

    public LongFilter getFsY() {
        return fsY;
    }

    public LongFilter fsY() {
        if (fsY == null) {
            fsY = new LongFilter();
        }
        return fsY;
    }

    public void setFsY(LongFilter fsY) {
        this.fsY = fsY;
    }

    public BigDecimalFilter getKflY() {
        return kflY;
    }

    public BigDecimalFilter kflY() {
        if (kflY == null) {
            kflY = new BigDecimalFilter();
        }
        return kflY;
    }

    public void setKflY(BigDecimalFilter kflY) {
        this.kflY = kflY;
    }

    public BigDecimalFilter getFzsrY() {
        return fzsrY;
    }

    public BigDecimalFilter fzsrY() {
        if (fzsrY == null) {
            fzsrY = new BigDecimalFilter();
        }
        return fzsrY;
    }

    public void setFzsrY(BigDecimalFilter fzsrY) {
        this.fzsrY = fzsrY;
    }

    public BigDecimalFilter getPjzY() {
        return pjzY;
    }

    public BigDecimalFilter pjzY() {
        if (pjzY == null) {
            pjzY = new BigDecimalFilter();
        }
        return pjzY;
    }

    public void setPjzY(BigDecimalFilter pjzY) {
        this.pjzY = pjzY;
    }

    public LongFilter getFsQ() {
        return fsQ;
    }

    public LongFilter fsQ() {
        if (fsQ == null) {
            fsQ = new LongFilter();
        }
        return fsQ;
    }

    public void setFsQ(LongFilter fsQ) {
        this.fsQ = fsQ;
    }

    public BigDecimalFilter getKflQ() {
        return kflQ;
    }

    public BigDecimalFilter kflQ() {
        if (kflQ == null) {
            kflQ = new BigDecimalFilter();
        }
        return kflQ;
    }

    public void setKflQ(BigDecimalFilter kflQ) {
        this.kflQ = kflQ;
    }

    public BigDecimalFilter getFzsrQ() {
        return fzsrQ;
    }

    public BigDecimalFilter fzsrQ() {
        if (fzsrQ == null) {
            fzsrQ = new BigDecimalFilter();
        }
        return fzsrQ;
    }

    public void setFzsrQ(BigDecimalFilter fzsrQ) {
        this.fzsrQ = fzsrQ;
    }

    public BigDecimalFilter getPjzQ() {
        return pjzQ;
    }

    public BigDecimalFilter pjzQ() {
        if (pjzQ == null) {
            pjzQ = new BigDecimalFilter();
        }
        return pjzQ;
    }

    public void setPjzQ(BigDecimalFilter pjzQ) {
        this.pjzQ = pjzQ;
    }

    public StringFilter getDateY() {
        return dateY;
    }

    public StringFilter dateY() {
        if (dateY == null) {
            dateY = new StringFilter();
        }
        return dateY;
    }

    public void setDateY(StringFilter dateY) {
        this.dateY = dateY;
    }

    public InstantFilter getDqdate() {
        return dqdate;
    }

    public InstantFilter dqdate() {
        if (dqdate == null) {
            dqdate = new InstantFilter();
        }
        return dqdate;
    }

    public void setDqdate(InstantFilter dqdate) {
        this.dqdate = dqdate;
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

    public LongFilter getNumber() {
        return number;
    }

    public LongFilter number() {
        if (number == null) {
            number = new LongFilter();
        }
        return number;
    }

    public void setNumber(LongFilter number) {
        this.number = number;
    }

    public LongFilter getNumberM() {
        return numberM;
    }

    public LongFilter numberM() {
        if (numberM == null) {
            numberM = new LongFilter();
        }
        return numberM;
    }

    public void setNumberM(LongFilter numberM) {
        this.numberM = numberM;
    }

    public LongFilter getNumberY() {
        return numberY;
    }

    public LongFilter numberY() {
        if (numberY == null) {
            numberY = new LongFilter();
        }
        return numberY;
    }

    public void setNumberY(LongFilter numberY) {
        this.numberY = numberY;
    }

    public StringFilter getHoteldm() {
        return hoteldm;
    }

    public StringFilter hoteldm() {
        if (hoteldm == null) {
            hoteldm = new StringFilter();
        }
        return hoteldm;
    }

    public void setHoteldm(StringFilter hoteldm) {
        this.hoteldm = hoteldm;
    }

    public LongFilter getIsnew() {
        return isnew;
    }

    public LongFilter isnew() {
        if (isnew == null) {
            isnew = new LongFilter();
        }
        return isnew;
    }

    public void setIsnew(LongFilter isnew) {
        this.isnew = isnew;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final CzCzl2Criteria that = (CzCzl2Criteria) o;
        return (
            Objects.equals(id, that.id) &&
            Objects.equals(dr, that.dr) &&
            Objects.equals(type, that.type) &&
            Objects.equals(fs, that.fs) &&
            Objects.equals(kfl, that.kfl) &&
            Objects.equals(fzsr, that.fzsr) &&
            Objects.equals(pjz, that.pjz) &&
            Objects.equals(fsM, that.fsM) &&
            Objects.equals(kflM, that.kflM) &&
            Objects.equals(fzsrM, that.fzsrM) &&
            Objects.equals(pjzM, that.pjzM) &&
            Objects.equals(fsY, that.fsY) &&
            Objects.equals(kflY, that.kflY) &&
            Objects.equals(fzsrY, that.fzsrY) &&
            Objects.equals(pjzY, that.pjzY) &&
            Objects.equals(fsQ, that.fsQ) &&
            Objects.equals(kflQ, that.kflQ) &&
            Objects.equals(fzsrQ, that.fzsrQ) &&
            Objects.equals(pjzQ, that.pjzQ) &&
            Objects.equals(dateY, that.dateY) &&
            Objects.equals(dqdate, that.dqdate) &&
            Objects.equals(empn, that.empn) &&
            Objects.equals(number, that.number) &&
            Objects.equals(numberM, that.numberM) &&
            Objects.equals(numberY, that.numberY) &&
            Objects.equals(hoteldm, that.hoteldm) &&
            Objects.equals(isnew, that.isnew)
        );
    }

    @Override
    public int hashCode() {
        return Objects.hash(
            id,
            dr,
            type,
            fs,
            kfl,
            fzsr,
            pjz,
            fsM,
            kflM,
            fzsrM,
            pjzM,
            fsY,
            kflY,
            fzsrY,
            pjzY,
            fsQ,
            kflQ,
            fzsrQ,
            pjzQ,
            dateY,
            dqdate,
            empn,
            number,
            numberM,
            numberY,
            hoteldm,
            isnew
        );
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "CzCzl2Criteria{" +
            (id != null ? "id=" + id + ", " : "") +
            (dr != null ? "dr=" + dr + ", " : "") +
            (type != null ? "type=" + type + ", " : "") +
            (fs != null ? "fs=" + fs + ", " : "") +
            (kfl != null ? "kfl=" + kfl + ", " : "") +
            (fzsr != null ? "fzsr=" + fzsr + ", " : "") +
            (pjz != null ? "pjz=" + pjz + ", " : "") +
            (fsM != null ? "fsM=" + fsM + ", " : "") +
            (kflM != null ? "kflM=" + kflM + ", " : "") +
            (fzsrM != null ? "fzsrM=" + fzsrM + ", " : "") +
            (pjzM != null ? "pjzM=" + pjzM + ", " : "") +
            (fsY != null ? "fsY=" + fsY + ", " : "") +
            (kflY != null ? "kflY=" + kflY + ", " : "") +
            (fzsrY != null ? "fzsrY=" + fzsrY + ", " : "") +
            (pjzY != null ? "pjzY=" + pjzY + ", " : "") +
            (fsQ != null ? "fsQ=" + fsQ + ", " : "") +
            (kflQ != null ? "kflQ=" + kflQ + ", " : "") +
            (fzsrQ != null ? "fzsrQ=" + fzsrQ + ", " : "") +
            (pjzQ != null ? "pjzQ=" + pjzQ + ", " : "") +
            (dateY != null ? "dateY=" + dateY + ", " : "") +
            (dqdate != null ? "dqdate=" + dqdate + ", " : "") +
            (empn != null ? "empn=" + empn + ", " : "") +
            (number != null ? "number=" + number + ", " : "") +
            (numberM != null ? "numberM=" + numberM + ", " : "") +
            (numberY != null ? "numberY=" + numberY + ", " : "") +
            (hoteldm != null ? "hoteldm=" + hoteldm + ", " : "") +
            (isnew != null ? "isnew=" + isnew + ", " : "") +
            "}";
    }
}
