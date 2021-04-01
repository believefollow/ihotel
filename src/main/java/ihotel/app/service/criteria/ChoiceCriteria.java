package ihotel.app.service.criteria;

import java.io.Serializable;
import java.util.Objects;
import tech.jhipster.service.Criteria;
import tech.jhipster.service.filter.BooleanFilter;
import tech.jhipster.service.filter.DoubleFilter;
import tech.jhipster.service.filter.Filter;
import tech.jhipster.service.filter.FloatFilter;
import tech.jhipster.service.filter.InstantFilter;
import tech.jhipster.service.filter.IntegerFilter;
import tech.jhipster.service.filter.LongFilter;
import tech.jhipster.service.filter.StringFilter;

/**
 * Criteria class for the {@link ihotel.app.domain.Choice} entity. This class is used
 * in {@link ihotel.app.web.rest.ChoiceResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /choices?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class ChoiceCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private InstantFilter createdat;

    private InstantFilter updatedat;

    private StringFilter text;

    private LongFilter votes;

    private LongFilter questionId;

    public ChoiceCriteria() {}

    public ChoiceCriteria(ChoiceCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.createdat = other.createdat == null ? null : other.createdat.copy();
        this.updatedat = other.updatedat == null ? null : other.updatedat.copy();
        this.text = other.text == null ? null : other.text.copy();
        this.votes = other.votes == null ? null : other.votes.copy();
        this.questionId = other.questionId == null ? null : other.questionId.copy();
    }

    @Override
    public ChoiceCriteria copy() {
        return new ChoiceCriteria(this);
    }

    public LongFilter getId() {
        return id;
    }

    public LongFilter id() {
        if (id == null) {
            id = new LongFilter();
        }
        return id;
    }

    public void setId(LongFilter id) {
        this.id = id;
    }

    public InstantFilter getCreatedat() {
        return createdat;
    }

    public InstantFilter createdat() {
        if (createdat == null) {
            createdat = new InstantFilter();
        }
        return createdat;
    }

    public void setCreatedat(InstantFilter createdat) {
        this.createdat = createdat;
    }

    public InstantFilter getUpdatedat() {
        return updatedat;
    }

    public InstantFilter updatedat() {
        if (updatedat == null) {
            updatedat = new InstantFilter();
        }
        return updatedat;
    }

    public void setUpdatedat(InstantFilter updatedat) {
        this.updatedat = updatedat;
    }

    public StringFilter getText() {
        return text;
    }

    public StringFilter text() {
        if (text == null) {
            text = new StringFilter();
        }
        return text;
    }

    public void setText(StringFilter text) {
        this.text = text;
    }

    public LongFilter getVotes() {
        return votes;
    }

    public LongFilter votes() {
        if (votes == null) {
            votes = new LongFilter();
        }
        return votes;
    }

    public void setVotes(LongFilter votes) {
        this.votes = votes;
    }

    public LongFilter getQuestionId() {
        return questionId;
    }

    public LongFilter questionId() {
        if (questionId == null) {
            questionId = new LongFilter();
        }
        return questionId;
    }

    public void setQuestionId(LongFilter questionId) {
        this.questionId = questionId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final ChoiceCriteria that = (ChoiceCriteria) o;
        return (
            Objects.equals(id, that.id) &&
            Objects.equals(createdat, that.createdat) &&
            Objects.equals(updatedat, that.updatedat) &&
            Objects.equals(text, that.text) &&
            Objects.equals(votes, that.votes) &&
            Objects.equals(questionId, that.questionId)
        );
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, createdat, updatedat, text, votes, questionId);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ChoiceCriteria{" +
            (id != null ? "id=" + id + ", " : "") +
            (createdat != null ? "createdat=" + createdat + ", " : "") +
            (updatedat != null ? "updatedat=" + updatedat + ", " : "") +
            (text != null ? "text=" + text + ", " : "") +
            (votes != null ? "votes=" + votes + ", " : "") +
            (questionId != null ? "questionId=" + questionId + ", " : "") +
            "}";
    }
}
