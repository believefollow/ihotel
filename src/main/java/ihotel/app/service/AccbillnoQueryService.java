package ihotel.app.service;

import ihotel.app.domain.*; // for static metamodels
import ihotel.app.domain.Accbillno;
import ihotel.app.repository.AccbillnoRepository;
import ihotel.app.repository.search.AccbillnoSearchRepository;
import ihotel.app.service.criteria.AccbillnoCriteria;
import ihotel.app.service.dto.AccbillnoDTO;
import ihotel.app.service.mapper.AccbillnoMapper;
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
 * Service for executing complex queries for {@link Accbillno} entities in the database.
 * The main input is a {@link AccbillnoCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link AccbillnoDTO} or a {@link Page} of {@link AccbillnoDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class AccbillnoQueryService extends QueryService<Accbillno> {

    private final Logger log = LoggerFactory.getLogger(AccbillnoQueryService.class);

    private final AccbillnoRepository accbillnoRepository;

    private final AccbillnoMapper accbillnoMapper;

    private final AccbillnoSearchRepository accbillnoSearchRepository;

    public AccbillnoQueryService(
        AccbillnoRepository accbillnoRepository,
        AccbillnoMapper accbillnoMapper,
        AccbillnoSearchRepository accbillnoSearchRepository
    ) {
        this.accbillnoRepository = accbillnoRepository;
        this.accbillnoMapper = accbillnoMapper;
        this.accbillnoSearchRepository = accbillnoSearchRepository;
    }

    /**
     * Return a {@link List} of {@link AccbillnoDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<AccbillnoDTO> findByCriteria(AccbillnoCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<Accbillno> specification = createSpecification(criteria);
        return accbillnoMapper.toDto(accbillnoRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link AccbillnoDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<AccbillnoDTO> findByCriteria(AccbillnoCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<Accbillno> specification = createSpecification(criteria);
        return accbillnoRepository.findAll(specification, page).map(accbillnoMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(AccbillnoCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<Accbillno> specification = createSpecification(criteria);
        return accbillnoRepository.count(specification);
    }

    /**
     * Function to convert {@link AccbillnoCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<Accbillno> createSpecification(AccbillnoCriteria criteria) {
        Specification<Accbillno> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), Accbillno_.id));
            }
            if (criteria.getAccount() != null) {
                specification = specification.and(buildStringSpecification(criteria.getAccount(), Accbillno_.account));
            }
            if (criteria.getAccbillno() != null) {
                specification = specification.and(buildStringSpecification(criteria.getAccbillno(), Accbillno_.accbillno));
            }
        }
        return specification;
    }
}
