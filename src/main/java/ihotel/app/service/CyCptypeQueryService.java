package ihotel.app.service;

import ihotel.app.domain.*; // for static metamodels
import ihotel.app.domain.CyCptype;
import ihotel.app.repository.CyCptypeRepository;
import ihotel.app.repository.search.CyCptypeSearchRepository;
import ihotel.app.service.criteria.CyCptypeCriteria;
import ihotel.app.service.dto.CyCptypeDTO;
import ihotel.app.service.mapper.CyCptypeMapper;
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
 * Service for executing complex queries for {@link CyCptype} entities in the database.
 * The main input is a {@link CyCptypeCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link CyCptypeDTO} or a {@link Page} of {@link CyCptypeDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class CyCptypeQueryService extends QueryService<CyCptype> {

    private final Logger log = LoggerFactory.getLogger(CyCptypeQueryService.class);

    private final CyCptypeRepository cyCptypeRepository;

    private final CyCptypeMapper cyCptypeMapper;

    private final CyCptypeSearchRepository cyCptypeSearchRepository;

    public CyCptypeQueryService(
        CyCptypeRepository cyCptypeRepository,
        CyCptypeMapper cyCptypeMapper,
        CyCptypeSearchRepository cyCptypeSearchRepository
    ) {
        this.cyCptypeRepository = cyCptypeRepository;
        this.cyCptypeMapper = cyCptypeMapper;
        this.cyCptypeSearchRepository = cyCptypeSearchRepository;
    }

    /**
     * Return a {@link List} of {@link CyCptypeDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<CyCptypeDTO> findByCriteria(CyCptypeCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<CyCptype> specification = createSpecification(criteria);
        return cyCptypeMapper.toDto(cyCptypeRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link CyCptypeDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<CyCptypeDTO> findByCriteria(CyCptypeCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<CyCptype> specification = createSpecification(criteria);
        return cyCptypeRepository.findAll(specification, page).map(cyCptypeMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(CyCptypeCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<CyCptype> specification = createSpecification(criteria);
        return cyCptypeRepository.count(specification);
    }

    /**
     * Function to convert {@link CyCptypeCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<CyCptype> createSpecification(CyCptypeCriteria criteria) {
        Specification<CyCptype> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), CyCptype_.id));
            }
            if (criteria.getCptdm() != null) {
                specification = specification.and(buildStringSpecification(criteria.getCptdm(), CyCptype_.cptdm));
            }
            if (criteria.getCptname() != null) {
                specification = specification.and(buildStringSpecification(criteria.getCptname(), CyCptype_.cptname));
            }
            if (criteria.getPrintsign() != null) {
                specification = specification.and(buildSpecification(criteria.getPrintsign(), CyCptype_.printsign));
            }
            if (criteria.getPrinter() != null) {
                specification = specification.and(buildStringSpecification(criteria.getPrinter(), CyCptype_.printer));
            }
            if (criteria.getPrintnum() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getPrintnum(), CyCptype_.printnum));
            }
            if (criteria.getPrintcut() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getPrintcut(), CyCptype_.printcut));
            }
            if (criteria.getSyssign() != null) {
                specification = specification.and(buildSpecification(criteria.getSyssign(), CyCptype_.syssign));
            }
            if (criteria.getTypesign() != null) {
                specification = specification.and(buildStringSpecification(criteria.getTypesign(), CyCptype_.typesign));
            }
            if (criteria.getQy() != null) {
                specification = specification.and(buildStringSpecification(criteria.getQy(), CyCptype_.qy));
            }
        }
        return specification;
    }
}
