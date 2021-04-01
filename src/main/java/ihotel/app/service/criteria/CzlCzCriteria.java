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
 * Criteria class for the {@link ihotel.app.domain.CzlCz} entity. This class is used
 * in {@link ihotel.app.web.rest.CzlCzResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /czl-czs?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class CzlCzCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private InstantFilter tjrq;

    private LongFilter typeid;

    private StringFilter type;

    private LongFilter fjsl;

    private BigDecimalFilter kfl;

    private BigDecimalFilter pjz;

    private BigDecimalFilter ysfz;

    private BigDecimalFilter sjfz;

    private BigDecimalFilter fzcz;

    private BigDecimalFilter pjzcj;

    private BigDecimalFilter kfsM;

    private BigDecimalFilter kflM;

    private BigDecimalFilter pjzM;

    private BigDecimalFilter fzsr;

    private BigDecimalFilter dayz;

    private InstantFilter hoteltime;

    private StringFilter empn;

    private BigDecimalFilter monthz;

    private StringFilter hoteldm;

    private LongFilter isnew;

    public CzlCzCriteria() {}

    public CzlCzCriteria(CzlCzCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.tjrq = other.tjrq == null ? null : other.tjrq.copy();
        this.typeid = other.typeid == null ? null : other.typeid.copy();
        this.type = other.type == null ? null : other.type.copy();
        this.fjsl = other.fjsl == null ? null : other.fjsl.copy();
        this.kfl = other.kfl == null ? null : other.kfl.copy();
        this.pjz = other.pjz == null ? null : other.pjz.copy();
        this.ysfz = other.ysfz == null ? null : other.ysfz.copy();
        this.sjfz = other.sjfz == null ? null : other.sjfz.copy();
        this.fzcz = other.fzcz == null ? null : other.fzcz.copy();
        this.pjzcj = other.pjzcj == null ? null : other.pjzcj.copy();
        this.kfsM = other.kfsM == null ? null : other.kfsM.copy();
        this.kflM = other.kflM == null ? null : other.kflM.copy();
        this.pjzM = other.pjzM == null ? null : other.pjzM.copy();
        this.fzsr = other.fzsr == null ? null : other.fzsr.copy();
        this.dayz = other.dayz == null ? null : other.dayz.copy();
        this.hoteltime = other.hoteltime == null ? null : other.hoteltime.copy();
        this.empn = other.empn == null ? null : other.empn.copy();
        this.monthz = other.monthz == null ? null : other.monthz.copy();
        this.hoteldm = other.hoteldm == null ? null : other.hoteldm.copy();
        this.isnew = other.isnew == null ? null : other.isnew.copy();
    }

    @Override
    public CzlCzCriteria copy() {
        return new CzlCzCriteria(this);
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

    public InstantFilter getTjrq() {
        return tjrq;
    }

    public InstantFilter tjrq() {
        if (tjrq == null) {
            tjrq = new InstantFilter();
        }
        return tjrq;
    }

    public void setTjrq(InstantFilter tjrq) {
        this.tjrq = tjrq;
    }

    public LongFilter getTypeid() {
        return typeid;
    }

    public LongFilter typeid() {
        if (typeid == null) {
            typeid = new LongFilter();
        }
        return typeid;
    }

    public void setTypeid(LongFilter typeid) {
        this.typeid = typeid;
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

    public LongFilter getFjsl() {
        return fjsl;
    }

    public LongFilter fjsl() {
        if (fjsl == null) {
            fjsl = new LongFilter();
        }
        return fjsl;
    }

    public void setFjsl(LongFilter fjsl) {
        this.fjsl = fjsl;
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

    public BigDecimalFilter getYsfz() {
        return ysfz;
    }

    public BigDecimalFilter ysfz() {
        if (ysfz == null) {
            ysfz = new BigDecimalFilter();
        }
        return ysfz;
    }

    public void setYsfz(BigDecimalFilter ysfz) {
        this.ysfz = ysfz;
    }

    public BigDecimalFilter getSjfz() {
        return sjfz;
    }

    public BigDecimalFilter sjfz() {
        if (sjfz == null) {
            sjfz = new BigDecimalFilter();
        }
        return sjfz;
    }

    public void setSjfz(BigDecimalFilter sjfz) {
        this.sjfz = sjfz;
    }

    public BigDecimalFilter getFzcz() {
        return fzcz;
    }

    public BigDecimalFilter fzcz() {
        if (fzcz == null) {
            fzcz = new BigDecimalFilter();
        }
        return fzcz;
    }

    public void setFzcz(BigDecimalFilter fzcz) {
        this.fzcz = fzcz;
    }

    public BigDecimalFilter getPjzcj() {
        return pjzcj;
    }

    public BigDecimalFilter pjzcj() {
        if (pjzcj == null) {
            pjzcj = new BigDecimalFilter();
        }
        return pjzcj;
    }

    public void setPjzcj(BigDecimalFilter pjzcj) {
        this.pjzcj = pjzcj;
    }

    public BigDecimalFilter getKfsM() {
        return kfsM;
    }

    public BigDecimalFilter kfsM() {
        if (kfsM == null) {
            kfsM = new BigDecimalFilter();
        }
        return kfsM;
    }

    public void setKfsM(BigDecimalFilter kfsM) {
        this.kfsM = kfsM;
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

    public BigDecimalFilter getDayz() {
        return dayz;
    }

    public BigDecimalFilter dayz() {
        if (dayz == null) {
            dayz = new BigDecimalFilter();
        }
        return dayz;
    }

    public void setDayz(BigDecimalFilter dayz) {
        this.dayz = dayz;
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

    public BigDecimalFilter getMonthz() {
        return monthz;
    }

    public BigDecimalFilter monthz() {
        if (monthz == null) {
            monthz = new BigDecimalFilter();
        }
        return monthz;
    }

    public void setMonthz(BigDecimalFilter monthz) {
        this.monthz = monthz;
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
        final CzlCzCriteria that = (CzlCzCriteria) o;
        return (
            Objects.equals(id, that.id) &&
            Objects.equals(tjrq, that.tjrq) &&
            Objects.equals(typeid, that.typeid) &&
            Objects.equals(type, that.type) &&
            Objects.equals(fjsl, that.fjsl) &&
            Objects.equals(kfl, that.kfl) &&
            Objects.equals(pjz, that.pjz) &&
            Objects.equals(ysfz, that.ysfz) &&
            Objects.equals(sjfz, that.sjfz) &&
            Objects.equals(fzcz, that.fzcz) &&
            Objects.equals(pjzcj, that.pjzcj) &&
            Objects.equals(kfsM, that.kfsM) &&
            Objects.equals(kflM, that.kflM) &&
            Objects.equals(pjzM, that.pjzM) &&
            Objects.equals(fzsr, that.fzsr) &&
            Objects.equals(dayz, that.dayz) &&
            Objects.equals(hoteltime, that.hoteltime) &&
            Objects.equals(empn, that.empn) &&
            Objects.equals(monthz, that.monthz) &&
            Objects.equals(hoteldm, that.hoteldm) &&
            Objects.equals(isnew, that.isnew)
        );
    }

    @Override
    public int hashCode() {
        return Objects.hash(
            id,
            tjrq,
            typeid,
            type,
            fjsl,
            kfl,
            pjz,
            ysfz,
            sjfz,
            fzcz,
            pjzcj,
            kfsM,
            kflM,
            pjzM,
            fzsr,
            dayz,
            hoteltime,
            empn,
            monthz,
            hoteldm,
            isnew
        );
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "CzlCzCriteria{" +
            (id != null ? "id=" + id + ", " : "") +
            (tjrq != null ? "tjrq=" + tjrq + ", " : "") +
            (typeid != null ? "typeid=" + typeid + ", " : "") +
            (type != null ? "type=" + type + ", " : "") +
            (fjsl != null ? "fjsl=" + fjsl + ", " : "") +
            (kfl != null ? "kfl=" + kfl + ", " : "") +
            (pjz != null ? "pjz=" + pjz + ", " : "") +
            (ysfz != null ? "ysfz=" + ysfz + ", " : "") +
            (sjfz != null ? "sjfz=" + sjfz + ", " : "") +
            (fzcz != null ? "fzcz=" + fzcz + ", " : "") +
            (pjzcj != null ? "pjzcj=" + pjzcj + ", " : "") +
            (kfsM != null ? "kfsM=" + kfsM + ", " : "") +
            (kflM != null ? "kflM=" + kflM + ", " : "") +
            (pjzM != null ? "pjzM=" + pjzM + ", " : "") +
            (fzsr != null ? "fzsr=" + fzsr + ", " : "") +
            (dayz != null ? "dayz=" + dayz + ", " : "") +
            (hoteltime != null ? "hoteltime=" + hoteltime + ", " : "") +
            (empn != null ? "empn=" + empn + ", " : "") +
            (monthz != null ? "monthz=" + monthz + ", " : "") +
            (hoteldm != null ? "hoteldm=" + hoteldm + ", " : "") +
            (isnew != null ? "isnew=" + isnew + ", " : "") +
            "}";
    }
}
