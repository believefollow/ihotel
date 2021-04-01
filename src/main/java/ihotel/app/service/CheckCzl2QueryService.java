package ihotel.app.service;

import ihotel.app.domain.*; // for static metamodels
import ihotel.app.domain.CheckCzl2;
import ihotel.app.repository.CheckCzl2Repository;
import ihotel.app.repository.search.CheckCzl2SearchRepository;
import ihotel.app.service.criteria.CheckCzl2Criteria;
import ihotel.app.service.dto.CheckCzl2DTO;
import ihotel.app.service.mapper.CheckCzl2Mapper;
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
 * Service for executing complex queries for {@link CheckCzl2} entities in the database.
 * The main input is a {@link CheckCzl2Criteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link CheckCzl2DTO} or a {@link Page} of {@link CheckCzl2DTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class CheckCzl2QueryService extends QueryService<CheckCzl2> {

    private final Logger log = LoggerFactory.getLogger(CheckCzl2QueryService.class);

    private final CheckCzl2Repository checkCzl2Repository;

    private final CheckCzl2Mapper checkCzl2Mapper;

    private final CheckCzl2SearchRepository checkCzl2SearchRepository;

    public CheckCzl2QueryService(
        CheckCzl2Repository checkCzl2Repository,
        CheckCzl2Mapper checkCzl2Mapper,
        CheckCzl2SearchRepository checkCzl2SearchRepository
    ) {
        this.checkCzl2Repository = checkCzl2Repository;
        this.checkCzl2Mapper = checkCzl2Mapper;
        this.checkCzl2SearchRepository = checkCzl2SearchRepository;
    }

    /**
     * Return a {@link List} of {@link CheckCzl2DTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<CheckCzl2DTO> findByCriteria(CheckCzl2Criteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<CheckCzl2> specification = createSpecification(criteria);
        return checkCzl2Mapper.toDto(checkCzl2Repository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link CheckCzl2DTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<CheckCzl2DTO> findByCriteria(CheckCzl2Criteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<CheckCzl2> specification = createSpecification(criteria);
        return checkCzl2Repository.findAll(specification, page).map(checkCzl2Mapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(CheckCzl2Criteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<CheckCzl2> specification = createSpecification(criteria);
        return checkCzl2Repository.count(specification);
    }

    /**
     * Function to convert {@link CheckCzl2Criteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<CheckCzl2> createSpecification(CheckCzl2Criteria criteria) {
        Specification<CheckCzl2> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), CheckCzl2_.id));
            }
            if (criteria.getHoteltime() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getHoteltime(), CheckCzl2_.hoteltime));
            }
            if (criteria.getProtocol() != null) {
                specification = specification.and(buildStringSpecification(criteria.getProtocol(), CheckCzl2_.protocol));
            }
            if (criteria.getRnum() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getRnum(), CheckCzl2_.rnum));
            }
            if (criteria.getCzl() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getCzl(), CheckCzl2_.czl));
            }
            if (criteria.getChagrge() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getChagrge(), CheckCzl2_.chagrge));
            }
            if (criteria.getChagrgeAvg() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getChagrgeAvg(), CheckCzl2_.chagrgeAvg));
            }
            if (criteria.getEmpn() != null) {
                specification = specification.and(buildStringSpecification(criteria.getEmpn(), CheckCzl2_.empn));
            }
            if (criteria.getEntertime() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getEntertime(), CheckCzl2_.entertime));
            }
        }
        return specification;
    }
}
