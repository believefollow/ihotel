package ihotel.app.service;

import ihotel.app.domain.*; // for static metamodels
import ihotel.app.domain.CzBqz;
import ihotel.app.repository.CzBqzRepository;
import ihotel.app.repository.search.CzBqzSearchRepository;
import ihotel.app.service.criteria.CzBqzCriteria;
import ihotel.app.service.dto.CzBqzDTO;
import ihotel.app.service.mapper.CzBqzMapper;
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
 * Service for executing complex queries for {@link CzBqz} entities in the database.
 * The main input is a {@link CzBqzCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link CzBqzDTO} or a {@link Page} of {@link CzBqzDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class CzBqzQueryService extends QueryService<CzBqz> {

    private final Logger log = LoggerFactory.getLogger(CzBqzQueryService.class);

    private final CzBqzRepository czBqzRepository;

    private final CzBqzMapper czBqzMapper;

    private final CzBqzSearchRepository czBqzSearchRepository;

    public CzBqzQueryService(CzBqzRepository czBqzRepository, CzBqzMapper czBqzMapper, CzBqzSearchRepository czBqzSearchRepository) {
        this.czBqzRepository = czBqzRepository;
        this.czBqzMapper = czBqzMapper;
        this.czBqzSearchRepository = czBqzSearchRepository;
    }

    /**
     * Return a {@link List} of {@link CzBqzDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<CzBqzDTO> findByCriteria(CzBqzCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<CzBqz> specification = createSpecification(criteria);
        return czBqzMapper.toDto(czBqzRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link CzBqzDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<CzBqzDTO> findByCriteria(CzBqzCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<CzBqz> specification = createSpecification(criteria);
        return czBqzRepository.findAll(specification, page).map(czBqzMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(CzBqzCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<CzBqz> specification = createSpecification(criteria);
        return czBqzRepository.count(specification);
    }

    /**
     * Function to convert {@link CzBqzCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<CzBqz> createSpecification(CzBqzCriteria criteria) {
        Specification<CzBqz> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), CzBqz_.id));
            }
            if (criteria.getRq() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getRq(), CzBqz_.rq));
            }
            if (criteria.getqSl() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getqSl(), CzBqz_.qSl));
            }
            if (criteria.getqKfl() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getqKfl(), CzBqz_.qKfl));
            }
            if (criteria.getqPjz() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getqPjz(), CzBqz_.qPjz));
            }
            if (criteria.getqYsfz() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getqYsfz(), CzBqz_.qYsfz));
            }
            if (criteria.getqSjfz() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getqSjfz(), CzBqz_.qSjfz));
            }
            if (criteria.getqFzcz() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getqFzcz(), CzBqz_.qFzcz));
            }
            if (criteria.getqPjzcz() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getqPjzcz(), CzBqz_.qPjzcz));
            }
            if (criteria.getbSl() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getbSl(), CzBqz_.bSl));
            }
            if (criteria.getbKfl() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getbKfl(), CzBqz_.bKfl));
            }
            if (criteria.getbPjz() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getbPjz(), CzBqz_.bPjz));
            }
            if (criteria.getbYsfz() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getbYsfz(), CzBqz_.bYsfz));
            }
            if (criteria.getbSjfz() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getbSjfz(), CzBqz_.bSjfz));
            }
            if (criteria.getbFzcz() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getbFzcz(), CzBqz_.bFzcz));
            }
            if (criteria.getbPjzcz() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getbPjzcz(), CzBqz_.bPjzcz));
            }
            if (criteria.getzSl() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getzSl(), CzBqz_.zSl));
            }
            if (criteria.getzKfl() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getzKfl(), CzBqz_.zKfl));
            }
            if (criteria.getzPjz() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getzPjz(), CzBqz_.zPjz));
            }
            if (criteria.getzYsfz() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getzYsfz(), CzBqz_.zYsfz));
            }
            if (criteria.getzSjfz() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getzSjfz(), CzBqz_.zSjfz));
            }
            if (criteria.getzFzcz() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getzFzcz(), CzBqz_.zFzcz));
            }
            if (criteria.getzPjzcz() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getzPjzcz(), CzBqz_.zPjzcz));
            }
            if (criteria.getZk() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getZk(), CzBqz_.zk));
            }
        }
        return specification;
    }
}
