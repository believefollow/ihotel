package ihotel.app.service.dto;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.Instant;
import java.util.Objects;
import javax.validation.constraints.*;

/**
 * A DTO for the {@link ihotel.app.domain.CtClass} entity.
 */
public class CtClassDTO implements Serializable {

    private Long id;

    @NotNull
    private Instant dt;

    @NotNull
    @Size(max = 10)
    private String empn;

    private Long flag;

    @Size(max = 20)
    private String jbempn;

    private Instant gotime;

    private BigDecimal xj;

    private BigDecimal zp;

    private BigDecimal sk;

    private BigDecimal hyk;

    private BigDecimal cq;

    private BigDecimal gz;

    private BigDecimal gfz;

    private BigDecimal yq;

    private BigDecimal yh;

    private BigDecimal zzh;

    @Size(max = 2)
    private String checkSign;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Instant getDt() {
        return dt;
    }

    public void setDt(Instant dt) {
        this.dt = dt;
    }

    public String getEmpn() {
        return empn;
    }

    public void setEmpn(String empn) {
        this.empn = empn;
    }

    public Long getFlag() {
        return flag;
    }

    public void setFlag(Long flag) {
        this.flag = flag;
    }

    public String getJbempn() {
        return jbempn;
    }

    public void setJbempn(String jbempn) {
        this.jbempn = jbempn;
    }

    public Instant getGotime() {
        return gotime;
    }

    public void setGotime(Instant gotime) {
        this.gotime = gotime;
    }

    public BigDecimal getXj() {
        return xj;
    }

    public void setXj(BigDecimal xj) {
        this.xj = xj;
    }

    public BigDecimal getZp() {
        return zp;
    }

    public void setZp(BigDecimal zp) {
        this.zp = zp;
    }

    public BigDecimal getSk() {
        return sk;
    }

    public void setSk(BigDecimal sk) {
        this.sk = sk;
    }

    public BigDecimal getHyk() {
        return hyk;
    }

    public void setHyk(BigDecimal hyk) {
        this.hyk = hyk;
    }

    public BigDecimal getCq() {
        return cq;
    }

    public void setCq(BigDecimal cq) {
        this.cq = cq;
    }

    public BigDecimal getGz() {
        return gz;
    }

    public void setGz(BigDecimal gz) {
        this.gz = gz;
    }

    public BigDecimal getGfz() {
        return gfz;
    }

    public void setGfz(BigDecimal gfz) {
        this.gfz = gfz;
    }

    public BigDecimal getYq() {
        return yq;
    }

    public void setYq(BigDecimal yq) {
        this.yq = yq;
    }

    public BigDecimal getYh() {
        return yh;
    }

    public void setYh(BigDecimal yh) {
        this.yh = yh;
    }

    public BigDecimal getZzh() {
        return zzh;
    }

    public void setZzh(BigDecimal zzh) {
        this.zzh = zzh;
    }

    public String getCheckSign() {
        return checkSign;
    }

    public void setCheckSign(String checkSign) {
        this.checkSign = checkSign;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof CtClassDTO)) {
            return false;
        }

        CtClassDTO ctClassDTO = (CtClassDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, ctClassDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "CtClassDTO{" +
            "id=" + getId() +
            ", dt='" + getDt() + "'" +
            ", empn='" + getEmpn() + "'" +
            ", flag=" + getFlag() +
            ", jbempn='" + getJbempn() + "'" +
            ", gotime='" + getGotime() + "'" +
            ", xj=" + getXj() +
            ", zp=" + getZp() +
            ", sk=" + getSk() +
            ", hyk=" + getHyk() +
            ", cq=" + getCq() +
            ", gz=" + getGz() +
            ", gfz=" + getGfz() +
            ", yq=" + getYq() +
            ", yh=" + getYh() +
            ", zzh=" + getZzh() +
            ", checkSign='" + getCheckSign() + "'" +
            "}";
    }
}
