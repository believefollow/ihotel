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
 * Criteria class for the {@link ihotel.app.domain.Feetype} entity. This class is used
 * in {@link ihotel.app.web.rest.FeetypeResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /feetypes?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class FeetypeCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private LongFilter feenum;

    private StringFilter feename;

    private BigDecimalFilter price;

    private LongFilter sign;

    private StringFilter beizhu;

    private StringFilter pym;

    private LongFilter salespotn;

    private StringFilter depot;

    private LongFilter cbsign;

    private LongFilter ordersign;

    private StringFilter hoteldm;

    private LongFilter isnew;

    private BigDecimalFilter ygj;

    private StringFilter autosign;

    private BigDecimalFilter jj;

    private BigDecimalFilter hyj;

    private BigDecimalFilter dqkc;

    public FeetypeCriteria() {}

    public FeetypeCriteria(FeetypeCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.feenum = other.feenum == null ? null : other.feenum.copy();
        this.feename = other.feename == null ? null : other.feename.copy();
        this.price = other.price == null ? null : other.price.copy();
        this.sign = other.sign == null ? null : other.sign.copy();
        this.beizhu = other.beizhu == null ? null : other.beizhu.copy();
        this.pym = other.pym == null ? null : other.pym.copy();
        this.salespotn = other.salespotn == null ? null : other.salespotn.copy();
        this.depot = other.depot == null ? null : other.depot.copy();
        this.cbsign = other.cbsign == null ? null : other.cbsign.copy();
        this.ordersign = other.ordersign == null ? null : other.ordersign.copy();
        this.hoteldm = other.hoteldm == null ? null : other.hoteldm.copy();
        this.isnew = other.isnew == null ? null : other.isnew.copy();
        this.ygj = other.ygj == null ? null : other.ygj.copy();
        this.autosign = other.autosign == null ? null : other.autosign.copy();
        this.jj = other.jj == null ? null : other.jj.copy();
        this.hyj = other.hyj == null ? null : other.hyj.copy();
        this.dqkc = other.dqkc == null ? null : other.dqkc.copy();
    }

    @Override
    public FeetypeCriteria copy() {
        return new FeetypeCriteria(this);
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

    public LongFilter getFeenum() {
        return feenum;
    }

    public LongFilter feenum() {
        if (feenum == null) {
            feenum = new LongFilter();
        }
        return feenum;
    }

    public void setFeenum(LongFilter feenum) {
        this.feenum = feenum;
    }

    public StringFilter getFeename() {
        return feename;
    }

    public StringFilter feename() {
        if (feename == null) {
            feename = new StringFilter();
        }
        return feename;
    }

    public void setFeename(StringFilter feename) {
        this.feename = feename;
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

    public LongFilter getSign() {
        return sign;
    }

    public LongFilter sign() {
        if (sign == null) {
            sign = new LongFilter();
        }
        return sign;
    }

    public void setSign(LongFilter sign) {
        this.sign = sign;
    }

    public StringFilter getBeizhu() {
        return beizhu;
    }

    public StringFilter beizhu() {
        if (beizhu == null) {
            beizhu = new StringFilter();
        }
        return beizhu;
    }

    public void setBeizhu(StringFilter beizhu) {
        this.beizhu = beizhu;
    }

    public StringFilter getPym() {
        return pym;
    }

    public StringFilter pym() {
        if (pym == null) {
            pym = new StringFilter();
        }
        return pym;
    }

    public void setPym(StringFilter pym) {
        this.pym = pym;
    }

    public LongFilter getSalespotn() {
        return salespotn;
    }

    public LongFilter salespotn() {
        if (salespotn == null) {
            salespotn = new LongFilter();
        }
        return salespotn;
    }

    public void setSalespotn(LongFilter salespotn) {
        this.salespotn = salespotn;
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

    public LongFilter getCbsign() {
        return cbsign;
    }

    public LongFilter cbsign() {
        if (cbsign == null) {
            cbsign = new LongFilter();
        }
        return cbsign;
    }

    public void setCbsign(LongFilter cbsign) {
        this.cbsign = cbsign;
    }

    public LongFilter getOrdersign() {
        return ordersign;
    }

    public LongFilter ordersign() {
        if (ordersign == null) {
            ordersign = new LongFilter();
        }
        return ordersign;
    }

    public void setOrdersign(LongFilter ordersign) {
        this.ordersign = ordersign;
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

    public BigDecimalFilter getYgj() {
        return ygj;
    }

    public BigDecimalFilter ygj() {
        if (ygj == null) {
            ygj = new BigDecimalFilter();
        }
        return ygj;
    }

    public void setYgj(BigDecimalFilter ygj) {
        this.ygj = ygj;
    }

    public StringFilter getAutosign() {
        return autosign;
    }

    public StringFilter autosign() {
        if (autosign == null) {
            autosign = new StringFilter();
        }
        return autosign;
    }

    public void setAutosign(StringFilter autosign) {
        this.autosign = autosign;
    }

    public BigDecimalFilter getJj() {
        return jj;
    }

    public BigDecimalFilter jj() {
        if (jj == null) {
            jj = new BigDecimalFilter();
        }
        return jj;
    }

    public void setJj(BigDecimalFilter jj) {
        this.jj = jj;
    }

    public BigDecimalFilter getHyj() {
        return hyj;
    }

    public BigDecimalFilter hyj() {
        if (hyj == null) {
            hyj = new BigDecimalFilter();
        }
        return hyj;
    }

    public void setHyj(BigDecimalFilter hyj) {
        this.hyj = hyj;
    }

    public BigDecimalFilter getDqkc() {
        return dqkc;
    }

    public BigDecimalFilter dqkc() {
        if (dqkc == null) {
            dqkc = new BigDecimalFilter();
        }
        return dqkc;
    }

    public void setDqkc(BigDecimalFilter dqkc) {
        this.dqkc = dqkc;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final FeetypeCriteria that = (FeetypeCriteria) o;
        return (
            Objects.equals(id, that.id) &&
            Objects.equals(feenum, that.feenum) &&
            Objects.equals(feename, that.feename) &&
            Objects.equals(price, that.price) &&
            Objects.equals(sign, that.sign) &&
            Objects.equals(beizhu, that.beizhu) &&
            Objects.equals(pym, that.pym) &&
            Objects.equals(salespotn, that.salespotn) &&
            Objects.equals(depot, that.depot) &&
            Objects.equals(cbsign, that.cbsign) &&
            Objects.equals(ordersign, that.ordersign) &&
            Objects.equals(hoteldm, that.hoteldm) &&
            Objects.equals(isnew, that.isnew) &&
            Objects.equals(ygj, that.ygj) &&
            Objects.equals(autosign, that.autosign) &&
            Objects.equals(jj, that.jj) &&
            Objects.equals(hyj, that.hyj) &&
            Objects.equals(dqkc, that.dqkc)
        );
    }

    @Override
    public int hashCode() {
        return Objects.hash(
            id,
            feenum,
            feename,
            price,
            sign,
            beizhu,
            pym,
            salespotn,
            depot,
            cbsign,
            ordersign,
            hoteldm,
            isnew,
            ygj,
            autosign,
            jj,
            hyj,
            dqkc
        );
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "FeetypeCriteria{" +
            (id != null ? "id=" + id + ", " : "") +
            (feenum != null ? "feenum=" + feenum + ", " : "") +
            (feename != null ? "feename=" + feename + ", " : "") +
            (price != null ? "price=" + price + ", " : "") +
            (sign != null ? "sign=" + sign + ", " : "") +
            (beizhu != null ? "beizhu=" + beizhu + ", " : "") +
            (pym != null ? "pym=" + pym + ", " : "") +
            (salespotn != null ? "salespotn=" + salespotn + ", " : "") +
            (depot != null ? "depot=" + depot + ", " : "") +
            (cbsign != null ? "cbsign=" + cbsign + ", " : "") +
            (ordersign != null ? "ordersign=" + ordersign + ", " : "") +
            (hoteldm != null ? "hoteldm=" + hoteldm + ", " : "") +
            (isnew != null ? "isnew=" + isnew + ", " : "") +
            (ygj != null ? "ygj=" + ygj + ", " : "") +
            (autosign != null ? "autosign=" + autosign + ", " : "") +
            (jj != null ? "jj=" + jj + ", " : "") +
            (hyj != null ? "hyj=" + hyj + ", " : "") +
            (dqkc != null ? "dqkc=" + dqkc + ", " : "") +
            "}";
    }
}
