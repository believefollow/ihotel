package ihotel.app.service;

import ihotel.app.domain.*; // for static metamodels
import ihotel.app.domain.CtClass;
import ihotel.app.repository.CtClassRepository;
import ihotel.app.repository.search.CtClassSearchRepository;
import ihotel.app.service.criteria.CtClassCriteria;
import ihotel.app.service.dto.CtClassDTO;
import ihotel.app.service.mapper.CtClassMapper;
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
 * Service for executing complex queries for {@link CtClass} entities in the database.
 * The main input is a {@link CtClassCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link CtClassDTO} or a {@link Page} of {@link CtClassDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class CtClassQueryService extends QueryService<CtClass> {

    private final Logger log = LoggerFactory.getLogger(CtClassQueryService.class);

    private final CtClassRepository ctClassRepository;

    private final CtClassMapper ctClassMapper;

    private final CtClassSearchRepository ctClassSearchRepository;

    public CtClassQueryService(
        CtClassRepository ctClassRepository,
        CtClassMapper ctClassMapper,
        CtClassSearchRepository ctClassSearchRepository
    ) {
        this.ctClassRepository = ctClassRepository;
        this.ctClassMapper = ctClassMapper;
        this.ctClassSearchRepository = ctClassSearchRepository;
    }

    /**
     * Return a {@link List} of {@link CtClassDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<CtClassDTO> findByCriteria(CtClassCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<CtClass> specification = createSpecification(criteria);
        return ctClassMapper.toDto(ctClassRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link CtClassDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<CtClassDTO> findByCriteria(CtClassCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<CtClass> specification = createSpecification(criteria);
        return ctClassRepository.findAll(specification, page).map(ctClassMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(CtClassCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<CtClass> specification = createSpecification(criteria);
        return ctClassRepository.count(specification);
    }

    /**
     * Function to convert {@link CtClassCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<CtClass> createSpecification(CtClassCriteria criteria) {
        Specification<CtClass> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), CtClass_.id));
            }
            if (criteria.getDt() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getDt(), CtClass_.dt));
            }
            if (criteria.getEmpn() != null) {
                specification = specification.and(buildStringSpecification(criteria.getEmpn(), CtClass_.empn));
            }
            if (criteria.getFlag() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getFlag(), CtClass_.flag));
            }
            if (criteria.getJbempn() != null) {
                specification = specification.and(buildStringSpecification(criteria.getJbempn(), CtClass_.jbempn));
            }
            if (criteria.getGotime() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getGotime(), CtClass_.gotime));
            }
            if (criteria.getXj() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getXj(), CtClass_.xj));
            }
            if (criteria.getZp() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getZp(), CtClass_.zp));
            }
            if (criteria.getSk() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getSk(), CtClass_.sk));
            }
            if (criteria.getHyk() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getHyk(), CtClass_.hyk));
            }
            if (criteria.getCq() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getCq(), CtClass_.cq));
            }
            if (criteria.getGz() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getGz(), CtClass_.gz));
            }
            if (criteria.getGfz() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getGfz(), CtClass_.gfz));
            }
            if (criteria.getYq() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getYq(), CtClass_.yq));
            }
            if (criteria.getYh() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getYh(), CtClass_.yh));
            }
            if (criteria.getZzh() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getZzh(), CtClass_.zzh));
            }
            if (criteria.getCheckSign() != null) {
                specification = specification.and(buildStringSpecification(criteria.getCheckSign(), CtClass_.checkSign));
            }
        }
        return specification;
    }
}
