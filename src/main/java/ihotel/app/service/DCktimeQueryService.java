package ihotel.app.service;

import ihotel.app.domain.*; // for static metamodels
import ihotel.app.domain.DCktime;
import ihotel.app.repository.DCktimeRepository;
import ihotel.app.repository.search.DCktimeSearchRepository;
import ihotel.app.service.criteria.DCktimeCriteria;
import ihotel.app.service.dto.DCktimeDTO;
import ihotel.app.service.mapper.DCktimeMapper;
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
 * Service for executing complex queries for {@link DCktime} entities in the database.
 * The main input is a {@link DCktimeCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link DCktimeDTO} or a {@link Page} of {@link DCktimeDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class DCktimeQueryService extends QueryService<DCktime> {

    private final Logger log = LoggerFactory.getLogger(DCktimeQueryService.class);

    private final DCktimeRepository dCktimeRepository;

    private final DCktimeMapper dCktimeMapper;

    private final DCktimeSearchRepository dCktimeSearchRepository;

    public DCktimeQueryService(
        DCktimeRepository dCktimeRepository,
        DCktimeMapper dCktimeMapper,
        DCktimeSearchRepository dCktimeSearchRepository
    ) {
        this.dCktimeRepository = dCktimeRepository;
        this.dCktimeMapper = dCktimeMapper;
        this.dCktimeSearchRepository = dCktimeSearchRepository;
    }

    /**
     * Return a {@link List} of {@link DCktimeDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<DCktimeDTO> findByCriteria(DCktimeCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<DCktime> specification = createSpecification(criteria);
        return dCktimeMapper.toDto(dCktimeRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link DCktimeDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<DCktimeDTO> findByCriteria(DCktimeCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<DCktime> specification = createSpecification(criteria);
        return dCktimeRepository.findAll(specification, page).map(dCktimeMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(DCktimeCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<DCktime> specification = createSpecification(criteria);
        return dCktimeRepository.count(specification);
    }

    /**
     * Function to convert {@link DCktimeCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<DCktime> createSpecification(DCktimeCriteria criteria) {
        Specification<DCktime> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), DCktime_.id));
            }
            if (criteria.getBegintime() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getBegintime(), DCktime_.begintime));
            }
            if (criteria.getEndtime() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getEndtime(), DCktime_.endtime));
            }
            if (criteria.getDepot() != null) {
                specification = specification.and(buildStringSpecification(criteria.getDepot(), DCktime_.depot));
            }
            if (criteria.getCkbillno() != null) {
                specification = specification.and(buildStringSpecification(criteria.getCkbillno(), DCktime_.ckbillno));
            }
        }
        return specification;
    }
}
