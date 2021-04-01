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
 * Criteria class for the {@link ihotel.app.domain.CzCzl3} entity. This class is used
 * in {@link ihotel.app.web.rest.CzCzl3Resource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /cz-czl-3-s?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class CzCzl3Criteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private LongFilter zfs;

    private BigDecimalFilter kfs;

    private StringFilter protocoln;

    private StringFilter roomtype;

    private LongFilter sl;

    public CzCzl3Criteria() {}

    public CzCzl3Criteria(CzCzl3Criteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.zfs = other.zfs == null ? null : other.zfs.copy();
        this.kfs = other.kfs == null ? null : other.kfs.copy();
        this.protocoln = other.protocoln == null ? null : other.protocoln.copy();
        this.roomtype = other.roomtype == null ? null : other.roomtype.copy();
        this.sl = other.sl == null ? null : other.sl.copy();
    }

    @Override
    public CzCzl3Criteria copy() {
        return new CzCzl3Criteria(this);
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

    public LongFilter getZfs() {
        return zfs;
    }

    public LongFilter zfs() {
        if (zfs == null) {
            zfs = new LongFilter();
        }
        return zfs;
    }

    public void setZfs(LongFilter zfs) {
        this.zfs = zfs;
    }

    public BigDecimalFilter getKfs() {
        return kfs;
    }

    public BigDecimalFilter kfs() {
        if (kfs == null) {
            kfs = new BigDecimalFilter();
        }
        return kfs;
    }

    public void setKfs(BigDecimalFilter kfs) {
        this.kfs = kfs;
    }

    public StringFilter getProtocoln() {
        return protocoln;
    }

    public StringFilter protocoln() {
        if (protocoln == null) {
            protocoln = new StringFilter();
        }
        return protocoln;
    }

    public void setProtocoln(StringFilter protocoln) {
        this.protocoln = protocoln;
    }

    public StringFilter getRoomtype() {
        return roomtype;
    }

    public StringFilter roomtype() {
        if (roomtype == null) {
            roomtype = new StringFilter();
        }
        return roomtype;
    }

    public void setRoomtype(StringFilter roomtype) {
        this.roomtype = roomtype;
    }

    public LongFilter getSl() {
        return sl;
    }

    public LongFilter sl() {
        if (sl == null) {
            sl = new LongFilter();
        }
        return sl;
    }

    public void setSl(LongFilter sl) {
        this.sl = sl;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final CzCzl3Criteria that = (CzCzl3Criteria) o;
        return (
            Objects.equals(id, that.id) &&
            Objects.equals(zfs, that.zfs) &&
            Objects.equals(kfs, that.kfs) &&
            Objects.equals(protocoln, that.protocoln) &&
            Objects.equals(roomtype, that.roomtype) &&
            Objects.equals(sl, that.sl)
        );
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, zfs, kfs, protocoln, roomtype, sl);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "CzCzl3Criteria{" +
            (id != null ? "id=" + id + ", " : "") +
            (zfs != null ? "zfs=" + zfs + ", " : "") +
            (kfs != null ? "kfs=" + kfs + ", " : "") +
            (protocoln != null ? "protocoln=" + protocoln + ", " : "") +
            (roomtype != null ? "roomtype=" + roomtype + ", " : "") +
            (sl != null ? "sl=" + sl + ", " : "") +
            "}";
    }
}
