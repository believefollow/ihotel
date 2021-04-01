package ihotel.app.service;

import ihotel.app.domain.*; // for static metamodels
import ihotel.app.domain.Classreport;
import ihotel.app.repository.ClassreportRepository;
import ihotel.app.repository.search.ClassreportSearchRepository;
import ihotel.app.service.criteria.ClassreportCriteria;
import ihotel.app.service.dto.ClassreportDTO;
import ihotel.app.service.mapper.ClassreportMapper;
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
 * Service for executing complex queries for {@link Classreport} entities in the database.
 * The main input is a {@link ClassreportCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link ClassreportDTO} or a {@link Page} of {@link ClassreportDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class ClassreportQueryService extends QueryService<Classreport> {

    private final Logger log = LoggerFactory.getLogger(ClassreportQueryService.class);

    private final ClassreportRepository classreportRepository;

    private final ClassreportMapper classreportMapper;

    private final ClassreportSearchRepository classreportSearchRepository;

    public ClassreportQueryService(
        ClassreportRepository classreportRepository,
        ClassreportMapper classreportMapper,
        ClassreportSearchRepository classreportSearchRepository
    ) {
        this.classreportRepository = classreportRepository;
        this.classreportMapper = classreportMapper;
        this.classreportSearchRepository = classreportSearchRepository;
    }

    /**
     * Return a {@link List} of {@link ClassreportDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<ClassreportDTO> findByCriteria(ClassreportCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<Classreport> specification = createSpecification(criteria);
        return classreportMapper.toDto(classreportRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link ClassreportDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<ClassreportDTO> findByCriteria(ClassreportCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<Classreport> specification = createSpecification(criteria);
        return classreportRepository.findAll(specification, page).map(classreportMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(ClassreportCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<Classreport> specification = createSpecification(criteria);
        return classreportRepository.count(specification);
    }

    /**
     * Function to convert {@link ClassreportCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<Classreport> createSpecification(ClassreportCriteria criteria) {
        Specification<Classreport> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), Classreport_.id));
            }
            if (criteria.getEmpn() != null) {
                specification = specification.and(buildStringSpecification(criteria.getEmpn(), Classreport_.empn));
            }
            if (criteria.getDt() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getDt(), Classreport_.dt));
            }
            if (criteria.getXjUp() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getXjUp(), Classreport_.xjUp));
            }
            if (criteria.getYfjA() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getYfjA(), Classreport_.yfjA));
            }
            if (criteria.getYfjD() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getYfjD(), Classreport_.yfjD));
            }
            if (criteria.getGz() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getGz(), Classreport_.gz));
            }
            if (criteria.getZz() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getZz(), Classreport_.zz));
            }
            if (criteria.getZzYj() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getZzYj(), Classreport_.zzYj));
            }
            if (criteria.getZzJs() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getZzJs(), Classreport_.zzJs));
            }
            if (criteria.getZzTc() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getZzTc(), Classreport_.zzTc));
            }
            if (criteria.getFf() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getFf(), Classreport_.ff));
            }
            if (criteria.getMinibar() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getMinibar(), Classreport_.minibar));
            }
            if (criteria.getPhone() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getPhone(), Classreport_.phone));
            }
            if (criteria.getOther() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getOther(), Classreport_.other));
            }
            if (criteria.getPc() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getPc(), Classreport_.pc));
            }
            if (criteria.getCz() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getCz(), Classreport_.cz));
            }
            if (criteria.getCy() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getCy(), Classreport_.cy));
            }
            if (criteria.getMd() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getMd(), Classreport_.md));
            }
            if (criteria.getHuiy() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getHuiy(), Classreport_.huiy));
            }
            if (criteria.getDtb() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getDtb(), Classreport_.dtb));
            }
            if (criteria.getSszx() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getSszx(), Classreport_.sszx));
            }
            if (criteria.getCyz() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getCyz(), Classreport_.cyz));
            }
            if (criteria.getHoteldm() != null) {
                specification = specification.and(buildStringSpecification(criteria.getHoteldm(), Classreport_.hoteldm));
            }
            if (criteria.getGzxj() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getGzxj(), Classreport_.gzxj));
            }
            if (criteria.getIsnew() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getIsnew(), Classreport_.isnew));
            }
        }
        return specification;
    }
}
