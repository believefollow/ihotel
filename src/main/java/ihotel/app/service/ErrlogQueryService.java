package ihotel.app.service;

import ihotel.app.domain.*; // for static metamodels
import ihotel.app.domain.Errlog;
import ihotel.app.repository.ErrlogRepository;
import ihotel.app.repository.search.ErrlogSearchRepository;
import ihotel.app.service.criteria.ErrlogCriteria;
import ihotel.app.service.dto.ErrlogDTO;
import ihotel.app.service.mapper.ErrlogMapper;
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
 * Service for executing complex queries for {@link Errlog} entities in the database.
 * The main input is a {@link ErrlogCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link ErrlogDTO} or a {@link Page} of {@link ErrlogDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class ErrlogQueryService extends QueryService<Errlog> {

    private final Logger log = LoggerFactory.getLogger(ErrlogQueryService.class);

    private final ErrlogRepository errlogRepository;

    private final ErrlogMapper errlogMapper;

    private final ErrlogSearchRepository errlogSearchRepository;

    public ErrlogQueryService(ErrlogRepository errlogRepository, ErrlogMapper errlogMapper, ErrlogSearchRepository errlogSearchRepository) {
        this.errlogRepository = errlogRepository;
        this.errlogMapper = errlogMapper;
        this.errlogSearchRepository = errlogSearchRepository;
    }

    /**
     * Return a {@link List} of {@link ErrlogDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<ErrlogDTO> findByCriteria(ErrlogCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<Errlog> specification = createSpecification(criteria);
        return errlogMapper.toDto(errlogRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link ErrlogDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<ErrlogDTO> findByCriteria(ErrlogCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<Errlog> specification = createSpecification(criteria);
        return errlogRepository.findAll(specification, page).map(errlogMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(ErrlogCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<Errlog> specification = createSpecification(criteria);
        return errlogRepository.count(specification);
    }

    /**
     * Function to convert {@link ErrlogCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<Errlog> createSpecification(ErrlogCriteria criteria) {
        Specification<Errlog> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), Errlog_.id));
            }
            if (criteria.getIderrlog() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getIderrlog(), Errlog_.iderrlog));
            }
            if (criteria.getErrnumber() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getErrnumber(), Errlog_.errnumber));
            }
            if (criteria.getErrtext() != null) {
                specification = specification.and(buildStringSpecification(criteria.getErrtext(), Errlog_.errtext));
            }
            if (criteria.getErrwindowmenu() != null) {
                specification = specification.and(buildStringSpecification(criteria.getErrwindowmenu(), Errlog_.errwindowmenu));
            }
            if (criteria.getErrobject() != null) {
                specification = specification.and(buildStringSpecification(criteria.getErrobject(), Errlog_.errobject));
            }
            if (criteria.getErrevent() != null) {
                specification = specification.and(buildStringSpecification(criteria.getErrevent(), Errlog_.errevent));
            }
            if (criteria.getErrline() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getErrline(), Errlog_.errline));
            }
            if (criteria.getErrtime() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getErrtime(), Errlog_.errtime));
            }
            if (criteria.getSumbitsign() != null) {
                specification = specification.and(buildSpecification(criteria.getSumbitsign(), Errlog_.sumbitsign));
            }
            if (criteria.getBmpfile() != null) {
                specification = specification.and(buildStringSpecification(criteria.getBmpfile(), Errlog_.bmpfile));
            }
        }
        return specification;
    }
}
