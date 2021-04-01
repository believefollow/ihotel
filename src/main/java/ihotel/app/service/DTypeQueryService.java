package ihotel.app.service;

import ihotel.app.domain.*; // for static metamodels
import ihotel.app.domain.DType;
import ihotel.app.repository.DTypeRepository;
import ihotel.app.repository.search.DTypeSearchRepository;
import ihotel.app.service.criteria.DTypeCriteria;
import ihotel.app.service.dto.DTypeDTO;
import ihotel.app.service.mapper.DTypeMapper;
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
 * Service for executing complex queries for {@link DType} entities in the database.
 * The main input is a {@link DTypeCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link DTypeDTO} or a {@link Page} of {@link DTypeDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class DTypeQueryService extends QueryService<DType> {

    private final Logger log = LoggerFactory.getLogger(DTypeQueryService.class);

    private final DTypeRepository dTypeRepository;

    private final DTypeMapper dTypeMapper;

    private final DTypeSearchRepository dTypeSearchRepository;

    public DTypeQueryService(DTypeRepository dTypeRepository, DTypeMapper dTypeMapper, DTypeSearchRepository dTypeSearchRepository) {
        this.dTypeRepository = dTypeRepository;
        this.dTypeMapper = dTypeMapper;
        this.dTypeSearchRepository = dTypeSearchRepository;
    }

    /**
     * Return a {@link List} of {@link DTypeDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<DTypeDTO> findByCriteria(DTypeCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<DType> specification = createSpecification(criteria);
        return dTypeMapper.toDto(dTypeRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link DTypeDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<DTypeDTO> findByCriteria(DTypeCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<DType> specification = createSpecification(criteria);
        return dTypeRepository.findAll(specification, page).map(dTypeMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(DTypeCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<DType> specification = createSpecification(criteria);
        return dTypeRepository.count(specification);
    }

    /**
     * Function to convert {@link DTypeCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<DType> createSpecification(DTypeCriteria criteria) {
        Specification<DType> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), DType_.id));
            }
            if (criteria.getTypeid() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getTypeid(), DType_.typeid));
            }
            if (criteria.getTypename() != null) {
                specification = specification.and(buildStringSpecification(criteria.getTypename(), DType_.typename));
            }
            if (criteria.getFatherid() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getFatherid(), DType_.fatherid));
            }
            if (criteria.getDisabled() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getDisabled(), DType_.disabled));
            }
        }
        return specification;
    }
}
