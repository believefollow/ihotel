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
 * Criteria class for the {@link ihotel.app.domain.DGoods} entity. This class is used
 * in {@link ihotel.app.web.rest.DGoodsResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /d-goods?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class DGoodsCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private LongFilter typeid;

    private StringFilter goodsname;

    private StringFilter goodsid;

    private StringFilter ggxh;

    private StringFilter pysj;

    private StringFilter wbsj;

    private StringFilter unit;

    private BigDecimalFilter gcsl;

    private BigDecimalFilter dcsl;

    private StringFilter remark;

    public DGoodsCriteria() {}

    public DGoodsCriteria(DGoodsCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.typeid = other.typeid == null ? null : other.typeid.copy();
        this.goodsname = other.goodsname == null ? null : other.goodsname.copy();
        this.goodsid = other.goodsid == null ? null : other.goodsid.copy();
        this.ggxh = other.ggxh == null ? null : other.ggxh.copy();
        this.pysj = other.pysj == null ? null : other.pysj.copy();
        this.wbsj = other.wbsj == null ? null : other.wbsj.copy();
        this.unit = other.unit == null ? null : other.unit.copy();
        this.gcsl = other.gcsl == null ? null : other.gcsl.copy();
        this.dcsl = other.dcsl == null ? null : other.dcsl.copy();
        this.remark = other.remark == null ? null : other.remark.copy();
    }

    @Override
    public DGoodsCriteria copy() {
        return new DGoodsCriteria(this);
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

    public StringFilter getGoodsname() {
        return goodsname;
    }

    public StringFilter goodsname() {
        if (goodsname == null) {
            goodsname = new StringFilter();
        }
        return goodsname;
    }

    public void setGoodsname(StringFilter goodsname) {
        this.goodsname = goodsname;
    }

    public StringFilter getGoodsid() {
        return goodsid;
    }

    public StringFilter goodsid() {
        if (goodsid == null) {
            goodsid = new StringFilter();
        }
        return goodsid;
    }

    public void setGoodsid(StringFilter goodsid) {
        this.goodsid = goodsid;
    }

    public StringFilter getGgxh() {
        return ggxh;
    }

    public StringFilter ggxh() {
        if (ggxh == null) {
            ggxh = new StringFilter();
        }
        return ggxh;
    }

    public void setGgxh(StringFilter ggxh) {
        this.ggxh = ggxh;
    }

    public StringFilter getPysj() {
        return pysj;
    }

    public StringFilter pysj() {
        if (pysj == null) {
            pysj = new StringFilter();
        }
        return pysj;
    }

    public void setPysj(StringFilter pysj) {
        this.pysj = pysj;
    }

    public StringFilter getWbsj() {
        return wbsj;
    }

    public StringFilter wbsj() {
        if (wbsj == null) {
            wbsj = new StringFilter();
        }
        return wbsj;
    }

    public void setWbsj(StringFilter wbsj) {
        this.wbsj = wbsj;
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

    public BigDecimalFilter getGcsl() {
        return gcsl;
    }

    public BigDecimalFilter gcsl() {
        if (gcsl == null) {
            gcsl = new BigDecimalFilter();
        }
        return gcsl;
    }

    public void setGcsl(BigDecimalFilter gcsl) {
        this.gcsl = gcsl;
    }

    public BigDecimalFilter getDcsl() {
        return dcsl;
    }

    public BigDecimalFilter dcsl() {
        if (dcsl == null) {
            dcsl = new BigDecimalFilter();
        }
        return dcsl;
    }

    public void setDcsl(BigDecimalFilter dcsl) {
        this.dcsl = dcsl;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final DGoodsCriteria that = (DGoodsCriteria) o;
        return (
            Objects.equals(id, that.id) &&
            Objects.equals(typeid, that.typeid) &&
            Objects.equals(goodsname, that.goodsname) &&
            Objects.equals(goodsid, that.goodsid) &&
            Objects.equals(ggxh, that.ggxh) &&
            Objects.equals(pysj, that.pysj) &&
            Objects.equals(wbsj, that.wbsj) &&
            Objects.equals(unit, that.unit) &&
            Objects.equals(gcsl, that.gcsl) &&
            Objects.equals(dcsl, that.dcsl) &&
            Objects.equals(remark, that.remark)
        );
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, typeid, goodsname, goodsid, ggxh, pysj, wbsj, unit, gcsl, dcsl, remark);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "DGoodsCriteria{" +
            (id != null ? "id=" + id + ", " : "") +
            (typeid != null ? "typeid=" + typeid + ", " : "") +
            (goodsname != null ? "goodsname=" + goodsname + ", " : "") +
            (goodsid != null ? "goodsid=" + goodsid + ", " : "") +
            (ggxh != null ? "ggxh=" + ggxh + ", " : "") +
            (pysj != null ? "pysj=" + pysj + ", " : "") +
            (wbsj != null ? "wbsj=" + wbsj + ", " : "") +
            (unit != null ? "unit=" + unit + ", " : "") +
            (gcsl != null ? "gcsl=" + gcsl + ", " : "") +
            (dcsl != null ? "dcsl=" + dcsl + ", " : "") +
            (remark != null ? "remark=" + remark + ", " : "") +
            "}";
    }
}
