package ihotel.app.domain;

import java.io.Serializable;
import java.math.BigDecimal;
import javax.persistence.*;
import javax.validation.constraints.*;
import org.springframework.data.elasticsearch.annotations.FieldType;

/**
 * A DKc.
 */
@Entity
@Table(name = "d_kc")
@org.springframework.data.elasticsearch.annotations.Document(indexName = "dkc")
public class DKc implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Size(max = 20)
    @Column(name = "depot", length = 20, nullable = false)
    private String depot;

    @NotNull
    @Size(max = 20)
    @Column(name = "spbm", length = 20, nullable = false)
    private String spbm;

    @NotNull
    @Size(max = 50)
    @Column(name = "spmc", length = 50, nullable = false)
    private String spmc;

    @Size(max = 50)
    @Column(name = "xh", length = 50)
    private String xh;

    @Size(max = 20)
    @Column(name = "dw", length = 20)
    private String dw;

    @Column(name = "price", precision = 21, scale = 2)
    private BigDecimal price;

    @Column(name = "sl", precision = 21, scale = 2)
    private BigDecimal sl;

    @Column(name = "je", precision = 21, scale = 2)
    private BigDecimal je;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public DKc id(Long id) {
        this.id = id;
        return this;
    }

    public String getDepot() {
        return this.depot;
    }

    public DKc depot(String depot) {
        this.depot = depot;
        return this;
    }

    public void setDepot(String depot) {
        this.depot = depot;
    }

    public String getSpbm() {
        return this.spbm;
    }

    public DKc spbm(String spbm) {
        this.spbm = spbm;
        return this;
    }

    public void setSpbm(String spbm) {
        this.spbm = spbm;
    }

    public String getSpmc() {
        return this.spmc;
    }

    public DKc spmc(String spmc) {
        this.spmc = spmc;
        return this;
    }

    public void setSpmc(String spmc) {
        this.spmc = spmc;
    }

    public String getXh() {
        return this.xh;
    }

    public DKc xh(String xh) {
        this.xh = xh;
        return this;
    }

    public void setXh(String xh) {
        this.xh = xh;
    }

    public String getDw() {
        return this.dw;
    }

    public DKc dw(String dw) {
        this.dw = dw;
        return this;
    }

    public void setDw(String dw) {
        this.dw = dw;
    }

    public BigDecimal getPrice() {
        return this.price;
    }

    public DKc price(BigDecimal price) {
        this.price = price;
        return this;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public BigDecimal getSl() {
        return this.sl;
    }

    public DKc sl(BigDecimal sl) {
        this.sl = sl;
        return this;
    }

    public void setSl(BigDecimal sl) {
        this.sl = sl;
    }

    public BigDecimal getJe() {
        return this.je;
    }

    public DKc je(BigDecimal je) {
        this.je = je;
        return this;
    }

    public void setJe(BigDecimal je) {
        this.je = je;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof DKc)) {
            return false;
        }
        return id != null && id.equals(((DKc) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "DKc{" +
            "id=" + getId() +
            ", depot='" + getDepot() + "'" +
            ", spbm='" + getSpbm() + "'" +
            ", spmc='" + getSpmc() + "'" +
            ", xh='" + getXh() + "'" +
            ", dw='" + getDw() + "'" +
            ", price=" + getPrice() +
            ", sl=" + getSl() +
            ", je=" + getJe() +
            "}";
    }
}
