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
 * Criteria class for the {@link ihotel.app.domain.CheckinAccount} entity. This class is used
 * in {@link ihotel.app.web.rest.CheckinAccountResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /checkin-accounts?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class CheckinAccountCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter account;

    private StringFilter roomn;

    private InstantFilter indatetime;

    private InstantFilter gotime;

    private BigDecimalFilter kfang;

    private BigDecimalFilter dhua;

    private BigDecimalFilter minin;

    private BigDecimalFilter peich;

    private BigDecimalFilter qit;

    private BigDecimalFilter total;

    public CheckinAccountCriteria() {}

    public CheckinAccountCriteria(CheckinAccountCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.account = other.account == null ? null : other.account.copy();
        this.roomn = other.roomn == null ? null : other.roomn.copy();
        this.indatetime = other.indatetime == null ? null : other.indatetime.copy();
        this.gotime = other.gotime == null ? null : other.gotime.copy();
        this.kfang = other.kfang == null ? null : other.kfang.copy();
        this.dhua = other.dhua == null ? null : other.dhua.copy();
        this.minin = other.minin == null ? null : other.minin.copy();
        this.peich = other.peich == null ? null : other.peich.copy();
        this.qit = other.qit == null ? null : other.qit.copy();
        this.total = other.total == null ? null : other.total.copy();
    }

    @Override
    public CheckinAccountCriteria copy() {
        return new CheckinAccountCriteria(this);
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

    public StringFilter getAccount() {
        return account;
    }

    public StringFilter account() {
        if (account == null) {
            account = new StringFilter();
        }
        return account;
    }

    public void setAccount(StringFilter account) {
        this.account = account;
    }

    public StringFilter getRoomn() {
        return roomn;
    }

    public StringFilter roomn() {
        if (roomn == null) {
            roomn = new StringFilter();
        }
        return roomn;
    }

    public void setRoomn(StringFilter roomn) {
        this.roomn = roomn;
    }

    public InstantFilter getIndatetime() {
        return indatetime;
    }

    public InstantFilter indatetime() {
        if (indatetime == null) {
            indatetime = new InstantFilter();
        }
        return indatetime;
    }

    public void setIndatetime(InstantFilter indatetime) {
        this.indatetime = indatetime;
    }

    public InstantFilter getGotime() {
        return gotime;
    }

    public InstantFilter gotime() {
        if (gotime == null) {
            gotime = new InstantFilter();
        }
        return gotime;
    }

    public void setGotime(InstantFilter gotime) {
        this.gotime = gotime;
    }

    public BigDecimalFilter getKfang() {
        return kfang;
    }

    public BigDecimalFilter kfang() {
        if (kfang == null) {
            kfang = new BigDecimalFilter();
        }
        return kfang;
    }

    public void setKfang(BigDecimalFilter kfang) {
        this.kfang = kfang;
    }

    public BigDecimalFilter getDhua() {
        return dhua;
    }

    public BigDecimalFilter dhua() {
        if (dhua == null) {
            dhua = new BigDecimalFilter();
        }
        return dhua;
    }

    public void setDhua(BigDecimalFilter dhua) {
        this.dhua = dhua;
    }

    public BigDecimalFilter getMinin() {
        return minin;
    }

    public BigDecimalFilter minin() {
        if (minin == null) {
            minin = new BigDecimalFilter();
        }
        return minin;
    }

    public void setMinin(BigDecimalFilter minin) {
        this.minin = minin;
    }

    public BigDecimalFilter getPeich() {
        return peich;
    }

    public BigDecimalFilter peich() {
        if (peich == null) {
            peich = new BigDecimalFilter();
        }
        return peich;
    }

    public void setPeich(BigDecimalFilter peich) {
        this.peich = peich;
    }

    public BigDecimalFilter getQit() {
        return qit;
    }

    public BigDecimalFilter qit() {
        if (qit == null) {
            qit = new BigDecimalFilter();
        }
        return qit;
    }

    public void setQit(BigDecimalFilter qit) {
        this.qit = qit;
    }

    public BigDecimalFilter getTotal() {
        return total;
    }

    public BigDecimalFilter total() {
        if (total == null) {
            total = new BigDecimalFilter();
        }
        return total;
    }

    public void setTotal(BigDecimalFilter total) {
        this.total = total;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final CheckinAccountCriteria that = (CheckinAccountCriteria) o;
        return (
            Objects.equals(id, that.id) &&
            Objects.equals(account, that.account) &&
            Objects.equals(roomn, that.roomn) &&
            Objects.equals(indatetime, that.indatetime) &&
            Objects.equals(gotime, that.gotime) &&
            Objects.equals(kfang, that.kfang) &&
            Objects.equals(dhua, that.dhua) &&
            Objects.equals(minin, that.minin) &&
            Objects.equals(peich, that.peich) &&
            Objects.equals(qit, that.qit) &&
            Objects.equals(total, that.total)
        );
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, account, roomn, indatetime, gotime, kfang, dhua, minin, peich, qit, total);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "CheckinAccountCriteria{" +
            (id != null ? "id=" + id + ", " : "") +
            (account != null ? "account=" + account + ", " : "") +
            (roomn != null ? "roomn=" + roomn + ", " : "") +
            (indatetime != null ? "indatetime=" + indatetime + ", " : "") +
            (gotime != null ? "gotime=" + gotime + ", " : "") +
            (kfang != null ? "kfang=" + kfang + ", " : "") +
            (dhua != null ? "dhua=" + dhua + ", " : "") +
            (minin != null ? "minin=" + minin + ", " : "") +
            (peich != null ? "peich=" + peich + ", " : "") +
            (qit != null ? "qit=" + qit + ", " : "") +
            (total != null ? "total=" + total + ", " : "") +
            "}";
    }
}
