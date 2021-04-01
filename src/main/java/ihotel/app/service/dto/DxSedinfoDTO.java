package ihotel.app.service.dto;

import java.io.Serializable;
import java.util.Objects;
import javax.validation.constraints.*;

/**
 * A DTO for the {@link ihotel.app.domain.DxSedinfo} entity.
 */
public class DxSedinfoDTO implements Serializable {

    @NotNull
    private Long id;

    @Size(max = 2)
    private String yddx;

    @Size(max = 50)
    private String yddxmemo;

    @Size(max = 2)
    private String qxyddx;

    @Size(max = 50)
    private String qxydmemo;

    @Size(max = 2)
    private String czdx;

    @Size(max = 300)
    private String czmemo;

    @Size(max = 10)
    private String qxczdx;

    @Size(max = 300)
    private String qxczmemo;

    @Size(max = 2)
    private String yyedx;

    @Size(max = 300)
    private String yyememo;

    @Size(max = 20)
    private String fstime;

    @Size(max = 2)
    private String sffshm;

    @Size(max = 50)
    private String rzdx;

    @Size(max = 50)
    private String rzdxroomn;

    @Size(max = 50)
    private String jfdz;

    @Size(max = 2)
    private String blhy;

    @Size(max = 300)
    private String rzmemo;

    @Size(max = 300)
    private String blhymemo;

    @Size(max = 2)
    private String tfdx;

    @Size(max = 300)
    private String tfdxmemo;

    @Size(max = 2)
    private String fslb;

    @Size(max = 300)
    private String fslbmemo;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getYddx() {
        return yddx;
    }

    public void setYddx(String yddx) {
        this.yddx = yddx;
    }

    public String getYddxmemo() {
        return yddxmemo;
    }

    public void setYddxmemo(String yddxmemo) {
        this.yddxmemo = yddxmemo;
    }

    public String getQxyddx() {
        return qxyddx;
    }

    public void setQxyddx(String qxyddx) {
        this.qxyddx = qxyddx;
    }

    public String getQxydmemo() {
        return qxydmemo;
    }

    public void setQxydmemo(String qxydmemo) {
        this.qxydmemo = qxydmemo;
    }

    public String getCzdx() {
        return czdx;
    }

    public void setCzdx(String czdx) {
        this.czdx = czdx;
    }

    public String getCzmemo() {
        return czmemo;
    }

    public void setCzmemo(String czmemo) {
        this.czmemo = czmemo;
    }

    public String getQxczdx() {
        return qxczdx;
    }

    public void setQxczdx(String qxczdx) {
        this.qxczdx = qxczdx;
    }

    public String getQxczmemo() {
        return qxczmemo;
    }

    public void setQxczmemo(String qxczmemo) {
        this.qxczmemo = qxczmemo;
    }

    public String getYyedx() {
        return yyedx;
    }

    public void setYyedx(String yyedx) {
        this.yyedx = yyedx;
    }

    public String getYyememo() {
        return yyememo;
    }

    public void setYyememo(String yyememo) {
        this.yyememo = yyememo;
    }

    public String getFstime() {
        return fstime;
    }

    public void setFstime(String fstime) {
        this.fstime = fstime;
    }

    public String getSffshm() {
        return sffshm;
    }

    public void setSffshm(String sffshm) {
        this.sffshm = sffshm;
    }

    public String getRzdx() {
        return rzdx;
    }

    public void setRzdx(String rzdx) {
        this.rzdx = rzdx;
    }

    public String getRzdxroomn() {
        return rzdxroomn;
    }

    public void setRzdxroomn(String rzdxroomn) {
        this.rzdxroomn = rzdxroomn;
    }

    public String getJfdz() {
        return jfdz;
    }

    public void setJfdz(String jfdz) {
        this.jfdz = jfdz;
    }

    public String getBlhy() {
        return blhy;
    }

    public void setBlhy(String blhy) {
        this.blhy = blhy;
    }

    public String getRzmemo() {
        return rzmemo;
    }

    public void setRzmemo(String rzmemo) {
        this.rzmemo = rzmemo;
    }

    public String getBlhymemo() {
        return blhymemo;
    }

    public void setBlhymemo(String blhymemo) {
        this.blhymemo = blhymemo;
    }

    public String getTfdx() {
        return tfdx;
    }

    public void setTfdx(String tfdx) {
        this.tfdx = tfdx;
    }

    public String getTfdxmemo() {
        return tfdxmemo;
    }

    public void setTfdxmemo(String tfdxmemo) {
        this.tfdxmemo = tfdxmemo;
    }

    public String getFslb() {
        return fslb;
    }

    public void setFslb(String fslb) {
        this.fslb = fslb;
    }

    public String getFslbmemo() {
        return fslbmemo;
    }

    public void setFslbmemo(String fslbmemo) {
        this.fslbmemo = fslbmemo;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof DxSedinfoDTO)) {
            return false;
        }

        DxSedinfoDTO dxSedinfoDTO = (DxSedinfoDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, dxSedinfoDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "DxSedinfoDTO{" +
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
