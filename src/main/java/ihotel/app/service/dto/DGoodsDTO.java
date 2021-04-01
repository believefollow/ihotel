package ihotel.app.service.dto;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Objects;
import javax.validation.constraints.*;

/**
 * A DTO for the {@link ihotel.app.domain.DGoods} entity.
 */
public class DGoodsDTO implements Serializable {

    private Long id;

    @NotNull
    private Long typeid;

    @NotNull
    @Size(max = 100)
    private String goodsname;

    @NotNull
    @Size(max = 20)
    private String goodsid;

    @Size(max = 50)
    private String ggxh;

    @Size(max = 50)
    private String pysj;

    @Size(max = 50)
    private String wbsj;

    @Size(max = 50)
    private String unit;

    private BigDecimal gcsl;

    private BigDecimal dcsl;

    @Size(max = 50)
    private String remark;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getTypeid() {
        return typeid;
    }

    public void setTypeid(Long typeid) {
        this.typeid = typeid;
    }

    public String getGoodsname() {
        return goodsname;
    }

    public void setGoodsname(String goodsname) {
        this.goodsname = goodsname;
    }

    public String getGoodsid() {
        return goodsid;
    }

    public void setGoodsid(String goodsid) {
        this.goodsid = goodsid;
    }

    public String getGgxh() {
        return ggxh;
    }

    public void setGgxh(String ggxh) {
        this.ggxh = ggxh;
    }

    public String getPysj() {
        return pysj;
    }

    public void setPysj(String pysj) {
        this.pysj = pysj;
    }

    public String getWbsj() {
        return wbsj;
    }

    public void setWbsj(String wbsj) {
        this.wbsj = wbsj;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public BigDecimal getGcsl() {
        return gcsl;
    }

    public void setGcsl(BigDecimal gcsl) {
        this.gcsl = gcsl;
    }

    public BigDecimal getDcsl() {
        return dcsl;
    }

    public void setDcsl(BigDecimal dcsl) {
        this.dcsl = dcsl;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof DGoodsDTO)) {
            return false;
        }

        DGoodsDTO dGoodsDTO = (DGoodsDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, dGoodsDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "DGoodsDTO{" +
            "id=" + getId() +
            ", typeid=" + getTypeid() +
            ", goodsname='" + getGoodsname() + "'" +
            ", goodsid='" + getGoodsid() + "'" +
            ", ggxh='" + getGgxh() + "'" +
            ", pysj='" + getPysj() + "'" +
            ", wbsj='" + getWbsj() + "'" +
            ", unit='" + getUnit() + "'" +
            ", gcsl=" + getGcsl() +
            ", dcsl=" + getDcsl() +
            ", remark='" + getRemark() + "'" +
            "}";
    }
}
