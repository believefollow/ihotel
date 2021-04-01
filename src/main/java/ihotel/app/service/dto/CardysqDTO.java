package ihotel.app.service.dto;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.Instant;
import java.util.Objects;
import javax.validation.constraints.*;

/**
 * A DTO for the {@link ihotel.app.domain.Cardysq} entity.
 */
public class CardysqDTO implements Serializable {

    @NotNull
    private Long id;

    @Size(max = 30)
    private String roomn;

    @Size(max = 50)
    private String guestname;

    @Size(max = 50)
    private String account;

    private Instant rq;

    @Size(max = 100)
    private String cardid;

    @Size(max = 100)
    private String djh;

    @Size(max = 100)
    private String sqh;

    @Size(max = 50)
    private String empn;

    @Size(max = 2)
    private String sign;

    private Instant hoteltime;

    private Instant yxrq;

    private BigDecimal je;

    @Size(max = 100)
    private String ysqmemo;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getRoomn() {
        return roomn;
    }

    public void setRoomn(String roomn) {
        this.roomn = roomn;
    }

    public String getGuestname() {
        return guestname;
    }

    public void setGuestname(String guestname) {
        this.guestname = guestname;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public Instant getRq() {
        return rq;
    }

    public void setRq(Instant rq) {
        this.rq = rq;
    }

    public String getCardid() {
        return cardid;
    }

    public void setCardid(String cardid) {
        this.cardid = cardid;
    }

    public String getDjh() {
        return djh;
    }

    public void setDjh(String djh) {
        this.djh = djh;
    }

    public String getSqh() {
        return sqh;
    }

    public void setSqh(String sqh) {
        this.sqh = sqh;
    }

    public String getEmpn() {
        return empn;
    }

    public void setEmpn(String empn) {
        this.empn = empn;
    }

    public String getSign() {
        return sign;
    }

    public void setSign(String sign) {
        this.sign = sign;
    }

    public Instant getHoteltime() {
        return hoteltime;
    }

    public void setHoteltime(Instant hoteltime) {
        this.hoteltime = hoteltime;
    }

    public Instant getYxrq() {
        return yxrq;
    }

    public void setYxrq(Instant yxrq) {
        this.yxrq = yxrq;
    }

    public BigDecimal getJe() {
        return je;
    }

    public void setJe(BigDecimal je) {
        this.je = je;
    }

    public String getYsqmemo() {
        return ysqmemo;
    }

    public void setYsqmemo(String ysqmemo) {
        this.ysqmemo = ysqmemo;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof CardysqDTO)) {
            return false;
        }

        CardysqDTO cardysqDTO = (CardysqDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, cardysqDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "CardysqDTO{" +
            "id=" + getId() +
            ", roomn='" + getRoomn() + "'" +
            ", guestname='" + getGuestname() + "'" +
            ", account='" + getAccount() + "'" +
            ", rq='" + getRq() + "'" +
            ", cardid='" + getCardid() + "'" +
            ", djh='" + getDjh() + "'" +
            ", sqh='" + getSqh() + "'" +
            ", empn='" + getEmpn() + "'" +
            ", sign='" + getSign() + "'" +
            ", hoteltime='" + getHoteltime() + "'" +
            ", yxrq='" + getYxrq() + "'" +
            ", je=" + getJe() +
            ", ysqmemo='" + getYsqmemo() + "'" +
            "}";
    }
}
