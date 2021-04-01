package ihotel.app.service;

import ihotel.app.domain.*; // for static metamodels
import ihotel.app.domain.Crinfo;
import ihotel.app.repository.CrinfoRepository;
import ihotel.app.repository.search.CrinfoSearchRepository;
import ihotel.app.service.criteria.CrinfoCriteria;
import ihotel.app.service.dto.CrinfoDTO;
import ihotel.app.service.mapper.CrinfoMapper;
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
 * Service for executing complex queries for {@link Crinfo} entities in the database.
 * The main input is a {@link CrinfoCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link CrinfoDTO} or a {@link Page} of {@link CrinfoDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class CrinfoQueryService extends QueryService<Crinfo> {

    private final Logger log = LoggerFactory.getLogger(CrinfoQueryService.class);

    private final CrinfoRepository crinfoRepository;

    private final CrinfoMapper crinfoMapper;

    private final CrinfoSearchRepository crinfoSearchRepository;

    public CrinfoQueryService(CrinfoRepository crinfoRepository, CrinfoMapper crinfoMapper, CrinfoSearchRepository crinfoSearchRepository) {
        this.crinfoRepository = crinfoRepository;
        this.crinfoMapper = crinfoMapper;
        this.crinfoSearchRepository = crinfoSearchRepository;
    }

    /**
     * Return a {@link List} of {@link CrinfoDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<CrinfoDTO> findByCriteria(CrinfoCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<Crinfo> specification = createSpecification(criteria);
        return crinfoMapper.toDto(crinfoRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link CrinfoDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<CrinfoDTO> findByCriteria(CrinfoCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<Crinfo> specification = createSpecification(criteria);
        return crinfoRepository.findAll(specification, page).map(crinfoMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(CrinfoCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<Crinfo> specification = createSpecification(criteria);
        return crinfoRepository.count(specification);
    }

    /**
     * Function to convert {@link CrinfoCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<Crinfo> createSpecification(CrinfoCriteria criteria) {
        Specification<Crinfo> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), Crinfo_.id));
            }
            if (criteria.getOperatetime() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getOperatetime(), Crinfo_.operatetime));
            }
            if (criteria.getOldrent() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getOldrent(), Crinfo_.oldrent));
            }
            if (criteria.getNewrent() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getNewrent(), Crinfo_.newrent));
            }
            if (criteria.getOldroomn() != null) {
                specification = specification.and(buildStringSpecification(criteria.getOldroomn(), Crinfo_.oldroomn));
            }
            if (criteria.getNewroomn() != null) {
                specification = specification.and(buildStringSpecification(criteria.getNewroomn(), Crinfo_.newroomn));
            }
            if (criteria.getAccount() != null) {
                specification = specification.and(buildStringSpecification(criteria.getAccount(), Crinfo_.account));
            }
            if (criteria.getEmpn() != null) {
                specification = specification.and(buildStringSpecification(criteria.getEmpn(), Crinfo_.empn));
            }
            if (criteria.getOldday() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getOldday(), Crinfo_.oldday));
            }
            if (criteria.getNewday() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getNewday(), Crinfo_.newday));
            }
            if (criteria.getHoteltime() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getHoteltime(), Crinfo_.hoteltime));
            }
            if (criteria.getRoomn() != null) {
                specification = specification.and(buildStringSpecification(criteria.getRoomn(), Crinfo_.roomn));
            }
            if (criteria.getMemo() != null) {
                specification = specification.and(buildStringSpecification(criteria.getMemo(), Crinfo_.memo));
            }
            if (criteria.getRealname() != null) {
                specification = specification.and(buildStringSpecification(criteria.getRealname(), Crinfo_.realname));
            }
            if (criteria.getIsup() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getIsup(), Crinfo_.isup));
            }
        }
        return specification;
    }
}
