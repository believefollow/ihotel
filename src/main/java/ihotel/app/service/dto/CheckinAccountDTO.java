package ihotel.app.service.dto;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.Instant;
import java.util.Objects;
import javax.validation.constraints.*;

/**
 * A DTO for the {@link ihotel.app.domain.CheckinAccount} entity.
 */
public class CheckinAccountDTO implements Serializable {

    private Long id;

    @NotNull
    @Size(max = 30)
    private String account;

    @Size(max = 10)
    private String roomn;

    private Instant indatetime;

    private Instant gotime;

    private BigDecimal kfang;

    private BigDecimal dhua;

    private BigDecimal minin;

    private BigDecimal peich;

    private BigDecimal qit;

    private BigDecimal total;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public String getRoomn() {
        return roomn;
    }

    public void setRoomn(String roomn) {
        this.roomn = roomn;
    }

    public Instant getIndatetime() {
        return indatetime;
    }

    public void setIndatetime(Instant indatetime) {
        this.indatetime = indatetime;
    }

    public Instant getGotime() {
        return gotime;
    }

    public void setGotime(Instant gotime) {
        this.gotime = gotime;
    }

    public BigDecimal getKfang() {
        return kfang;
    }

    public void setKfang(BigDecimal kfang) {
        this.kfang = kfang;
    }

    public BigDecimal getDhua() {
        return dhua;
    }

    public void setDhua(BigDecimal dhua) {
        this.dhua = dhua;
    }

    public BigDecimal getMinin() {
        return minin;
    }

    public void setMinin(BigDecimal minin) {
        this.minin = minin;
    }

    public BigDecimal getPeich() {
        return peich;
    }

    public void setPeich(BigDecimal peich) {
        this.peich = peich;
    }

    public BigDecimal getQit() {
        return qit;
    }

    public void setQit(BigDecimal qit) {
        this.qit = qit;
    }

    public BigDecimal getTotal() {
        return total;
    }

    public void setTotal(BigDecimal total) {
        this.total = total;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof CheckinAccountDTO)) {
            return false;
        }

        CheckinAccountDTO checkinAccountDTO = (CheckinAccountDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, checkinAccountDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "CheckinAccountDTO{" +
            "id=" + getId() +
            ", account='" + getAccount() + "'" +
            ", roomn='" + getRoomn() + "'" +
            ", indatetime='" + getIndatetime() + "'" +
            ", gotime='" + getGotime() + "'" +
            ", kfang=" + getKfang() +
            ", dhua=" + getDhua() +
            ", minin=" + getMinin() +
            ", peich=" + getPeich() +
            ", qit=" + getQit() +
            ", total=" + getTotal() +
            "}";
    }
}
