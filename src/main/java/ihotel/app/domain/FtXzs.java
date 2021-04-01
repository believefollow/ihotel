package ihotel.app.domain;

import java.io.Serializable;
import javax.persistence.*;
import javax.validation.constraints.*;
import org.springframework.data.elasticsearch.annotations.FieldType;

/**
 * A FtXzs.
 */
@Entity
@Table(name = "ft_xzs")
@org.springframework.data.elasticsearch.annotations.Document(indexName = "ftxzs")
public class FtXzs implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Size(max = 50)
    @Column(name = "roomn", length = 50)
    private String roomn;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public FtXzs id(Long id) {
        this.id = id;
        return this;
    }

    public String getRoomn() {
        return this.roomn;
    }

    public FtXzs roomn(String roomn) {
        this.roomn = roomn;
        return this;
    }

    public void setRoomn(String roomn) {
        this.roomn = roomn;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof FtXzs)) {
            return false;
        }
        return id != null && id.equals(((FtXzs) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "FtXzs{" +
            "id=" + getId() +
            ", roomn='" + getRoomn() + "'" +
            "}";
    }
}
