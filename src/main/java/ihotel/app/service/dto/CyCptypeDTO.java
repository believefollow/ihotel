package ihotel.app.service.dto;

import java.io.Serializable;
import java.util.Objects;
import javax.validation.constraints.*;

/**
 * A DTO for the {@link ihotel.app.domain.CyCptype} entity.
 */
public class CyCptypeDTO implements Serializable {

    private Long id;

    @NotNull
    @Size(max = 4)
    private String cptdm;

    @NotNull
    @Size(max = 20)
    private String cptname;

    @NotNull
    private Boolean printsign;

    @Size(max = 100)
    private String printer;

    private Long printnum;

    private Long printcut;

    private Boolean syssign;

    @Size(max = 100)
    private String typesign;

    @Size(max = 20)
    private String qy;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCptdm() {
        return cptdm;
    }

    public void setCptdm(String cptdm) {
        this.cptdm = cptdm;
    }

    public String getCptname() {
        return cptname;
    }

    public void setCptname(String cptname) {
        this.cptname = cptname;
    }

    public Boolean getPrintsign() {
        return printsign;
    }

    public void setPrintsign(Boolean printsign) {
        this.printsign = printsign;
    }

    public String getPrinter() {
        return printer;
    }

    public void setPrinter(String printer) {
        this.printer = printer;
    }

    public Long getPrintnum() {
        return printnum;
    }

    public void setPrintnum(Long printnum) {
        this.printnum = printnum;
    }

    public Long getPrintcut() {
        return printcut;
    }

    public void setPrintcut(Long printcut) {
        this.printcut = printcut;
    }

    public Boolean getSyssign() {
        return syssign;
    }

    public void setSyssign(Boolean syssign) {
        this.syssign = syssign;
    }

    public String getTypesign() {
        return typesign;
    }

    public void setTypesign(String typesign) {
        this.typesign = typesign;
    }

    public String getQy() {
        return qy;
    }

    public void setQy(String qy) {
        this.qy = qy;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof CyCptypeDTO)) {
            return false;
        }

        CyCptypeDTO cyCptypeDTO = (CyCptypeDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, cyCptypeDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "CyCptypeDTO{" +
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
