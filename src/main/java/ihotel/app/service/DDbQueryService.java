package ihotel.app.service;

import ihotel.app.domain.*; // for static metamodels
import ihotel.app.domain.DDb;
import ihotel.app.repository.DDbRepository;
import ihotel.app.repository.search.DDbSearchRepository;
import ihotel.app.service.criteria.DDbCriteria;
import ihotel.app.service.dto.DDbDTO;
import ihotel.app.service.mapper.DDbMapper;
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
 * Service for executing complex queries for {@link DDb} entities in the database.
 * The main input is a {@link DDbCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link DDbDTO} or a {@link Page} of {@link DDbDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class DDbQueryService extends QueryService<DDb> {

    private final Logger log = LoggerFactory.getLogger(DDbQueryService.class);

    private final DDbRepository dDbRepository;

    private final DDbMapper dDbMapper;

    private final DDbSearchRepository dDbSearchRepository;

    public DDbQueryService(DDbRepository dDbRepository, DDbMapper dDbMapper, DDbSearchRepository dDbSearchRepository) {
        this.dDbRepository = dDbRepository;
        this.dDbMapper = dDbMapper;
        this.dDbSearchRepository = dDbSearchRepository;
    }

    /**
     * Return a {@link List} of {@link DDbDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<DDbDTO> findByCriteria(DDbCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<DDb> specification = createSpecification(criteria);
        return dDbMapper.toDto(dDbRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link DDbDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<DDbDTO> findByCriteria(DDbCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<DDb> specification = createSpecification(criteria);
        return dDbRepository.findAll(specification, page).map(dDbMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(DDbCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<DDb> specification = createSpecification(criteria);
        return dDbRepository.count(specification);
    }

    /**
     * Function to convert {@link DDbCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<DDb> createSpecification(DDbCriteria criteria) {
        Specification<DDb> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), DDb_.id));
            }
            if (criteria.getDbdate() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getDbdate(), DDb_.dbdate));
            }
            if (criteria.getDbbillno() != null) {
                specification = specification.and(buildStringSpecification(criteria.getDbbillno(), DDb_.dbbillno));
            }
            if (criteria.getRdepot() != null) {
                specification = specification.and(buildStringSpecification(criteria.getRdepot(), DDb_.rdepot));
            }
            if (criteria.getCdepot() != null) {
                specification = specification.and(buildStringSpecification(criteria.getCdepot(), DDb_.cdepot));
            }
            if (criteria.getJbr() != null) {
                specification = specification.and(buildStringSpecification(criteria.getJbr(), DDb_.jbr));
            }
            if (criteria.getRemark() != null) {
                specification = specification.and(buildStringSpecification(criteria.getRemark(), DDb_.remark));
            }
            if (criteria.getSpbm() != null) {
                specification = specification.and(buildStringSpecification(criteria.getSpbm(), DDb_.spbm));
            }
            if (criteria.getSpmc() != null) {
                specification = specification.and(buildStringSpecification(criteria.getSpmc(), DDb_.spmc));
            }
            if (criteria.getUnit() != null) {
                specification = specification.and(buildStringSpecification(criteria.getUnit(), DDb_.unit));
            }
            if (criteria.getPrice() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getPrice(), DDb_.price));
            }
            if (criteria.getSl() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getSl(), DDb_.sl));
            }
            if (criteria.getJe() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getJe(), DDb_.je));
            }
            if (criteria.getMemo() != null) {
                specification = specification.and(buildStringSpecification(criteria.getMemo(), DDb_.memo));
            }
            if (criteria.getFlag() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getFlag(), DDb_.flag));
            }
            if (criteria.getKcid() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getKcid(), DDb_.kcid));
            }
            if (criteria.getEmpn() != null) {
                specification = specification.and(buildStringSpecification(criteria.getEmpn(), DDb_.empn));
            }
            if (criteria.getLrdate() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getLrdate(), DDb_.lrdate));
            }
            if (criteria.getCkbillno() != null) {
                specification = specification.and(buildStringSpecification(criteria.getCkbillno(), DDb_.ckbillno));
            }
            if (criteria.getf1() != null) {
                specification = specification.and(buildStringSpecification(criteria.getf1(), DDb_.f1));
            }
            if (criteria.getf2() != null) {
                specification = specification.and(buildStringSpecification(criteria.getf2(), DDb_.f2));
            }
            if (criteria.getf1empn() != null) {
                specification = specification.and(buildStringSpecification(criteria.getf1empn(), DDb_.f1empn));
            }
            if (criteria.getf2empn() != null) {
                specification = specification.and(buildStringSpecification(criteria.getf2empn(), DDb_.f2empn));
            }
            if (criteria.getf1sj() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getf1sj(), DDb_.f1sj));
            }
            if (criteria.getf2sj() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getf2sj(), DDb_.f2sj));
            }
        }
        return specification;
    }
}
