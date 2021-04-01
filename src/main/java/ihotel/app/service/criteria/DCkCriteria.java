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
 * Criteria class for the {@link ihotel.app.domain.DCk} entity. This class is used
 * in {@link ihotel.app.web.rest.DCkResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /d-cks?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class DCkCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter depot;

    private InstantFilter ckdate;

    private StringFilter ckbillno;

    private StringFilter deptname;

    private StringFilter jbr;

    private StringFilter remark;

    private StringFilter spbm;

    private StringFilter spmc;

    private StringFilter unit;

    private BigDecimalFilter price;

    private BigDecimalFilter sl;

    private BigDecimalFilter je;

    private StringFilter memo;

    private LongFilter flag;

    private LongFilter dbSign;

    private StringFilter cktype;

    private StringFilter empn;

    private InstantFilter lrdate;

    private DoubleFilter kcid;

    private StringFilter f1;

    private StringFilter f2;

    private StringFilter f1empn;

    private StringFilter f2empn;

    private InstantFilter f1sj;

    private InstantFilter f2sj;

    public DCkCriteria() {}

    public DCkCriteria(DCkCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.depot = other.depot == null ? null : other.depot.copy();
        this.ckdate = other.ckdate == null ? null : other.ckdate.copy();
        this.ckbillno = other.ckbillno == null ? null : other.ckbillno.copy();
        this.deptname = other.deptname == null ? null : other.deptname.copy();
        this.jbr = other.jbr == null ? null : other.jbr.copy();
        this.remark = other.remark == null ? null : other.remark.copy();
        this.spbm = other.spbm == null ? null : other.spbm.copy();
        this.spmc = other.spmc == null ? null : other.spmc.copy();
        this.unit = other.unit == null ? null : other.unit.copy();
        this.price = other.price == null ? null : other.price.copy();
        this.sl = other.sl == null ? null : other.sl.copy();
        this.je = other.je == null ? null : other.je.copy();
        this.memo = other.memo == null ? null : other.memo.copy();
        this.flag = other.flag == null ? null : other.flag.copy();
        this.dbSign = other.dbSign == null ? null : other.dbSign.copy();
        this.cktype = other.cktype == null ? null : other.cktype.copy();
        this.empn = other.empn == null ? null : other.empn.copy();
        this.lrdate = other.lrdate == null ? null : other.lrdate.copy();
        this.kcid = other.kcid == null ? null : other.kcid.copy();
        this.f1 = other.f1 == null ? null : other.f1.copy();
        this.f2 = other.f2 == null ? null : other.f2.copy();
        this.f1empn = other.f1empn == null ? null : other.f1empn.copy();
        this.f2empn = other.f2empn == null ? null : other.f2empn.copy();
        this.f1sj = other.f1sj == null ? null : other.f1sj.copy();
        this.f2sj = other.f2sj == null ? null : other.f2sj.copy();
    }

    @Override
    public DCkCriteria copy() {
        return new DCkCriteria(this);
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

    public InstantFilter getCkdate() {
        return ckdate;
    }

    public InstantFilter ckdate() {
        if (ckdate == null) {
            ckdate = new InstantFilter();
        }
        return ckdate;
    }

    public void setCkdate(InstantFilter ckdate) {
        this.ckdate = ckdate;
    }

    public StringFilter getCkbillno() {
        return ckbillno;
    }

    public StringFilter ckbillno() {
        if (ckbillno == null) {
            ckbillno = new StringFilter();
        }
        return ckbillno;
    }

    public void setCkbillno(StringFilter ckbillno) {
        this.ckbillno = ckbillno;
    }

    public StringFilter getDeptname() {
        return deptname;
    }

    public StringFilter deptname() {
        if (deptname == null) {
            deptname = new StringFilter();
        }
        return deptname;
    }

    public void setDeptname(StringFilter deptname) {
        this.deptname = deptname;
    }

    public StringFilter getJbr() {
        return jbr;
    }

    public StringFilter jbr() {
        if (jbr == null) {
            jbr = new StringFilter();
        }
        return jbr;
    }

    public void setJbr(StringFilter jbr) {
        this.jbr = jbr;
    }

    public StringFilter getRemark() {
        return remark;
    }

    public StringFilter remark() {
        if (remark == null) {
            remark = new StringFilter();
        }
        return remark;
    }

    public void setRemark(StringFilter remark) {
        this.remark = remark;
    }

    public StringFilter getSpbm() {
        return spbm;
    }

    public StringFilter spbm() {
        if (spbm == null) {
            spbm = new StringFilter();
        }
        return spbm;
    }

    public void setSpbm(StringFilter spbm) {
        this.spbm = spbm;
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

    public StringFilter getUnit() {
        return unit;
    }

    public StringFilter unit() {
        if (unit == null) {
            unit = new StringFilter();
        }
        return unit;
    }

    public void setUnit(StringFilter unit) {
        this.unit = unit;
    }

    public BigDecimalFilter getPrice() {
        return price;
    }

    public BigDecimalFilter price() {
        if (price == null) {
            price = new BigDecimalFilter();
        }
        return price;
    }

    public void setPrice(BigDecimalFilter price) {
        this.price = price;
    }

    public BigDecimalFilter getSl() {
        return sl;
    }

    public BigDecimalFilter sl() {
        if (sl == null) {
            sl = new BigDecimalFilter();
        }
        return sl;
    }

    public void setSl(BigDecimalFilter sl) {
        this.sl = sl;
    }

    public BigDecimalFilter getJe() {
        return je;
    }

    public BigDecimalFilter je() {
        if (je == null) {
            je = new BigDecimalFilter();
        }
        return je;
    }

    public void setJe(BigDecimalFilter je) {
        this.je = je;
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

    public LongFilter getFlag() {
        return flag;
    }

    public LongFilter flag() {
        if (flag == null) {
            flag = new LongFilter();
        }
        return flag;
    }

    public void setFlag(LongFilter flag) {
        this.flag = flag;
    }

    public LongFilter getDbSign() {
        return dbSign;
    }

    public LongFilter dbSign() {
        if (dbSign == null) {
            dbSign = new LongFilter();
        }
        return dbSign;
    }

    public void setDbSign(LongFilter dbSign) {
        this.dbSign = dbSign;
    }

    public StringFilter getCktype() {
        return cktype;
    }

    public StringFilter cktype() {
        if (cktype == null) {
            cktype = new StringFilter();
        }
        return cktype;
    }

    public void setCktype(StringFilter cktype) {
        this.cktype = cktype;
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

    public InstantFilter getLrdate() {
        return lrdate;
    }

    public InstantFilter lrdate() {
        if (lrdate == null) {
            lrdate = new InstantFilter();
        }
        return lrdate;
    }

    public void setLrdate(InstantFilter lrdate) {
        this.lrdate = lrdate;
    }

    public DoubleFilter getKcid() {
        return kcid;
    }

    public DoubleFilter kcid() {
        if (kcid == null) {
            kcid = new DoubleFilter();
        }
        return kcid;
    }

    public void setKcid(DoubleFilter kcid) {
        this.kcid = kcid;
    }

    public StringFilter getf1() {
        return f1;
    }

    public StringFilter f1() {
        if (f1 == null) {
            f1 = new StringFilter();
        }
        return f1;
    }

    public void setf1(StringFilter f1) {
        this.f1 = f1;
    }

    public StringFilter getf2() {
        return f2;
    }

    public StringFilter f2() {
        if (f2 == null) {
            f2 = new StringFilter();
        }
        return f2;
    }

    public void setf2(StringFilter f2) {
        this.f2 = f2;
    }

    public StringFilter getf1empn() {
        return f1empn;
    }

    public StringFilter f1empn() {
        if (f1empn == null) {
            f1empn = new StringFilter();
        }
        return f1empn;
    }

    public void setf1empn(StringFilter f1empn) {
        this.f1empn = f1empn;
    }

    public StringFilter getf2empn() {
        return f2empn;
    }

    public StringFilter f2empn() {
        if (f2empn == null) {
            f2empn = new StringFilter();
        }
        return f2empn;
    }

    public void setf2empn(StringFilter f2empn) {
        this.f2empn = f2empn;
    }

    public InstantFilter getf1sj() {
        return f1sj;
    }

    public InstantFilter f1sj() {
        if (f1sj == null) {
            f1sj = new InstantFilter();
        }
        return f1sj;
    }

    public void setf1sj(InstantFilter f1sj) {
        this.f1sj = f1sj;
    }

    public InstantFilter getf2sj() {
        return f2sj;
    }

    public InstantFilter f2sj() {
        if (f2sj == null) {
            f2sj = new InstantFilter();
        }
        return f2sj;
    }

    public void setf2sj(InstantFilter f2sj) {
        this.f2sj = f2sj;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final DCkCriteria that = (DCkCriteria) o;
        return (
            Objects.equals(id, that.id) &&
            Objects.equals(depot, that.depot) &&
            Objects.equals(ckdate, that.ckdate) &&
            Objects.equals(ckbillno, that.ckbillno) &&
            Objects.equals(deptname, that.deptname) &&
            Objects.equals(jbr, that.jbr) &&
            Objects.equals(remark, that.remark) &&
            Objects.equals(spbm, that.spbm) &&
            Objects.equals(spmc, that.spmc) &&
            Objects.equals(unit, that.unit) &&
            Objects.equals(price, that.price) &&
            Objects.equals(sl, that.sl) &&
            Objects.equals(je, that.je) &&
            Objects.equals(memo, that.memo) &&
            Objects.equals(flag, that.flag) &&
            Objects.equals(dbSign, that.dbSign) &&
            Objects.equals(cktype, that.cktype) &&
            Objects.equals(empn, that.empn) &&
            Objects.equals(lrdate, that.lrdate) &&
            Objects.equals(kcid, that.kcid) &&
            Objects.equals(f1, that.f1) &&
            Objects.equals(f2, that.f2) &&
            Objects.equals(f1empn, that.f1empn) &&
            Objects.equals(f2empn, that.f2empn) &&
            Objects.equals(f1sj, that.f1sj) &&
            Objects.equals(f2sj, that.f2sj)
        );
    }

    @Override
    public int hashCode() {
        return Objects.hash(
            id,
            depot,
            ckdate,
            ckbillno,
            deptname,
            jbr,
            remark,
            spbm,
            spmc,
            unit,
            price,
            sl,
            je,
            memo,
            flag,
            dbSign,
            cktype,
            empn,
            lrdate,
            kcid,
            f1,
            f2,
            f1empn,
            f2empn,
            f1sj,
            f2sj
        );
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "DCkCriteria{" +
            (id != null ? "id=" + id + ", " : "") +
            (depot != null ? "depot=" + depot + ", " : "") +
            (ckdate != null ? "ckdate=" + ckdate + ", " : "") +
            (ckbillno != null ? "ckbillno=" + ckbillno + ", " : "") +
            (deptname != null ? "deptname=" + deptname + ", " : "") +
            (jbr != null ? "jbr=" + jbr + ", " : "") +
            (remark != null ? "remark=" + remark + ", " : "") +
            (spbm != null ? "spbm=" + spbm + ", " : "") +
            (spmc != null ? "spmc=" + spmc + ", " : "") +
            (unit != null ? "unit=" + unit + ", " : "") +
            (price != null ? "price=" + price + ", " : "") +
            (sl != null ? "sl=" + sl + ", " : "") +
            (je != null ? "je=" + je + ", " : "") +
            (memo != null ? "memo=" + memo + ", " : "") +
            (flag != null ? "flag=" + flag + ", " : "") +
            (dbSign != null ? "dbSign=" + dbSign + ", " : "") +
            (cktype != null ? "cktype=" + cktype + ", " : "") +
            (empn != null ? "empn=" + empn + ", " : "") +
            (lrdate != null ? "lrdate=" + lrdate + ", " : "") +
            (kcid != null ? "kcid=" + kcid + ", " : "") +
            (f1 != null ? "f1=" + f1 + ", " : "") +
            (f2 != null ? "f2=" + f2 + ", " : "") +
            (f1empn != null ? "f1empn=" + f1empn + ", " : "") +
            (f2empn != null ? "f2empn=" + f2empn + ", " : "") +
            (f1sj != null ? "f1sj=" + f1sj + ", " : "") +
            (f2sj != null ? "f2sj=" + f2sj + ", " : "") +
            "}";
    }
}
