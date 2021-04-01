package ihotel.app.domain;

import java.io.Serializable;
import javax.persistence.*;
import javax.validation.constraints.*;
import org.springframework.data.elasticsearch.annotations.FieldType;

/**
 * A Accbillno.
 */
@Entity
@Table(name = "accbillno")
@org.springframework.data.elasticsearch.annotations.Document(indexName = "accbillno")
public class Accbillno implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Size(max = 30)
    @Column(name = "account", length = 30, nullable = false)
    private String account;

    @Size(max = 30)
    @Column(name = "accbillno", length = 30)
    private String accbillno;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Accbillno id(Long id) {
        this.id = id;
        return this;
    }

    public String getAccount() {
        return this.account;
    }

    public Accbillno account(String account) {
        this.account = account;
        return this;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public String getAccbillno() {
        return this.accbillno;
    }

    public Accbillno accbillno(String accbillno) {
        this.accbillno = accbillno;
        return this;
    }

    public void setAccbillno(String accbillno) {
        this.accbillno = accbillno;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Accbillno)) {
            return false;
        }
        return id != null && id.equals(((Accbillno) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Accbillno{" +
            "id=" + getId() +
            ", account='" + getAccount() + "'" +
            ", accbillno='" + getAccbillno() + "'" +
            "}";
    }
}
