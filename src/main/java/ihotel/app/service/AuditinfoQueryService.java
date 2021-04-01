package ihotel.app.service;

import ihotel.app.domain.*; // for static metamodels
import ihotel.app.domain.Auditinfo;
import ihotel.app.repository.AuditinfoRepository;
import ihotel.app.repository.search.AuditinfoSearchRepository;
import ihotel.app.service.criteria.AuditinfoCriteria;
import ihotel.app.service.dto.AuditinfoDTO;
import ihotel.app.service.mapper.AuditinfoMapper;
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
 * Service for executing complex queries for {@link Auditinfo} entities in the database.
 * The main input is a {@link AuditinfoCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link AuditinfoDTO} or a {@link Page} of {@link AuditinfoDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class AuditinfoQueryService extends QueryService<Auditinfo> {

    private final Logger log = LoggerFactory.getLogger(AuditinfoQueryService.class);

    private final AuditinfoRepository auditinfoRepository;

    private final AuditinfoMapper auditinfoMapper;

    private final AuditinfoSearchRepository auditinfoSearchRepository;

    public AuditinfoQueryService(
        AuditinfoRepository auditinfoRepository,
        AuditinfoMapper auditinfoMapper,
        AuditinfoSearchRepository auditinfoSearchRepository
    ) {
        this.auditinfoRepository = auditinfoRepository;
        this.auditinfoMapper = auditinfoMapper;
        this.auditinfoSearchRepository = auditinfoSearchRepository;
    }

    /**
     * Return a {@link List} of {@link AuditinfoDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<AuditinfoDTO> findByCriteria(AuditinfoCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<Auditinfo> specification = createSpecification(criteria);
        return auditinfoMapper.toDto(auditinfoRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link AuditinfoDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<AuditinfoDTO> findByCriteria(AuditinfoCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<Auditinfo> specification = createSpecification(criteria);
        return auditinfoRepository.findAll(specification, page).map(auditinfoMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(AuditinfoCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<Auditinfo> specification = createSpecification(criteria);
        return auditinfoRepository.count(specification);
    }

    /**
     * Function to convert {@link AuditinfoCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<Auditinfo> createSpecification(AuditinfoCriteria criteria) {
        Specification<Auditinfo> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), Auditinfo_.id));
            }
            if (criteria.getAuditdate() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getAuditdate(), Auditinfo_.auditdate));
            }
            if (criteria.getAudittime() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getAudittime(), Auditinfo_.audittime));
            }
            if (criteria.getEmpn() != null) {
                specification = specification.and(buildStringSpecification(criteria.getEmpn(), Auditinfo_.empn));
            }
            if (criteria.getAidentify() != null) {
                specification = specification.and(buildStringSpecification(criteria.getAidentify(), Auditinfo_.aidentify));
            }
        }
        return specification;
    }
}
