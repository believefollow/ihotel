package ihotel.app.service;

import ihotel.app.domain.*; // for static metamodels
import ihotel.app.domain.Feetype;
import ihotel.app.repository.FeetypeRepository;
import ihotel.app.repository.search.FeetypeSearchRepository;
import ihotel.app.service.criteria.FeetypeCriteria;
import ihotel.app.service.dto.FeetypeDTO;
import ihotel.app.service.mapper.FeetypeMapper;
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
 * Service for executing complex queries for {@link Feetype} entities in the database.
 * The main input is a {@link FeetypeCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link FeetypeDTO} or a {@link Page} of {@link FeetypeDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class FeetypeQueryService extends QueryService<Feetype> {

    private final Logger log = LoggerFactory.getLogger(FeetypeQueryService.class);

    private final FeetypeRepository feetypeRepository;

    private final FeetypeMapper feetypeMapper;

    private final FeetypeSearchRepository feetypeSearchRepository;

    public FeetypeQueryService(
        FeetypeRepository feetypeRepository,
        FeetypeMapper feetypeMapper,
        FeetypeSearchRepository feetypeSearchRepository
    ) {
        this.feetypeRepository = feetypeRepository;
        this.feetypeMapper = feetypeMapper;
        this.feetypeSearchRepository = feetypeSearchRepository;
    }

    /**
     * Return a {@link List} of {@link FeetypeDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<FeetypeDTO> findByCriteria(FeetypeCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<Feetype> specification = createSpecification(criteria);
        return feetypeMapper.toDto(feetypeRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link FeetypeDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<FeetypeDTO> findByCriteria(FeetypeCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<Feetype> specification = createSpecification(criteria);
        return feetypeRepository.findAll(specification, page).map(feetypeMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(FeetypeCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<Feetype> specification = createSpecification(criteria);
        return feetypeRepository.count(specification);
    }

    /**
     * Function to convert {@link FeetypeCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<Feetype> createSpecification(FeetypeCriteria criteria) {
        Specification<Feetype> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), Feetype_.id));
            }
            if (criteria.getFeenum() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getFeenum(), Feetype_.feenum));
            }
            if (criteria.getFeename() != null) {
                specification = specification.and(buildStringSpecification(criteria.getFeename(), Feetype_.feename));
            }
            if (criteria.getPrice() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getPrice(), Feetype_.price));
            }
            if (criteria.getSign() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getSign(), Feetype_.sign));
            }
            if (criteria.getBeizhu() != null) {
                specification = specification.and(buildStringSpecification(criteria.getBeizhu(), Feetype_.beizhu));
            }
            if (criteria.getPym() != null) {
                specification = specification.and(buildStringSpecification(criteria.getPym(), Feetype_.pym));
            }
            if (criteria.getSalespotn() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getSalespotn(), Feetype_.salespotn));
            }
            if (criteria.getDepot() != null) {
                specification = specification.and(buildStringSpecification(criteria.getDepot(), Feetype_.depot));
            }
            if (criteria.getCbsign() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getCbsign(), Feetype_.cbsign));
            }
            if (criteria.getOrdersign() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getOrdersign(), Feetype_.ordersign));
            }
            if (criteria.getHoteldm() != null) {
                specification = specification.and(buildStringSpecification(criteria.getHoteldm(), Feetype_.hoteldm));
            }
            if (criteria.getIsnew() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getIsnew(), Feetype_.isnew));
            }
            if (criteria.getYgj() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getYgj(), Feetype_.ygj));
            }
            if (criteria.getAutosign() != null) {
                specification = specification.and(buildStringSpecification(criteria.getAutosign(), Feetype_.autosign));
            }
            if (criteria.getJj() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getJj(), Feetype_.jj));
            }
            if (criteria.getHyj() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getHyj(), Feetype_.hyj));
            }
            if (criteria.getDqkc() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getDqkc(), Feetype_.dqkc));
            }
        }
        return specification;
    }
}
