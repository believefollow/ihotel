package ihotel.app.service;

import ihotel.app.domain.*; // for static metamodels
import ihotel.app.domain.DUnit;
import ihotel.app.repository.DUnitRepository;
import ihotel.app.repository.search.DUnitSearchRepository;
import ihotel.app.service.criteria.DUnitCriteria;
import ihotel.app.service.dto.DUnitDTO;
import ihotel.app.service.mapper.DUnitMapper;
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
 * Service for executing complex queries for {@link DUnit} entities in the database.
 * The main input is a {@link DUnitCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link DUnitDTO} or a {@link Page} of {@link DUnitDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class DUnitQueryService extends QueryService<DUnit> {

    private final Logger log = LoggerFactory.getLogger(DUnitQueryService.class);

    private final DUnitRepository dUnitRepository;

    private final DUnitMapper dUnitMapper;

    private final DUnitSearchRepository dUnitSearchRepository;

    public DUnitQueryService(DUnitRepository dUnitRepository, DUnitMapper dUnitMapper, DUnitSearchRepository dUnitSearchRepository) {
        this.dUnitRepository = dUnitRepository;
        this.dUnitMapper = dUnitMapper;
        this.dUnitSearchRepository = dUnitSearchRepository;
    }

    /**
     * Return a {@link List} of {@link DUnitDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<DUnitDTO> findByCriteria(DUnitCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<DUnit> specification = createSpecification(criteria);
        return dUnitMapper.toDto(dUnitRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link DUnitDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<DUnitDTO> findByCriteria(DUnitCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<DUnit> specification = createSpecification(criteria);
        return dUnitRepository.findAll(specification, page).map(dUnitMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(DUnitCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<DUnit> specification = createSpecification(criteria);
        return dUnitRepository.count(specification);
    }

    /**
     * Function to convert {@link DUnitCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<DUnit> createSpecification(DUnitCriteria criteria) {
        Specification<DUnit> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), DUnit_.id));
            }
            if (criteria.getUnit() != null) {
                specification = specification.and(buildStringSpecification(criteria.getUnit(), DUnit_.unit));
            }
        }
        return specification;
    }
}
