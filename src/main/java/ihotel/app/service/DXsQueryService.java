package ihotel.app.service;

import ihotel.app.domain.*; // for static metamodels
import ihotel.app.domain.DXs;
import ihotel.app.repository.DXsRepository;
import ihotel.app.repository.search.DXsSearchRepository;
import ihotel.app.service.criteria.DXsCriteria;
import ihotel.app.service.dto.DXsDTO;
import ihotel.app.service.mapper.DXsMapper;
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
 * Service for executing complex queries for {@link DXs} entities in the database.
 * The main input is a {@link DXsCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link DXsDTO} or a {@link Page} of {@link DXsDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class DXsQueryService extends QueryService<DXs> {

    private final Logger log = LoggerFactory.getLogger(DXsQueryService.class);

    private final DXsRepository dXsRepository;

    private final DXsMapper dXsMapper;

    private final DXsSearchRepository dXsSearchRepository;

    public DXsQueryService(DXsRepository dXsRepository, DXsMapper dXsMapper, DXsSearchRepository dXsSearchRepository) {
        this.dXsRepository = dXsRepository;
        this.dXsMapper = dXsMapper;
        this.dXsSearchRepository = dXsSearchRepository;
    }

    /**
     * Return a {@link List} of {@link DXsDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<DXsDTO> findByCriteria(DXsCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<DXs> specification = createSpecification(criteria);
        return dXsMapper.toDto(dXsRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link DXsDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<DXsDTO> findByCriteria(DXsCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<DXs> specification = createSpecification(criteria);
        return dXsRepository.findAll(specification, page).map(dXsMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(DXsCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<DXs> specification = createSpecification(criteria);
        return dXsRepository.count(specification);
    }

    /**
     * Function to convert {@link DXsCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<DXs> createSpecification(DXsCriteria criteria) {
        Specification<DXs> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), DXs_.id));
            }
            if (criteria.getBegintime() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getBegintime(), DXs_.begintime));
            }
            if (criteria.getEndtime() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getEndtime(), DXs_.endtime));
            }
            if (criteria.getCkbillno() != null) {
                specification = specification.and(buildStringSpecification(criteria.getCkbillno(), DXs_.ckbillno));
            }
            if (criteria.getDepot() != null) {
                specification = specification.and(buildStringSpecification(criteria.getDepot(), DXs_.depot));
            }
            if (criteria.getKcid() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getKcid(), DXs_.kcid));
            }
            if (criteria.getCkid() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getCkid(), DXs_.ckid));
            }
            if (criteria.getSpbm() != null) {
                specification = specification.and(buildStringSpecification(criteria.getSpbm(), DXs_.spbm));
            }
            if (criteria.getSpmc() != null) {
                specification = specification.and(buildStringSpecification(criteria.getSpmc(), DXs_.spmc));
            }
            if (criteria.getUnit() != null) {
                specification = specification.and(buildStringSpecification(criteria.getUnit(), DXs_.unit));
            }
            if (criteria.getRkprice() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getRkprice(), DXs_.rkprice));
            }
            if (criteria.getXsprice() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getXsprice(), DXs_.xsprice));
            }
            if (criteria.getSl() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getSl(), DXs_.sl));
            }
            if (criteria.getRkje() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getRkje(), DXs_.rkje));
            }
            if (criteria.getXsje() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getXsje(), DXs_.xsje));
            }
        }
        return specification;
    }
}
