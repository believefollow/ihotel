package ihotel.app.domain;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.Instant;
import javax.persistence.*;
import javax.validation.constraints.*;
import org.springframework.data.elasticsearch.annotations.FieldType;

/**
 * A DPdb.
 */
@Entity
@Table(name = "d_pdb")
@org.springframework.data.elasticsearch.annotations.Document(indexName = "dpdb")
public class DPdb implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "begindate")
    private Instant begindate;

    @Column(name = "enddate")
    private Instant enddate;

    @Size(max = 20)
    @Column(name = "bm", length = 20)
    private String bm;

    @Size(max = 50)
    @Column(name = "spmc", length = 50)
    private String spmc;

    @Column(name = "qcsl", precision = 21, scale = 2)
    private BigDecimal qcsl;

    @Column(name = "rksl", precision = 21, scale = 2)
    private BigDecimal rksl;

    @Column(name = "xssl", precision = 21, scale = 2)
    private BigDecimal xssl;

    @Column(name = "dbsl", precision = 21, scale = 2)
    private BigDecimal dbsl;

    @Column(name = "qtck", precision = 21, scale = 2)
    private BigDecimal qtck;

    @Column(name = "jcsl", precision = 21, scale = 2)
    private BigDecimal jcsl;

    @Column(name = "swsl", precision = 21, scale = 2)
    private BigDecimal swsl;

    @Column(name = "pyk", precision = 21, scale = 2)
    private BigDecimal pyk;

    @Size(max = 200)
    @Column(name = "memo", length = 200)
    private String memo;

    @Size(max = 50)
    @Column(name = "depot", length = 50)
    private String depot;

    @Column(name = "rkje", precision = 21, scale = 2)
    private BigDecimal rkje;

    @Column(name = "xsje", precision = 21, scale = 2)
    private BigDecimal xsje;

    @Column(name = "dbje", precision = 21, scale = 2)
    private BigDecimal dbje;

    @Column(name = "jcje", precision = 21, scale = 2)
    private BigDecimal jcje;

    @Size(max = 50)
    @Column(name = "dp", length = 50)
    private String dp;

    @Column(name = "qcje", precision = 21, scale = 2)
    private BigDecimal qcje;

    @Column(name = "swje", precision = 21, scale = 2)
    private BigDecimal swje;

    @Column(name = "qtje", precision = 21, scale = 2)
    private BigDecimal qtje;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public DPdb id(Long id) {
        this.id = id;
        return this;
    }

    public Instant getBegindate() {
        return this.begindate;
    }

    public DPdb begindate(Instant begindate) {
        this.begindate = begindate;
        return this;
    }

    public void setBegindate(Instant begindate) {
        this.begindate = begindate;
    }

    public Instant getEnddate() {
        return this.enddate;
    }

    public DPdb enddate(Instant enddate) {
        this.enddate = enddate;
        return this;
    }

    public void setEnddate(Instant enddate) {
        this.enddate = enddate;
    }

    public String getBm() {
        return this.bm;
    }

    public DPdb bm(String bm) {
        this.bm = bm;
        return this;
    }

    public void setBm(String bm) {
        this.bm = bm;
    }

    public String getSpmc() {
        return this.spmc;
    }

    public DPdb spmc(String spmc) {
        this.spmc = spmc;
        return this;
    }

    public void setSpmc(String spmc) {
        this.spmc = spmc;
    }

    public BigDecimal getQcsl() {
        return this.qcsl;
    }

    public DPdb qcsl(BigDecimal qcsl) {
        this.qcsl = qcsl;
        return this;
    }

    public void setQcsl(BigDecimal qcsl) {
        this.qcsl = qcsl;
    }

    public BigDecimal getRksl() {
        return this.rksl;
    }

    public DPdb rksl(BigDecimal rksl) {
        this.rksl = rksl;
        return this;
    }

    public void setRksl(BigDecimal rksl) {
        this.rksl = rksl;
    }

    public BigDecimal getXssl() {
        return this.xssl;
    }

    public DPdb xssl(BigDecimal xssl) {
        this.xssl = xssl;
        return this;
    }

    public void setXssl(BigDecimal xssl) {
        this.xssl = xssl;
    }

    public BigDecimal getDbsl() {
        return this.dbsl;
    }

    public DPdb dbsl(BigDecimal dbsl) {
        this.dbsl = dbsl;
        return this;
    }

    public void setDbsl(BigDecimal dbsl) {
        this.dbsl = dbsl;
    }

    public BigDecimal getQtck() {
        return this.qtck;
    }

    public DPdb qtck(BigDecimal qtck) {
        this.qtck = qtck;
        return this;
    }

    public void setQtck(BigDecimal qtck) {
        this.qtck = qtck;
    }

    public BigDecimal getJcsl() {
        return this.jcsl;
    }

    public DPdb jcsl(BigDecimal jcsl) {
        this.jcsl = jcsl;
        return this;
    }

    public void setJcsl(BigDecimal jcsl) {
        this.jcsl = jcsl;
    }

    public BigDecimal getSwsl() {
        return this.swsl;
    }

    public DPdb swsl(BigDecimal swsl) {
        this.swsl = swsl;
        return this;
    }

    public void setSwsl(BigDecimal swsl) {
        this.swsl = swsl;
    }

    public BigDecimal getPyk() {
        return this.pyk;
    }

    public DPdb pyk(BigDecimal pyk) {
        this.pyk = pyk;
        return this;
    }

    public void setPyk(BigDecimal pyk) {
        this.pyk = pyk;
    }

    public String getMemo() {
        return this.memo;
    }

    public DPdb memo(String memo) {
        this.memo = memo;
        return this;
    }

    public void setMemo(String memo) {
        this.memo = memo;
    }

    public String getDepot() {
        return this.depot;
    }

    public DPdb depot(String depot) {
        this.depot = depot;
        return this;
    }

    public void setDepot(String depot) {
        this.depot = depot;
    }

    public BigDecimal getRkje() {
        return this.rkje;
    }

    public DPdb rkje(BigDecimal rkje) {
        this.rkje = rkje;
        return this;
    }

    public void setRkje(BigDecimal rkje) {
        this.rkje = rkje;
    }

    public BigDecimal getXsje() {
        return this.xsje;
    }

    public DPdb xsje(BigDecimal xsje) {
        this.xsje = xsje;
        return this;
    }

    public void setXsje(BigDecimal xsje) {
        this.xsje = xsje;
    }

    public BigDecimal getDbje() {
        return this.dbje;
    }

    public DPdb dbje(BigDecimal dbje) {
        this.dbje = dbje;
        return this;
    }

    public void setDbje(BigDecimal dbje) {
        this.dbje = dbje;
    }

    public BigDecimal getJcje() {
        return this.jcje;
    }

    public DPdb jcje(BigDecimal jcje) {
        this.jcje = jcje;
        return this;
    }

    public void setJcje(BigDecimal jcje) {
        this.jcje = jcje;
    }

    public String getDp() {
        return this.dp;
    }

    public DPdb dp(String dp) {
        this.dp = dp;
        return this;
    }

    public void setDp(String dp) {
        this.dp = dp;
    }

    public BigDecimal getQcje() {
        return this.qcje;
    }

    public DPdb qcje(BigDecimal qcje) {
        this.qcje = qcje;
        return this;
    }

    public void setQcje(BigDecimal qcje) {
        this.qcje = qcje;
    }

    public BigDecimal getSwje() {
        return this.swje;
    }

    public DPdb swje(BigDecimal swje) {
        this.swje = swje;
        return this;
    }

    public void setSwje(BigDecimal swje) {
        this.swje = swje;
    }

    public BigDecimal getQtje() {
        return this.qtje;
    }

    public DPdb qtje(BigDecimal qtje) {
        this.qtje = qtje;
        return this;
    }

    public void setQtje(BigDecimal qtje) {
        this.qtje = qtje;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof DPdb)) {
            return false;
        }
        return id != null && id.equals(((DPdb) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "DPdb{" +
            "id=" + getId() +
            ", begindate='" + getBegindate() + "'" +
            ", enddate='" + getEnddate() + "'" +
            ", bm='" + getBm() + "'" +
            ", spmc='" + getSpmc() + "'" +
            ", qcsl=" + getQcsl() +
            ", rksl=" + getRksl() +
            ", xssl=" + getXssl() +
            ", dbsl=" + getDbsl() +
            ", qtck=" + getQtck() +
            ", jcsl=" + getJcsl() +
            ", swsl=" + getSwsl() +
            ", pyk=" + getPyk() +
            ", memo='" + getMemo() + "'" +
            ", depot='" + getDepot() + "'" +
            ", rkje=" + getRkje() +
            ", xsje=" + getXsje() +
            ", dbje=" + getDbje() +
            ", jcje=" + getJcje() +
            ", dp='" + getDp() + "'" +
            ", qcje=" + getQcje() +
            ", swje=" + getSwje() +
            ", qtje=" + getQtje() +
            "}";
    }
}
