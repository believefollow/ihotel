package ihotel.app.service;

import ihotel.app.domain.*; // for static metamodels
import ihotel.app.domain.CzCzl3;
import ihotel.app.repository.CzCzl3Repository;
import ihotel.app.repository.search.CzCzl3SearchRepository;
import ihotel.app.service.criteria.CzCzl3Criteria;
import ihotel.app.service.dto.CzCzl3DTO;
import ihotel.app.service.mapper.CzCzl3Mapper;
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
 * Service for executing complex queries for {@link CzCzl3} entities in the database.
 * The main input is a {@link CzCzl3Criteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link CzCzl3DTO} or a {@link Page} of {@link CzCzl3DTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class CzCzl3QueryService extends QueryService<CzCzl3> {

    private final Logger log = LoggerFactory.getLogger(CzCzl3QueryService.class);

    private final CzCzl3Repository czCzl3Repository;

    private final CzCzl3Mapper czCzl3Mapper;

    private final CzCzl3SearchRepository czCzl3SearchRepository;

    public CzCzl3QueryService(CzCzl3Repository czCzl3Repository, CzCzl3Mapper czCzl3Mapper, CzCzl3SearchRepository czCzl3SearchRepository) {
        this.czCzl3Repository = czCzl3Repository;
        this.czCzl3Mapper = czCzl3Mapper;
        this.czCzl3SearchRepository = czCzl3SearchRepository;
    }

    /**
     * Return a {@link List} of {@link CzCzl3DTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<CzCzl3DTO> findByCriteria(CzCzl3Criteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<CzCzl3> specification = createSpecification(criteria);
        return czCzl3Mapper.toDto(czCzl3Repository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link CzCzl3DTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<CzCzl3DTO> findByCriteria(CzCzl3Criteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<CzCzl3> specification = createSpecification(criteria);
        return czCzl3Repository.findAll(specification, page).map(czCzl3Mapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(CzCzl3Criteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<CzCzl3> specification = createSpecification(criteria);
        return czCzl3Repository.count(specification);
    }

    /**
     * Function to convert {@link CzCzl3Criteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<CzCzl3> createSpecification(CzCzl3Criteria criteria) {
        Specification<CzCzl3> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), CzCzl3_.id));
            }
            if (criteria.getZfs() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getZfs(), CzCzl3_.zfs));
            }
            if (criteria.getKfs() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getKfs(), CzCzl3_.kfs));
            }
            if (criteria.getProtocoln() != null) {
                specification = specification.and(buildStringSpecification(criteria.getProtocoln(), CzCzl3_.protocoln));
            }
            if (criteria.getRoomtype() != null) {
                specification = specification.and(buildStringSpecification(criteria.getRoomtype(), CzCzl3_.roomtype));
            }
            if (criteria.getSl() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getSl(), CzCzl3_.sl));
            }
        }
        return specification;
    }
}
