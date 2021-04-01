package ihotel.app.domain;

import java.io.Serializable;
import java.math.BigDecimal;
import javax.persistence.*;
import javax.validation.constraints.*;
import org.springframework.data.elasticsearch.annotations.FieldType;

/**
 * A DGoods.
 */
@Entity
@Table(name = "d_goods")
@org.springframework.data.elasticsearch.annotations.Document(indexName = "dgoods")
public class DGoods implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "typeid", nullable = false)
    private Long typeid;

    @NotNull
    @Size(max = 100)
    @Column(name = "goodsname", length = 100, nullable = false)
    private String goodsname;

    @NotNull
    @Size(max = 20)
    @Column(name = "goodsid", length = 20, nullable = false)
    private String goodsid;

    @Size(max = 50)
    @Column(name = "ggxh", length = 50)
    private String ggxh;

    @Size(max = 50)
    @Column(name = "pysj", length = 50)
    private String pysj;

    @Size(max = 50)
    @Column(name = "wbsj", length = 50)
    private String wbsj;

    @Size(max = 50)
    @Column(name = "unit", length = 50)
    private String unit;

    @Column(name = "gcsl", precision = 21, scale = 2)
    private BigDecimal gcsl;

    @Column(name = "dcsl", precision = 21, scale = 2)
    private BigDecimal dcsl;

    @Size(max = 50)
    @Column(name = "remark", length = 50)
    private String remark;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public DGoods id(Long id) {
        this.id = id;
        return this;
    }

    public Long getTypeid() {
        return this.typeid;
    }

    public DGoods typeid(Long typeid) {
        this.typeid = typeid;
        return this;
    }

    public void setTypeid(Long typeid) {
        this.typeid = typeid;
    }

    public String getGoodsname() {
        return this.goodsname;
    }

    public DGoods goodsname(String goodsname) {
        this.goodsname = goodsname;
        return this;
    }

    public void setGoodsname(String goodsname) {
        this.goodsname = goodsname;
    }

    public String getGoodsid() {
        return this.goodsid;
    }

    public DGoods goodsid(String goodsid) {
        this.goodsid = goodsid;
        return this;
    }

    public void setGoodsid(String goodsid) {
        this.goodsid = goodsid;
    }

    public String getGgxh() {
        return this.ggxh;
    }

    public DGoods ggxh(String ggxh) {
        this.ggxh = ggxh;
        return this;
    }

    public void setGgxh(String ggxh) {
        this.ggxh = ggxh;
    }

    public String getPysj() {
        return this.pysj;
    }

    public DGoods pysj(String pysj) {
        this.pysj = pysj;
        return this;
    }

    public void setPysj(String pysj) {
        this.pysj = pysj;
    }

    public String getWbsj() {
        return this.wbsj;
    }

    public DGoods wbsj(String wbsj) {
        this.wbsj = wbsj;
        return this;
    }

    public void setWbsj(String wbsj) {
        this.wbsj = wbsj;
    }

    public String getUnit() {
        return this.unit;
    }

    public DGoods unit(String unit) {
        this.unit = unit;
        return this;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public BigDecimal getGcsl() {
        return this.gcsl;
    }

    public DGoods gcsl(BigDecimal gcsl) {
        this.gcsl = gcsl;
        return this;
    }

    public void setGcsl(BigDecimal gcsl) {
        this.gcsl = gcsl;
    }

    public BigDecimal getDcsl() {
        return this.dcsl;
    }

    public DGoods dcsl(BigDecimal dcsl) {
        this.dcsl = dcsl;
        return this;
    }

    public void setDcsl(BigDecimal dcsl) {
        this.dcsl = dcsl;
    }

    public String getRemark() {
        return this.remark;
    }

    public DGoods remark(String remark) {
        this.remark = remark;
        return this;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof DGoods)) {
            return false;
        }
        return id != null && id.equals(((DGoods) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "DGoods{" +
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
