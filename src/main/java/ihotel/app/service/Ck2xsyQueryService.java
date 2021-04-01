package ihotel.app.service;

import ihotel.app.domain.*; // for static metamodels
import ihotel.app.domain.Ck2xsy;
import ihotel.app.repository.Ck2xsyRepository;
import ihotel.app.repository.search.Ck2xsySearchRepository;
import ihotel.app.service.criteria.Ck2xsyCriteria;
import ihotel.app.service.dto.Ck2xsyDTO;
import ihotel.app.service.mapper.Ck2xsyMapper;
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
 * Service for executing complex queries for {@link Ck2xsy} entities in the database.
 * The main input is a {@link Ck2xsyCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link Ck2xsyDTO} or a {@link Page} of {@link Ck2xsyDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class Ck2xsyQueryService extends QueryService<Ck2xsy> {

    private final Logger log = LoggerFactory.getLogger(Ck2xsyQueryService.class);

    private final Ck2xsyRepository ck2xsyRepository;

    private final Ck2xsyMapper ck2xsyMapper;

    private final Ck2xsySearchRepository ck2xsySearchRepository;

    public Ck2xsyQueryService(Ck2xsyRepository ck2xsyRepository, Ck2xsyMapper ck2xsyMapper, Ck2xsySearchRepository ck2xsySearchRepository) {
        this.ck2xsyRepository = ck2xsyRepository;
        this.ck2xsyMapper = ck2xsyMapper;
        this.ck2xsySearchRepository = ck2xsySearchRepository;
    }

    /**
     * Return a {@link List} of {@link Ck2xsyDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<Ck2xsyDTO> findByCriteria(Ck2xsyCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<Ck2xsy> specification = createSpecification(criteria);
        return ck2xsyMapper.toDto(ck2xsyRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link Ck2xsyDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<Ck2xsyDTO> findByCriteria(Ck2xsyCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<Ck2xsy> specification = createSpecification(criteria);
        return ck2xsyRepository.findAll(specification, page).map(ck2xsyMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(Ck2xsyCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<Ck2xsy> specification = createSpecification(criteria);
        return ck2xsyRepository.count(specification);
    }

    /**
     * Function to convert {@link Ck2xsyCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<Ck2xsy> createSpecification(Ck2xsyCriteria criteria) {
        Specification<Ck2xsy> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), Ck2xsy_.id));
            }
            if (criteria.getRq() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getRq(), Ck2xsy_.rq));
            }
            if (criteria.getCpbh() != null) {
                specification = specification.and(buildStringSpecification(criteria.getCpbh(), Ck2xsy_.cpbh));
            }
            if (criteria.getSl() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getSl(), Ck2xsy_.sl));
            }
        }
        return specification;
    }
}
