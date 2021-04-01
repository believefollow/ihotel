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
 * Criteria class for the {@link ihotel.app.domain.DDepot} entity. This class is used
 * in {@link ihotel.app.web.rest.DDepotResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /d-depots?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class DDepotCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private BooleanFilter depotid;

    private StringFilter depot;

    public DDepotCriteria() {}

    public DDepotCriteria(DDepotCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.depotid = other.depotid == null ? null : other.depotid.copy();
        this.depot = other.depot == null ? null : other.depot.copy();
    }

    @Override
    public DDepotCriteria copy() {
        return new DDepotCriteria(this);
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

    public BooleanFilter getDepotid() {
        return depotid;
    }

    public BooleanFilter depotid() {
        if (depotid == null) {
            depotid = new BooleanFilter();
        }
        return depotid;
    }

    public void setDepotid(BooleanFilter depotid) {
        this.depotid = depotid;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final DDepotCriteria that = (DDepotCriteria) o;
        return Objects.equals(id, that.id) && Objects.equals(depotid, that.depotid) && Objects.equals(depot, that.depot);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, depotid, depot);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "DDepotCriteria{" +
            (id != null ? "id=" + id + ", " : "") +
            (depotid != null ? "depotid=" + depotid + ", " : "") +
            (depot != null ? "depot=" + depot + ", " : "") +
            "}";
    }
}
