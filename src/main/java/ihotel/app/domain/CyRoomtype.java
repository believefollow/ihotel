package ihotel.app.domain;

import java.io.Serializable;
import java.math.BigDecimal;
import javax.persistence.*;
import javax.validation.constraints.*;
import org.springframework.data.elasticsearch.annotations.FieldType;

/**
 * A CyRoomtype.
 */
@Entity
@Table(name = "cy_roomtype")
@org.springframework.data.elasticsearch.annotations.Document(indexName = "cyroomtype")
public class CyRoomtype implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Size(max = 10)
    @Column(name = "rtdm", length = 10, nullable = false)
    private String rtdm;

    @Column(name = "minc", precision = 21, scale = 2)
    private BigDecimal minc;

    @Column(name = "servicerate", precision = 21, scale = 2)
    private BigDecimal servicerate;

    @Size(max = 100)
    @Column(name = "printer", length = 100)
    private String printer;

    @Column(name = "printnum")
    private Long printnum;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public CyRoomtype id(Long id) {
        this.id = id;
        return this;
    }

    public String getRtdm() {
        return this.rtdm;
    }

    public CyRoomtype rtdm(String rtdm) {
        this.rtdm = rtdm;
        return this;
    }

    public void setRtdm(String rtdm) {
        this.rtdm = rtdm;
    }

    public BigDecimal getMinc() {
        return this.minc;
    }

    public CyRoomtype minc(BigDecimal minc) {
        this.minc = minc;
        return this;
    }

    public void setMinc(BigDecimal minc) {
        this.minc = minc;
    }

    public BigDecimal getServicerate() {
        return this.servicerate;
    }

    public CyRoomtype servicerate(BigDecimal servicerate) {
        this.servicerate = servicerate;
        return this;
    }

    public void setServicerate(BigDecimal servicerate) {
        this.servicerate = servicerate;
    }

    public String getPrinter() {
        return this.printer;
    }

    public CyRoomtype printer(String printer) {
        this.printer = printer;
        return this;
    }

    public void setPrinter(String printer) {
        this.printer = printer;
    }

    public Long getPrintnum() {
        return this.printnum;
    }

    public CyRoomtype printnum(Long printnum) {
        this.printnum = printnum;
        return this;
    }

    public void setPrintnum(Long printnum) {
        this.printnum = printnum;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof CyRoomtype)) {
            return false;
        }
        return id != null && id.equals(((CyRoomtype) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "CyRoomtype{" +
            "id=" + getId() +
            ", rtdm='" + getRtdm() + "'" +
            ", minc=" + getMinc() +
            ", servicerate=" + getServicerate() +
            ", printer='" + getPrinter() + "'" +
            ", printnum=" + getPrintnum() +
            "}";
    }
}
