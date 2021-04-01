package ihotel.app.domain;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.Instant;
import javax.persistence.*;
import javax.validation.constraints.*;
import org.springframework.data.elasticsearch.annotations.FieldType;

/**
 * A Dayearndetail.
 */
@Entity
@Table(name = "dayearndetail")
@org.springframework.data.elasticsearch.annotations.Document(indexName = "dayearndetail")
public class Dayearndetail implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "earndate", nullable = false)
    private Instant earndate;

    @NotNull
    @Column(name = "salespotn", nullable = false)
    private Long salespotn;

    @Column(name = "money", precision = 21, scale = 2)
    private BigDecimal money;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Dayearndetail id(Long id) {
        this.id = id;
        return this;
    }

    public Instant getEarndate() {
        return this.earndate;
    }

    public Dayearndetail earndate(Instant earndate) {
        this.earndate = earndate;
        return this;
    }

    public void setEarndate(Instant earndate) {
        this.earndate = earndate;
    }

    public Long getSalespotn() {
        return this.salespotn;
    }

    public Dayearndetail salespotn(Long salespotn) {
        this.salespotn = salespotn;
        return this;
    }

    public void setSalespotn(Long salespotn) {
        this.salespotn = salespotn;
    }

    public BigDecimal getMoney() {
        return this.money;
    }

    public Dayearndetail money(BigDecimal money) {
        this.money = money;
        return this;
    }

    public void setMoney(BigDecimal money) {
        this.money = money;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Dayearndetail)) {
            return false;
        }
        return id != null && id.equals(((Dayearndetail) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Dayearndetail{" +
            "id=" + getId() +
            ", earndate='" + getEarndate() + "'" +
            ", salespotn=" + getSalespotn() +
            ", money=" + getMoney() +
            "}";
    }
}
