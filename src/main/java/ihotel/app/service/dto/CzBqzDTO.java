package ihotel.app.service.dto;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.Instant;
import java.util.Objects;

/**
 * A DTO for the {@link ihotel.app.domain.CzBqz} entity.
 */
public class CzBqzDTO implements Serializable {

    private Long id;

    private Instant rq;

    private Long qSl;

    private BigDecimal qKfl;

    private BigDecimal qPjz;

    private BigDecimal qYsfz;

    private BigDecimal qSjfz;

    private BigDecimal qFzcz;

    private BigDecimal qPjzcz;

    private Long bSl;

    private BigDecimal bKfl;

    private BigDecimal bPjz;

    private BigDecimal bYsfz;

    private BigDecimal bSjfz;

    private BigDecimal bFzcz;

    private BigDecimal bPjzcz;

    private Long zSl;

    private BigDecimal zKfl;

    private BigDecimal zPjz;

    private BigDecimal zYsfz;

    private BigDecimal zSjfz;

    private BigDecimal zFzcz;

    private BigDecimal zPjzcz;

    private BigDecimal zk;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Instant getRq() {
        return rq;
    }

    public void setRq(Instant rq) {
        this.rq = rq;
    }

    public Long getqSl() {
        return qSl;
    }

    public void setqSl(Long qSl) {
        this.qSl = qSl;
    }

    public BigDecimal getqKfl() {
        return qKfl;
    }

    public void setqKfl(BigDecimal qKfl) {
        this.qKfl = qKfl;
    }

    public BigDecimal getqPjz() {
        return qPjz;
    }

    public void setqPjz(BigDecimal qPjz) {
        this.qPjz = qPjz;
    }

    public BigDecimal getqYsfz() {
        return qYsfz;
    }

    public void setqYsfz(BigDecimal qYsfz) {
        this.qYsfz = qYsfz;
    }

    public BigDecimal getqSjfz() {
        return qSjfz;
    }

    public void setqSjfz(BigDecimal qSjfz) {
        this.qSjfz = qSjfz;
    }

    public BigDecimal getqFzcz() {
        return qFzcz;
    }

    public void setqFzcz(BigDecimal qFzcz) {
        this.qFzcz = qFzcz;
    }

    public BigDecimal getqPjzcz() {
        return qPjzcz;
    }

    public void setqPjzcz(BigDecimal qPjzcz) {
        this.qPjzcz = qPjzcz;
    }

    public Long getbSl() {
        return bSl;
    }

    public void setbSl(Long bSl) {
        this.bSl = bSl;
    }

    public BigDecimal getbKfl() {
        return bKfl;
    }

    public void setbKfl(BigDecimal bKfl) {
        this.bKfl = bKfl;
    }

    public BigDecimal getbPjz() {
        return bPjz;
    }

    public void setbPjz(BigDecimal bPjz) {
        this.bPjz = bPjz;
    }

    public BigDecimal getbYsfz() {
        return bYsfz;
    }

    public void setbYsfz(BigDecimal bYsfz) {
        this.bYsfz = bYsfz;
    }

    public BigDecimal getbSjfz() {
        return bSjfz;
    }

    public void setbSjfz(BigDecimal bSjfz) {
        this.bSjfz = bSjfz;
    }

    public BigDecimal getbFzcz() {
        return bFzcz;
    }

    public void setbFzcz(BigDecimal bFzcz) {
        this.bFzcz = bFzcz;
    }

    public BigDecimal getbPjzcz() {
        return bPjzcz;
    }

    public void setbPjzcz(BigDecimal bPjzcz) {
        this.bPjzcz = bPjzcz;
    }

    public Long getzSl() {
        return zSl;
    }

    public void setzSl(Long zSl) {
        this.zSl = zSl;
    }

    public BigDecimal getzKfl() {
        return zKfl;
    }

    public void setzKfl(BigDecimal zKfl) {
        this.zKfl = zKfl;
    }

    public BigDecimal getzPjz() {
        return zPjz;
    }

    public void setzPjz(BigDecimal zPjz) {
        this.zPjz = zPjz;
    }

    public BigDecimal getzYsfz() {
        return zYsfz;
    }

    public void setzYsfz(BigDecimal zYsfz) {
        this.zYsfz = zYsfz;
    }

    public BigDecimal getzSjfz() {
        return zSjfz;
    }

    public void setzSjfz(BigDecimal zSjfz) {
        this.zSjfz = zSjfz;
    }

    public BigDecimal getzFzcz() {
        return zFzcz;
    }

    public void setzFzcz(BigDecimal zFzcz) {
        this.zFzcz = zFzcz;
    }

    public BigDecimal getzPjzcz() {
        return zPjzcz;
    }

    public void setzPjzcz(BigDecimal zPjzcz) {
        this.zPjzcz = zPjzcz;
    }

    public BigDecimal getZk() {
        return zk;
    }

    public void setZk(BigDecimal zk) {
        this.zk = zk;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof CzBqzDTO)) {
            return false;
        }

        CzBqzDTO czBqzDTO = (CzBqzDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, czBqzDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "CzBqzDTO{" +
            "id=" + getId() +
            ", rq='" + getRq() + "'" +
            ", qSl=" + getqSl() +
            ", qKfl=" + getqKfl() +
            ", qPjz=" + getqPjz() +
            ", qYsfz=" + getqYsfz() +
            ", qSjfz=" + getqSjfz() +
            ", qFzcz=" + getqFzcz() +
            ", qPjzcz=" + getqPjzcz() +
            ", bSl=" + getbSl() +
            ", bKfl=" + getbKfl() +
            ", bPjz=" + getbPjz() +
            ", bYsfz=" + getbYsfz() +
            ", bSjfz=" + getbSjfz() +
            ", bFzcz=" + getbFzcz() +
            ", bPjzcz=" + getbPjzcz() +
            ", zSl=" + getzSl() +
            ", zKfl=" + getzKfl() +
            ", zPjz=" + getzPjz() +
            ", zYsfz=" + getzYsfz() +
            ", zSjfz=" + getzSjfz() +
            ", zFzcz=" + getzFzcz() +
            ", zPjzcz=" + getzPjzcz() +
            ", zk=" + getZk() +
            "}";
    }
}
