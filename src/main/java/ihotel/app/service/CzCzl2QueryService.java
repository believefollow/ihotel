package ihotel.app.service;

import ihotel.app.domain.*; // for static metamodels
import ihotel.app.domain.CzCzl2;
import ihotel.app.repository.CzCzl2Repository;
import ihotel.app.repository.search.CzCzl2SearchRepository;
import ihotel.app.service.criteria.CzCzl2Criteria;
import ihotel.app.service.dto.CzCzl2DTO;
import ihotel.app.service.mapper.CzCzl2Mapper;
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
 * Service for executing complex queries for {@link CzCzl2} entities in the database.
 * The main input is a {@link CzCzl2Criteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link CzCzl2DTO} or a {@link Page} of {@link CzCzl2DTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class CzCzl2QueryService extends QueryService<CzCzl2> {

    private final Logger log = LoggerFactory.getLogger(CzCzl2QueryService.class);

    private final CzCzl2Repository czCzl2Repository;

    private final CzCzl2Mapper czCzl2Mapper;

    private final CzCzl2SearchRepository czCzl2SearchRepository;

    public CzCzl2QueryService(CzCzl2Repository czCzl2Repository, CzCzl2Mapper czCzl2Mapper, CzCzl2SearchRepository czCzl2SearchRepository) {
        this.czCzl2Repository = czCzl2Repository;
        this.czCzl2Mapper = czCzl2Mapper;
        this.czCzl2SearchRepository = czCzl2SearchRepository;
    }

    /**
     * Return a {@link List} of {@link CzCzl2DTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<CzCzl2DTO> findByCriteria(CzCzl2Criteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<CzCzl2> specification = createSpecification(criteria);
        return czCzl2Mapper.toDto(czCzl2Repository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link CzCzl2DTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<CzCzl2DTO> findByCriteria(CzCzl2Criteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<CzCzl2> specification = createSpecification(criteria);
        return czCzl2Repository.findAll(specification, page).map(czCzl2Mapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(CzCzl2Criteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<CzCzl2> specification = createSpecification(criteria);
        return czCzl2Repository.count(specification);
    }

    /**
     * Function to convert {@link CzCzl2Criteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<CzCzl2> createSpecification(CzCzl2Criteria criteria) {
        Specification<CzCzl2> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), CzCzl2_.id));
            }
            if (criteria.getDr() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getDr(), CzCzl2_.dr));
            }
            if (criteria.getType() != null) {
                specification = specification.and(buildStringSpecification(criteria.getType(), CzCzl2_.type));
            }
            if (criteria.getFs() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getFs(), CzCzl2_.fs));
            }
            if (criteria.getKfl() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getKfl(), CzCzl2_.kfl));
            }
            if (criteria.getFzsr() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getFzsr(), CzCzl2_.fzsr));
            }
            if (criteria.getPjz() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getPjz(), CzCzl2_.pjz));
            }
            if (criteria.getFsM() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getFsM(), CzCzl2_.fsM));
            }
            if (criteria.getKflM() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getKflM(), CzCzl2_.kflM));
            }
            if (criteria.getFzsrM() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getFzsrM(), CzCzl2_.fzsrM));
            }
            if (criteria.getPjzM() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getPjzM(), CzCzl2_.pjzM));
            }
            if (criteria.getFsY() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getFsY(), CzCzl2_.fsY));
            }
            if (criteria.getKflY() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getKflY(), CzCzl2_.kflY));
            }
            if (criteria.getFzsrY() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getFzsrY(), CzCzl2_.fzsrY));
            }
            if (criteria.getPjzY() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getPjzY(), CzCzl2_.pjzY));
            }
            if (criteria.getFsQ() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getFsQ(), CzCzl2_.fsQ));
            }
            if (criteria.getKflQ() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getKflQ(), CzCzl2_.kflQ));
            }
            if (criteria.getFzsrQ() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getFzsrQ(), CzCzl2_.fzsrQ));
            }
            if (criteria.getPjzQ() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getPjzQ(), CzCzl2_.pjzQ));
            }
            if (criteria.getDateY() != null) {
                specification = specification.and(buildStringSpecification(criteria.getDateY(), CzCzl2_.dateY));
            }
            if (criteria.getDqdate() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getDqdate(), CzCzl2_.dqdate));
            }
            if (criteria.getEmpn() != null) {
                specification = specification.and(buildStringSpecification(criteria.getEmpn(), CzCzl2_.empn));
            }
            if (criteria.getNumber() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getNumber(), CzCzl2_.number));
            }
            if (criteria.getNumberM() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getNumberM(), CzCzl2_.numberM));
            }
            if (criteria.getNumberY() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getNumberY(), CzCzl2_.numberY));
            }
            if (criteria.getHoteldm() != null) {
                specification = specification.and(buildStringSpecification(criteria.getHoteldm(), CzCzl2_.hoteldm));
            }
            if (criteria.getIsnew() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getIsnew(), CzCzl2_.isnew));
            }
        }
        return specification;
    }
}
