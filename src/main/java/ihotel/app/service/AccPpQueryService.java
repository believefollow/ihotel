package ihotel.app.service;

import ihotel.app.domain.*; // for static metamodels
import ihotel.app.domain.AccPp;
import ihotel.app.repository.AccPpRepository;
import ihotel.app.repository.search.AccPpSearchRepository;
import ihotel.app.service.criteria.AccPpCriteria;
import ihotel.app.service.dto.AccPpDTO;
import ihotel.app.service.mapper.AccPpMapper;
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
 * Service for executing complex queries for {@link AccPp} entities in the database.
 * The main input is a {@link AccPpCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link AccPpDTO} or a {@link Page} of {@link AccPpDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class AccPpQueryService extends QueryService<AccPp> {

    private final Logger log = LoggerFactory.getLogger(AccPpQueryService.class);

    private final AccPpRepository accPpRepository;

    private final AccPpMapper accPpMapper;

    private final AccPpSearchRepository accPpSearchRepository;

    public AccPpQueryService(AccPpRepository accPpRepository, AccPpMapper accPpMapper, AccPpSearchRepository accPpSearchRepository) {
        this.accPpRepository = accPpRepository;
        this.accPpMapper = accPpMapper;
        this.accPpSearchRepository = accPpSearchRepository;
    }

    /**
     * Return a {@link List} of {@link AccPpDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<AccPpDTO> findByCriteria(AccPpCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<AccPp> specification = createSpecification(criteria);
        return accPpMapper.toDto(accPpRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link AccPpDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<AccPpDTO> findByCriteria(AccPpCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<AccPp> specification = createSpecification(criteria);
        return accPpRepository.findAll(specification, page).map(accPpMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(AccPpCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<AccPp> specification = createSpecification(criteria);
        return accPpRepository.count(specification);
    }

    /**
     * Function to convert {@link AccPpCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<AccPp> createSpecification(AccPpCriteria criteria) {
        Specification<AccPp> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), AccPp_.id));
            }
            if (criteria.getAcc() != null) {
                specification = specification.and(buildStringSpecification(criteria.getAcc(), AccPp_.acc));
            }
        }
        return specification;
    }
}
