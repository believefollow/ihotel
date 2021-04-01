package ihotel.app.domain;

import java.io.Serializable;
import javax.persistence.*;
import javax.validation.constraints.*;
import org.springframework.data.elasticsearch.annotations.FieldType;

/**
 * A CyCptype.
 */
@Entity
@Table(name = "cy_cptype")
@org.springframework.data.elasticsearch.annotations.Document(indexName = "cycptype")
public class CyCptype implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Size(max = 4)
    @Column(name = "cptdm", length = 4, nullable = false)
    private String cptdm;

    @NotNull
    @Size(max = 20)
    @Column(name = "cptname", length = 20, nullable = false)
    private String cptname;

    @NotNull
    @Column(name = "printsign", nullable = false)
    private Boolean printsign;

    @Size(max = 100)
    @Column(name = "printer", length = 100)
    private String printer;

    @Column(name = "printnum")
    private Long printnum;

    @Column(name = "printcut")
    private Long printcut;

    @Column(name = "syssign")
    private Boolean syssign;

    @Size(max = 100)
    @Column(name = "typesign", length = 100)
    private String typesign;

    @Size(max = 20)
    @Column(name = "qy", length = 20)
    private String qy;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public CyCptype id(Long id) {
        this.id = id;
        return this;
    }

    public String getCptdm() {
        return this.cptdm;
    }

    public CyCptype cptdm(String cptdm) {
        this.cptdm = cptdm;
        return this;
    }

    public void setCptdm(String cptdm) {
        this.cptdm = cptdm;
    }

    public String getCptname() {
        return this.cptname;
    }

    public CyCptype cptname(String cptname) {
        this.cptname = cptname;
        return this;
    }

    public void setCptname(String cptname) {
        this.cptname = cptname;
    }

    public Boolean getPrintsign() {
        return this.printsign;
    }

    public CyCptype printsign(Boolean printsign) {
        this.printsign = printsign;
        return this;
    }

    public void setPrintsign(Boolean printsign) {
        this.printsign = printsign;
    }

    public String getPrinter() {
        return this.printer;
    }

    public CyCptype printer(String printer) {
        this.printer = printer;
        return this;
    }

    public void setPrinter(String printer) {
        this.printer = printer;
    }

    public Long getPrintnum() {
        return this.printnum;
    }

    public CyCptype printnum(Long printnum) {
        this.printnum = printnum;
        return this;
    }

    public void setPrintnum(Long printnum) {
        this.printnum = printnum;
    }

    public Long getPrintcut() {
        return this.printcut;
    }

    public CyCptype printcut(Long printcut) {
        this.printcut = printcut;
        return this;
    }

    public void setPrintcut(Long printcut) {
        this.printcut = printcut;
    }

    public Boolean getSyssign() {
        return this.syssign;
    }

    public CyCptype syssign(Boolean syssign) {
        this.syssign = syssign;
        return this;
    }

    public void setSyssign(Boolean syssign) {
        this.syssign = syssign;
    }

    public String getTypesign() {
        return this.typesign;
    }

    public CyCptype typesign(String typesign) {
        this.typesign = typesign;
        return this;
    }

    public void setTypesign(String typesign) {
        this.typesign = typesign;
    }

    public String getQy() {
        return this.qy;
    }

    public CyCptype qy(String qy) {
        this.qy = qy;
        return this;
    }

    public void setQy(String qy) {
        this.qy = qy;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof CyCptype)) {
            return false;
        }
        return id != null && id.equals(((CyCptype) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "CyCptype{" +
            "id=" + getId() +
            ", cptdm='" + getCptdm() + "'" +
            ", cptname='" + getCptname() + "'" +
            ", printsign='" + getPrintsign() + "'" +
            ", printer='" + getPrinter() + "'" +
            ", printnum=" + getPrintnum() +
            ", printcut=" + getPrintcut() +
            ", syssign='" + getSyssign() + "'" +
            ", typesign='" + getTypesign() + "'" +
            ", qy='" + getQy() + "'" +
            "}";
    }
}
