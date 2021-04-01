package ihotel.app.service;

import ihotel.app.domain.*; // for static metamodels
import ihotel.app.domain.Acc;
import ihotel.app.repository.AccRepository;
import ihotel.app.repository.search.AccSearchRepository;
import ihotel.app.service.criteria.AccCriteria;
import ihotel.app.service.dto.AccDTO;
import ihotel.app.service.mapper.AccMapper;
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
 * Service for executing complex queries for {@link Acc} entities in the database.
 * The main input is a {@link AccCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link AccDTO} or a {@link Page} of {@link AccDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class AccQueryService extends QueryService<Acc> {

    private final Logger log = LoggerFactory.getLogger(AccQueryService.class);

    private final AccRepository accRepository;

    private final AccMapper accMapper;

    private final AccSearchRepository accSearchRepository;

    public AccQueryService(AccRepository accRepository, AccMapper accMapper, AccSearchRepository accSearchRepository) {
        this.accRepository = accRepository;
        this.accMapper = accMapper;
        this.accSearchRepository = accSearchRepository;
    }

    /**
     * Return a {@link List} of {@link AccDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<AccDTO> findByCriteria(AccCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<Acc> specification = createSpecification(criteria);
        return accMapper.toDto(accRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link AccDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<AccDTO> findByCriteria(AccCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<Acc> specification = createSpecification(criteria);
        return accRepository.findAll(specification, page).map(accMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(AccCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<Acc> specification = createSpecification(criteria);
        return accRepository.count(specification);
    }

    /**
     * Function to convert {@link AccCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<Acc> createSpecification(AccCriteria criteria) {
        Specification<Acc> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), Acc_.id));
            }
            if (criteria.getAcc() != null) {
                specification = specification.and(buildStringSpecification(criteria.getAcc(), Acc_.acc));
            }
        }
        return specification;
    }
}
