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
 * Criteria class for the {@link ihotel.app.domain.DPdb} entity. This class is used
 * in {@link ihotel.app.web.rest.DPdbResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /d-pdbs?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class DPdbCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private InstantFilter begindate;

    private InstantFilter enddate;

    private StringFilter bm;

    private StringFilter spmc;

    private BigDecimalFilter qcsl;

    private BigDecimalFilter rksl;

    private BigDecimalFilter xssl;

    private BigDecimalFilter dbsl;

    private BigDecimalFilter qtck;

    private BigDecimalFilter jcsl;

    private BigDecimalFilter swsl;

    private BigDecimalFilter pyk;

    private StringFilter memo;

    private StringFilter depot;

    private BigDecimalFilter rkje;

    private BigDecimalFilter xsje;

    private BigDecimalFilter dbje;

    private BigDecimalFilter jcje;

    private StringFilter dp;

    private BigDecimalFilter qcje;

    private BigDecimalFilter swje;

    private BigDecimalFilter qtje;

    public DPdbCriteria() {}

    public DPdbCriteria(DPdbCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.begindate = other.begindate == null ? null : other.begindate.copy();
        this.enddate = other.enddate == null ? null : other.enddate.copy();
        this.bm = other.bm == null ? null : other.bm.copy();
        this.spmc = other.spmc == null ? null : other.spmc.copy();
        this.qcsl = other.qcsl == null ? null : other.qcsl.copy();
        this.rksl = other.rksl == null ? null : other.rksl.copy();
        this.xssl = other.xssl == null ? null : other.xssl.copy();
        this.dbsl = other.dbsl == null ? null : other.dbsl.copy();
        this.qtck = other.qtck == null ? null : other.qtck.copy();
        this.jcsl = other.jcsl == null ? null : other.jcsl.copy();
        this.swsl = other.swsl == null ? null : other.swsl.copy();
        this.pyk = other.pyk == null ? null : other.pyk.copy();
        this.memo = other.memo == null ? null : other.memo.copy();
        this.depot = other.depot == null ? null : other.depot.copy();
        this.rkje = other.rkje == null ? null : other.rkje.copy();
        this.xsje = other.xsje == null ? null : other.xsje.copy();
        this.dbje = other.dbje == null ? null : other.dbje.copy();
        this.jcje = other.jcje == null ? null : other.jcje.copy();
        this.dp = other.dp == null ? null : other.dp.copy();
        this.qcje = other.qcje == null ? null : other.qcje.copy();
        this.swje = other.swje == null ? null : other.swje.copy();
        this.qtje = other.qtje == null ? null : other.qtje.copy();
    }

    @Override
    public DPdbCriteria copy() {
        return new DPdbCriteria(this);
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

    public InstantFilter getBegindate() {
        return begindate;
    }

    public InstantFilter begindate() {
        if (begindate == null) {
            begindate = new InstantFilter();
        }
        return begindate;
    }

    public void setBegindate(InstantFilter begindate) {
        this.begindate = begindate;
    }

    public InstantFilter getEnddate() {
        return enddate;
    }

    public InstantFilter enddate() {
        if (enddate == null) {
            enddate = new InstantFilter();
        }
        return enddate;
    }

    public void setEnddate(InstantFilter enddate) {
        this.enddate = enddate;
    }

    public StringFilter getBm() {
        return bm;
    }

    public StringFilter bm() {
        if (bm == null) {
            bm = new StringFilter();
        }
        return bm;
    }

    public void setBm(StringFilter bm) {
        this.bm = bm;
    }

    public StringFilter getSpmc() {
        return spmc;
    }

    public StringFilter spmc() {
        if (spmc == null) {
            spmc = new StringFilter();
        }
        return spmc;
    }

    public void setSpmc(StringFilter spmc) {
        this.spmc = spmc;
    }

    public BigDecimalFilter getQcsl() {
        return qcsl;
    }

    public BigDecimalFilter qcsl() {
        if (qcsl == null) {
            qcsl = new BigDecimalFilter();
        }
        return qcsl;
    }

    public void setQcsl(BigDecimalFilter qcsl) {
        this.qcsl = qcsl;
    }

    public BigDecimalFilter getRksl() {
        return rksl;
    }

    public BigDecimalFilter rksl() {
        if (rksl == null) {
            rksl = new BigDecimalFilter();
        }
        return rksl;
    }

    public void setRksl(BigDecimalFilter rksl) {
        this.rksl = rksl;
    }

    public BigDecimalFilter getXssl() {
        return xssl;
    }

    public BigDecimalFilter xssl() {
        if (xssl == null) {
            xssl = new BigDecimalFilter();
        }
        return xssl;
    }

    public void setXssl(BigDecimalFilter xssl) {
        this.xssl = xssl;
    }

    public BigDecimalFilter getDbsl() {
        return dbsl;
    }

    public BigDecimalFilter dbsl() {
        if (dbsl == null) {
            dbsl = new BigDecimalFilter();
        }
        return dbsl;
    }

    public void setDbsl(BigDecimalFilter dbsl) {
        this.dbsl = dbsl;
    }

    public BigDecimalFilter getQtck() {
        return qtck;
    }

    public BigDecimalFilter qtck() {
        if (qtck == null) {
            qtck = new BigDecimalFilter();
        }
        return qtck;
    }

    public void setQtck(BigDecimalFilter qtck) {
        this.qtck = qtck;
    }

    public BigDecimalFilter getJcsl() {
        return jcsl;
    }

    public BigDecimalFilter jcsl() {
        if (jcsl == null) {
            jcsl = new BigDecimalFilter();
        }
        return jcsl;
    }

    public void setJcsl(BigDecimalFilter jcsl) {
        this.jcsl = jcsl;
    }

    public BigDecimalFilter getSwsl() {
        return swsl;
    }

    public BigDecimalFilter swsl() {
        if (swsl == null) {
            swsl = new BigDecimalFilter();
        }
        return swsl;
    }

    public void setSwsl(BigDecimalFilter swsl) {
        this.swsl = swsl;
    }

    public BigDecimalFilter getPyk() {
        return pyk;
    }

    public BigDecimalFilter pyk() {
        if (pyk == null) {
            pyk = new BigDecimalFilter();
        }
        return pyk;
    }

    public void setPyk(BigDecimalFilter pyk) {
        this.pyk = pyk;
    }

    public StringFilter getMemo() {
        return memo;
    }

    public StringFilter memo() {
        if (memo == null) {
            memo = new StringFilter();
        }
        return memo;
    }

    public void setMemo(StringFilter memo) {
        this.memo = memo;
    }

    public StringFilter getDepot() {
        return depot;
    }

    public StringFilter depot() {
        if (depot == null) {
            depot = new StringFilter();
        }
        return depot;
    }

    public void setDepot(StringFilter depot) {
        this.depot = depot;
    }

    public BigDecimalFilter getRkje() {
        return rkje;
    }

    public BigDecimalFilter rkje() {
        if (rkje == null) {
            rkje = new BigDecimalFilter();
        }
        return rkje;
    }

    public void setRkje(BigDecimalFilter rkje) {
        this.rkje = rkje;
    }

    public BigDecimalFilter getXsje() {
        return xsje;
    }

    public BigDecimalFilter xsje() {
        if (xsje == null) {
            xsje = new BigDecimalFilter();
        }
        return xsje;
    }

    public void setXsje(BigDecimalFilter xsje) {
        this.xsje = xsje;
    }

    public BigDecimalFilter getDbje() {
        return dbje;
    }

    public BigDecimalFilter dbje() {
        if (dbje == null) {
            dbje = new BigDecimalFilter();
        }
        return dbje;
    }

    public void setDbje(BigDecimalFilter dbje) {
        this.dbje = dbje;
    }

    public BigDecimalFilter getJcje() {
        return jcje;
    }

    public BigDecimalFilter jcje() {
        if (jcje == null) {
            jcje = new BigDecimalFilter();
        }
        return jcje;
    }

    public void setJcje(BigDecimalFilter jcje) {
        this.jcje = jcje;
    }

    public StringFilter getDp() {
        return dp;
    }

    public StringFilter dp() {
        if (dp == null) {
            dp = new StringFilter();
        }
        return dp;
    }

    public void setDp(StringFilter dp) {
        this.dp = dp;
    }

    public BigDecimalFilter getQcje() {
        return qcje;
    }

    public BigDecimalFilter qcje() {
        if (qcje == null) {
            qcje = new BigDecimalFilter();
        }
        return qcje;
    }

    public void setQcje(BigDecimalFilter qcje) {
        this.qcje = qcje;
    }

    public BigDecimalFilter getSwje() {
        return swje;
    }

    public BigDecimalFilter swje() {
        if (swje == null) {
            swje = new BigDecimalFilter();
        }
        return swje;
    }

    public void setSwje(BigDecimalFilter swje) {
        this.swje = swje;
    }

    public BigDecimalFilter getQtje() {
        return qtje;
    }

    public BigDecimalFilter qtje() {
        if (qtje == null) {
            qtje = new BigDecimalFilter();
        }
        return qtje;
    }

    public void setQtje(BigDecimalFilter qtje) {
        this.qtje = qtje;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final DPdbCriteria that = (DPdbCriteria) o;
        return (
            Objects.equals(id, that.id) &&
            Objects.equals(begindate, that.begindate) &&
            Objects.equals(enddate, that.enddate) &&
            Objects.equals(bm, that.bm) &&
            Objects.equals(spmc, that.spmc) &&
            Objects.equals(qcsl, that.qcsl) &&
            Objects.equals(rksl, that.rksl) &&
            Objects.equals(xssl, that.xssl) &&
            Objects.equals(dbsl, that.dbsl) &&
            Objects.equals(qtck, that.qtck) &&
            Objects.equals(jcsl, that.jcsl) &&
            Objects.equals(swsl, that.swsl) &&
            Objects.equals(pyk, that.pyk) &&
            Objects.equals(memo, that.memo) &&
            Objects.equals(depot, that.depot) &&
            Objects.equals(rkje, that.rkje) &&
            Objects.equals(xsje, that.xsje) &&
            Objects.equals(dbje, that.dbje) &&
            Objects.equals(jcje, that.jcje) &&
            Objects.equals(dp, that.dp) &&
            Objects.equals(qcje, that.qcje) &&
            Objects.equals(swje, that.swje) &&
            Objects.equals(qtje, that.qtje)
        );
    }

    @Override
    public int hashCode() {
        return Objects.hash(
            id,
            begindate,
            enddate,
            bm,
            spmc,
            qcsl,
            rksl,
            xssl,
            dbsl,
            qtck,
            jcsl,
            swsl,
            pyk,
            memo,
            depot,
            rkje,
            xsje,
            dbje,
            jcje,
            dp,
            qcje,
            swje,
            qtje
        );
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "DPdbCriteria{" +
            (id != null ? "id=" + id + ", " : "") +
            (begindate != null ? "begindate=" + begindate + ", " : "") +
            (enddate != null ? "enddate=" + enddate + ", " : "") +
            (bm != null ? "bm=" + bm + ", " : "") +
            (spmc != null ? "spmc=" + spmc + ", " : "") +
            (qcsl != null ? "qcsl=" + qcsl + ", " : "") +
            (rksl != null ? "rksl=" + rksl + ", " : "") +
            (xssl != null ? "xssl=" + xssl + ", " : "") +
            (dbsl != null ? "dbsl=" + dbsl + ", " : "") +
            (qtck != null ? "qtck=" + qtck + ", " : "") +
            (jcsl != null ? "jcsl=" + jcsl + ", " : "") +
            (swsl != null ? "swsl=" + swsl + ", " : "") +
            (pyk != null ? "pyk=" + pyk + ", " : "") +
            (memo != null ? "memo=" + memo + ", " : "") +
            (depot != null ? "depot=" + depot + ", " : "") +
            (rkje != null ? "rkje=" + rkje + ", " : "") +
            (xsje != null ? "xsje=" + xsje + ", " : "") +
            (dbje != null ? "dbje=" + dbje + ", " : "") +
            (jcje != null ? "jcje=" + jcje + ", " : "") +
            (dp != null ? "dp=" + dp + ", " : "") +
            (qcje != null ? "qcje=" + qcje + ", " : "") +
            (swje != null ? "swje=" + swje + ", " : "") +
            (qtje != null ? "qtje=" + qtje + ", " : "") +
            "}";
    }
}
