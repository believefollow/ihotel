package ihotel.app.domain;

import java.io.Serializable;
import javax.persistence.*;
import javax.validation.constraints.*;
import org.springframework.data.elasticsearch.annotations.FieldType;

/**
 * A Code1.
 */
@Entity
@Table(name = "code_1")
@org.springframework.data.elasticsearch.annotations.Document(indexName = "code1")
public class Code1 implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Size(max = 20)
    @Column(name = "code_1", length = 20, nullable = false)
    private String code1;

    @NotNull
    @Size(max = 20)
    @Column(name = "code_2", length = 20, nullable = false)
    private String code2;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Code1 id(Long id) {
        this.id = id;
        return this;
    }

    public String getCode1() {
        return this.code1;
    }

    public Code1 code1(String code1) {
        this.code1 = code1;
        return this;
    }

    public void setCode1(String code1) {
        this.code1 = code1;
    }

    public String getCode2() {
        return this.code2;
    }

    public Code1 code2(String code2) {
        this.code2 = code2;
        return this;
    }

    public void setCode2(String code2) {
        this.code2 = code2;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Code1)) {
            return false;
        }
        return id != null && id.equals(((Code1) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Code1{" +
            "id=" + getId() +
            ", code1='" + getCode1() + "'" +
            ", code2='" + getCode2() + "'" +
            "}";
    }
}
