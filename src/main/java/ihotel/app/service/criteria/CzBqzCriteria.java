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
 * Criteria class for the {@link ihotel.app.domain.CzBqz} entity. This class is used
 * in {@link ihotel.app.web.rest.CzBqzResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /cz-bqzs?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class CzBqzCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private InstantFilter rq;

    private LongFilter qSl;

    private BigDecimalFilter qKfl;

    private BigDecimalFilter qPjz;

    private BigDecimalFilter qYsfz;

    private BigDecimalFilter qSjfz;

    private BigDecimalFilter qFzcz;

    private BigDecimalFilter qPjzcz;

    private LongFilter bSl;

    private BigDecimalFilter bKfl;

    private BigDecimalFilter bPjz;

    private BigDecimalFilter bYsfz;

    private BigDecimalFilter bSjfz;

    private BigDecimalFilter bFzcz;

    private BigDecimalFilter bPjzcz;

    private LongFilter zSl;

    private BigDecimalFilter zKfl;

    private BigDecimalFilter zPjz;

    private BigDecimalFilter zYsfz;

    private BigDecimalFilter zSjfz;

    private BigDecimalFilter zFzcz;

    private BigDecimalFilter zPjzcz;

    private BigDecimalFilter zk;

    public CzBqzCriteria() {}

    public CzBqzCriteria(CzBqzCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.rq = other.rq == null ? null : other.rq.copy();
        this.qSl = other.qSl == null ? null : other.qSl.copy();
        this.qKfl = other.qKfl == null ? null : other.qKfl.copy();
        this.qPjz = other.qPjz == null ? null : other.qPjz.copy();
        this.qYsfz = other.qYsfz == null ? null : other.qYsfz.copy();
        this.qSjfz = other.qSjfz == null ? null : other.qSjfz.copy();
        this.qFzcz = other.qFzcz == null ? null : other.qFzcz.copy();
        this.qPjzcz = other.qPjzcz == null ? null : other.qPjzcz.copy();
        this.bSl = other.bSl == null ? null : other.bSl.copy();
        this.bKfl = other.bKfl == null ? null : other.bKfl.copy();
        this.bPjz = other.bPjz == null ? null : other.bPjz.copy();
        this.bYsfz = other.bYsfz == null ? null : other.bYsfz.copy();
        this.bSjfz = other.bSjfz == null ? null : other.bSjfz.copy();
        this.bFzcz = other.bFzcz == null ? null : other.bFzcz.copy();
        this.bPjzcz = other.bPjzcz == null ? null : other.bPjzcz.copy();
        this.zSl = other.zSl == null ? null : other.zSl.copy();
        this.zKfl = other.zKfl == null ? null : other.zKfl.copy();
        this.zPjz = other.zPjz == null ? null : other.zPjz.copy();
        this.zYsfz = other.zYsfz == null ? null : other.zYsfz.copy();
        this.zSjfz = other.zSjfz == null ? null : other.zSjfz.copy();
        this.zFzcz = other.zFzcz == null ? null : other.zFzcz.copy();
        this.zPjzcz = other.zPjzcz == null ? null : other.zPjzcz.copy();
        this.zk = other.zk == null ? null : other.zk.copy();
    }

    @Override
    public CzBqzCriteria copy() {
        return new CzBqzCriteria(this);
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

    public InstantFilter getRq() {
        return rq;
    }

    public InstantFilter rq() {
        if (rq == null) {
            rq = new InstantFilter();
        }
        return rq;
    }

    public void setRq(InstantFilter rq) {
        this.rq = rq;
    }

    public LongFilter getqSl() {
        return qSl;
    }

    public LongFilter qSl() {
        if (qSl == null) {
            qSl = new LongFilter();
        }
        return qSl;
    }

    public void setqSl(LongFilter qSl) {
        this.qSl = qSl;
    }

    public BigDecimalFilter getqKfl() {
        return qKfl;
    }

    public BigDecimalFilter qKfl() {
        if (qKfl == null) {
            qKfl = new BigDecimalFilter();
        }
        return qKfl;
    }

    public void setqKfl(BigDecimalFilter qKfl) {
        this.qKfl = qKfl;
    }

    public BigDecimalFilter getqPjz() {
        return qPjz;
    }

    public BigDecimalFilter qPjz() {
        if (qPjz == null) {
            qPjz = new BigDecimalFilter();
        }
        return qPjz;
    }

    public void setqPjz(BigDecimalFilter qPjz) {
        this.qPjz = qPjz;
    }

    public BigDecimalFilter getqYsfz() {
        return qYsfz;
    }

    public BigDecimalFilter qYsfz() {
        if (qYsfz == null) {
            qYsfz = new BigDecimalFilter();
        }
        return qYsfz;
    }

    public void setqYsfz(BigDecimalFilter qYsfz) {
        this.qYsfz = qYsfz;
    }

    public BigDecimalFilter getqSjfz() {
        return qSjfz;
    }

    public BigDecimalFilter qSjfz() {
        if (qSjfz == null) {
            qSjfz = new BigDecimalFilter();
        }
        return qSjfz;
    }

    public void setqSjfz(BigDecimalFilter qSjfz) {
        this.qSjfz = qSjfz;
    }

    public BigDecimalFilter getqFzcz() {
        return qFzcz;
    }

    public BigDecimalFilter qFzcz() {
        if (qFzcz == null) {
            qFzcz = new BigDecimalFilter();
        }
        return qFzcz;
    }

    public void setqFzcz(BigDecimalFilter qFzcz) {
        this.qFzcz = qFzcz;
    }

    public BigDecimalFilter getqPjzcz() {
        return qPjzcz;
    }

    public BigDecimalFilter qPjzcz() {
        if (qPjzcz == null) {
            qPjzcz = new BigDecimalFilter();
        }
        return qPjzcz;
    }

    public void setqPjzcz(BigDecimalFilter qPjzcz) {
        this.qPjzcz = qPjzcz;
    }

    public LongFilter getbSl() {
        return bSl;
    }

    public LongFilter bSl() {
        if (bSl == null) {
            bSl = new LongFilter();
        }
        return bSl;
    }

    public void setbSl(LongFilter bSl) {
        this.bSl = bSl;
    }

    public BigDecimalFilter getbKfl() {
        return bKfl;
    }

    public BigDecimalFilter bKfl() {
        if (bKfl == null) {
            bKfl = new BigDecimalFilter();
        }
        return bKfl;
    }

    public void setbKfl(BigDecimalFilter bKfl) {
        this.bKfl = bKfl;
    }

    public BigDecimalFilter getbPjz() {
        return bPjz;
    }

    public BigDecimalFilter bPjz() {
        if (bPjz == null) {
            bPjz = new BigDecimalFilter();
        }
        return bPjz;
    }

    public void setbPjz(BigDecimalFilter bPjz) {
        this.bPjz = bPjz;
    }

    public BigDecimalFilter getbYsfz() {
        return bYsfz;
    }

    public BigDecimalFilter bYsfz() {
        if (bYsfz == null) {
            bYsfz = new BigDecimalFilter();
        }
        return bYsfz;
    }

    public void setbYsfz(BigDecimalFilter bYsfz) {
        this.bYsfz = bYsfz;
    }

    public BigDecimalFilter getbSjfz() {
        return bSjfz;
    }

    public BigDecimalFilter bSjfz() {
        if (bSjfz == null) {
            bSjfz = new BigDecimalFilter();
        }
        return bSjfz;
    }

    public void setbSjfz(BigDecimalFilter bSjfz) {
        this.bSjfz = bSjfz;
    }

    public BigDecimalFilter getbFzcz() {
        return bFzcz;
    }

    public BigDecimalFilter bFzcz() {
        if (bFzcz == null) {
            bFzcz = new BigDecimalFilter();
        }
        return bFzcz;
    }

    public void setbFzcz(BigDecimalFilter bFzcz) {
        this.bFzcz = bFzcz;
    }

    public BigDecimalFilter getbPjzcz() {
        return bPjzcz;
    }

    public BigDecimalFilter bPjzcz() {
        if (bPjzcz == null) {
            bPjzcz = new BigDecimalFilter();
        }
        return bPjzcz;
    }

    public void setbPjzcz(BigDecimalFilter bPjzcz) {
        this.bPjzcz = bPjzcz;
    }

    public LongFilter getzSl() {
        return zSl;
    }

    public LongFilter zSl() {
        if (zSl == null) {
            zSl = new LongFilter();
        }
        return zSl;
    }

    public void setzSl(LongFilter zSl) {
        this.zSl = zSl;
    }

    public BigDecimalFilter getzKfl() {
        return zKfl;
    }

    public BigDecimalFilter zKfl() {
        if (zKfl == null) {
            zKfl = new BigDecimalFilter();
        }
        return zKfl;
    }

    public void setzKfl(BigDecimalFilter zKfl) {
        this.zKfl = zKfl;
    }

    public BigDecimalFilter getzPjz() {
        return zPjz;
    }

    public BigDecimalFilter zPjz() {
        if (zPjz == null) {
            zPjz = new BigDecimalFilter();
        }
        return zPjz;
    }

    public void setzPjz(BigDecimalFilter zPjz) {
        this.zPjz = zPjz;
    }

    public BigDecimalFilter getzYsfz() {
        return zYsfz;
    }

    public BigDecimalFilter zYsfz() {
        if (zYsfz == null) {
            zYsfz = new BigDecimalFilter();
        }
        return zYsfz;
    }

    public void setzYsfz(BigDecimalFilter zYsfz) {
        this.zYsfz = zYsfz;
    }

    public BigDecimalFilter getzSjfz() {
        return zSjfz;
    }

    public BigDecimalFilter zSjfz() {
        if (zSjfz == null) {
            zSjfz = new BigDecimalFilter();
        }
        return zSjfz;
    }

    public void setzSjfz(BigDecimalFilter zSjfz) {
        this.zSjfz = zSjfz;
    }

    public BigDecimalFilter getzFzcz() {
        return zFzcz;
    }

    public BigDecimalFilter zFzcz() {
        if (zFzcz == null) {
            zFzcz = new BigDecimalFilter();
        }
        return zFzcz;
    }

    public void setzFzcz(BigDecimalFilter zFzcz) {
        this.zFzcz = zFzcz;
    }

    public BigDecimalFilter getzPjzcz() {
        return zPjzcz;
    }

    public BigDecimalFilter zPjzcz() {
        if (zPjzcz == null) {
            zPjzcz = new BigDecimalFilter();
        }
        return zPjzcz;
    }

    public void setzPjzcz(BigDecimalFilter zPjzcz) {
        this.zPjzcz = zPjzcz;
    }

    public BigDecimalFilter getZk() {
        return zk;
    }

    public BigDecimalFilter zk() {
        if (zk == null) {
            zk = new BigDecimalFilter();
        }
        return zk;
    }

    public void setZk(BigDecimalFilter zk) {
        this.zk = zk;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final CzBqzCriteria that = (CzBqzCriteria) o;
        return (
            Objects.equals(id, that.id) &&
            Objects.equals(rq, that.rq) &&
            Objects.equals(qSl, that.qSl) &&
            Objects.equals(qKfl, that.qKfl) &&
            Objects.equals(qPjz, that.qPjz) &&
            Objects.equals(qYsfz, that.qYsfz) &&
            Objects.equals(qSjfz, that.qSjfz) &&
            Objects.equals(qFzcz, that.qFzcz) &&
            Objects.equals(qPjzcz, that.qPjzcz) &&
            Objects.equals(bSl, that.bSl) &&
            Objects.equals(bKfl, that.bKfl) &&
            Objects.equals(bPjz, that.bPjz) &&
            Objects.equals(bYsfz, that.bYsfz) &&
            Objects.equals(bSjfz, that.bSjfz) &&
            Objects.equals(bFzcz, that.bFzcz) &&
            Objects.equals(bPjzcz, that.bPjzcz) &&
            Objects.equals(zSl, that.zSl) &&
            Objects.equals(zKfl, that.zKfl) &&
            Objects.equals(zPjz, that.zPjz) &&
            Objects.equals(zYsfz, that.zYsfz) &&
            Objects.equals(zSjfz, that.zSjfz) &&
            Objects.equals(zFzcz, that.zFzcz) &&
            Objects.equals(zPjzcz, that.zPjzcz) &&
            Objects.equals(zk, that.zk)
        );
    }

    @Override
    public int hashCode() {
        return Objects.hash(
            id,
            rq,
            qSl,
            qKfl,
            qPjz,
            qYsfz,
            qSjfz,
            qFzcz,
            qPjzcz,
            bSl,
            bKfl,
            bPjz,
            bYsfz,
            bSjfz,
            bFzcz,
            bPjzcz,
            zSl,
            zKfl,
            zPjz,
            zYsfz,
            zSjfz,
            zFzcz,
            zPjzcz,
            zk
        );
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "CzBqzCriteria{" +
            (id != null ? "id=" + id + ", " : "") +
            (rq != null ? "rq=" + rq + ", " : "") +
            (qSl != null ? "qSl=" + qSl + ", " : "") +
            (qKfl != null ? "qKfl=" + qKfl + ", " : "") +
            (qPjz != null ? "qPjz=" + qPjz + ", " : "") +
            (qYsfz != null ? "qYsfz=" + qYsfz + ", " : "") +
            (qSjfz != null ? "qSjfz=" + qSjfz + ", " : "") +
            (qFzcz != null ? "qFzcz=" + qFzcz + ", " : "") +
            (qPjzcz != null ? "qPjzcz=" + qPjzcz + ", " : "") +
            (bSl != null ? "bSl=" + bSl + ", " : "") +
            (bKfl != null ? "bKfl=" + bKfl + ", " : "") +
            (bPjz != null ? "bPjz=" + bPjz + ", " : "") +
            (bYsfz != null ? "bYsfz=" + bYsfz + ", " : "") +
            (bSjfz != null ? "bSjfz=" + bSjfz + ", " : "") +
            (bFzcz != null ? "bFzcz=" + bFzcz + ", " : "") +
            (bPjzcz != null ? "bPjzcz=" + bPjzcz + ", " : "") +
            (zSl != null ? "zSl=" + zSl + ", " : "") +
            (zKfl != null ? "zKfl=" + zKfl + ", " : "") +
            (zPjz != null ? "zPjz=" + zPjz + ", " : "") +
            (zYsfz != null ? "zYsfz=" + zYsfz + ", " : "") +
            (zSjfz != null ? "zSjfz=" + zSjfz + ", " : "") +
            (zFzcz != null ? "zFzcz=" + zFzcz + ", " : "") +
            (zPjzcz != null ? "zPjzcz=" + zPjzcz + ", " : "") +
            (zk != null ? "zk=" + zk + ", " : "") +
            "}";
    }
}
