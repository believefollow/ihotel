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
 * Criteria class for the {@link ihotel.app.domain.FtXz} entity. This class is used
 * in {@link ihotel.app.web.rest.FtXzResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /ft-xzs?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class FtXzCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter roomn;

    public FtXzCriteria() {}

    public FtXzCriteria(FtXzCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.roomn = other.roomn == null ? null : other.roomn.copy();
    }

    @Override
    public FtXzCriteria copy() {
        return new FtXzCriteria(this);
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

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final FtXzCriteria that = (FtXzCriteria) o;
        return Objects.equals(id, that.id) && Objects.equals(roomn, that.roomn);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, roomn);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "FtXzCriteria{" +
            (id != null ? "id=" + id + ", " : "") +
            (roomn != null ? "roomn=" + roomn + ", " : "") +
            "}";
    }
}
