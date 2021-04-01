package ihotel.app.service;

import ihotel.app.domain.*; // for static metamodels
import ihotel.app.domain.DDepot;
import ihotel.app.repository.DDepotRepository;
import ihotel.app.repository.search.DDepotSearchRepository;
import ihotel.app.service.criteria.DDepotCriteria;
import ihotel.app.service.dto.DDepotDTO;
import ihotel.app.service.mapper.DDepotMapper;
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
 * Service for executing complex queries for {@link DDepot} entities in the database.
 * The main input is a {@link DDepotCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link DDepotDTO} or a {@link Page} of {@link DDepotDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class DDepotQueryService extends QueryService<DDepot> {

    private final Logger log = LoggerFactory.getLogger(DDepotQueryService.class);

    private final DDepotRepository dDepotRepository;

    private final DDepotMapper dDepotMapper;

    private final DDepotSearchRepository dDepotSearchRepository;

    public DDepotQueryService(DDepotRepository dDepotRepository, DDepotMapper dDepotMapper, DDepotSearchRepository dDepotSearchRepository) {
        this.dDepotRepository = dDepotRepository;
        this.dDepotMapper = dDepotMapper;
        this.dDepotSearchRepository = dDepotSearchRepository;
    }

    /**
     * Return a {@link List} of {@link DDepotDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<DDepotDTO> findByCriteria(DDepotCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<DDepot> specification = createSpecification(criteria);
        return dDepotMapper.toDto(dDepotRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link DDepotDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<DDepotDTO> findByCriteria(DDepotCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<DDepot> specification = createSpecification(criteria);
        return dDepotRepository.findAll(specification, page).map(dDepotMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(DDepotCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<DDepot> specification = createSpecification(criteria);
        return dDepotRepository.count(specification);
    }

    /**
     * Function to convert {@link DDepotCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<DDepot> createSpecification(DDepotCriteria criteria) {
        Specification<DDepot> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), DDepot_.id));
            }
            if (criteria.getDepotid() != null) {
                specification = specification.and(buildSpecification(criteria.getDepotid(), DDepot_.depotid));
            }
            if (criteria.getDepot() != null) {
                specification = specification.and(buildStringSpecification(criteria.getDepot(), DDepot_.depot));
            }
        }
        return specification;
    }
}
