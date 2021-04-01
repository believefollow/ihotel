package ihotel.app.service;

import ihotel.app.domain.*; // for static metamodels
import ihotel.app.domain.FwDs;
import ihotel.app.repository.FwDsRepository;
import ihotel.app.repository.search.FwDsSearchRepository;
import ihotel.app.service.criteria.FwDsCriteria;
import ihotel.app.service.dto.FwDsDTO;
import ihotel.app.service.mapper.FwDsMapper;
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
 * Service for executing complex queries for {@link FwDs} entities in the database.
 * The main input is a {@link FwDsCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link FwDsDTO} or a {@link Page} of {@link FwDsDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class FwDsQueryService extends QueryService<FwDs> {

    private final Logger log = LoggerFactory.getLogger(FwDsQueryService.class);

    private final FwDsRepository fwDsRepository;

    private final FwDsMapper fwDsMapper;

    private final FwDsSearchRepository fwDsSearchRepository;

    public FwDsQueryService(FwDsRepository fwDsRepository, FwDsMapper fwDsMapper, FwDsSearchRepository fwDsSearchRepository) {
        this.fwDsRepository = fwDsRepository;
        this.fwDsMapper = fwDsMapper;
        this.fwDsSearchRepository = fwDsSearchRepository;
    }

    /**
     * Return a {@link List} of {@link FwDsDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<FwDsDTO> findByCriteria(FwDsCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<FwDs> specification = createSpecification(criteria);
        return fwDsMapper.toDto(fwDsRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link FwDsDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<FwDsDTO> findByCriteria(FwDsCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<FwDs> specification = createSpecification(criteria);
        return fwDsRepository.findAll(specification, page).map(fwDsMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(FwDsCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<FwDs> specification = createSpecification(criteria);
        return fwDsRepository.count(specification);
    }

    /**
     * Function to convert {@link FwDsCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<FwDs> createSpecification(FwDsCriteria criteria) {
        Specification<FwDs> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), FwDs_.id));
            }
            if (criteria.getHoteltime() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getHoteltime(), FwDs_.hoteltime));
            }
            if (criteria.getRq() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getRq(), FwDs_.rq));
            }
            if (criteria.getXz() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getXz(), FwDs_.xz));
            }
            if (criteria.getMemo() != null) {
                specification = specification.and(buildStringSpecification(criteria.getMemo(), FwDs_.memo));
            }
            if (criteria.getFwy() != null) {
                specification = specification.and(buildStringSpecification(criteria.getFwy(), FwDs_.fwy));
            }
            if (criteria.getRoomn() != null) {
                specification = specification.and(buildStringSpecification(criteria.getRoomn(), FwDs_.roomn));
            }
            if (criteria.getRtype() != null) {
                specification = specification.and(buildStringSpecification(criteria.getRtype(), FwDs_.rtype));
            }
            if (criteria.getEmpn() != null) {
                specification = specification.and(buildStringSpecification(criteria.getEmpn(), FwDs_.empn));
            }
            if (criteria.getSl() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getSl(), FwDs_.sl));
            }
        }
        return specification;
    }
}
