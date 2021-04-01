package ihotel.app.service;

import ihotel.app.domain.*; // for static metamodels
import ihotel.app.domain.FwYlwp;
import ihotel.app.repository.FwYlwpRepository;
import ihotel.app.repository.search.FwYlwpSearchRepository;
import ihotel.app.service.criteria.FwYlwpCriteria;
import ihotel.app.service.dto.FwYlwpDTO;
import ihotel.app.service.mapper.FwYlwpMapper;
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
 * Service for executing complex queries for {@link FwYlwp} entities in the database.
 * The main input is a {@link FwYlwpCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link FwYlwpDTO} or a {@link Page} of {@link FwYlwpDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class FwYlwpQueryService extends QueryService<FwYlwp> {

    private final Logger log = LoggerFactory.getLogger(FwYlwpQueryService.class);

    private final FwYlwpRepository fwYlwpRepository;

    private final FwYlwpMapper fwYlwpMapper;

    private final FwYlwpSearchRepository fwYlwpSearchRepository;

    public FwYlwpQueryService(FwYlwpRepository fwYlwpRepository, FwYlwpMapper fwYlwpMapper, FwYlwpSearchRepository fwYlwpSearchRepository) {
        this.fwYlwpRepository = fwYlwpRepository;
        this.fwYlwpMapper = fwYlwpMapper;
        this.fwYlwpSearchRepository = fwYlwpSearchRepository;
    }

    /**
     * Return a {@link List} of {@link FwYlwpDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<FwYlwpDTO> findByCriteria(FwYlwpCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<FwYlwp> specification = createSpecification(criteria);
        return fwYlwpMapper.toDto(fwYlwpRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link FwYlwpDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<FwYlwpDTO> findByCriteria(FwYlwpCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<FwYlwp> specification = createSpecification(criteria);
        return fwYlwpRepository.findAll(specification, page).map(fwYlwpMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(FwYlwpCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<FwYlwp> specification = createSpecification(criteria);
        return fwYlwpRepository.count(specification);
    }

    /**
     * Function to convert {@link FwYlwpCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<FwYlwp> createSpecification(FwYlwpCriteria criteria) {
        Specification<FwYlwp> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), FwYlwp_.id));
            }
            if (criteria.getRoomn() != null) {
                specification = specification.and(buildStringSpecification(criteria.getRoomn(), FwYlwp_.roomn));
            }
            if (criteria.getGuestname() != null) {
                specification = specification.and(buildStringSpecification(criteria.getGuestname(), FwYlwp_.guestname));
            }
            if (criteria.getMemo() != null) {
                specification = specification.and(buildStringSpecification(criteria.getMemo(), FwYlwp_.memo));
            }
            if (criteria.getSdr() != null) {
                specification = specification.and(buildStringSpecification(criteria.getSdr(), FwYlwp_.sdr));
            }
            if (criteria.getSdrq() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getSdrq(), FwYlwp_.sdrq));
            }
            if (criteria.getRlr() != null) {
                specification = specification.and(buildStringSpecification(criteria.getRlr(), FwYlwp_.rlr));
            }
            if (criteria.getRlrq() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getRlrq(), FwYlwp_.rlrq));
            }
            if (criteria.getRemark() != null) {
                specification = specification.and(buildStringSpecification(criteria.getRemark(), FwYlwp_.remark));
            }
            if (criteria.getEmpn() != null) {
                specification = specification.and(buildStringSpecification(criteria.getEmpn(), FwYlwp_.empn));
            }
            if (criteria.getCzrq() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getCzrq(), FwYlwp_.czrq));
            }
            if (criteria.getFlag() != null) {
                specification = specification.and(buildStringSpecification(criteria.getFlag(), FwYlwp_.flag));
            }
        }
        return specification;
    }
}
