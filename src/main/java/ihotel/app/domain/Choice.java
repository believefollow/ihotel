package ihotel.app.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import java.time.Instant;
import javax.persistence.*;
import javax.validation.constraints.*;
import org.springframework.data.elasticsearch.annotations.FieldType;

/**
 * A Choice.
 */
@Entity
@Table(name = "choice")
@org.springframework.data.elasticsearch.annotations.Document(indexName = "choice")
public class Choice implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "createdat", nullable = false)
    private Instant createdat;

    @NotNull
    @Column(name = "updatedat", nullable = false)
    private Instant updatedat;

    @NotNull
    @Size(max = 191)
    @Column(name = "text", length = 191, nullable = false)
    private String text;

    @NotNull
    @Column(name = "votes", nullable = false)
    private Long votes;

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties(value = { "choices" }, allowSetters = true)
    private Question question;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Choice id(Long id) {
        this.id = id;
        return this;
    }

    public Instant getCreatedat() {
        return this.createdat;
    }

    public Choice createdat(Instant createdat) {
        this.createdat = createdat;
        return this;
    }

    public void setCreatedat(Instant createdat) {
        this.createdat = createdat;
    }

    public Instant getUpdatedat() {
        return this.updatedat;
    }

    public Choice updatedat(Instant updatedat) {
        this.updatedat = updatedat;
        return this;
    }

    public void setUpdatedat(Instant updatedat) {
        this.updatedat = updatedat;
    }

    public String getText() {
        return this.text;
    }

    public Choice text(String text) {
        this.text = text;
        return this;
    }

    public void setText(String text) {
        this.text = text;
    }

    public Long getVotes() {
        return this.votes;
    }

    public Choice votes(Long votes) {
        this.votes = votes;
        return this;
    }

    public void setVotes(Long votes) {
        this.votes = votes;
    }

    public Question getQuestion() {
        return this.question;
    }

    public Choice question(Question question) {
        this.setQuestion(question);
        return this;
    }

    public void setQuestion(Question question) {
        this.question = question;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Choice)) {
            return false;
        }
        return id != null && id.equals(((Choice) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Choice{" +
            "id=" + getId() +
            ", createdat='" + getCreatedat() + "'" +
            ", updatedat='" + getUpdatedat() + "'" +
            ", text='" + getText() + "'" +
            ", votes=" + getVotes() +
            "}";
    }
}
