package ihotel.app.service;

import ihotel.app.domain.*; // for static metamodels
import ihotel.app.domain.FwEmpn;
import ihotel.app.repository.FwEmpnRepository;
import ihotel.app.repository.search.FwEmpnSearchRepository;
import ihotel.app.service.criteria.FwEmpnCriteria;
import ihotel.app.service.dto.FwEmpnDTO;
import ihotel.app.service.mapper.FwEmpnMapper;
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
 * Service for executing complex queries for {@link FwEmpn} entities in the database.
 * The main input is a {@link FwEmpnCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link FwEmpnDTO} or a {@link Page} of {@link FwEmpnDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class FwEmpnQueryService extends QueryService<FwEmpn> {

    private final Logger log = LoggerFactory.getLogger(FwEmpnQueryService.class);

    private final FwEmpnRepository fwEmpnRepository;

    private final FwEmpnMapper fwEmpnMapper;

    private final FwEmpnSearchRepository fwEmpnSearchRepository;

    public FwEmpnQueryService(FwEmpnRepository fwEmpnRepository, FwEmpnMapper fwEmpnMapper, FwEmpnSearchRepository fwEmpnSearchRepository) {
        this.fwEmpnRepository = fwEmpnRepository;
        this.fwEmpnMapper = fwEmpnMapper;
        this.fwEmpnSearchRepository = fwEmpnSearchRepository;
    }

    /**
     * Return a {@link List} of {@link FwEmpnDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<FwEmpnDTO> findByCriteria(FwEmpnCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<FwEmpn> specification = createSpecification(criteria);
        return fwEmpnMapper.toDto(fwEmpnRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link FwEmpnDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<FwEmpnDTO> findByCriteria(FwEmpnCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<FwEmpn> specification = createSpecification(criteria);
        return fwEmpnRepository.findAll(specification, page).map(fwEmpnMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(FwEmpnCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<FwEmpn> specification = createSpecification(criteria);
        return fwEmpnRepository.count(specification);
    }

    /**
     * Function to convert {@link FwEmpnCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<FwEmpn> createSpecification(FwEmpnCriteria criteria) {
        Specification<FwEmpn> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), FwEmpn_.id));
            }
            if (criteria.getEmpnid() != null) {
                specification = specification.and(buildStringSpecification(criteria.getEmpnid(), FwEmpn_.empnid));
            }
            if (criteria.getEmpn() != null) {
                specification = specification.and(buildStringSpecification(criteria.getEmpn(), FwEmpn_.empn));
            }
            if (criteria.getDeptid() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getDeptid(), FwEmpn_.deptid));
            }
            if (criteria.getPhone() != null) {
                specification = specification.and(buildStringSpecification(criteria.getPhone(), FwEmpn_.phone));
            }
        }
        return specification;
    }
}
