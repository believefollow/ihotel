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
 * Criteria class for the {@link ihotel.app.domain.DxSedinfo} entity. This class is used
 * in {@link ihotel.app.web.rest.DxSedinfoResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /dx-sedinfos?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class DxSedinfoCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter yddx;

    private StringFilter yddxmemo;

    private StringFilter qxyddx;

    private StringFilter qxydmemo;

    private StringFilter czdx;

    private StringFilter czmemo;

    private StringFilter qxczdx;

    private StringFilter qxczmemo;

    private StringFilter yyedx;

    private StringFilter yyememo;

    private StringFilter fstime;

    private StringFilter sffshm;

    private StringFilter rzdx;

    private StringFilter rzdxroomn;

    private StringFilter jfdz;

    private StringFilter blhy;

    private StringFilter rzmemo;

    private StringFilter blhymemo;

    private StringFilter tfdx;

    private StringFilter tfdxmemo;

    private StringFilter fslb;

    private StringFilter fslbmemo;

    public DxSedinfoCriteria() {}

    public DxSedinfoCriteria(DxSedinfoCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.yddx = other.yddx == null ? null : other.yddx.copy();
        this.yddxmemo = other.yddxmemo == null ? null : other.yddxmemo.copy();
        this.qxyddx = other.qxyddx == null ? null : other.qxyddx.copy();
        this.qxydmemo = other.qxydmemo == null ? null : other.qxydmemo.copy();
        this.czdx = other.czdx == null ? null : other.czdx.copy();
        this.czmemo = other.czmemo == null ? null : other.czmemo.copy();
        this.qxczdx = other.qxczdx == null ? null : other.qxczdx.copy();
        this.qxczmemo = other.qxczmemo == null ? null : other.qxczmemo.copy();
        this.yyedx = other.yyedx == null ? null : other.yyedx.copy();
        this.yyememo = other.yyememo == null ? null : other.yyememo.copy();
        this.fstime = other.fstime == null ? null : other.fstime.copy();
        this.sffshm = other.sffshm == null ? null : other.sffshm.copy();
        this.rzdx = other.rzdx == null ? null : other.rzdx.copy();
        this.rzdxroomn = other.rzdxroomn == null ? null : other.rzdxroomn.copy();
        this.jfdz = other.jfdz == null ? null : other.jfdz.copy();
        this.blhy = other.blhy == null ? null : other.blhy.copy();
        this.rzmemo = other.rzmemo == null ? null : other.rzmemo.copy();
        this.blhymemo = other.blhymemo == null ? null : other.blhymemo.copy();
        this.tfdx = other.tfdx == null ? null : other.tfdx.copy();
        this.tfdxmemo = other.tfdxmemo == null ? null : other.tfdxmemo.copy();
        this.fslb = other.fslb == null ? null : other.fslb.copy();
        this.fslbmemo = other.fslbmemo == null ? null : other.fslbmemo.copy();
    }

    @Override
    public DxSedinfoCriteria copy() {
        return new DxSedinfoCriteria(this);
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

    public StringFilter getYddx() {
        return yddx;
    }

    public StringFilter yddx() {
        if (yddx == null) {
            yddx = new StringFilter();
        }
        return yddx;
    }

    public void setYddx(StringFilter yddx) {
        this.yddx = yddx;
    }

    public StringFilter getYddxmemo() {
        return yddxmemo;
    }

    public StringFilter yddxmemo() {
        if (yddxmemo == null) {
            yddxmemo = new StringFilter();
        }
        return yddxmemo;
    }

    public void setYddxmemo(StringFilter yddxmemo) {
        this.yddxmemo = yddxmemo;
    }

    public StringFilter getQxyddx() {
        return qxyddx;
    }

    public StringFilter qxyddx() {
        if (qxyddx == null) {
            qxyddx = new StringFilter();
        }
        return qxyddx;
    }

    public void setQxyddx(StringFilter qxyddx) {
        this.qxyddx = qxyddx;
    }

    public StringFilter getQxydmemo() {
        return qxydmemo;
    }

    public StringFilter qxydmemo() {
        if (qxydmemo == null) {
            qxydmemo = new StringFilter();
        }
        return qxydmemo;
    }

    public void setQxydmemo(StringFilter qxydmemo) {
        this.qxydmemo = qxydmemo;
    }

    public StringFilter getCzdx() {
        return czdx;
    }

    public StringFilter czdx() {
        if (czdx == null) {
            czdx = new StringFilter();
        }
        return czdx;
    }

    public void setCzdx(StringFilter czdx) {
        this.czdx = czdx;
    }

    public StringFilter getCzmemo() {
        return czmemo;
    }

    public StringFilter czmemo() {
        if (czmemo == null) {
            czmemo = new StringFilter();
        }
        return czmemo;
    }

    public void setCzmemo(StringFilter czmemo) {
        this.czmemo = czmemo;
    }

    public StringFilter getQxczdx() {
        return qxczdx;
    }

    public StringFilter qxczdx() {
        if (qxczdx == null) {
            qxczdx = new StringFilter();
        }
        return qxczdx;
    }

    public void setQxczdx(StringFilter qxczdx) {
        this.qxczdx = qxczdx;
    }

    public StringFilter getQxczmemo() {
        return qxczmemo;
    }

    public StringFilter qxczmemo() {
        if (qxczmemo == null) {
            qxczmemo = new StringFilter();
        }
        return qxczmemo;
    }

    public void setQxczmemo(StringFilter qxczmemo) {
        this.qxczmemo = qxczmemo;
    }

    public StringFilter getYyedx() {
        return yyedx;
    }

    public StringFilter yyedx() {
        if (yyedx == null) {
            yyedx = new StringFilter();
        }
        return yyedx;
    }

    public void setYyedx(StringFilter yyedx) {
        this.yyedx = yyedx;
    }

    public StringFilter getYyememo() {
        return yyememo;
    }

    public StringFilter yyememo() {
        if (yyememo == null) {
            yyememo = new StringFilter();
        }
        return yyememo;
    }

    public void setYyememo(StringFilter yyememo) {
        this.yyememo = yyememo;
    }

    public StringFilter getFstime() {
        return fstime;
    }

    public StringFilter fstime() {
        if (fstime == null) {
            fstime = new StringFilter();
        }
        return fstime;
    }

    public void setFstime(StringFilter fstime) {
        this.fstime = fstime;
    }

    public StringFilter getSffshm() {
        return sffshm;
    }

    public StringFilter sffshm() {
        if (sffshm == null) {
            sffshm = new StringFilter();
        }
        return sffshm;
    }

    public void setSffshm(StringFilter sffshm) {
        this.sffshm = sffshm;
    }

    public StringFilter getRzdx() {
        return rzdx;
    }

    public StringFilter rzdx() {
        if (rzdx == null) {
            rzdx = new StringFilter();
        }
        return rzdx;
    }

    public void setRzdx(StringFilter rzdx) {
        this.rzdx = rzdx;
    }

    public StringFilter getRzdxroomn() {
        return rzdxroomn;
    }

    public StringFilter rzdxroomn() {
        if (rzdxroomn == null) {
            rzdxroomn = new StringFilter();
        }
        return rzdxroomn;
    }

    public void setRzdxroomn(StringFilter rzdxroomn) {
        this.rzdxroomn = rzdxroomn;
    }

    public StringFilter getJfdz() {
        return jfdz;
    }

    public StringFilter jfdz() {
        if (jfdz == null) {
            jfdz = new StringFilter();
        }
        return jfdz;
    }

    public void setJfdz(StringFilter jfdz) {
        this.jfdz = jfdz;
    }

    public StringFilter getBlhy() {
        return blhy;
    }

    public StringFilter blhy() {
        if (blhy == null) {
            blhy = new StringFilter();
        }
        return blhy;
    }

    public void setBlhy(StringFilter blhy) {
        this.blhy = blhy;
    }

    public StringFilter getRzmemo() {
        return rzmemo;
    }

    public StringFilter rzmemo() {
        if (rzmemo == null) {
            rzmemo = new StringFilter();
        }
        return rzmemo;
    }

    public void setRzmemo(StringFilter rzmemo) {
        this.rzmemo = rzmemo;
    }

    public StringFilter getBlhymemo() {
        return blhymemo;
    }

    public StringFilter blhymemo() {
        if (blhymemo == null) {
            blhymemo = new StringFilter();
        }
        return blhymemo;
    }

    public void setBlhymemo(StringFilter blhymemo) {
        this.blhymemo = blhymemo;
    }

    public StringFilter getTfdx() {
        return tfdx;
    }

    public StringFilter tfdx() {
        if (tfdx == null) {
            tfdx = new StringFilter();
        }
        return tfdx;
    }

    public void setTfdx(StringFilter tfdx) {
        this.tfdx = tfdx;
    }

    public StringFilter getTfdxmemo() {
        return tfdxmemo;
    }

    public StringFilter tfdxmemo() {
        if (tfdxmemo == null) {
            tfdxmemo = new StringFilter();
        }
        return tfdxmemo;
    }

    public void setTfdxmemo(StringFilter tfdxmemo) {
        this.tfdxmemo = tfdxmemo;
    }

    public StringFilter getFslb() {
        return fslb;
    }

    public StringFilter fslb() {
        if (fslb == null) {
            fslb = new StringFilter();
        }
        return fslb;
    }

    public void setFslb(StringFilter fslb) {
        this.fslb = fslb;
    }

    public StringFilter getFslbmemo() {
        return fslbmemo;
    }

    public StringFilter fslbmemo() {
        if (fslbmemo == null) {
            fslbmemo = new StringFilter();
        }
        return fslbmemo;
    }

    public void setFslbmemo(StringFilter fslbmemo) {
        this.fslbmemo = fslbmemo;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final DxSedinfoCriteria that = (DxSedinfoCriteria) o;
        return (
            Objects.equals(id, that.id) &&
            Objects.equals(yddx, that.yddx) &&
            Objects.equals(yddxmemo, that.yddxmemo) &&
            Objects.equals(qxyddx, that.qxyddx) &&
            Objects.equals(qxydmemo, that.qxydmemo) &&
            Objects.equals(czdx, that.czdx) &&
            Objects.equals(czmemo, that.czmemo) &&
            Objects.equals(qxczdx, that.qxczdx) &&
            Objects.equals(qxczmemo, that.qxczmemo) &&
            Objects.equals(yyedx, that.yyedx) &&
            Objects.equals(yyememo, that.yyememo) &&
            Objects.equals(fstime, that.fstime) &&
            Objects.equals(sffshm, that.sffshm) &&
            Objects.equals(rzdx, that.rzdx) &&
            Objects.equals(rzdxroomn, that.rzdxroomn) &&
            Objects.equals(jfdz, that.jfdz) &&
            Objects.equals(blhy, that.blhy) &&
            Objects.equals(rzmemo, that.rzmemo) &&
            Objects.equals(blhymemo, that.blhymemo) &&
            Objects.equals(tfdx, that.tfdx) &&
            Objects.equals(tfdxmemo, that.tfdxmemo) &&
            Objects.equals(fslb, that.fslb) &&
            Objects.equals(fslbmemo, that.fslbmemo)
        );
    }

    @Override
    public int hashCode() {
        return Objects.hash(
            id,
            yddx,
            yddxmemo,
            qxyddx,
            qxydmemo,
            czdx,
            czmemo,
            qxczdx,
            qxczmemo,
            yyedx,
            yyememo,
            fstime,
            sffshm,
            rzdx,
            rzdxroomn,
            jfdz,
            blhy,
            rzmemo,
            blhymemo,
            tfdx,
            tfdxmemo,
            fslb,
            fslbmemo
        );
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "DxSedinfoCriteria{" +
            (id != null ? "id=" + id + ", " : "") +
            (yddx != null ? "yddx=" + yddx + ", " : "") +
            (yddxmemo != null ? "yddxmemo=" + yddxmemo + ", " : "") +
            (qxyddx != null ? "qxyddx=" + qxyddx + ", " : "") +
            (qxydmemo != null ? "qxydmemo=" + qxydmemo + ", " : "") +
            (czdx != null ? "czdx=" + czdx + ", " : "") +
            (czmemo != null ? "czmemo=" + czmemo + ", " : "") +
            (qxczdx != null ? "qxczdx=" + qxczdx + ", " : "") +
            (qxczmemo != null ? "qxczmemo=" + qxczmemo + ", " : "") +
            (yyedx != null ? "yyedx=" + yyedx + ", " : "") +
            (yyememo != null ? "yyememo=" + yyememo + ", " : "") +
            (fstime != null ? "fstime=" + fstime + ", " : "") +
            (sffshm != null ? "sffshm=" + sffshm + ", " : "") +
            (rzdx != null ? "rzdx=" + rzdx + ", " : "") +
            (rzdxroomn != null ? "rzdxroomn=" + rzdxroomn + ", " : "") +
            (jfdz != null ? "jfdz=" + jfdz + ", " : "") +
            (blhy != null ? "blhy=" + blhy + ", " : "") +
            (rzmemo != null ? "rzmemo=" + rzmemo + ", " : "") +
            (blhymemo != null ? "blhymemo=" + blhymemo + ", " : "") +
            (tfdx != null ? "tfdx=" + tfdx + ", " : "") +
            (tfdxmemo != null ? "tfdxmemo=" + tfdxmemo + ", " : "") +
            (fslb != null ? "fslb=" + fslb + ", " : "") +
            (fslbmemo != null ? "fslbmemo=" + fslbmemo + ", " : "") +
            "}";
    }
}
