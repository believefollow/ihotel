package ihotel.app.service;

import ihotel.app.domain.*; // for static metamodels
import ihotel.app.domain.CzlCz;
import ihotel.app.repository.CzlCzRepository;
import ihotel.app.repository.search.CzlCzSearchRepository;
import ihotel.app.service.criteria.CzlCzCriteria;
import ihotel.app.service.dto.CzlCzDTO;
import ihotel.app.service.mapper.CzlCzMapper;
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
 * Service for executing complex queries for {@link CzlCz} entities in the database.
 * The main input is a {@link CzlCzCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link CzlCzDTO} or a {@link Page} of {@link CzlCzDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class CzlCzQueryService extends QueryService<CzlCz> {

    private final Logger log = LoggerFactory.getLogger(CzlCzQueryService.class);

    private final CzlCzRepository czlCzRepository;

    private final CzlCzMapper czlCzMapper;

    private final CzlCzSearchRepository czlCzSearchRepository;

    public CzlCzQueryService(CzlCzRepository czlCzRepository, CzlCzMapper czlCzMapper, CzlCzSearchRepository czlCzSearchRepository) {
        this.czlCzRepository = czlCzRepository;
        this.czlCzMapper = czlCzMapper;
        this.czlCzSearchRepository = czlCzSearchRepository;
    }

    /**
     * Return a {@link List} of {@link CzlCzDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<CzlCzDTO> findByCriteria(CzlCzCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<CzlCz> specification = createSpecification(criteria);
        return czlCzMapper.toDto(czlCzRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link CzlCzDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<CzlCzDTO> findByCriteria(CzlCzCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<CzlCz> specification = createSpecification(criteria);
        return czlCzRepository.findAll(specification, page).map(czlCzMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(CzlCzCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<CzlCz> specification = createSpecification(criteria);
        return czlCzRepository.count(specification);
    }

    /**
     * Function to convert {@link CzlCzCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<CzlCz> createSpecification(CzlCzCriteria criteria) {
        Specification<CzlCz> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), CzlCz_.id));
            }
            if (criteria.getTjrq() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getTjrq(), CzlCz_.tjrq));
            }
            if (criteria.getTypeid() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getTypeid(), CzlCz_.typeid));
            }
            if (criteria.getType() != null) {
                specification = specification.and(buildStringSpecification(criteria.getType(), CzlCz_.type));
            }
            if (criteria.getFjsl() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getFjsl(), CzlCz_.fjsl));
            }
            if (criteria.getKfl() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getKfl(), CzlCz_.kfl));
            }
            if (criteria.getPjz() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getPjz(), CzlCz_.pjz));
            }
            if (criteria.getYsfz() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getYsfz(), CzlCz_.ysfz));
            }
            if (criteria.getSjfz() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getSjfz(), CzlCz_.sjfz));
            }
            if (criteria.getFzcz() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getFzcz(), CzlCz_.fzcz));
            }
            if (criteria.getPjzcj() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getPjzcj(), CzlCz_.pjzcj));
            }
            if (criteria.getKfsM() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getKfsM(), CzlCz_.kfsM));
            }
            if (criteria.getKflM() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getKflM(), CzlCz_.kflM));
            }
            if (criteria.getPjzM() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getPjzM(), CzlCz_.pjzM));
            }
            if (criteria.getFzsr() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getFzsr(), CzlCz_.fzsr));
            }
            if (criteria.getDayz() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getDayz(), CzlCz_.dayz));
            }
            if (criteria.getHoteltime() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getHoteltime(), CzlCz_.hoteltime));
            }
            if (criteria.getEmpn() != null) {
                specification = specification.and(buildStringSpecification(criteria.getEmpn(), CzlCz_.empn));
            }
            if (criteria.getMonthz() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getMonthz(), CzlCz_.monthz));
            }
            if (criteria.getHoteldm() != null) {
                specification = specification.and(buildStringSpecification(criteria.getHoteldm(), CzlCz_.hoteldm));
            }
            if (criteria.getIsnew() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getIsnew(), CzlCz_.isnew));
            }
        }
        return specification;
    }
}
