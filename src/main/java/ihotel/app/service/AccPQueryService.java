package ihotel.app.service;

import ihotel.app.domain.*; // for static metamodels
import ihotel.app.domain.AccP;
import ihotel.app.repository.AccPRepository;
import ihotel.app.repository.search.AccPSearchRepository;
import ihotel.app.service.criteria.AccPCriteria;
import ihotel.app.service.dto.AccPDTO;
import ihotel.app.service.mapper.AccPMapper;
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
 * Service for executing complex queries for {@link AccP} entities in the database.
 * The main input is a {@link AccPCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link AccPDTO} or a {@link Page} of {@link AccPDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class AccPQueryService extends QueryService<AccP> {

    private final Logger log = LoggerFactory.getLogger(AccPQueryService.class);

    private final AccPRepository accPRepository;

    private final AccPMapper accPMapper;

    private final AccPSearchRepository accPSearchRepository;

    public AccPQueryService(AccPRepository accPRepository, AccPMapper accPMapper, AccPSearchRepository accPSearchRepository) {
        this.accPRepository = accPRepository;
        this.accPMapper = accPMapper;
        this.accPSearchRepository = accPSearchRepository;
    }

    /**
     * Return a {@link List} of {@link AccPDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<AccPDTO> findByCriteria(AccPCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<AccP> specification = createSpecification(criteria);
        return accPMapper.toDto(accPRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link AccPDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<AccPDTO> findByCriteria(AccPCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<AccP> specification = createSpecification(criteria);
        return accPRepository.findAll(specification, page).map(accPMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(AccPCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<AccP> specification = createSpecification(criteria);
        return accPRepository.count(specification);
    }

    /**
     * Function to convert {@link AccPCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<AccP> createSpecification(AccPCriteria criteria) {
        Specification<AccP> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), AccP_.id));
            }
            if (criteria.getAcc() != null) {
                specification = specification.and(buildStringSpecification(criteria.getAcc(), AccP_.acc));
            }
        }
        return specification;
    }
}
