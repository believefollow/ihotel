package ihotel.app.service;

import ihotel.app.domain.*; // for static metamodels
import ihotel.app.domain.Ee;
import ihotel.app.repository.EeRepository;
import ihotel.app.repository.search.EeSearchRepository;
import ihotel.app.service.criteria.EeCriteria;
import ihotel.app.service.dto.EeDTO;
import ihotel.app.service.mapper.EeMapper;
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
 * Service for executing complex queries for {@link Ee} entities in the database.
 * The main input is a {@link EeCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link EeDTO} or a {@link Page} of {@link EeDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class EeQueryService extends QueryService<Ee> {

    private final Logger log = LoggerFactory.getLogger(EeQueryService.class);

    private final EeRepository eeRepository;

    private final EeMapper eeMapper;

    private final EeSearchRepository eeSearchRepository;

    public EeQueryService(EeRepository eeRepository, EeMapper eeMapper, EeSearchRepository eeSearchRepository) {
        this.eeRepository = eeRepository;
        this.eeMapper = eeMapper;
        this.eeSearchRepository = eeSearchRepository;
    }

    /**
     * Return a {@link List} of {@link EeDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<EeDTO> findByCriteria(EeCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<Ee> specification = createSpecification(criteria);
        return eeMapper.toDto(eeRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link EeDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<EeDTO> findByCriteria(EeCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<Ee> specification = createSpecification(criteria);
        return eeRepository.findAll(specification, page).map(eeMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(EeCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<Ee> specification = createSpecification(criteria);
        return eeRepository.count(specification);
    }

    /**
     * Function to convert {@link EeCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<Ee> createSpecification(EeCriteria criteria) {
        Specification<Ee> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), Ee_.id));
            }
            if (criteria.getAcc() != null) {
                specification = specification.and(buildStringSpecification(criteria.getAcc(), Ee_.acc));
            }
        }
        return specification;
    }
}
