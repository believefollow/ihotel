package ihotel.app.service;

import ihotel.app.domain.*; // for static metamodels
import ihotel.app.domain.Dayearndetail;
import ihotel.app.repository.DayearndetailRepository;
import ihotel.app.repository.search.DayearndetailSearchRepository;
import ihotel.app.service.criteria.DayearndetailCriteria;
import ihotel.app.service.dto.DayearndetailDTO;
import ihotel.app.service.mapper.DayearndetailMapper;
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
 * Service for executing complex queries for {@link Dayearndetail} entities in the database.
 * The main input is a {@link DayearndetailCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link DayearndetailDTO} or a {@link Page} of {@link DayearndetailDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class DayearndetailQueryService extends QueryService<Dayearndetail> {

    private final Logger log = LoggerFactory.getLogger(DayearndetailQueryService.class);

    private final DayearndetailRepository dayearndetailRepository;

    private final DayearndetailMapper dayearndetailMapper;

    private final DayearndetailSearchRepository dayearndetailSearchRepository;

    public DayearndetailQueryService(
        DayearndetailRepository dayearndetailRepository,
        DayearndetailMapper dayearndetailMapper,
        DayearndetailSearchRepository dayearndetailSearchRepository
    ) {
        this.dayearndetailRepository = dayearndetailRepository;
        this.dayearndetailMapper = dayearndetailMapper;
        this.dayearndetailSearchRepository = dayearndetailSearchRepository;
    }

    /**
     * Return a {@link List} of {@link DayearndetailDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<DayearndetailDTO> findByCriteria(DayearndetailCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<Dayearndetail> specification = createSpecification(criteria);
        return dayearndetailMapper.toDto(dayearndetailRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link DayearndetailDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<DayearndetailDTO> findByCriteria(DayearndetailCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<Dayearndetail> specification = createSpecification(criteria);
        return dayearndetailRepository.findAll(specification, page).map(dayearndetailMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(DayearndetailCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<Dayearndetail> specification = createSpecification(criteria);
        return dayearndetailRepository.count(specification);
    }

    /**
     * Function to convert {@link DayearndetailCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<Dayearndetail> createSpecification(DayearndetailCriteria criteria) {
        Specification<Dayearndetail> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), Dayearndetail_.id));
            }
            if (criteria.getEarndate() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getEarndate(), Dayearndetail_.earndate));
            }
            if (criteria.getSalespotn() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getSalespotn(), Dayearndetail_.salespotn));
            }
            if (criteria.getMoney() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getMoney(), Dayearndetail_.money));
            }
        }
        return specification;
    }
}
