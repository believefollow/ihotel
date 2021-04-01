package ihotel.app.domain;

import java.io.Serializable;
import javax.persistence.*;
import javax.validation.constraints.*;
import org.springframework.data.elasticsearch.annotations.FieldType;

/**
 * A DxSedinfo.
 */
@Entity
@Table(name = "dx_sedinfo")
@org.springframework.data.elasticsearch.annotations.Document(indexName = "dxsedinfo")
public class DxSedinfo implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Size(max = 2)
    @Column(name = "yddx", length = 2)
    private String yddx;

    @Size(max = 50)
    @Column(name = "yddxmemo", length = 50)
    private String yddxmemo;

    @Size(max = 2)
    @Column(name = "qxyddx", length = 2)
    private String qxyddx;

    @Size(max = 50)
    @Column(name = "qxydmemo", length = 50)
    private String qxydmemo;

    @Size(max = 2)
    @Column(name = "czdx", length = 2)
    private String czdx;

    @Size(max = 300)
    @Column(name = "czmemo", length = 300)
    private String czmemo;

    @Size(max = 10)
    @Column(name = "qxczdx", length = 10)
    private String qxczdx;

    @Size(max = 300)
    @Column(name = "qxczmemo", length = 300)
    private String qxczmemo;

    @Size(max = 2)
    @Column(name = "yyedx", length = 2)
    private String yyedx;

    @Size(max = 300)
    @Column(name = "yyememo", length = 300)
    private String yyememo;

    @Size(max = 20)
    @Column(name = "fstime", length = 20)
    private String fstime;

    @Size(max = 2)
    @Column(name = "sffshm", length = 2)
    private String sffshm;

    @Size(max = 50)
    @Column(name = "rzdx", length = 50)
    private String rzdx;

    @Size(max = 50)
    @Column(name = "rzdxroomn", length = 50)
    private String rzdxroomn;

    @Size(max = 50)
    @Column(name = "jfdz", length = 50)
    private String jfdz;

    @Size(max = 2)
    @Column(name = "blhy", length = 2)
    private String blhy;

    @Size(max = 300)
    @Column(name = "rzmemo", length = 300)
    private String rzmemo;

    @Size(max = 300)
    @Column(name = "blhymemo", length = 300)
    private String blhymemo;

    @Size(max = 2)
    @Column(name = "tfdx", length = 2)
    private String tfdx;

    @Size(max = 300)
    @Column(name = "tfdxmemo", length = 300)
    private String tfdxmemo;

    @Size(max = 2)
    @Column(name = "fslb", length = 2)
    private String fslb;

    @Size(max = 300)
    @Column(name = "fslbmemo", length = 300)
    private String fslbmemo;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public DxSedinfo id(Long id) {
        this.id = id;
        return this;
    }

    public String getYddx() {
        return this.yddx;
    }

    public DxSedinfo yddx(String yddx) {
        this.yddx = yddx;
        return this;
    }

    public void setYddx(String yddx) {
        this.yddx = yddx;
    }

    public String getYddxmemo() {
        return this.yddxmemo;
    }

    public DxSedinfo yddxmemo(String yddxmemo) {
        this.yddxmemo = yddxmemo;
        return this;
    }

    public void setYddxmemo(String yddxmemo) {
        this.yddxmemo = yddxmemo;
    }

    public String getQxyddx() {
        return this.qxyddx;
    }

    public DxSedinfo qxyddx(String qxyddx) {
        this.qxyddx = qxyddx;
        return this;
    }

    public void setQxyddx(String qxyddx) {
        this.qxyddx = qxyddx;
    }

    public String getQxydmemo() {
        return this.qxydmemo;
    }

    public DxSedinfo qxydmemo(String qxydmemo) {
        this.qxydmemo = qxydmemo;
        return this;
    }

    public void setQxydmemo(String qxydmemo) {
        this.qxydmemo = qxydmemo;
    }

    public String getCzdx() {
        return this.czdx;
    }

    public DxSedinfo czdx(String czdx) {
        this.czdx = czdx;
        return this;
    }

    public void setCzdx(String czdx) {
        this.czdx = czdx;
    }

    public String getCzmemo() {
        return this.czmemo;
    }

    public DxSedinfo czmemo(String czmemo) {
        this.czmemo = czmemo;
        return this;
    }

    public void setCzmemo(String czmemo) {
        this.czmemo = czmemo;
    }

    public String getQxczdx() {
        return this.qxczdx;
    }

    public DxSedinfo qxczdx(String qxczdx) {
        this.qxczdx = qxczdx;
        return this;
    }

    public void setQxczdx(String qxczdx) {
        this.qxczdx = qxczdx;
    }

    public String getQxczmemo() {
        return this.qxczmemo;
    }

    public DxSedinfo qxczmemo(String qxczmemo) {
        this.qxczmemo = qxczmemo;
        return this;
    }

    public void setQxczmemo(String qxczmemo) {
        this.qxczmemo = qxczmemo;
    }

    public String getYyedx() {
        return this.yyedx;
    }

    public DxSedinfo yyedx(String yyedx) {
        this.yyedx = yyedx;
        return this;
    }

    public void setYyedx(String yyedx) {
        this.yyedx = yyedx;
    }

    public String getYyememo() {
        return this.yyememo;
    }

    public DxSedinfo yyememo(String yyememo) {
        this.yyememo = yyememo;
        return this;
    }

    public void setYyememo(String yyememo) {
        this.yyememo = yyememo;
    }

    public String getFstime() {
        return this.fstime;
    }

    public DxSedinfo fstime(String fstime) {
        this.fstime = fstime;
        return this;
    }

    public void setFstime(String fstime) {
        this.fstime = fstime;
    }

    public String getSffshm() {
        return this.sffshm;
    }

    public DxSedinfo sffshm(String sffshm) {
        this.sffshm = sffshm;
        return this;
    }

    public void setSffshm(String sffshm) {
        this.sffshm = sffshm;
    }

    public String getRzdx() {
        return this.rzdx;
    }

    public DxSedinfo rzdx(String rzdx) {
        this.rzdx = rzdx;
        return this;
    }

    public void setRzdx(String rzdx) {
        this.rzdx = rzdx;
    }

    public String getRzdxroomn() {
        return this.rzdxroomn;
    }

    public DxSedinfo rzdxroomn(String rzdxroomn) {
        this.rzdxroomn = rzdxroomn;
        return this;
    }

    public void setRzdxroomn(String rzdxroomn) {
        this.rzdxroomn = rzdxroomn;
    }

    public String getJfdz() {
        return this.jfdz;
    }

    public DxSedinfo jfdz(String jfdz) {
        this.jfdz = jfdz;
        return this;
    }

    public void setJfdz(String jfdz) {
        this.jfdz = jfdz;
    }

    public String getBlhy() {
        return this.blhy;
    }

    public DxSedinfo blhy(String blhy) {
        this.blhy = blhy;
        return this;
    }

    public void setBlhy(String blhy) {
        this.blhy = blhy;
    }

    public String getRzmemo() {
        return this.rzmemo;
    }

    public DxSedinfo rzmemo(String rzmemo) {
        this.rzmemo = rzmemo;
        return this;
    }

    public void setRzmemo(String rzmemo) {
        this.rzmemo = rzmemo;
    }

    public String getBlhymemo() {
        return this.blhymemo;
    }

    public DxSedinfo blhymemo(String blhymemo) {
        this.blhymemo = blhymemo;
        return this;
    }

    public void setBlhymemo(String blhymemo) {
        this.blhymemo = blhymemo;
    }

    public String getTfdx() {
        return this.tfdx;
    }

    public DxSedinfo tfdx(String tfdx) {
        this.tfdx = tfdx;
        return this;
    }

    public void setTfdx(String tfdx) {
        this.tfdx = tfdx;
    }

    public String getTfdxmemo() {
        return this.tfdxmemo;
    }

    public DxSedinfo tfdxmemo(String tfdxmemo) {
        this.tfdxmemo = tfdxmemo;
        return this;
    }

    public void setTfdxmemo(String tfdxmemo) {
        this.tfdxmemo = tfdxmemo;
    }

    public String getFslb() {
        return this.fslb;
    }

    public DxSedinfo fslb(String fslb) {
        this.fslb = fslb;
        return this;
    }

    public void setFslb(String fslb) {
        this.fslb = fslb;
    }

    public String getFslbmemo() {
        return this.fslbmemo;
    }

    public DxSedinfo fslbmemo(String fslbmemo) {
        this.fslbmemo = fslbmemo;
        return this;
    }

    public void setFslbmemo(String fslbmemo) {
        this.fslbmemo = fslbmemo;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof DxSedinfo)) {
            return false;
        }
        return id != null && id.equals(((DxSedinfo) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "DxSedinfo{" +
            "id=" + getId() +
            ", yddx='" + getYddx() + "'" +
            ", yddxmemo='" + getYddxmemo() + "'" +
            ", qxyddx='" + getQxyddx() + "'" +
            ", qxydmemo='" + getQxydmemo() + "'" +
            ", czdx='" + getCzdx() + "'" +
            ", czmemo='" + getCzmemo() + "'" +
            ", qxczdx='" + getQxczdx() + "'" +
            ", qxczmemo='" + getQxczmemo() + "'" +
            ", yyedx='" + getYyedx() + "'" +
            ", yyememo='" + getYyememo() + "'" +
            ", fstime='" + getFstime() + "'" +
            ", sffshm='" + getSffshm() + "'" +
            ", rzdx='" + getRzdx() + "'" +
            ", rzdxroomn='" + getRzdxroomn() + "'" +
            ", jfdz='" + getJfdz() + "'" +
            ", blhy='" + getBlhy() + "'" +
            ", rzmemo='" + getRzmemo() + "'" +
            ", blhymemo='" + getBlhymemo() + "'" +
            ", tfdx='" + getTfdx() + "'" +
            ", tfdxmemo='" + getTfdxmemo() + "'" +
            ", fslb='" + getFslb() + "'" +
            ", fslbmemo='" + getFslbmemo() + "'" +
            "}";
    }
}
