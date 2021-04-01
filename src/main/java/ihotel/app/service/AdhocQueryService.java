package ihotel.app.service;

import ihotel.app.domain.*; // for static metamodels
import ihotel.app.domain.Adhoc;
import ihotel.app.repository.AdhocRepository;
import ihotel.app.repository.search.AdhocSearchRepository;
import ihotel.app.service.criteria.AdhocCriteria;
import ihotel.app.service.dto.AdhocDTO;
import ihotel.app.service.mapper.AdhocMapper;
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
 * Service for executing complex queries for {@link Adhoc} entities in the database.
 * The main input is a {@link AdhocCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link AdhocDTO} or a {@link Page} of {@link AdhocDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class AdhocQueryService extends QueryService<Adhoc> {

    private final Logger log = LoggerFactory.getLogger(AdhocQueryService.class);

    private final AdhocRepository adhocRepository;

    private final AdhocMapper adhocMapper;

    private final AdhocSearchRepository adhocSearchRepository;

    public AdhocQueryService(AdhocRepository adhocRepository, AdhocMapper adhocMapper, AdhocSearchRepository adhocSearchRepository) {
        this.adhocRepository = adhocRepository;
        this.adhocMapper = adhocMapper;
        this.adhocSearchRepository = adhocSearchRepository;
    }

    /**
     * Return a {@link List} of {@link AdhocDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<AdhocDTO> findByCriteria(AdhocCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<Adhoc> specification = createSpecification(criteria);
        return adhocMapper.toDto(adhocRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link AdhocDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<AdhocDTO> findByCriteria(AdhocCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<Adhoc> specification = createSpecification(criteria);
        return adhocRepository.findAll(specification, page).map(adhocMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(AdhocCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<Adhoc> specification = createSpecification(criteria);
        return adhocRepository.count(specification);
    }

    /**
     * Function to convert {@link AdhocCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<Adhoc> createSpecification(AdhocCriteria criteria) {
        Specification<Adhoc> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildStringSpecification(criteria.getId(), Adhoc_.id));
            }
            if (criteria.getRemark() != null) {
                specification = specification.and(buildStringSpecification(criteria.getRemark(), Adhoc_.remark));
            }
        }
        return specification;
    }
}
