package ihotel.app.service;

import ihotel.app.domain.*; // for static metamodels
import ihotel.app.domain.FkCz;
import ihotel.app.repository.FkCzRepository;
import ihotel.app.repository.search.FkCzSearchRepository;
import ihotel.app.service.criteria.FkCzCriteria;
import ihotel.app.service.dto.FkCzDTO;
import ihotel.app.service.mapper.FkCzMapper;
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
 * Service for executing complex queries for {@link FkCz} entities in the database.
 * The main input is a {@link FkCzCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link FkCzDTO} or a {@link Page} of {@link FkCzDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class FkCzQueryService extends QueryService<FkCz> {

    private final Logger log = LoggerFactory.getLogger(FkCzQueryService.class);

    private final FkCzRepository fkCzRepository;

    private final FkCzMapper fkCzMapper;

    private final FkCzSearchRepository fkCzSearchRepository;

    public FkCzQueryService(FkCzRepository fkCzRepository, FkCzMapper fkCzMapper, FkCzSearchRepository fkCzSearchRepository) {
        this.fkCzRepository = fkCzRepository;
        this.fkCzMapper = fkCzMapper;
        this.fkCzSearchRepository = fkCzSearchRepository;
    }

    /**
     * Return a {@link List} of {@link FkCzDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<FkCzDTO> findByCriteria(FkCzCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<FkCz> specification = createSpecification(criteria);
        return fkCzMapper.toDto(fkCzRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link FkCzDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<FkCzDTO> findByCriteria(FkCzCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<FkCz> specification = createSpecification(criteria);
        return fkCzRepository.findAll(specification, page).map(fkCzMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(FkCzCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<FkCz> specification = createSpecification(criteria);
        return fkCzRepository.count(specification);
    }

    /**
     * Function to convert {@link FkCzCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<FkCz> createSpecification(FkCzCriteria criteria) {
        Specification<FkCz> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), FkCz_.id));
            }
            if (criteria.getHoteltime() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getHoteltime(), FkCz_.hoteltime));
            }
            if (criteria.getWxf() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getWxf(), FkCz_.wxf));
            }
            if (criteria.getKsf() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getKsf(), FkCz_.ksf));
            }
            if (criteria.getKf() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getKf(), FkCz_.kf));
            }
            if (criteria.getZfs() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getZfs(), FkCz_.zfs));
            }
            if (criteria.getGroupyd() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getGroupyd(), FkCz_.groupyd));
            }
            if (criteria.getSkyd() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getSkyd(), FkCz_.skyd));
            }
            if (criteria.getYdwd() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getYdwd(), FkCz_.ydwd));
            }
            if (criteria.getQxyd() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getQxyd(), FkCz_.qxyd));
            }
            if (criteria.getIsnew() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getIsnew(), FkCz_.isnew));
            }
            if (criteria.getHoteldm() != null) {
                specification = specification.and(buildStringSpecification(criteria.getHoteldm(), FkCz_.hoteldm));
            }
            if (criteria.getHys() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getHys(), FkCz_.hys));
            }
            if (criteria.getKhys() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getKhys(), FkCz_.khys));
            }
        }
        return specification;
    }
}
