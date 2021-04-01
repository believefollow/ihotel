package ihotel.app.service.dto;

import java.io.Serializable;
import java.time.Instant;
import java.util.Objects;
import javax.validation.constraints.*;

/**
 * A DTO for the {@link ihotel.app.domain.Bookingtime} entity.
 */
public class BookingtimeDTO implements Serializable {

    @NotNull
    private Long id;

    @Size(max = 50)
    private String bookid;

    @Size(max = 20)
    private String roomn;

    private Instant booktime;

    @Size(max = 50)
    private String rtype;

    private Long sl;

    @Size(max = 100)
    private String remark;

    private Long sign;

    private Long rzsign;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getBookid() {
        return bookid;
    }

    public void setBookid(String bookid) {
        this.bookid = bookid;
    }

    public String getRoomn() {
        return roomn;
    }

    public void setRoomn(String roomn) {
        this.roomn = roomn;
    }

    public Instant getBooktime() {
        return booktime;
    }

    public void setBooktime(Instant booktime) {
        this.booktime = booktime;
    }

    public String getRtype() {
        return rtype;
    }

    public void setRtype(String rtype) {
        this.rtype = rtype;
    }

    public Long getSl() {
        return sl;
    }

    public void setSl(Long sl) {
        this.sl = sl;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public Long getSign() {
        return sign;
    }

    public void setSign(Long sign) {
        this.sign = sign;
    }

    public Long getRzsign() {
        return rzsign;
    }

    public void setRzsign(Long rzsign) {
        this.rzsign = rzsign;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof BookingtimeDTO)) {
            return false;
        }

        BookingtimeDTO bookingtimeDTO = (BookingtimeDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, bookingtimeDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "BookingtimeDTO{" +
            "id=" + getId() +
            ", bookid='" + getBookid() + "'" +
            ", roomn='" + getRoomn() + "'" +
            ", booktime='" + getBooktime() + "'" +
            ", rtype='" + getRtype() + "'" +
            ", sl=" + getSl() +
            ", remark='" + getRemark() + "'" +
            ", sign=" + getSign() +
            ", rzsign=" + getRzsign() +
            "}";
    }
}
