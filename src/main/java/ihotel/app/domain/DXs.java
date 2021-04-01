package ihotel.app.domain;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.Instant;
import javax.persistence.*;
import javax.validation.constraints.*;
import org.springframework.data.elasticsearch.annotations.FieldType;

/**
 * A DXs.
 */
@Entity
@Table(name = "d_xs")
@org.springframework.data.elasticsearch.annotations.Document(indexName = "dxs")
public class DXs implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "begintime", nullable = false)
    private Instant begintime;

    @NotNull
    @Column(name = "endtime", nullable = false)
    private Instant endtime;

    @NotNull
    @Size(max = 30)
    @Column(name = "ckbillno", length = 30, nullable = false)
    private String ckbillno;

    @Size(max = 50)
    @Column(name = "depot", length = 50)
    private String depot;

    @NotNull
    @Column(name = "kcid", nullable = false)
    private Long kcid;

    @NotNull
    @Column(name = "ckid", nullable = false)
    private Long ckid;

    @NotNull
    @Size(max = 30)
    @Column(name = "spbm", length = 30, nullable = false)
    private String spbm;

    @NotNull
    @Size(max = 50)
    @Column(name = "spmc", length = 50, nullable = false)
    private String spmc;

    @Size(max = 20)
    @Column(name = "unit", length = 20)
    private String unit;

    @Column(name = "rkprice", precision = 21, scale = 2)
    private BigDecimal rkprice;

    @Column(name = "xsprice", precision = 21, scale = 2)
    private BigDecimal xsprice;

    @Column(name = "sl", precision = 21, scale = 2)
    private BigDecimal sl;

    @Column(name = "rkje", precision = 21, scale = 2)
    private BigDecimal rkje;

    @Column(name = "xsje", precision = 21, scale = 2)
    private BigDecimal xsje;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public DXs id(Long id) {
        this.id = id;
        return this;
    }

    public Instant getBegintime() {
        return this.begintime;
    }

    public DXs begintime(Instant begintime) {
        this.begintime = begintime;
        return this;
    }

    public void setBegintime(Instant begintime) {
        this.begintime = begintime;
    }

    public Instant getEndtime() {
        return this.endtime;
    }

    public DXs endtime(Instant endtime) {
        this.endtime = endtime;
        return this;
    }

    public void setEndtime(Instant endtime) {
        this.endtime = endtime;
    }

    public String getCkbillno() {
        return this.ckbillno;
    }

    public DXs ckbillno(String ckbillno) {
        this.ckbillno = ckbillno;
        return this;
    }

    public void setCkbillno(String ckbillno) {
        this.ckbillno = ckbillno;
    }

    public String getDepot() {
        return this.depot;
    }

    public DXs depot(String depot) {
        this.depot = depot;
        return this;
    }

    public void setDepot(String depot) {
        this.depot = depot;
    }

    public Long getKcid() {
        return this.kcid;
    }

    public DXs kcid(Long kcid) {
        this.kcid = kcid;
        return this;
    }

    public void setKcid(Long kcid) {
        this.kcid = kcid;
    }

    public Long getCkid() {
        return this.ckid;
    }

    public DXs ckid(Long ckid) {
        this.ckid = ckid;
        return this;
    }

    public void setCkid(Long ckid) {
        this.ckid = ckid;
    }

    public String getSpbm() {
        return this.spbm;
    }

    public DXs spbm(String spbm) {
        this.spbm = spbm;
        return this;
    }

    public void setSpbm(String spbm) {
        this.spbm = spbm;
    }

    public String getSpmc() {
        return this.spmc;
    }

    public DXs spmc(String spmc) {
        this.spmc = spmc;
        return this;
    }

    public void setSpmc(String spmc) {
        this.spmc = spmc;
    }

    public String getUnit() {
        return this.unit;
    }

    public DXs unit(String unit) {
        this.unit = unit;
        return this;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public BigDecimal getRkprice() {
        return this.rkprice;
    }

    public DXs rkprice(BigDecimal rkprice) {
        this.rkprice = rkprice;
        return this;
    }

    public void setRkprice(BigDecimal rkprice) {
        this.rkprice = rkprice;
    }

    public BigDecimal getXsprice() {
        return this.xsprice;
    }

    public DXs xsprice(BigDecimal xsprice) {
        this.xsprice = xsprice;
        return this;
    }

    public void setXsprice(BigDecimal xsprice) {
        this.xsprice = xsprice;
    }

    public BigDecimal getSl() {
        return this.sl;
    }

    public DXs sl(BigDecimal sl) {
        this.sl = sl;
        return this;
    }

    public void setSl(BigDecimal sl) {
        this.sl = sl;
    }

    public BigDecimal getRkje() {
        return this.rkje;
    }

    public DXs rkje(BigDecimal rkje) {
        this.rkje = rkje;
        return this;
    }

    public void setRkje(BigDecimal rkje) {
        this.rkje = rkje;
    }

    public BigDecimal getXsje() {
        return this.xsje;
    }

    public DXs xsje(BigDecimal xsje) {
        this.xsje = xsje;
        return this;
    }

    public void setXsje(BigDecimal xsje) {
        this.xsje = xsje;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof DXs)) {
            return false;
        }
        return id != null && id.equals(((DXs) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "DXs{" +
            "id=" + getId() +
            ", begintime='" + getBegintime() + "'" +
            ", endtime='" + getEndtime() + "'" +
            ", ckbillno='" + getCkbillno() + "'" +
            ", depot='" + getDepot() + "'" +
            ", kcid=" + getKcid() +
            ", ckid=" + getCkid() +
            ", spbm='" + getSpbm() + "'" +
            ", spmc='" + getSpmc() + "'" +
            ", unit='" + getUnit() + "'" +
            ", rkprice=" + getRkprice() +
            ", xsprice=" + getXsprice() +
            ", sl=" + getSl() +
            ", rkje=" + getRkje() +
            ", xsje=" + getXsje() +
            "}";
    }
}
