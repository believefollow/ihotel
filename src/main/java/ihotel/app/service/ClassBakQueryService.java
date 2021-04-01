package ihotel.app.service;

import ihotel.app.domain.*; // for static metamodels
import ihotel.app.domain.ClassBak;
import ihotel.app.repository.ClassBakRepository;
import ihotel.app.repository.search.ClassBakSearchRepository;
import ihotel.app.service.criteria.ClassBakCriteria;
import ihotel.app.service.dto.ClassBakDTO;
import ihotel.app.service.mapper.ClassBakMapper;
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
 * Service for executing complex queries for {@link ClassBak} entities in the database.
 * The main input is a {@link ClassBakCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link ClassBakDTO} or a {@link Page} of {@link ClassBakDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class ClassBakQueryService extends QueryService<ClassBak> {

    private final Logger log = LoggerFactory.getLogger(ClassBakQueryService.class);

    private final ClassBakRepository classBakRepository;

    private final ClassBakMapper classBakMapper;

    private final ClassBakSearchRepository classBakSearchRepository;

    public ClassBakQueryService(
        ClassBakRepository classBakRepository,
        ClassBakMapper classBakMapper,
        ClassBakSearchRepository classBakSearchRepository
    ) {
        this.classBakRepository = classBakRepository;
        this.classBakMapper = classBakMapper;
        this.classBakSearchRepository = classBakSearchRepository;
    }

    /**
     * Return a {@link List} of {@link ClassBakDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<ClassBakDTO> findByCriteria(ClassBakCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<ClassBak> specification = createSpecification(criteria);
        return classBakMapper.toDto(classBakRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link ClassBakDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<ClassBakDTO> findByCriteria(ClassBakCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<ClassBak> specification = createSpecification(criteria);
        return classBakRepository.findAll(specification, page).map(classBakMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(ClassBakCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<ClassBak> specification = createSpecification(criteria);
        return classBakRepository.count(specification);
    }

    /**
     * Function to convert {@link ClassBakCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<ClassBak> createSpecification(ClassBakCriteria criteria) {
        Specification<ClassBak> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), ClassBak_.id));
            }
            if (criteria.getEmpn() != null) {
                specification = specification.and(buildStringSpecification(criteria.getEmpn(), ClassBak_.empn));
            }
            if (criteria.getDt() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getDt(), ClassBak_.dt));
            }
            if (criteria.getRq() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getRq(), ClassBak_.rq));
            }
            if (criteria.getGhname() != null) {
                specification = specification.and(buildStringSpecification(criteria.getGhname(), ClassBak_.ghname));
            }
            if (criteria.getBak() != null) {
                specification = specification.and(buildStringSpecification(criteria.getBak(), ClassBak_.bak));
            }
        }
        return specification;
    }
}
