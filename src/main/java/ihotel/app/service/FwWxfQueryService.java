package ihotel.app.service;

import ihotel.app.domain.*; // for static metamodels
import ihotel.app.domain.FwWxf;
import ihotel.app.repository.FwWxfRepository;
import ihotel.app.repository.search.FwWxfSearchRepository;
import ihotel.app.service.criteria.FwWxfCriteria;
import ihotel.app.service.dto.FwWxfDTO;
import ihotel.app.service.mapper.FwWxfMapper;
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
 * Service for executing complex queries for {@link FwWxf} entities in the database.
 * The main input is a {@link FwWxfCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link FwWxfDTO} or a {@link Page} of {@link FwWxfDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class FwWxfQueryService extends QueryService<FwWxf> {

    private final Logger log = LoggerFactory.getLogger(FwWxfQueryService.class);

    private final FwWxfRepository fwWxfRepository;

    private final FwWxfMapper fwWxfMapper;

    private final FwWxfSearchRepository fwWxfSearchRepository;

    public FwWxfQueryService(FwWxfRepository fwWxfRepository, FwWxfMapper fwWxfMapper, FwWxfSearchRepository fwWxfSearchRepository) {
        this.fwWxfRepository = fwWxfRepository;
        this.fwWxfMapper = fwWxfMapper;
        this.fwWxfSearchRepository = fwWxfSearchRepository;
    }

    /**
     * Return a {@link List} of {@link FwWxfDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<FwWxfDTO> findByCriteria(FwWxfCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<FwWxf> specification = createSpecification(criteria);
        return fwWxfMapper.toDto(fwWxfRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link FwWxfDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<FwWxfDTO> findByCriteria(FwWxfCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<FwWxf> specification = createSpecification(criteria);
        return fwWxfRepository.findAll(specification, page).map(fwWxfMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(FwWxfCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<FwWxf> specification = createSpecification(criteria);
        return fwWxfRepository.count(specification);
    }

    /**
     * Function to convert {@link FwWxfCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<FwWxf> createSpecification(FwWxfCriteria criteria) {
        Specification<FwWxf> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), FwWxf_.id));
            }
            if (criteria.getRoomn() != null) {
                specification = specification.and(buildStringSpecification(criteria.getRoomn(), FwWxf_.roomn));
            }
            if (criteria.getMemo() != null) {
                specification = specification.and(buildStringSpecification(criteria.getMemo(), FwWxf_.memo));
            }
            if (criteria.getDjrq() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getDjrq(), FwWxf_.djrq));
            }
            if (criteria.getWxr() != null) {
                specification = specification.and(buildStringSpecification(criteria.getWxr(), FwWxf_.wxr));
            }
            if (criteria.getWcrq() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getWcrq(), FwWxf_.wcrq));
            }
            if (criteria.getDjr() != null) {
                specification = specification.and(buildStringSpecification(criteria.getDjr(), FwWxf_.djr));
            }
            if (criteria.getFlag() != null) {
                specification = specification.and(buildStringSpecification(criteria.getFlag(), FwWxf_.flag));
            }
        }
        return specification;
    }
}
