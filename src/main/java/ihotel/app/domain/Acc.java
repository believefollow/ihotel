package ihotel.app.domain;

import java.io.Serializable;
import javax.persistence.*;
import javax.validation.constraints.*;
import org.springframework.data.elasticsearch.annotations.FieldType;

/**
 * A Acc.
 */
@Entity
@Table(name = "acc")
@org.springframework.data.elasticsearch.annotations.Document(indexName = "acc")
public class Acc implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Size(max = 50)
    @Column(name = "acc", length = 50)
    private String acc;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Acc id(Long id) {
        this.id = id;
        return this;
    }

    public String getAcc() {
        return this.acc;
    }

    public Acc acc(String acc) {
        this.acc = acc;
        return this;
    }

    public void setAcc(String acc) {
        this.acc = acc;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Acc)) {
            return false;
        }
        return id != null && id.equals(((Acc) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Acc{" +
            "id=" + getId() +
            ", acc='" + getAcc() + "'" +
            "}";
    }
}
