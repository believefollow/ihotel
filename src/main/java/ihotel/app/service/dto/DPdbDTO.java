package ihotel.app.service.dto;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.Instant;
import java.util.Objects;
import javax.validation.constraints.*;

/**
 * A DTO for the {@link ihotel.app.domain.DPdb} entity.
 */
public class DPdbDTO implements Serializable {

    @NotNull
    private Long id;

    private Instant begindate;

    private Instant enddate;

    @Size(max = 20)
    private String bm;

    @Size(max = 50)
    private String spmc;

    private BigDecimal qcsl;

    private BigDecimal rksl;

    private BigDecimal xssl;

    private BigDecimal dbsl;

    private BigDecimal qtck;

    private BigDecimal jcsl;

    private BigDecimal swsl;

    private BigDecimal pyk;

    @Size(max = 200)
    private String memo;

    @Size(max = 50)
    private String depot;

    private BigDecimal rkje;

    private BigDecimal xsje;

    private BigDecimal dbje;

    private BigDecimal jcje;

    @Size(max = 50)
    private String dp;

    private BigDecimal qcje;

    private BigDecimal swje;

    private BigDecimal qtje;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Instant getBegindate() {
        return begindate;
    }

    public void setBegindate(Instant begindate) {
        this.begindate = begindate;
    }

    public Instant getEnddate() {
        return enddate;
    }

    public void setEnddate(Instant enddate) {
        this.enddate = enddate;
    }

    public String getBm() {
        return bm;
    }

    public void setBm(String bm) {
        this.bm = bm;
    }

    public String getSpmc() {
        return spmc;
    }

    public void setSpmc(String spmc) {
        this.spmc = spmc;
    }

    public BigDecimal getQcsl() {
        return qcsl;
    }

    public void setQcsl(BigDecimal qcsl) {
        this.qcsl = qcsl;
    }

    public BigDecimal getRksl() {
        return rksl;
    }

    public void setRksl(BigDecimal rksl) {
        this.rksl = rksl;
    }

    public BigDecimal getXssl() {
        return xssl;
    }

    public void setXssl(BigDecimal xssl) {
        this.xssl = xssl;
    }

    public BigDecimal getDbsl() {
        return dbsl;
    }

    public void setDbsl(BigDecimal dbsl) {
        this.dbsl = dbsl;
    }

    public BigDecimal getQtck() {
        return qtck;
    }

    public void setQtck(BigDecimal qtck) {
        this.qtck = qtck;
    }

    public BigDecimal getJcsl() {
        return jcsl;
    }

    public void setJcsl(BigDecimal jcsl) {
        this.jcsl = jcsl;
    }

    public BigDecimal getSwsl() {
        return swsl;
    }

    public void setSwsl(BigDecimal swsl) {
        this.swsl = swsl;
    }

    public BigDecimal getPyk() {
        return pyk;
    }

    public void setPyk(BigDecimal pyk) {
        this.pyk = pyk;
    }

    public String getMemo() {
        return memo;
    }

    public void setMemo(String memo) {
        this.memo = memo;
    }

    public String getDepot() {
        return depot;
    }

    public void setDepot(String depot) {
        this.depot = depot;
    }

    public BigDecimal getRkje() {
        return rkje;
    }

    public void setRkje(BigDecimal rkje) {
        this.rkje = rkje;
    }

    public BigDecimal getXsje() {
        return xsje;
    }

    public void setXsje(BigDecimal xsje) {
        this.xsje = xsje;
    }

    public BigDecimal getDbje() {
        return dbje;
    }

    public void setDbje(BigDecimal dbje) {
        this.dbje = dbje;
    }

    public BigDecimal getJcje() {
        return jcje;
    }

    public void setJcje(BigDecimal jcje) {
        this.jcje = jcje;
    }

    public String getDp() {
        return dp;
    }

    public void setDp(String dp) {
        this.dp = dp;
    }

    public BigDecimal getQcje() {
        return qcje;
    }

    public void setQcje(BigDecimal qcje) {
        this.qcje = qcje;
    }

    public BigDecimal getSwje() {
        return swje;
    }

    public void setSwje(BigDecimal swje) {
        this.swje = swje;
    }

    public BigDecimal getQtje() {
        return qtje;
    }

    public void setQtje(BigDecimal qtje) {
        this.qtje = qtje;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof DPdbDTO)) {
            return false;
        }

        DPdbDTO dPdbDTO = (DPdbDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, dPdbDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "DPdbDTO{" +
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
