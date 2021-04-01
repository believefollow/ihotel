package ihotel.app.domain;

import java.io.Serializable;
import java.time.Instant;
import javax.persistence.*;
import javax.validation.constraints.*;
import org.springframework.data.elasticsearch.annotations.FieldType;

/**
 * A DCktime.
 */
@Entity
@Table(name = "d_cktime")
@org.springframework.data.elasticsearch.annotations.Document(indexName = "dcktime")
public class DCktime implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "begintime", nullable = false)
    private Instant begintime;

    @NotNull
    @Column(name = "endtime", nullable = false)
    private Instant endtime;

    @NotNull
    @Size(max = 20)
    @Column(name = "depot", length = 20, nullable = false)
    private String depot;

    @Size(max = 30)
    @Column(name = "ckbillno", length = 30)
    private String ckbillno;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public DCktime id(Long id) {
        this.id = id;
        return this;
    }

    public Instant getBegintime() {
        return this.begintime;
    }

    public DCktime begintime(Instant begintime) {
        this.begintime = begintime;
        return this;
    }

    public void setBegintime(Instant begintime) {
        this.begintime = begintime;
    }

    public Instant getEndtime() {
        return this.endtime;
    }

    public DCktime endtime(Instant endtime) {
        this.endtime = endtime;
        return this;
    }

    public void setEndtime(Instant endtime) {
        this.endtime = endtime;
    }

    public String getDepot() {
        return this.depot;
    }

    public DCktime depot(String depot) {
        this.depot = depot;
        return this;
    }

    public void setDepot(String depot) {
        this.depot = depot;
    }

    public String getCkbillno() {
        return this.ckbillno;
    }

    public DCktime ckbillno(String ckbillno) {
        this.ckbillno = ckbillno;
        return this;
    }

    public void setCkbillno(String ckbillno) {
        this.ckbillno = ckbillno;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof DCktime)) {
            return false;
        }
        return id != null && id.equals(((DCktime) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "DCktime{" +
            "id=" + getId() +
            ", begintime='" + getBegintime() + "'" +
            ", endtime='" + getEndtime() + "'" +
            ", depot='" + getDepot() + "'" +
            ", ckbillno='" + getCkbillno() + "'" +
            "}";
    }
}
