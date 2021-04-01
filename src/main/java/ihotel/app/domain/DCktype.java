package ihotel.app.domain;

import java.io.Serializable;
import javax.persistence.*;
import javax.validation.constraints.*;
import org.springframework.data.elasticsearch.annotations.FieldType;

/**
 * A DCktype.
 */
@Entity
@Table(name = "d_cktype")
@org.springframework.data.elasticsearch.annotations.Document(indexName = "dcktype")
public class DCktype implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Size(max = 30)
    @Column(name = "cktype", length = 30, nullable = false)
    private String cktype;

    @Size(max = 100)
    @Column(name = "memo", length = 100)
    private String memo;

    @NotNull
    @Size(max = 20)
    @Column(name = "sign", length = 20, nullable = false)
    private String sign;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public DCktype id(Long id) {
        this.id = id;
        return this;
    }

    public String getCktype() {
        return this.cktype;
    }

    public DCktype cktype(String cktype) {
        this.cktype = cktype;
        return this;
    }

    public void setCktype(String cktype) {
        this.cktype = cktype;
    }

    public String getMemo() {
        return this.memo;
    }

    public DCktype memo(String memo) {
        this.memo = memo;
        return this;
    }

    public void setMemo(String memo) {
        this.memo = memo;
    }

    public String getSign() {
        return this.sign;
    }

    public DCktype sign(String sign) {
        this.sign = sign;
        return this;
    }

    public void setSign(String sign) {
        this.sign = sign;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof DCktype)) {
            return false;
        }
        return id != null && id.equals(((DCktype) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "DCktype{" +
            "id=" + getId() +
            ", cktype='" + getCktype() + "'" +
            ", memo='" + getMemo() + "'" +
            ", sign='" + getSign() + "'" +
            "}";
    }
}
