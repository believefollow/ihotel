package ihotel.app.service;

import ihotel.app.domain.*; // for static metamodels
import ihotel.app.domain.Choice;
import ihotel.app.repository.ChoiceRepository;
import ihotel.app.repository.search.ChoiceSearchRepository;
import ihotel.app.service.criteria.ChoiceCriteria;
import ihotel.app.service.dto.ChoiceDTO;
import ihotel.app.service.mapper.ChoiceMapper;
import java.util.List;
import javax.persistence.criteria.JoinType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tech.jhipster.service.QueryService;

/**
 * Service for executing complex queries for {@link Choice} entities in the database.
 * The main input is a {@link ChoiceCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link ChoiceDTO} or a {@link Page} of {@link ChoiceDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class ChoiceQueryService extends QueryService<Choice> {

    private final Logger log = LoggerFactory.getLogger(ChoiceQueryService.class);

    private final ChoiceRepository choiceRepository;

    private final ChoiceMapper choiceMapper;

    private final ChoiceSearchRepository choiceSearchRepository;

    public ChoiceQueryService(ChoiceRepository choiceRepository, ChoiceMapper choiceMapper, ChoiceSearchRepository choiceSearchRepository) {
        this.choiceRepository = choiceRepository;
        this.choiceMapper = choiceMapper;
        this.choiceSearchRepository = choiceSearchRepository;
    }

    /**
     * Return a {@link List} of {@link ChoiceDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<ChoiceDTO> findByCriteria(ChoiceCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<Choice> specification = createSpecification(criteria);
        return choiceMapper.toDto(choiceRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link ChoiceDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<ChoiceDTO> findByCriteria(ChoiceCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<Choice> specification = createSpecification(criteria);
        return choiceRepository.findAll(specification, page).map(choiceMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(ChoiceCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<Choice> specification = createSpecification(criteria);
        return choiceRepository.count(specification);
    }

    /**
     * Function to convert {@link ChoiceCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<Choice> createSpecification(ChoiceCriteria criteria) {
        Specification<Choice> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), Choice_.id));
            }
            if (criteria.getCreatedat() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getCreatedat(), Choice_.createdat));
            }
            if (criteria.getUpdatedat() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getUpdatedat(), Choice_.updatedat));
            }
            if (criteria.getText() != null) {
                specification = specification.and(buildStringSpecification(criteria.getText(), Choice_.text));
            }
            if (criteria.getVotes() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getVotes(), Choice_.votes));
            }
            if (criteria.getQuestionId() != null) {
                specification =
                    specification.and(
                        buildSpecification(criteria.getQuestionId(), root -> root.join(Choice_.question, JoinType.LEFT).get(Question_.id))
                    );
            }
        }
        return specification;
    }
}
