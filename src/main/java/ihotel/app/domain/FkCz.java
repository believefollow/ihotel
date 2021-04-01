package ihotel.app.domain;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.Instant;
import javax.persistence.*;
import javax.validation.constraints.*;
import org.springframework.data.elasticsearch.annotations.FieldType;

/**
 * A FkCz.
 */
@Entity
@Table(name = "fk_cz")
@org.springframework.data.elasticsearch.annotations.Document(indexName = "fkcz")
public class FkCz implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "hoteltime", nullable = false)
    private Instant hoteltime;

    @Column(name = "wxf")
    private Long wxf;

    @Column(name = "ksf")
    private Long ksf;

    @Column(name = "kf")
    private Long kf;

    @Column(name = "zfs")
    private Long zfs;

    @Column(name = "groupyd")
    private Long groupyd;

    @Column(name = "skyd")
    private Long skyd;

    @Column(name = "ydwd")
    private Long ydwd;

    @Column(name = "qxyd")
    private Long qxyd;

    @Column(name = "isnew")
    private Long isnew;

    @Size(max = 50)
    @Column(name = "hoteldm", length = 50)
    private String hoteldm;

    @Column(name = "hys", precision = 21, scale = 2)
    private BigDecimal hys;

    @Column(name = "khys", precision = 21, scale = 2)
    private BigDecimal khys;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public FkCz id(Long id) {
        this.id = id;
        return this;
    }

    public Instant getHoteltime() {
        return this.hoteltime;
    }

    public FkCz hoteltime(Instant hoteltime) {
        this.hoteltime = hoteltime;
        return this;
    }

    public void setHoteltime(Instant hoteltime) {
        this.hoteltime = hoteltime;
    }

    public Long getWxf() {
        return this.wxf;
    }

    public FkCz wxf(Long wxf) {
        this.wxf = wxf;
        return this;
    }

    public void setWxf(Long wxf) {
        this.wxf = wxf;
    }

    public Long getKsf() {
        return this.ksf;
    }

    public FkCz ksf(Long ksf) {
        this.ksf = ksf;
        return this;
    }

    public void setKsf(Long ksf) {
        this.ksf = ksf;
    }

    public Long getKf() {
        return this.kf;
    }

    public FkCz kf(Long kf) {
        this.kf = kf;
        return this;
    }

    public void setKf(Long kf) {
        this.kf = kf;
    }

    public Long getZfs() {
        return this.zfs;
    }

    public FkCz zfs(Long zfs) {
        this.zfs = zfs;
        return this;
    }

    public void setZfs(Long zfs) {
        this.zfs = zfs;
    }

    public Long getGroupyd() {
        return this.groupyd;
    }

    public FkCz groupyd(Long groupyd) {
        this.groupyd = groupyd;
        return this;
    }

    public void setGroupyd(Long groupyd) {
        this.groupyd = groupyd;
    }

    public Long getSkyd() {
        return this.skyd;
    }

    public FkCz skyd(Long skyd) {
        this.skyd = skyd;
        return this;
    }

    public void setSkyd(Long skyd) {
        this.skyd = skyd;
    }

    public Long getYdwd() {
        return this.ydwd;
    }

    public FkCz ydwd(Long ydwd) {
        this.ydwd = ydwd;
        return this;
    }

    public void setYdwd(Long ydwd) {
        this.ydwd = ydwd;
    }

    public Long getQxyd() {
        return this.qxyd;
    }

    public FkCz qxyd(Long qxyd) {
        this.qxyd = qxyd;
        return this;
    }

    public void setQxyd(Long qxyd) {
        this.qxyd = qxyd;
    }

    public Long getIsnew() {
        return this.isnew;
    }

    public FkCz isnew(Long isnew) {
        this.isnew = isnew;
        return this;
    }

    public void setIsnew(Long isnew) {
        this.isnew = isnew;
    }

    public String getHoteldm() {
        return this.hoteldm;
    }

    public FkCz hoteldm(String hoteldm) {
        this.hoteldm = hoteldm;
        return this;
    }

    public void setHoteldm(String hoteldm) {
        this.hoteldm = hoteldm;
    }

    public BigDecimal getHys() {
        return this.hys;
    }

    public FkCz hys(BigDecimal hys) {
        this.hys = hys;
        return this;
    }

    public void setHys(BigDecimal hys) {
        this.hys = hys;
    }

    public BigDecimal getKhys() {
        return this.khys;
    }

    public FkCz khys(BigDecimal khys) {
        this.khys = khys;
        return this;
    }

    public void setKhys(BigDecimal khys) {
        this.khys = khys;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof FkCz)) {
            return false;
        }
        return id != null && id.equals(((FkCz) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "FkCz{" +
            "id=" + getId() +
            ", hoteltime='" + getHoteltime() + "'" +
            ", wxf=" + getWxf() +
            ", ksf=" + getKsf() +
            ", kf=" + getKf() +
            ", zfs=" + getZfs() +
            ", groupyd=" + getGroupyd() +
            ", skyd=" + getSkyd() +
            ", ydwd=" + getYdwd() +
            ", qxyd=" + getQxyd() +
            ", isnew=" + getIsnew() +
            ", hoteldm='" + getHoteldm() + "'" +
            ", hys=" + getHys() +
            ", khys=" + getKhys() +
            "}";
    }
}
