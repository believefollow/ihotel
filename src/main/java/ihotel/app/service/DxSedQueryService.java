package ihotel.app.service;

import ihotel.app.domain.*; // for static metamodels
import ihotel.app.domain.DxSed;
import ihotel.app.repository.DxSedRepository;
import ihotel.app.repository.search.DxSedSearchRepository;
import ihotel.app.service.criteria.DxSedCriteria;
import ihotel.app.service.dto.DxSedDTO;
import ihotel.app.service.mapper.DxSedMapper;
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
 * Service for executing complex queries for {@link DxSed} entities in the database.
 * The main input is a {@link DxSedCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link DxSedDTO} or a {@link Page} of {@link DxSedDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class DxSedQueryService extends QueryService<DxSed> {

    private final Logger log = LoggerFactory.getLogger(DxSedQueryService.class);

    private final DxSedRepository dxSedRepository;

    private final DxSedMapper dxSedMapper;

    private final DxSedSearchRepository dxSedSearchRepository;

    public DxSedQueryService(DxSedRepository dxSedRepository, DxSedMapper dxSedMapper, DxSedSearchRepository dxSedSearchRepository) {
        this.dxSedRepository = dxSedRepository;
        this.dxSedMapper = dxSedMapper;
        this.dxSedSearchRepository = dxSedSearchRepository;
    }

    /**
     * Return a {@link List} of {@link DxSedDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<DxSedDTO> findByCriteria(DxSedCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<DxSed> specification = createSpecification(criteria);
        return dxSedMapper.toDto(dxSedRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link DxSedDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<DxSedDTO> findByCriteria(DxSedCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<DxSed> specification = createSpecification(criteria);
        return dxSedRepository.findAll(specification, page).map(dxSedMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(DxSedCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<DxSed> specification = createSpecification(criteria);
        return dxSedRepository.count(specification);
    }

    /**
     * Function to convert {@link DxSedCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<DxSed> createSpecification(DxSedCriteria criteria) {
        Specification<DxSed> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), DxSed_.id));
            }
            if (criteria.getDxRq() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getDxRq(), DxSed_.dxRq));
            }
            if (criteria.getDxZt() != null) {
                specification = specification.and(buildStringSpecification(criteria.getDxZt(), DxSed_.dxZt));
            }
            if (criteria.getFsSj() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getFsSj(), DxSed_.fsSj));
            }
        }
        return specification;
    }
}
