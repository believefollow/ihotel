package ihotel.app.service;

import ihotel.app.domain.*; // for static metamodels
import ihotel.app.domain.DRk;
import ihotel.app.repository.DRkRepository;
import ihotel.app.repository.search.DRkSearchRepository;
import ihotel.app.service.criteria.DRkCriteria;
import ihotel.app.service.dto.DRkDTO;
import ihotel.app.service.mapper.DRkMapper;
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
 * Service for executing complex queries for {@link DRk} entities in the database.
 * The main input is a {@link DRkCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link DRkDTO} or a {@link Page} of {@link DRkDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class DRkQueryService extends QueryService<DRk> {

    private final Logger log = LoggerFactory.getLogger(DRkQueryService.class);

    private final DRkRepository dRkRepository;

    private final DRkMapper dRkMapper;

    private final DRkSearchRepository dRkSearchRepository;

    public DRkQueryService(DRkRepository dRkRepository, DRkMapper dRkMapper, DRkSearchRepository dRkSearchRepository) {
        this.dRkRepository = dRkRepository;
        this.dRkMapper = dRkMapper;
        this.dRkSearchRepository = dRkSearchRepository;
    }

    /**
     * Return a {@link List} of {@link DRkDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<DRkDTO> findByCriteria(DRkCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<DRk> specification = createSpecification(criteria);
        return dRkMapper.toDto(dRkRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link DRkDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<DRkDTO> findByCriteria(DRkCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<DRk> specification = createSpecification(criteria);
        return dRkRepository.findAll(specification, page).map(dRkMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(DRkCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<DRk> specification = createSpecification(criteria);
        return dRkRepository.count(specification);
    }

    /**
     * Function to convert {@link DRkCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<DRk> createSpecification(DRkCriteria criteria) {
        Specification<DRk> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), DRk_.id));
            }
            if (criteria.getRkdate() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getRkdate(), DRk_.rkdate));
            }
            if (criteria.getDepot() != null) {
                specification = specification.and(buildStringSpecification(criteria.getDepot(), DRk_.depot));
            }
            if (criteria.getRklx() != null) {
                specification = specification.and(buildStringSpecification(criteria.getRklx(), DRk_.rklx));
            }
            if (criteria.getRkbillno() != null) {
                specification = specification.and(buildStringSpecification(criteria.getRkbillno(), DRk_.rkbillno));
            }
            if (criteria.getCompany() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getCompany(), DRk_.company));
            }
            if (criteria.getDeptname() != null) {
                specification = specification.and(buildStringSpecification(criteria.getDeptname(), DRk_.deptname));
            }
            if (criteria.getJbr() != null) {
                specification = specification.and(buildStringSpecification(criteria.getJbr(), DRk_.jbr));
            }
            if (criteria.getRemark() != null) {
                specification = specification.and(buildStringSpecification(criteria.getRemark(), DRk_.remark));
            }
            if (criteria.getEmpn() != null) {
                specification = specification.and(buildStringSpecification(criteria.getEmpn(), DRk_.empn));
            }
            if (criteria.getLrdate() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getLrdate(), DRk_.lrdate));
            }
            if (criteria.getSpbm() != null) {
                specification = specification.and(buildStringSpecification(criteria.getSpbm(), DRk_.spbm));
            }
            if (criteria.getSpmc() != null) {
                specification = specification.and(buildStringSpecification(criteria.getSpmc(), DRk_.spmc));
            }
            if (criteria.getGgxh() != null) {
                specification = specification.and(buildStringSpecification(criteria.getGgxh(), DRk_.ggxh));
            }
            if (criteria.getDw() != null) {
                specification = specification.and(buildStringSpecification(criteria.getDw(), DRk_.dw));
            }
            if (criteria.getPrice() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getPrice(), DRk_.price));
            }
            if (criteria.getSl() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getSl(), DRk_.sl));
            }
            if (criteria.getJe() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getJe(), DRk_.je));
            }
            if (criteria.getSign() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getSign(), DRk_.sign));
            }
            if (criteria.getMemo() != null) {
                specification = specification.and(buildStringSpecification(criteria.getMemo(), DRk_.memo));
            }
            if (criteria.getFlag() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getFlag(), DRk_.flag));
            }
            if (criteria.getf1() != null) {
                specification = specification.and(buildStringSpecification(criteria.getf1(), DRk_.f1));
            }
            if (criteria.getf2() != null) {
                specification = specification.and(buildStringSpecification(criteria.getf2(), DRk_.f2));
            }
            if (criteria.getf1empn() != null) {
                specification = specification.and(buildStringSpecification(criteria.getf1empn(), DRk_.f1empn));
            }
            if (criteria.getf2empn() != null) {
                specification = specification.and(buildStringSpecification(criteria.getf2empn(), DRk_.f2empn));
            }
            if (criteria.getf1sj() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getf1sj(), DRk_.f1sj));
            }
            if (criteria.getf2sj() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getf2sj(), DRk_.f2sj));
            }
        }
        return specification;
    }
}
