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
 * Criteria class for the {@link ihotel.app.domain.DCompany} entity. This class is used
 * in {@link ihotel.app.web.rest.DCompanyResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /d-companies?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class DCompanyCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private StringFilter company;

    private StringFilter linkman;

    private StringFilter phone;

    private StringFilter address;

    private StringFilter remark;

    private StringFilter fax;

    private LongFilter id;

    public DCompanyCriteria() {}

    public DCompanyCriteria(DCompanyCriteria other) {
        this.company = other.company == null ? null : other.company.copy();
        this.linkman = other.linkman == null ? null : other.linkman.copy();
        this.phone = other.phone == null ? null : other.phone.copy();
        this.address = other.address == null ? null : other.address.copy();
        this.remark = other.remark == null ? null : other.remark.copy();
        this.fax = other.fax == null ? null : other.fax.copy();
        this.id = other.id == null ? null : other.id.copy();
    }

    @Override
    public DCompanyCriteria copy() {
        return new DCompanyCriteria(this);
    }

    public StringFilter getCompany() {
        return company;
    }

    public StringFilter company() {
        if (company == null) {
            company = new StringFilter();
        }
        return company;
    }

    public void setCompany(StringFilter company) {
        this.company = company;
    }

    public StringFilter getLinkman() {
        return linkman;
    }

    public StringFilter linkman() {
        if (linkman == null) {
            linkman = new StringFilter();
        }
        return linkman;
    }

    public void setLinkman(StringFilter linkman) {
        this.linkman = linkman;
    }

    public StringFilter getPhone() {
        return phone;
    }

    public StringFilter phone() {
        if (phone == null) {
            phone = new StringFilter();
        }
        return phone;
    }

    public void setPhone(StringFilter phone) {
        this.phone = phone;
    }

    public StringFilter getAddress() {
        return address;
    }

    public StringFilter address() {
        if (address == null) {
            address = new StringFilter();
        }
        return address;
    }

    public void setAddress(StringFilter address) {
        this.address = address;
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

    public StringFilter getFax() {
        return fax;
    }

    public StringFilter fax() {
        if (fax == null) {
            fax = new StringFilter();
        }
        return fax;
    }

    public void setFax(StringFilter fax) {
        this.fax = fax;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final DCompanyCriteria that = (DCompanyCriteria) o;
        return (
            Objects.equals(company, that.company) &&
            Objects.equals(linkman, that.linkman) &&
            Objects.equals(phone, that.phone) &&
            Objects.equals(address, that.address) &&
            Objects.equals(remark, that.remark) &&
            Objects.equals(fax, that.fax) &&
            Objects.equals(id, that.id)
        );
    }

    @Override
    public int hashCode() {
        return Objects.hash(company, linkman, phone, address, remark, fax, id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "DCompanyCriteria{" +
            (company != null ? "company=" + company + ", " : "") +
            (linkman != null ? "linkman=" + linkman + ", " : "") +
            (phone != null ? "phone=" + phone + ", " : "") +
            (address != null ? "address=" + address + ", " : "") +
            (remark != null ? "remark=" + remark + ", " : "") +
            (fax != null ? "fax=" + fax + ", " : "") +
            (id != null ? "id=" + id + ", " : "") +
            "}";
    }
}
