package ihotel.app.domain;

import java.io.Serializable;
import java.time.Instant;
import javax.persistence.*;
import javax.validation.constraints.*;
import org.springframework.data.elasticsearch.annotations.FieldType;

/**
 * A Errlog.
 */
@Entity
@Table(name = "errlog")
@org.springframework.data.elasticsearch.annotations.Document(indexName = "errlog")
public class Errlog implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "iderrlog", nullable = false)
    private Long iderrlog;

    @Column(name = "errnumber")
    private Long errnumber;

    @Size(max = 255)
    @Column(name = "errtext", length = 255)
    private String errtext;

    @Size(max = 255)
    @Column(name = "errwindowmenu", length = 255)
    private String errwindowmenu;

    @Size(max = 255)
    @Column(name = "errobject", length = 255)
    private String errobject;

    @Size(max = 255)
    @Column(name = "errevent", length = 255)
    private String errevent;

    @Column(name = "errline")
    private Long errline;

    @Column(name = "errtime")
    private Instant errtime;

    @Column(name = "sumbitsign")
    private Boolean sumbitsign;

    @Size(max = 255)
    @Column(name = "bmpfile", length = 255)
    private String bmpfile;

    @Lob
    @Column(name = "bmpblob")
    private byte[] bmpblob;

    @Column(name = "bmpblob_content_type")
    private String bmpblobContentType;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Errlog id(Long id) {
        this.id = id;
        return this;
    }

    public Long getIderrlog() {
        return this.iderrlog;
    }

    public Errlog iderrlog(Long iderrlog) {
        this.iderrlog = iderrlog;
        return this;
    }

    public void setIderrlog(Long iderrlog) {
        this.iderrlog = iderrlog;
    }

    public Long getErrnumber() {
        return this.errnumber;
    }

    public Errlog errnumber(Long errnumber) {
        this.errnumber = errnumber;
        return this;
    }

    public void setErrnumber(Long errnumber) {
        this.errnumber = errnumber;
    }

    public String getErrtext() {
        return this.errtext;
    }

    public Errlog errtext(String errtext) {
        this.errtext = errtext;
        return this;
    }

    public void setErrtext(String errtext) {
        this.errtext = errtext;
    }

    public String getErrwindowmenu() {
        return this.errwindowmenu;
    }

    public Errlog errwindowmenu(String errwindowmenu) {
        this.errwindowmenu = errwindowmenu;
        return this;
    }

    public void setErrwindowmenu(String errwindowmenu) {
        this.errwindowmenu = errwindowmenu;
    }

    public String getErrobject() {
        return this.errobject;
    }

    public Errlog errobject(String errobject) {
        this.errobject = errobject;
        return this;
    }

    public void setErrobject(String errobject) {
        this.errobject = errobject;
    }

    public String getErrevent() {
        return this.errevent;
    }

    public Errlog errevent(String errevent) {
        this.errevent = errevent;
        return this;
    }

    public void setErrevent(String errevent) {
        this.errevent = errevent;
    }

    public Long getErrline() {
        return this.errline;
    }

    public Errlog errline(Long errline) {
        this.errline = errline;
        return this;
    }

    public void setErrline(Long errline) {
        this.errline = errline;
    }

    public Instant getErrtime() {
        return this.errtime;
    }

    public Errlog errtime(Instant errtime) {
        this.errtime = errtime;
        return this;
    }

    public void setErrtime(Instant errtime) {
        this.errtime = errtime;
    }

    public Boolean getSumbitsign() {
        return this.sumbitsign;
    }

    public Errlog sumbitsign(Boolean sumbitsign) {
        this.sumbitsign = sumbitsign;
        return this;
    }

    public void setSumbitsign(Boolean sumbitsign) {
        this.sumbitsign = sumbitsign;
    }

    public String getBmpfile() {
        return this.bmpfile;
    }

    public Errlog bmpfile(String bmpfile) {
        this.bmpfile = bmpfile;
        return this;
    }

    public void setBmpfile(String bmpfile) {
        this.bmpfile = bmpfile;
    }

    public byte[] getBmpblob() {
        return this.bmpblob;
    }

    public Errlog bmpblob(byte[] bmpblob) {
        this.bmpblob = bmpblob;
        return this;
    }

    public void setBmpblob(byte[] bmpblob) {
        this.bmpblob = bmpblob;
    }

    public String getBmpblobContentType() {
        return this.bmpblobContentType;
    }

    public Errlog bmpblobContentType(String bmpblobContentType) {
        this.bmpblobContentType = bmpblobContentType;
        return this;
    }

    public void setBmpblobContentType(String bmpblobContentType) {
        this.bmpblobContentType = bmpblobContentType;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Errlog)) {
            return false;
        }
        return id != null && id.equals(((Errlog) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Errlog{" +
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
            ", bmpblobContentType='" + getBmpblobContentType() + "'" +
            "}";
    }
}
