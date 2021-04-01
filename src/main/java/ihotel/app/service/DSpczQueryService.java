package ihotel.app.service;

import ihotel.app.domain.*; // for static metamodels
import ihotel.app.domain.DSpcz;
import ihotel.app.repository.DSpczRepository;
import ihotel.app.repository.search.DSpczSearchRepository;
import ihotel.app.service.criteria.DSpczCriteria;
import ihotel.app.service.dto.DSpczDTO;
import ihotel.app.service.mapper.DSpczMapper;
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
 * Service for executing complex queries for {@link DSpcz} entities in the database.
 * The main input is a {@link DSpczCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link DSpczDTO} or a {@link Page} of {@link DSpczDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class DSpczQueryService extends QueryService<DSpcz> {

    private final Logger log = LoggerFactory.getLogger(DSpczQueryService.class);

    private final DSpczRepository dSpczRepository;

    private final DSpczMapper dSpczMapper;

    private final DSpczSearchRepository dSpczSearchRepository;

    public DSpczQueryService(DSpczRepository dSpczRepository, DSpczMapper dSpczMapper, DSpczSearchRepository dSpczSearchRepository) {
        this.dSpczRepository = dSpczRepository;
        this.dSpczMapper = dSpczMapper;
        this.dSpczSearchRepository = dSpczSearchRepository;
    }

    /**
     * Return a {@link List} of {@link DSpczDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<DSpczDTO> findByCriteria(DSpczCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<DSpcz> specification = createSpecification(criteria);
        return dSpczMapper.toDto(dSpczRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link DSpczDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<DSpczDTO> findByCriteria(DSpczCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<DSpcz> specification = createSpecification(criteria);
        return dSpczRepository.findAll(specification, page).map(dSpczMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(DSpczCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<DSpcz> specification = createSpecification(criteria);
        return dSpczRepository.count(specification);
    }

    /**
     * Function to convert {@link DSpczCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<DSpcz> createSpecification(DSpczCriteria criteria) {
        Specification<DSpcz> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), DSpcz_.id));
            }
            if (criteria.getRq() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getRq(), DSpcz_.rq));
            }
            if (criteria.getCzrq() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getCzrq(), DSpcz_.czrq));
            }
        }
        return specification;
    }
}
