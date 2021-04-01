package ihotel.app.service;

import ihotel.app.domain.*; // for static metamodels
import ihotel.app.domain.Bookingtime;
import ihotel.app.repository.BookingtimeRepository;
import ihotel.app.repository.search.BookingtimeSearchRepository;
import ihotel.app.service.criteria.BookingtimeCriteria;
import ihotel.app.service.dto.BookingtimeDTO;
import ihotel.app.service.mapper.BookingtimeMapper;
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
 * Service for executing complex queries for {@link Bookingtime} entities in the database.
 * The main input is a {@link BookingtimeCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link BookingtimeDTO} or a {@link Page} of {@link BookingtimeDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class BookingtimeQueryService extends QueryService<Bookingtime> {

    private final Logger log = LoggerFactory.getLogger(BookingtimeQueryService.class);

    private final BookingtimeRepository bookingtimeRepository;

    private final BookingtimeMapper bookingtimeMapper;

    private final BookingtimeSearchRepository bookingtimeSearchRepository;

    public BookingtimeQueryService(
        BookingtimeRepository bookingtimeRepository,
        BookingtimeMapper bookingtimeMapper,
        BookingtimeSearchRepository bookingtimeSearchRepository
    ) {
        this.bookingtimeRepository = bookingtimeRepository;
        this.bookingtimeMapper = bookingtimeMapper;
        this.bookingtimeSearchRepository = bookingtimeSearchRepository;
    }

    /**
     * Return a {@link List} of {@link BookingtimeDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<BookingtimeDTO> findByCriteria(BookingtimeCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<Bookingtime> specification = createSpecification(criteria);
        return bookingtimeMapper.toDto(bookingtimeRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link BookingtimeDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<BookingtimeDTO> findByCriteria(BookingtimeCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<Bookingtime> specification = createSpecification(criteria);
        return bookingtimeRepository.findAll(specification, page).map(bookingtimeMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(BookingtimeCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<Bookingtime> specification = createSpecification(criteria);
        return bookingtimeRepository.count(specification);
    }

    /**
     * Function to convert {@link BookingtimeCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<Bookingtime> createSpecification(BookingtimeCriteria criteria) {
        Specification<Bookingtime> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), Bookingtime_.id));
            }
            if (criteria.getBookid() != null) {
                specification = specification.and(buildStringSpecification(criteria.getBookid(), Bookingtime_.bookid));
            }
            if (criteria.getRoomn() != null) {
                specification = specification.and(buildStringSpecification(criteria.getRoomn(), Bookingtime_.roomn));
            }
            if (criteria.getBooktime() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getBooktime(), Bookingtime_.booktime));
            }
            if (criteria.getRtype() != null) {
                specification = specification.and(buildStringSpecification(criteria.getRtype(), Bookingtime_.rtype));
            }
            if (criteria.getSl() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getSl(), Bookingtime_.sl));
            }
            if (criteria.getRemark() != null) {
                specification = specification.and(buildStringSpecification(criteria.getRemark(), Bookingtime_.remark));
            }
            if (criteria.getSign() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getSign(), Bookingtime_.sign));
            }
            if (criteria.getRzsign() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getRzsign(), Bookingtime_.rzsign));
            }
        }
        return specification;
    }
}
