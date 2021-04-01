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
 * Criteria class for the {@link ihotel.app.domain.Accbillno} entity. This class is used
 * in {@link ihotel.app.web.rest.AccbillnoResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /accbillnos?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class AccbillnoCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter account;

    private StringFilter accbillno;

    public AccbillnoCriteria() {}

    public AccbillnoCriteria(AccbillnoCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.account = other.account == null ? null : other.account.copy();
        this.accbillno = other.accbillno == null ? null : other.accbillno.copy();
    }

    @Override
    public AccbillnoCriteria copy() {
        return new AccbillnoCriteria(this);
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

    public StringFilter getAccbillno() {
        return accbillno;
    }

    public StringFilter accbillno() {
        if (accbillno == null) {
            accbillno = new StringFilter();
        }
        return accbillno;
    }

    public void setAccbillno(StringFilter accbillno) {
        this.accbillno = accbillno;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final AccbillnoCriteria that = (AccbillnoCriteria) o;
        return Objects.equals(id, that.id) && Objects.equals(account, that.account) && Objects.equals(accbillno, that.accbillno);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, account, accbillno);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "AccbillnoCriteria{" +
            (id != null ? "id=" + id + ", " : "") +
            (account != null ? "account=" + account + ", " : "") +
            (accbillno != null ? "accbillno=" + accbillno + ", " : "") +
            "}";
    }
}
