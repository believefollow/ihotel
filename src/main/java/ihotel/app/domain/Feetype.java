package ihotel.app.domain;

import java.io.Serializable;
import java.math.BigDecimal;
import javax.persistence.*;
import javax.validation.constraints.*;
import org.springframework.data.elasticsearch.annotations.FieldType;

/**
 * A Feetype.
 */
@Entity
@Table(name = "feetype")
@org.springframework.data.elasticsearch.annotations.Document(indexName = "feetype")
public class Feetype implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "feenum", nullable = false)
    private Long feenum;

    @NotNull
    @Size(max = 45)
    @Column(name = "feename", length = 45, nullable = false)
    private String feename;

    @NotNull
    @Column(name = "price", precision = 21, scale = 2, nullable = false)
    private BigDecimal price;

    @NotNull
    @Column(name = "sign", nullable = false)
    private Long sign;

    @Size(max = 45)
    @Column(name = "beizhu", length = 45)
    private String beizhu;

    @Size(max = 45)
    @Column(name = "pym", length = 45)
    private String pym;

    @NotNull
    @Column(name = "salespotn", nullable = false)
    private Long salespotn;

    @Size(max = 50)
    @Column(name = "depot", length = 50)
    private String depot;

    @Column(name = "cbsign")
    private Long cbsign;

    @Column(name = "ordersign")
    private Long ordersign;

    @Size(max = 20)
    @Column(name = "hoteldm", length = 20)
    private String hoteldm;

    @Column(name = "isnew")
    private Long isnew;

    @Column(name = "ygj", precision = 21, scale = 2)
    private BigDecimal ygj;

    @Size(max = 2)
    @Column(name = "autosign", length = 2)
    private String autosign;

    @Column(name = "jj", precision = 21, scale = 2)
    private BigDecimal jj;

    @Column(name = "hyj", precision = 21, scale = 2)
    private BigDecimal hyj;

    @Column(name = "dqkc", precision = 21, scale = 2)
    private BigDecimal dqkc;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Feetype id(Long id) {
        this.id = id;
        return this;
    }

    public Long getFeenum() {
        return this.feenum;
    }

    public Feetype feenum(Long feenum) {
        this.feenum = feenum;
        return this;
    }

    public void setFeenum(Long feenum) {
        this.feenum = feenum;
    }

    public String getFeename() {
        return this.feename;
    }

    public Feetype feename(String feename) {
        this.feename = feename;
        return this;
    }

    public void setFeename(String feename) {
        this.feename = feename;
    }

    public BigDecimal getPrice() {
        return this.price;
    }

    public Feetype price(BigDecimal price) {
        this.price = price;
        return this;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public Long getSign() {
        return this.sign;
    }

    public Feetype sign(Long sign) {
        this.sign = sign;
        return this;
    }

    public void setSign(Long sign) {
        this.sign = sign;
    }

    public String getBeizhu() {
        return this.beizhu;
    }

    public Feetype beizhu(String beizhu) {
        this.beizhu = beizhu;
        return this;
    }

    public void setBeizhu(String beizhu) {
        this.beizhu = beizhu;
    }

    public String getPym() {
        return this.pym;
    }

    public Feetype pym(String pym) {
        this.pym = pym;
        return this;
    }

    public void setPym(String pym) {
        this.pym = pym;
    }

    public Long getSalespotn() {
        return this.salespotn;
    }

    public Feetype salespotn(Long salespotn) {
        this.salespotn = salespotn;
        return this;
    }

    public void setSalespotn(Long salespotn) {
        this.salespotn = salespotn;
    }

    public String getDepot() {
        return this.depot;
    }

    public Feetype depot(String depot) {
        this.depot = depot;
        return this;
    }

    public void setDepot(String depot) {
        this.depot = depot;
    }

    public Long getCbsign() {
        return this.cbsign;
    }

    public Feetype cbsign(Long cbsign) {
        this.cbsign = cbsign;
        return this;
    }

    public void setCbsign(Long cbsign) {
        this.cbsign = cbsign;
    }

    public Long getOrdersign() {
        return this.ordersign;
    }

    public Feetype ordersign(Long ordersign) {
        this.ordersign = ordersign;
        return this;
    }

    public void setOrdersign(Long ordersign) {
        this.ordersign = ordersign;
    }

    public String getHoteldm() {
        return this.hoteldm;
    }

    public Feetype hoteldm(String hoteldm) {
        this.hoteldm = hoteldm;
        return this;
    }

    public void setHoteldm(String hoteldm) {
        this.hoteldm = hoteldm;
    }

    public Long getIsnew() {
        return this.isnew;
    }

    public Feetype isnew(Long isnew) {
        this.isnew = isnew;
        return this;
    }

    public void setIsnew(Long isnew) {
        this.isnew = isnew;
    }

    public BigDecimal getYgj() {
        return this.ygj;
    }

    public Feetype ygj(BigDecimal ygj) {
        this.ygj = ygj;
        return this;
    }

    public void setYgj(BigDecimal ygj) {
        this.ygj = ygj;
    }

    public String getAutosign() {
        return this.autosign;
    }

    public Feetype autosign(String autosign) {
        this.autosign = autosign;
        return this;
    }

    public void setAutosign(String autosign) {
        this.autosign = autosign;
    }

    public BigDecimal getJj() {
        return this.jj;
    }

    public Feetype jj(BigDecimal jj) {
        this.jj = jj;
        return this;
    }

    public void setJj(BigDecimal jj) {
        this.jj = jj;
    }

    public BigDecimal getHyj() {
        return this.hyj;
    }

    public Feetype hyj(BigDecimal hyj) {
        this.hyj = hyj;
        return this;
    }

    public void setHyj(BigDecimal hyj) {
        this.hyj = hyj;
    }

    public BigDecimal getDqkc() {
        return this.dqkc;
    }

    public Feetype dqkc(BigDecimal dqkc) {
        this.dqkc = dqkc;
        return this;
    }

    public void setDqkc(BigDecimal dqkc) {
        this.dqkc = dqkc;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Feetype)) {
            return false;
        }
        return id != null && id.equals(((Feetype) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Feetype{" +
            "id=" + getId() +
            ", feenum=" + getFeenum() +
            ", feename='" + getFeename() + "'" +
            ", price=" + getPrice() +
            ", sign=" + getSign() +
            ", beizhu='" + getBeizhu() + "'" +
            ", pym='" + getPym() + "'" +
            ", salespotn=" + getSalespotn() +
            ", depot='" + getDepot() + "'" +
            ", cbsign=" + getCbsign() +
            ", ordersign=" + getOrdersign() +
            ", hoteldm='" + getHoteldm() + "'" +
            ", isnew=" + getIsnew() +
            ", ygj=" + getYgj() +
            ", autosign='" + getAutosign() + "'" +
            ", jj=" + getJj() +
            ", hyj=" + getHyj() +
            ", dqkc=" + getDqkc() +
            "}";
    }
}
