package ihotel.app.service;

import ihotel.app.domain.*; // for static metamodels
import ihotel.app.domain.DPdb;
import ihotel.app.repository.DPdbRepository;
import ihotel.app.repository.search.DPdbSearchRepository;
import ihotel.app.service.criteria.DPdbCriteria;
import ihotel.app.service.dto.DPdbDTO;
import ihotel.app.service.mapper.DPdbMapper;
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
 * Service for executing complex queries for {@link DPdb} entities in the database.
 * The main input is a {@link DPdbCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link DPdbDTO} or a {@link Page} of {@link DPdbDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class DPdbQueryService extends QueryService<DPdb> {

    private final Logger log = LoggerFactory.getLogger(DPdbQueryService.class);

    private final DPdbRepository dPdbRepository;

    private final DPdbMapper dPdbMapper;

    private final DPdbSearchRepository dPdbSearchRepository;

    public DPdbQueryService(DPdbRepository dPdbRepository, DPdbMapper dPdbMapper, DPdbSearchRepository dPdbSearchRepository) {
        this.dPdbRepository = dPdbRepository;
        this.dPdbMapper = dPdbMapper;
        this.dPdbSearchRepository = dPdbSearchRepository;
    }

    /**
     * Return a {@link List} of {@link DPdbDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<DPdbDTO> findByCriteria(DPdbCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<DPdb> specification = createSpecification(criteria);
        return dPdbMapper.toDto(dPdbRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link DPdbDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<DPdbDTO> findByCriteria(DPdbCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<DPdb> specification = createSpecification(criteria);
        return dPdbRepository.findAll(specification, page).map(dPdbMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(DPdbCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<DPdb> specification = createSpecification(criteria);
        return dPdbRepository.count(specification);
    }

    /**
     * Function to convert {@link DPdbCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<DPdb> createSpecification(DPdbCriteria criteria) {
        Specification<DPdb> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), DPdb_.id));
            }
            if (criteria.getBegindate() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getBegindate(), DPdb_.begindate));
            }
            if (criteria.getEnddate() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getEnddate(), DPdb_.enddate));
            }
            if (criteria.getBm() != null) {
                specification = specification.and(buildStringSpecification(criteria.getBm(), DPdb_.bm));
            }
            if (criteria.getSpmc() != null) {
                specification = specification.and(buildStringSpecification(criteria.getSpmc(), DPdb_.spmc));
            }
            if (criteria.getQcsl() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getQcsl(), DPdb_.qcsl));
            }
            if (criteria.getRksl() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getRksl(), DPdb_.rksl));
            }
            if (criteria.getXssl() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getXssl(), DPdb_.xssl));
            }
            if (criteria.getDbsl() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getDbsl(), DPdb_.dbsl));
            }
            if (criteria.getQtck() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getQtck(), DPdb_.qtck));
            }
            if (criteria.getJcsl() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getJcsl(), DPdb_.jcsl));
            }
            if (criteria.getSwsl() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getSwsl(), DPdb_.swsl));
            }
            if (criteria.getPyk() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getPyk(), DPdb_.pyk));
            }
            if (criteria.getMemo() != null) {
                specification = specification.and(buildStringSpecification(criteria.getMemo(), DPdb_.memo));
            }
            if (criteria.getDepot() != null) {
                specification = specification.and(buildStringSpecification(criteria.getDepot(), DPdb_.depot));
            }
            if (criteria.getRkje() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getRkje(), DPdb_.rkje));
            }
            if (criteria.getXsje() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getXsje(), DPdb_.xsje));
            }
            if (criteria.getDbje() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getDbje(), DPdb_.dbje));
            }
            if (criteria.getJcje() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getJcje(), DPdb_.jcje));
            }
            if (criteria.getDp() != null) {
                specification = specification.and(buildStringSpecification(criteria.getDp(), DPdb_.dp));
            }
            if (criteria.getQcje() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getQcje(), DPdb_.qcje));
            }
            if (criteria.getSwje() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getSwje(), DPdb_.swje));
            }
            if (criteria.getQtje() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getQtje(), DPdb_.qtje));
            }
        }
        return specification;
    }
}
