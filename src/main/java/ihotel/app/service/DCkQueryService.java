package ihotel.app.service;

import ihotel.app.domain.*; // for static metamodels
import ihotel.app.domain.DCk;
import ihotel.app.repository.DCkRepository;
import ihotel.app.repository.search.DCkSearchRepository;
import ihotel.app.service.criteria.DCkCriteria;
import ihotel.app.service.dto.DCkDTO;
import ihotel.app.service.mapper.DCkMapper;
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
 * Service for executing complex queries for {@link DCk} entities in the database.
 * The main input is a {@link DCkCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link DCkDTO} or a {@link Page} of {@link DCkDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class DCkQueryService extends QueryService<DCk> {

    private final Logger log = LoggerFactory.getLogger(DCkQueryService.class);

    private final DCkRepository dCkRepository;

    private final DCkMapper dCkMapper;

    private final DCkSearchRepository dCkSearchRepository;

    public DCkQueryService(DCkRepository dCkRepository, DCkMapper dCkMapper, DCkSearchRepository dCkSearchRepository) {
        this.dCkRepository = dCkRepository;
        this.dCkMapper = dCkMapper;
        this.dCkSearchRepository = dCkSearchRepository;
    }

    /**
     * Return a {@link List} of {@link DCkDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<DCkDTO> findByCriteria(DCkCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<DCk> specification = createSpecification(criteria);
        return dCkMapper.toDto(dCkRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link DCkDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<DCkDTO> findByCriteria(DCkCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<DCk> specification = createSpecification(criteria);
        return dCkRepository.findAll(specification, page).map(dCkMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(DCkCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<DCk> specification = createSpecification(criteria);
        return dCkRepository.count(specification);
    }

    /**
     * Function to convert {@link DCkCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<DCk> createSpecification(DCkCriteria criteria) {
        Specification<DCk> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), DCk_.id));
            }
            if (criteria.getDepot() != null) {
                specification = specification.and(buildStringSpecification(criteria.getDepot(), DCk_.depot));
            }
            if (criteria.getCkdate() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getCkdate(), DCk_.ckdate));
            }
            if (criteria.getCkbillno() != null) {
                specification = specification.and(buildStringSpecification(criteria.getCkbillno(), DCk_.ckbillno));
            }
            if (criteria.getDeptname() != null) {
                specification = specification.and(buildStringSpecification(criteria.getDeptname(), DCk_.deptname));
            }
            if (criteria.getJbr() != null) {
                specification = specification.and(buildStringSpecification(criteria.getJbr(), DCk_.jbr));
            }
            if (criteria.getRemark() != null) {
                specification = specification.and(buildStringSpecification(criteria.getRemark(), DCk_.remark));
            }
            if (criteria.getSpbm() != null) {
                specification = specification.and(buildStringSpecification(criteria.getSpbm(), DCk_.spbm));
            }
            if (criteria.getSpmc() != null) {
                specification = specification.and(buildStringSpecification(criteria.getSpmc(), DCk_.spmc));
            }
            if (criteria.getUnit() != null) {
                specification = specification.and(buildStringSpecification(criteria.getUnit(), DCk_.unit));
            }
            if (criteria.getPrice() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getPrice(), DCk_.price));
            }
            if (criteria.getSl() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getSl(), DCk_.sl));
            }
            if (criteria.getJe() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getJe(), DCk_.je));
            }
            if (criteria.getMemo() != null) {
                specification = specification.and(buildStringSpecification(criteria.getMemo(), DCk_.memo));
            }
            if (criteria.getFlag() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getFlag(), DCk_.flag));
            }
            if (criteria.getDbSign() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getDbSign(), DCk_.dbSign));
            }
            if (criteria.getCktype() != null) {
                specification = specification.and(buildStringSpecification(criteria.getCktype(), DCk_.cktype));
            }
            if (criteria.getEmpn() != null) {
                specification = specification.and(buildStringSpecification(criteria.getEmpn(), DCk_.empn));
            }
            if (criteria.getLrdate() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getLrdate(), DCk_.lrdate));
            }
            if (criteria.getKcid() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getKcid(), DCk_.kcid));
            }
            if (criteria.getf1() != null) {
                specification = specification.and(buildStringSpecification(criteria.getf1(), DCk_.f1));
            }
            if (criteria.getf2() != null) {
                specification = specification.and(buildStringSpecification(criteria.getf2(), DCk_.f2));
            }
            if (criteria.getf1empn() != null) {
                specification = specification.and(buildStringSpecification(criteria.getf1empn(), DCk_.f1empn));
            }
            if (criteria.getf2empn() != null) {
                specification = specification.and(buildStringSpecification(criteria.getf2empn(), DCk_.f2empn));
            }
            if (criteria.getf1sj() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getf1sj(), DCk_.f1sj));
            }
            if (criteria.getf2sj() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getf2sj(), DCk_.f2sj));
            }
        }
        return specification;
    }
}
