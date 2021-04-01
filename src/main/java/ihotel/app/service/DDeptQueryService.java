package ihotel.app.service;

import ihotel.app.domain.*; // for static metamodels
import ihotel.app.domain.DDept;
import ihotel.app.repository.DDeptRepository;
import ihotel.app.repository.search.DDeptSearchRepository;
import ihotel.app.service.criteria.DDeptCriteria;
import ihotel.app.service.dto.DDeptDTO;
import ihotel.app.service.mapper.DDeptMapper;
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
 * Service for executing complex queries for {@link DDept} entities in the database.
 * The main input is a {@link DDeptCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link DDeptDTO} or a {@link Page} of {@link DDeptDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class DDeptQueryService extends QueryService<DDept> {

    private final Logger log = LoggerFactory.getLogger(DDeptQueryService.class);

    private final DDeptRepository dDeptRepository;

    private final DDeptMapper dDeptMapper;

    private final DDeptSearchRepository dDeptSearchRepository;

    public DDeptQueryService(DDeptRepository dDeptRepository, DDeptMapper dDeptMapper, DDeptSearchRepository dDeptSearchRepository) {
        this.dDeptRepository = dDeptRepository;
        this.dDeptMapper = dDeptMapper;
        this.dDeptSearchRepository = dDeptSearchRepository;
    }

    /**
     * Return a {@link List} of {@link DDeptDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<DDeptDTO> findByCriteria(DDeptCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<DDept> specification = createSpecification(criteria);
        return dDeptMapper.toDto(dDeptRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link DDeptDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<DDeptDTO> findByCriteria(DDeptCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<DDept> specification = createSpecification(criteria);
        return dDeptRepository.findAll(specification, page).map(dDeptMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(DDeptCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<DDept> specification = createSpecification(criteria);
        return dDeptRepository.count(specification);
    }

    /**
     * Function to convert {@link DDeptCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<DDept> createSpecification(DDeptCriteria criteria) {
        Specification<DDept> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), DDept_.id));
            }
            if (criteria.getDeptid() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getDeptid(), DDept_.deptid));
            }
            if (criteria.getDeptname() != null) {
                specification = specification.and(buildStringSpecification(criteria.getDeptname(), DDept_.deptname));
            }
        }
        return specification;
    }
}
