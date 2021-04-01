package ihotel.app.service;

import ihotel.app.domain.*; // for static metamodels
import ihotel.app.domain.DEmpn;
import ihotel.app.repository.DEmpnRepository;
import ihotel.app.repository.search.DEmpnSearchRepository;
import ihotel.app.service.criteria.DEmpnCriteria;
import ihotel.app.service.dto.DEmpnDTO;
import ihotel.app.service.mapper.DEmpnMapper;
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
 * Service for executing complex queries for {@link DEmpn} entities in the database.
 * The main input is a {@link DEmpnCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link DEmpnDTO} or a {@link Page} of {@link DEmpnDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class DEmpnQueryService extends QueryService<DEmpn> {

    private final Logger log = LoggerFactory.getLogger(DEmpnQueryService.class);

    private final DEmpnRepository dEmpnRepository;

    private final DEmpnMapper dEmpnMapper;

    private final DEmpnSearchRepository dEmpnSearchRepository;

    public DEmpnQueryService(DEmpnRepository dEmpnRepository, DEmpnMapper dEmpnMapper, DEmpnSearchRepository dEmpnSearchRepository) {
        this.dEmpnRepository = dEmpnRepository;
        this.dEmpnMapper = dEmpnMapper;
        this.dEmpnSearchRepository = dEmpnSearchRepository;
    }

    /**
     * Return a {@link List} of {@link DEmpnDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<DEmpnDTO> findByCriteria(DEmpnCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<DEmpn> specification = createSpecification(criteria);
        return dEmpnMapper.toDto(dEmpnRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link DEmpnDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<DEmpnDTO> findByCriteria(DEmpnCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<DEmpn> specification = createSpecification(criteria);
        return dEmpnRepository.findAll(specification, page).map(dEmpnMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(DEmpnCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<DEmpn> specification = createSpecification(criteria);
        return dEmpnRepository.count(specification);
    }

    /**
     * Function to convert {@link DEmpnCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<DEmpn> createSpecification(DEmpnCriteria criteria) {
        Specification<DEmpn> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), DEmpn_.id));
            }
            if (criteria.getEmpnid() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getEmpnid(), DEmpn_.empnid));
            }
            if (criteria.getEmpn() != null) {
                specification = specification.and(buildStringSpecification(criteria.getEmpn(), DEmpn_.empn));
            }
            if (criteria.getDeptid() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getDeptid(), DEmpn_.deptid));
            }
            if (criteria.getPhone() != null) {
                specification = specification.and(buildStringSpecification(criteria.getPhone(), DEmpn_.phone));
            }
        }
        return specification;
    }
}
