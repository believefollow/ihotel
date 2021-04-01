package ihotel.app.service;

import ihotel.app.domain.*; // for static metamodels
import ihotel.app.domain.DCktype;
import ihotel.app.repository.DCktypeRepository;
import ihotel.app.repository.search.DCktypeSearchRepository;
import ihotel.app.service.criteria.DCktypeCriteria;
import ihotel.app.service.dto.DCktypeDTO;
import ihotel.app.service.mapper.DCktypeMapper;
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
 * Service for executing complex queries for {@link DCktype} entities in the database.
 * The main input is a {@link DCktypeCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link DCktypeDTO} or a {@link Page} of {@link DCktypeDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class DCktypeQueryService extends QueryService<DCktype> {

    private final Logger log = LoggerFactory.getLogger(DCktypeQueryService.class);

    private final DCktypeRepository dCktypeRepository;

    private final DCktypeMapper dCktypeMapper;

    private final DCktypeSearchRepository dCktypeSearchRepository;

    public DCktypeQueryService(
        DCktypeRepository dCktypeRepository,
        DCktypeMapper dCktypeMapper,
        DCktypeSearchRepository dCktypeSearchRepository
    ) {
        this.dCktypeRepository = dCktypeRepository;
        this.dCktypeMapper = dCktypeMapper;
        this.dCktypeSearchRepository = dCktypeSearchRepository;
    }

    /**
     * Return a {@link List} of {@link DCktypeDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<DCktypeDTO> findByCriteria(DCktypeCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<DCktype> specification = createSpecification(criteria);
        return dCktypeMapper.toDto(dCktypeRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link DCktypeDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<DCktypeDTO> findByCriteria(DCktypeCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<DCktype> specification = createSpecification(criteria);
        return dCktypeRepository.findAll(specification, page).map(dCktypeMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(DCktypeCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<DCktype> specification = createSpecification(criteria);
        return dCktypeRepository.count(specification);
    }

    /**
     * Function to convert {@link DCktypeCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<DCktype> createSpecification(DCktypeCriteria criteria) {
        Specification<DCktype> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), DCktype_.id));
            }
            if (criteria.getCktype() != null) {
                specification = specification.and(buildStringSpecification(criteria.getCktype(), DCktype_.cktype));
            }
            if (criteria.getMemo() != null) {
                specification = specification.and(buildStringSpecification(criteria.getMemo(), DCktype_.memo));
            }
            if (criteria.getSign() != null) {
                specification = specification.and(buildStringSpecification(criteria.getSign(), DCktype_.sign));
            }
        }
        return specification;
    }
}
