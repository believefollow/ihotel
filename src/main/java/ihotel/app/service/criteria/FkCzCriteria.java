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
 * Criteria class for the {@link ihotel.app.domain.FkCz} entity. This class is used
 * in {@link ihotel.app.web.rest.FkCzResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /fk-czs?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class FkCzCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private InstantFilter hoteltime;

    private LongFilter wxf;

    private LongFilter ksf;

    private LongFilter kf;

    private LongFilter zfs;

    private LongFilter groupyd;

    private LongFilter skyd;

    private LongFilter ydwd;

    private LongFilter qxyd;

    private LongFilter isnew;

    private StringFilter hoteldm;

    private BigDecimalFilter hys;

    private BigDecimalFilter khys;

    public FkCzCriteria() {}

    public FkCzCriteria(FkCzCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.hoteltime = other.hoteltime == null ? null : other.hoteltime.copy();
        this.wxf = other.wxf == null ? null : other.wxf.copy();
        this.ksf = other.ksf == null ? null : other.ksf.copy();
        this.kf = other.kf == null ? null : other.kf.copy();
        this.zfs = other.zfs == null ? null : other.zfs.copy();
        this.groupyd = other.groupyd == null ? null : other.groupyd.copy();
        this.skyd = other.skyd == null ? null : other.skyd.copy();
        this.ydwd = other.ydwd == null ? null : other.ydwd.copy();
        this.qxyd = other.qxyd == null ? null : other.qxyd.copy();
        this.isnew = other.isnew == null ? null : other.isnew.copy();
        this.hoteldm = other.hoteldm == null ? null : other.hoteldm.copy();
        this.hys = other.hys == null ? null : other.hys.copy();
        this.khys = other.khys == null ? null : other.khys.copy();
    }

    @Override
    public FkCzCriteria copy() {
        return new FkCzCriteria(this);
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

    public LongFilter getWxf() {
        return wxf;
    }

    public LongFilter wxf() {
        if (wxf == null) {
            wxf = new LongFilter();
        }
        return wxf;
    }

    public void setWxf(LongFilter wxf) {
        this.wxf = wxf;
    }

    public LongFilter getKsf() {
        return ksf;
    }

    public LongFilter ksf() {
        if (ksf == null) {
            ksf = new LongFilter();
        }
        return ksf;
    }

    public void setKsf(LongFilter ksf) {
        this.ksf = ksf;
    }

    public LongFilter getKf() {
        return kf;
    }

    public LongFilter kf() {
        if (kf == null) {
            kf = new LongFilter();
        }
        return kf;
    }

    public void setKf(LongFilter kf) {
        this.kf = kf;
    }

    public LongFilter getZfs() {
        return zfs;
    }

    public LongFilter zfs() {
        if (zfs == null) {
            zfs = new LongFilter();
        }
        return zfs;
    }

    public void setZfs(LongFilter zfs) {
        this.zfs = zfs;
    }

    public LongFilter getGroupyd() {
        return groupyd;
    }

    public LongFilter groupyd() {
        if (groupyd == null) {
            groupyd = new LongFilter();
        }
        return groupyd;
    }

    public void setGroupyd(LongFilter groupyd) {
        this.groupyd = groupyd;
    }

    public LongFilter getSkyd() {
        return skyd;
    }

    public LongFilter skyd() {
        if (skyd == null) {
            skyd = new LongFilter();
        }
        return skyd;
    }

    public void setSkyd(LongFilter skyd) {
        this.skyd = skyd;
    }

    public LongFilter getYdwd() {
        return ydwd;
    }

    public LongFilter ydwd() {
        if (ydwd == null) {
            ydwd = new LongFilter();
        }
        return ydwd;
    }

    public void setYdwd(LongFilter ydwd) {
        this.ydwd = ydwd;
    }

    public LongFilter getQxyd() {
        return qxyd;
    }

    public LongFilter qxyd() {
        if (qxyd == null) {
            qxyd = new LongFilter();
        }
        return qxyd;
    }

    public void setQxyd(LongFilter qxyd) {
        this.qxyd = qxyd;
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

    public BigDecimalFilter getHys() {
        return hys;
    }

    public BigDecimalFilter hys() {
        if (hys == null) {
            hys = new BigDecimalFilter();
        }
        return hys;
    }

    public void setHys(BigDecimalFilter hys) {
        this.hys = hys;
    }

    public BigDecimalFilter getKhys() {
        return khys;
    }

    public BigDecimalFilter khys() {
        if (khys == null) {
            khys = new BigDecimalFilter();
        }
        return khys;
    }

    public void setKhys(BigDecimalFilter khys) {
        this.khys = khys;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final FkCzCriteria that = (FkCzCriteria) o;
        return (
            Objects.equals(id, that.id) &&
            Objects.equals(hoteltime, that.hoteltime) &&
            Objects.equals(wxf, that.wxf) &&
            Objects.equals(ksf, that.ksf) &&
            Objects.equals(kf, that.kf) &&
            Objects.equals(zfs, that.zfs) &&
            Objects.equals(groupyd, that.groupyd) &&
            Objects.equals(skyd, that.skyd) &&
            Objects.equals(ydwd, that.ydwd) &&
            Objects.equals(qxyd, that.qxyd) &&
            Objects.equals(isnew, that.isnew) &&
            Objects.equals(hoteldm, that.hoteldm) &&
            Objects.equals(hys, that.hys) &&
            Objects.equals(khys, that.khys)
        );
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, hoteltime, wxf, ksf, kf, zfs, groupyd, skyd, ydwd, qxyd, isnew, hoteldm, hys, khys);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "FkCzCriteria{" +
            (id != null ? "id=" + id + ", " : "") +
            (hoteltime != null ? "hoteltime=" + hoteltime + ", " : "") +
            (wxf != null ? "wxf=" + wxf + ", " : "") +
            (ksf != null ? "ksf=" + ksf + ", " : "") +
            (kf != null ? "kf=" + kf + ", " : "") +
            (zfs != null ? "zfs=" + zfs + ", " : "") +
            (groupyd != null ? "groupyd=" + groupyd + ", " : "") +
            (skyd != null ? "skyd=" + skyd + ", " : "") +
            (ydwd != null ? "ydwd=" + ydwd + ", " : "") +
            (qxyd != null ? "qxyd=" + qxyd + ", " : "") +
            (isnew != null ? "isnew=" + isnew + ", " : "") +
            (hoteldm != null ? "hoteldm=" + hoteldm + ", " : "") +
            (hys != null ? "hys=" + hys + ", " : "") +
            (khys != null ? "khys=" + khys + ", " : "") +
            "}";
    }
}
