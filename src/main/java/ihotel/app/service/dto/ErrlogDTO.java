package ihotel.app.service.dto;

import java.io.Serializable;
import java.time.Instant;
import java.util.Objects;
import javax.persistence.Lob;
import javax.validation.constraints.*;

/**
 * A DTO for the {@link ihotel.app.domain.Errlog} entity.
 */
public class ErrlogDTO implements Serializable {

    private Long id;

    @NotNull
    private Long iderrlog;

    private Long errnumber;

    @Size(max = 255)
    private String errtext;

    @Size(max = 255)
    private String errwindowmenu;

    @Size(max = 255)
    private String errobject;

    @Size(max = 255)
    private String errevent;

    private Long errline;

    private Instant errtime;

    private Boolean sumbitsign;

    @Size(max = 255)
    private String bmpfile;

    @Lob
    private byte[] bmpblob;

    private String bmpblobContentType;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getIderrlog() {
        return iderrlog;
    }

    public void setIderrlog(Long iderrlog) {
        this.iderrlog = iderrlog;
    }

    public Long getErrnumber() {
        return errnumber;
    }

    public void setErrnumber(Long errnumber) {
        this.errnumber = errnumber;
    }

    public String getErrtext() {
        return errtext;
    }

    public void setErrtext(String errtext) {
        this.errtext = errtext;
    }

    public String getErrwindowmenu() {
        return errwindowmenu;
    }

    public void setErrwindowmenu(String errwindowmenu) {
        this.errwindowmenu = errwindowmenu;
    }

    public String getErrobject() {
        return errobject;
    }

    public void setErrobject(String errobject) {
        this.errobject = errobject;
    }

    public String getErrevent() {
        return errevent;
    }

    public void setErrevent(String errevent) {
        this.errevent = errevent;
    }

    public Long getErrline() {
        return errline;
    }

    public void setErrline(Long errline) {
        this.errline = errline;
    }

    public Instant getErrtime() {
        return errtime;
    }

    public void setErrtime(Instant errtime) {
        this.errtime = errtime;
    }

    public Boolean getSumbitsign() {
        return sumbitsign;
    }

    public void setSumbitsign(Boolean sumbitsign) {
        this.sumbitsign = sumbitsign;
    }

    public String getBmpfile() {
        return bmpfile;
    }

    public void setBmpfile(String bmpfile) {
        this.bmpfile = bmpfile;
    }

    public byte[] getBmpblob() {
        return bmpblob;
    }

    public void setBmpblob(byte[] bmpblob) {
        this.bmpblob = bmpblob;
    }

    public String getBmpblobContentType() {
        return bmpblobContentType;
    }

    public void setBmpblobContentType(String bmpblobContentType) {
        this.bmpblobContentType = bmpblobContentType;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ErrlogDTO)) {
            return false;
        }

        ErrlogDTO errlogDTO = (ErrlogDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, errlogDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ErrlogDTO{" +
            "id=" + getId() +
            ", iderrlog=" + getIderrlog() +
            ", errnumber=" + getErrnumber() +
            ", errtext='" + getErrtext() + "'" +
            ", errwindowmenu='" + getErrwindowmenu() + "'" +
            ", errobject='" + getErrobject() + "'" +
            ", errevent='" + getErrevent() + "'" +
            ", errline=" + getErrline() +
            ", errtime='" + getErrtime() + "'" +
            ", sumbitsign='" + getSumbitsign() + "'" +
            ", bmpfile='" + getBmpfile() + "'" +
            ", bmpblob='" + getBmpblob() + "'" +
            "}";
    }
}
