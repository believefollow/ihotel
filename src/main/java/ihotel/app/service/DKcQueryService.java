package ihotel.app.service;

import ihotel.app.domain.*; // for static metamodels
import ihotel.app.domain.DKc;
import ihotel.app.repository.DKcRepository;
import ihotel.app.repository.search.DKcSearchRepository;
import ihotel.app.service.criteria.DKcCriteria;
import ihotel.app.service.dto.DKcDTO;
import ihotel.app.service.mapper.DKcMapper;
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
 * Service for executing complex queries for {@link DKc} entities in the database.
 * The main input is a {@link DKcCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link DKcDTO} or a {@link Page} of {@link DKcDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class DKcQueryService extends QueryService<DKc> {

    private final Logger log = LoggerFactory.getLogger(DKcQueryService.class);

    private final DKcRepository dKcRepository;

    private final DKcMapper dKcMapper;

    private final DKcSearchRepository dKcSearchRepository;

    public DKcQueryService(DKcRepository dKcRepository, DKcMapper dKcMapper, DKcSearchRepository dKcSearchRepository) {
        this.dKcRepository = dKcRepository;
        this.dKcMapper = dKcMapper;
        this.dKcSearchRepository = dKcSearchRepository;
    }

    /**
     * Return a {@link List} of {@link DKcDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<DKcDTO> findByCriteria(DKcCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<DKc> specification = createSpecification(criteria);
        return dKcMapper.toDto(dKcRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link DKcDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<DKcDTO> findByCriteria(DKcCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<DKc> specification = createSpecification(criteria);
        return dKcRepository.findAll(specification, page).map(dKcMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(DKcCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<DKc> specification = createSpecification(criteria);
        return dKcRepository.count(specification);
    }

    /**
     * Function to convert {@link DKcCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<DKc> createSpecification(DKcCriteria criteria) {
        Specification<DKc> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), DKc_.id));
            }
            if (criteria.getDepot() != null) {
                specification = specification.and(buildStringSpecification(criteria.getDepot(), DKc_.depot));
            }
            if (criteria.getSpbm() != null) {
                specification = specification.and(buildStringSpecification(criteria.getSpbm(), DKc_.spbm));
            }
            if (criteria.getSpmc() != null) {
                specification = specification.and(buildStringSpecification(criteria.getSpmc(), DKc_.spmc));
            }
            if (criteria.getXh() != null) {
                specification = specification.and(buildStringSpecification(criteria.getXh(), DKc_.xh));
            }
            if (criteria.getDw() != null) {
                specification = specification.and(buildStringSpecification(criteria.getDw(), DKc_.dw));
            }
            if (criteria.getPrice() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getPrice(), DKc_.price));
            }
            if (criteria.getSl() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getSl(), DKc_.sl));
            }
            if (criteria.getJe() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getJe(), DKc_.je));
            }
        }
        return specification;
    }
}
