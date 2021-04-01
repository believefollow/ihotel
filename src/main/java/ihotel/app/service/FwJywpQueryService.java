package ihotel.app.service;

import ihotel.app.domain.*; // for static metamodels
import ihotel.app.domain.FwJywp;
import ihotel.app.repository.FwJywpRepository;
import ihotel.app.repository.search.FwJywpSearchRepository;
import ihotel.app.service.criteria.FwJywpCriteria;
import ihotel.app.service.dto.FwJywpDTO;
import ihotel.app.service.mapper.FwJywpMapper;
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
 * Service for executing complex queries for {@link FwJywp} entities in the database.
 * The main input is a {@link FwJywpCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link FwJywpDTO} or a {@link Page} of {@link FwJywpDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class FwJywpQueryService extends QueryService<FwJywp> {

    private final Logger log = LoggerFactory.getLogger(FwJywpQueryService.class);

    private final FwJywpRepository fwJywpRepository;

    private final FwJywpMapper fwJywpMapper;

    private final FwJywpSearchRepository fwJywpSearchRepository;

    public FwJywpQueryService(FwJywpRepository fwJywpRepository, FwJywpMapper fwJywpMapper, FwJywpSearchRepository fwJywpSearchRepository) {
        this.fwJywpRepository = fwJywpRepository;
        this.fwJywpMapper = fwJywpMapper;
        this.fwJywpSearchRepository = fwJywpSearchRepository;
    }

    /**
     * Return a {@link List} of {@link FwJywpDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<FwJywpDTO> findByCriteria(FwJywpCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<FwJywp> specification = createSpecification(criteria);
        return fwJywpMapper.toDto(fwJywpRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link FwJywpDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<FwJywpDTO> findByCriteria(FwJywpCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<FwJywp> specification = createSpecification(criteria);
        return fwJywpRepository.findAll(specification, page).map(fwJywpMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(FwJywpCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<FwJywp> specification = createSpecification(criteria);
        return fwJywpRepository.count(specification);
    }

    /**
     * Function to convert {@link FwJywpCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<FwJywp> createSpecification(FwJywpCriteria criteria) {
        Specification<FwJywp> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), FwJywp_.id));
            }
            if (criteria.getJyrq() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getJyrq(), FwJywp_.jyrq));
            }
            if (criteria.getRoomn() != null) {
                specification = specification.and(buildStringSpecification(criteria.getRoomn(), FwJywp_.roomn));
            }
            if (criteria.getGuestname() != null) {
                specification = specification.and(buildStringSpecification(criteria.getGuestname(), FwJywp_.guestname));
            }
            if (criteria.getJywp() != null) {
                specification = specification.and(buildStringSpecification(criteria.getJywp(), FwJywp_.jywp));
            }
            if (criteria.getFwy() != null) {
                specification = specification.and(buildStringSpecification(criteria.getFwy(), FwJywp_.fwy));
            }
            if (criteria.getDjr() != null) {
                specification = specification.and(buildStringSpecification(criteria.getDjr(), FwJywp_.djr));
            }
            if (criteria.getFlag() != null) {
                specification = specification.and(buildStringSpecification(criteria.getFlag(), FwJywp_.flag));
            }
            if (criteria.getGhrq() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getGhrq(), FwJywp_.ghrq));
            }
            if (criteria.getDjrq() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getDjrq(), FwJywp_.djrq));
            }
            if (criteria.getRemark() != null) {
                specification = specification.and(buildStringSpecification(criteria.getRemark(), FwJywp_.remark));
            }
        }
        return specification;
    }
}
