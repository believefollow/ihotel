package ihotel.app.service;

import ihotel.app.domain.*; // for static metamodels
import ihotel.app.domain.Clog;
import ihotel.app.repository.ClogRepository;
import ihotel.app.repository.search.ClogSearchRepository;
import ihotel.app.service.criteria.ClogCriteria;
import ihotel.app.service.dto.ClogDTO;
import ihotel.app.service.mapper.ClogMapper;
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
 * Service for executing complex queries for {@link Clog} entities in the database.
 * The main input is a {@link ClogCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link ClogDTO} or a {@link Page} of {@link ClogDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class ClogQueryService extends QueryService<Clog> {

    private final Logger log = LoggerFactory.getLogger(ClogQueryService.class);

    private final ClogRepository clogRepository;

    private final ClogMapper clogMapper;

    private final ClogSearchRepository clogSearchRepository;

    public ClogQueryService(ClogRepository clogRepository, ClogMapper clogMapper, ClogSearchRepository clogSearchRepository) {
        this.clogRepository = clogRepository;
        this.clogMapper = clogMapper;
        this.clogSearchRepository = clogSearchRepository;
    }

    /**
     * Return a {@link List} of {@link ClogDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<ClogDTO> findByCriteria(ClogCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<Clog> specification = createSpecification(criteria);
        return clogMapper.toDto(clogRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link ClogDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<ClogDTO> findByCriteria(ClogCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<Clog> specification = createSpecification(criteria);
        return clogRepository.findAll(specification, page).map(clogMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(ClogCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<Clog> specification = createSpecification(criteria);
        return clogRepository.count(specification);
    }

    /**
     * Function to convert {@link ClogCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<Clog> createSpecification(ClogCriteria criteria) {
        Specification<Clog> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), Clog_.id));
            }
            if (criteria.getEmpn() != null) {
                specification = specification.and(buildStringSpecification(criteria.getEmpn(), Clog_.empn));
            }
            if (criteria.getBegindate() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getBegindate(), Clog_.begindate));
            }
            if (criteria.getEnddate() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getEnddate(), Clog_.enddate));
            }
            if (criteria.getDqrq() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getDqrq(), Clog_.dqrq));
            }
        }
        return specification;
    }
}
