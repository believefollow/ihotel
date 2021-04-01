package ihotel.app.service;

import ihotel.app.domain.*; // for static metamodels
import ihotel.app.domain.CheckCzl;
import ihotel.app.repository.CheckCzlRepository;
import ihotel.app.repository.search.CheckCzlSearchRepository;
import ihotel.app.service.criteria.CheckCzlCriteria;
import ihotel.app.service.dto.CheckCzlDTO;
import ihotel.app.service.mapper.CheckCzlMapper;
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
 * Service for executing complex queries for {@link CheckCzl} entities in the database.
 * The main input is a {@link CheckCzlCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link CheckCzlDTO} or a {@link Page} of {@link CheckCzlDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class CheckCzlQueryService extends QueryService<CheckCzl> {

    private final Logger log = LoggerFactory.getLogger(CheckCzlQueryService.class);

    private final CheckCzlRepository checkCzlRepository;

    private final CheckCzlMapper checkCzlMapper;

    private final CheckCzlSearchRepository checkCzlSearchRepository;

    public CheckCzlQueryService(
        CheckCzlRepository checkCzlRepository,
        CheckCzlMapper checkCzlMapper,
        CheckCzlSearchRepository checkCzlSearchRepository
    ) {
        this.checkCzlRepository = checkCzlRepository;
        this.checkCzlMapper = checkCzlMapper;
        this.checkCzlSearchRepository = checkCzlSearchRepository;
    }

    /**
     * Return a {@link List} of {@link CheckCzlDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<CheckCzlDTO> findByCriteria(CheckCzlCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<CheckCzl> specification = createSpecification(criteria);
        return checkCzlMapper.toDto(checkCzlRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link CheckCzlDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<CheckCzlDTO> findByCriteria(CheckCzlCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<CheckCzl> specification = createSpecification(criteria);
        return checkCzlRepository.findAll(specification, page).map(checkCzlMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(CheckCzlCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<CheckCzl> specification = createSpecification(criteria);
        return checkCzlRepository.count(specification);
    }

    /**
     * Function to convert {@link CheckCzlCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<CheckCzl> createSpecification(CheckCzlCriteria criteria) {
        Specification<CheckCzl> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), CheckCzl_.id));
            }
            if (criteria.getHoteltime() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getHoteltime(), CheckCzl_.hoteltime));
            }
            if (criteria.getRtype() != null) {
                specification = specification.and(buildStringSpecification(criteria.getRtype(), CheckCzl_.rtype));
            }
            if (criteria.getRnum() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getRnum(), CheckCzl_.rnum));
            }
            if (criteria.getrOutnum() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getrOutnum(), CheckCzl_.rOutnum));
            }
            if (criteria.getCzl() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getCzl(), CheckCzl_.czl));
            }
            if (criteria.getChagrge() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getChagrge(), CheckCzl_.chagrge));
            }
            if (criteria.getChagrgeAvg() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getChagrgeAvg(), CheckCzl_.chagrgeAvg));
            }
            if (criteria.getEmpn() != null) {
                specification = specification.and(buildStringSpecification(criteria.getEmpn(), CheckCzl_.empn));
            }
            if (criteria.getEntertime() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getEntertime(), CheckCzl_.entertime));
            }
        }
        return specification;
    }
}
