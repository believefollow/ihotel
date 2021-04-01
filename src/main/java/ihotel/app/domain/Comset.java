package ihotel.app.domain;

import java.io.Serializable;
import javax.persistence.*;
import javax.validation.constraints.*;
import org.springframework.data.elasticsearch.annotations.FieldType;

/**
 * A Comset.
 */
@Entity
@Table(name = "comset")
@org.springframework.data.elasticsearch.annotations.Document(indexName = "comset")
public class Comset implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Size(max = 10)
    @Column(name = "com_num", length = 10, nullable = false)
    private String comNum;

    @NotNull
    @Size(max = 18)
    @Column(name = "com_bytes", length = 18, nullable = false)
    private String comBytes;

    @NotNull
    @Size(max = 10)
    @Column(name = "com_databit", length = 10, nullable = false)
    private String comDatabit;

    @NotNull
    @Size(max = 10)
    @Column(name = "com_paritycheck", length = 10, nullable = false)
    private String comParitycheck;

    @NotNull
    @Size(max = 18)
    @Column(name = "com_stopbit", length = 18, nullable = false)
    private String comStopbit;

    @NotNull
    @Column(name = "com_function", nullable = false)
    private Long comFunction;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Comset id(Long id) {
        this.id = id;
        return this;
    }

    public String getComNum() {
        return this.comNum;
    }

    public Comset comNum(String comNum) {
        this.comNum = comNum;
        return this;
    }

    public void setComNum(String comNum) {
        this.comNum = comNum;
    }

    public String getComBytes() {
        return this.comBytes;
    }

    public Comset comBytes(String comBytes) {
        this.comBytes = comBytes;
        return this;
    }

    public void setComBytes(String comBytes) {
        this.comBytes = comBytes;
    }

    public String getComDatabit() {
        return this.comDatabit;
    }

    public Comset comDatabit(String comDatabit) {
        this.comDatabit = comDatabit;
        return this;
    }

    public void setComDatabit(String comDatabit) {
        this.comDatabit = comDatabit;
    }

    public String getComParitycheck() {
        return this.comParitycheck;
    }

    public Comset comParitycheck(String comParitycheck) {
        this.comParitycheck = comParitycheck;
        return this;
    }

    public void setComParitycheck(String comParitycheck) {
        this.comParitycheck = comParitycheck;
    }

    public String getComStopbit() {
        return this.comStopbit;
    }

    public Comset comStopbit(String comStopbit) {
        this.comStopbit = comStopbit;
        return this;
    }

    public void setComStopbit(String comStopbit) {
        this.comStopbit = comStopbit;
    }

    public Long getComFunction() {
        return this.comFunction;
    }

    public Comset comFunction(Long comFunction) {
        this.comFunction = comFunction;
        return this;
    }

    public void setComFunction(Long comFunction) {
        this.comFunction = comFunction;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Comset)) {
            return false;
        }
        return id != null && id.equals(((Comset) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Comset{" +
            "id=" + getId() +
            ", comNum='" + getComNum() + "'" +
            ", comBytes='" + getComBytes() + "'" +
            ", comDatabit='" + getComDatabit() + "'" +
            ", comParitycheck='" + getComParitycheck() + "'" +
            ", comStopbit='" + getComStopbit() + "'" +
            ", comFunction=" + getComFunction() +
            "}";
    }
}
