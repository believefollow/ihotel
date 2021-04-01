package ihotel.app.domain;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.Instant;
import javax.persistence.*;
import org.springframework.data.elasticsearch.annotations.FieldType;

/**
 * A CzBqz.
 */
@Entity
@Table(name = "cz_bqz")
@org.springframework.data.elasticsearch.annotations.Document(indexName = "czbqz")
public class CzBqz implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "rq")
    private Instant rq;

    @Column(name = "q_sl")
    private Long qSl;

    @Column(name = "q_kfl", precision = 21, scale = 2)
    private BigDecimal qKfl;

    @Column(name = "q_pjz", precision = 21, scale = 2)
    private BigDecimal qPjz;

    @Column(name = "q_ysfz", precision = 21, scale = 2)
    private BigDecimal qYsfz;

    @Column(name = "q_sjfz", precision = 21, scale = 2)
    private BigDecimal qSjfz;

    @Column(name = "q_fzcz", precision = 21, scale = 2)
    private BigDecimal qFzcz;

    @Column(name = "q_pjzcz", precision = 21, scale = 2)
    private BigDecimal qPjzcz;

    @Column(name = "b_sl")
    private Long bSl;

    @Column(name = "b_kfl", precision = 21, scale = 2)
    private BigDecimal bKfl;

    @Column(name = "b_pjz", precision = 21, scale = 2)
    private BigDecimal bPjz;

    @Column(name = "b_ysfz", precision = 21, scale = 2)
    private BigDecimal bYsfz;

    @Column(name = "b_sjfz", precision = 21, scale = 2)
    private BigDecimal bSjfz;

    @Column(name = "b_fzcz", precision = 21, scale = 2)
    private BigDecimal bFzcz;

    @Column(name = "b_pjzcz", precision = 21, scale = 2)
    private BigDecimal bPjzcz;

    @Column(name = "z_sl")
    private Long zSl;

    @Column(name = "z_kfl", precision = 21, scale = 2)
    private BigDecimal zKfl;

    @Column(name = "z_pjz", precision = 21, scale = 2)
    private BigDecimal zPjz;

    @Column(name = "z_ysfz", precision = 21, scale = 2)
    private BigDecimal zYsfz;

    @Column(name = "z_sjfz", precision = 21, scale = 2)
    private BigDecimal zSjfz;

    @Column(name = "z_fzcz", precision = 21, scale = 2)
    private BigDecimal zFzcz;

    @Column(name = "z_pjzcz", precision = 21, scale = 2)
    private BigDecimal zPjzcz;

    @Column(name = "zk", precision = 21, scale = 2)
    private BigDecimal zk;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public CzBqz id(Long id) {
        this.id = id;
        return this;
    }

    public Instant getRq() {
        return this.rq;
    }

    public CzBqz rq(Instant rq) {
        this.rq = rq;
        return this;
    }

    public void setRq(Instant rq) {
        this.rq = rq;
    }

    public Long getqSl() {
        return this.qSl;
    }

    public CzBqz qSl(Long qSl) {
        this.qSl = qSl;
        return this;
    }

    public void setqSl(Long qSl) {
        this.qSl = qSl;
    }

    public BigDecimal getqKfl() {
        return this.qKfl;
    }

    public CzBqz qKfl(BigDecimal qKfl) {
        this.qKfl = qKfl;
        return this;
    }

    public void setqKfl(BigDecimal qKfl) {
        this.qKfl = qKfl;
    }

    public BigDecimal getqPjz() {
        return this.qPjz;
    }

    public CzBqz qPjz(BigDecimal qPjz) {
        this.qPjz = qPjz;
        return this;
    }

    public void setqPjz(BigDecimal qPjz) {
        this.qPjz = qPjz;
    }

    public BigDecimal getqYsfz() {
        return this.qYsfz;
    }

    public CzBqz qYsfz(BigDecimal qYsfz) {
        this.qYsfz = qYsfz;
        return this;
    }

    public void setqYsfz(BigDecimal qYsfz) {
        this.qYsfz = qYsfz;
    }

    public BigDecimal getqSjfz() {
        return this.qSjfz;
    }

    public CzBqz qSjfz(BigDecimal qSjfz) {
        this.qSjfz = qSjfz;
        return this;
    }

    public void setqSjfz(BigDecimal qSjfz) {
        this.qSjfz = qSjfz;
    }

    public BigDecimal getqFzcz() {
        return this.qFzcz;
    }

    public CzBqz qFzcz(BigDecimal qFzcz) {
        this.qFzcz = qFzcz;
        return this;
    }

    public void setqFzcz(BigDecimal qFzcz) {
        this.qFzcz = qFzcz;
    }

    public BigDecimal getqPjzcz() {
        return this.qPjzcz;
    }

    public CzBqz qPjzcz(BigDecimal qPjzcz) {
        this.qPjzcz = qPjzcz;
        return this;
    }

    public void setqPjzcz(BigDecimal qPjzcz) {
        this.qPjzcz = qPjzcz;
    }

    public Long getbSl() {
        return this.bSl;
    }

    public CzBqz bSl(Long bSl) {
        this.bSl = bSl;
        return this;
    }

    public void setbSl(Long bSl) {
        this.bSl = bSl;
    }

    public BigDecimal getbKfl() {
        return this.bKfl;
    }

    public CzBqz bKfl(BigDecimal bKfl) {
        this.bKfl = bKfl;
        return this;
    }

    public void setbKfl(BigDecimal bKfl) {
        this.bKfl = bKfl;
    }

    public BigDecimal getbPjz() {
        return this.bPjz;
    }

    public CzBqz bPjz(BigDecimal bPjz) {
        this.bPjz = bPjz;
        return this;
    }

    public void setbPjz(BigDecimal bPjz) {
        this.bPjz = bPjz;
    }

    public BigDecimal getbYsfz() {
        return this.bYsfz;
    }

    public CzBqz bYsfz(BigDecimal bYsfz) {
        this.bYsfz = bYsfz;
        return this;
    }

    public void setbYsfz(BigDecimal bYsfz) {
        this.bYsfz = bYsfz;
    }

    public BigDecimal getbSjfz() {
        return this.bSjfz;
    }

    public CzBqz bSjfz(BigDecimal bSjfz) {
        this.bSjfz = bSjfz;
        return this;
    }

    public void setbSjfz(BigDecimal bSjfz) {
        this.bSjfz = bSjfz;
    }

    public BigDecimal getbFzcz() {
        return this.bFzcz;
    }

    public CzBqz bFzcz(BigDecimal bFzcz) {
        this.bFzcz = bFzcz;
        return this;
    }

    public void setbFzcz(BigDecimal bFzcz) {
        this.bFzcz = bFzcz;
    }

    public BigDecimal getbPjzcz() {
        return this.bPjzcz;
    }

    public CzBqz bPjzcz(BigDecimal bPjzcz) {
        this.bPjzcz = bPjzcz;
        return this;
    }

    public void setbPjzcz(BigDecimal bPjzcz) {
        this.bPjzcz = bPjzcz;
    }

    public Long getzSl() {
        return this.zSl;
    }

    public CzBqz zSl(Long zSl) {
        this.zSl = zSl;
        return this;
    }

    public void setzSl(Long zSl) {
        this.zSl = zSl;
    }

    public BigDecimal getzKfl() {
        return this.zKfl;
    }

    public CzBqz zKfl(BigDecimal zKfl) {
        this.zKfl = zKfl;
        return this;
    }

    public void setzKfl(BigDecimal zKfl) {
        this.zKfl = zKfl;
    }

    public BigDecimal getzPjz() {
        return this.zPjz;
    }

    public CzBqz zPjz(BigDecimal zPjz) {
        this.zPjz = zPjz;
        return this;
    }

    public void setzPjz(BigDecimal zPjz) {
        this.zPjz = zPjz;
    }

    public BigDecimal getzYsfz() {
        return this.zYsfz;
    }

    public CzBqz zYsfz(BigDecimal zYsfz) {
        this.zYsfz = zYsfz;
        return this;
    }

    public void setzYsfz(BigDecimal zYsfz) {
        this.zYsfz = zYsfz;
    }

    public BigDecimal getzSjfz() {
        return this.zSjfz;
    }

    public CzBqz zSjfz(BigDecimal zSjfz) {
        this.zSjfz = zSjfz;
        return this;
    }

    public void setzSjfz(BigDecimal zSjfz) {
        this.zSjfz = zSjfz;
    }

    public BigDecimal getzFzcz() {
        return this.zFzcz;
    }

    public CzBqz zFzcz(BigDecimal zFzcz) {
        this.zFzcz = zFzcz;
        return this;
    }

    public void setzFzcz(BigDecimal zFzcz) {
        this.zFzcz = zFzcz;
    }

    public BigDecimal getzPjzcz() {
        return this.zPjzcz;
    }

    public CzBqz zPjzcz(BigDecimal zPjzcz) {
        this.zPjzcz = zPjzcz;
        return this;
    }

    public void setzPjzcz(BigDecimal zPjzcz) {
        this.zPjzcz = zPjzcz;
    }

    public BigDecimal getZk() {
        return this.zk;
    }

    public CzBqz zk(BigDecimal zk) {
        this.zk = zk;
        return this;
    }

    public void setZk(BigDecimal zk) {
        this.zk = zk;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof CzBqz)) {
            return false;
        }
        return id != null && id.equals(((CzBqz) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "CzBqz{" +
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
