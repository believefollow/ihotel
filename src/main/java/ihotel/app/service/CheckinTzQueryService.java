package ihotel.app.service;

import ihotel.app.domain.*; // for static metamodels
import ihotel.app.domain.CheckinTz;
import ihotel.app.repository.CheckinTzRepository;
import ihotel.app.repository.search.CheckinTzSearchRepository;
import ihotel.app.service.criteria.CheckinTzCriteria;
import ihotel.app.service.dto.CheckinTzDTO;
import ihotel.app.service.mapper.CheckinTzMapper;
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
 * Service for executing complex queries for {@link CheckinTz} entities in the database.
 * The main input is a {@link CheckinTzCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link CheckinTzDTO} or a {@link Page} of {@link CheckinTzDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class CheckinTzQueryService extends QueryService<CheckinTz> {

    private final Logger log = LoggerFactory.getLogger(CheckinTzQueryService.class);

    private final CheckinTzRepository checkinTzRepository;

    private final CheckinTzMapper checkinTzMapper;

    private final CheckinTzSearchRepository checkinTzSearchRepository;

    public CheckinTzQueryService(
        CheckinTzRepository checkinTzRepository,
        CheckinTzMapper checkinTzMapper,
        CheckinTzSearchRepository checkinTzSearchRepository
    ) {
        this.checkinTzRepository = checkinTzRepository;
        this.checkinTzMapper = checkinTzMapper;
        this.checkinTzSearchRepository = checkinTzSearchRepository;
    }

    /**
     * Return a {@link List} of {@link CheckinTzDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<CheckinTzDTO> findByCriteria(CheckinTzCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<CheckinTz> specification = createSpecification(criteria);
        return checkinTzMapper.toDto(checkinTzRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link CheckinTzDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<CheckinTzDTO> findByCriteria(CheckinTzCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<CheckinTz> specification = createSpecification(criteria);
        return checkinTzRepository.findAll(specification, page).map(checkinTzMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(CheckinTzCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<CheckinTz> specification = createSpecification(criteria);
        return checkinTzRepository.count(specification);
    }

    /**
     * Function to convert {@link CheckinTzCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<CheckinTz> createSpecification(CheckinTzCriteria criteria) {
        Specification<CheckinTz> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), CheckinTz_.id));
            }
            if (criteria.getGuestId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getGuestId(), CheckinTz_.guestId));
            }
            if (criteria.getAccount() != null) {
                specification = specification.and(buildStringSpecification(criteria.getAccount(), CheckinTz_.account));
            }
            if (criteria.getHoteltime() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getHoteltime(), CheckinTz_.hoteltime));
            }
            if (criteria.getIndatetime() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getIndatetime(), CheckinTz_.indatetime));
            }
            if (criteria.getResidefate() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getResidefate(), CheckinTz_.residefate));
            }
            if (criteria.getGotime() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getGotime(), CheckinTz_.gotime));
            }
            if (criteria.getEmpn() != null) {
                specification = specification.and(buildStringSpecification(criteria.getEmpn(), CheckinTz_.empn));
            }
            if (criteria.getRoomn() != null) {
                specification = specification.and(buildStringSpecification(criteria.getRoomn(), CheckinTz_.roomn));
            }
            if (criteria.getRentp() != null) {
                specification = specification.and(buildStringSpecification(criteria.getRentp(), CheckinTz_.rentp));
            }
            if (criteria.getProtocolrent() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getProtocolrent(), CheckinTz_.protocolrent));
            }
            if (criteria.getRemark() != null) {
                specification = specification.and(buildStringSpecification(criteria.getRemark(), CheckinTz_.remark));
            }
            if (criteria.getPhonen() != null) {
                specification = specification.and(buildStringSpecification(criteria.getPhonen(), CheckinTz_.phonen));
            }
            if (criteria.getEmpn2() != null) {
                specification = specification.and(buildStringSpecification(criteria.getEmpn2(), CheckinTz_.empn2));
            }
            if (criteria.getMemo() != null) {
                specification = specification.and(buildStringSpecification(criteria.getMemo(), CheckinTz_.memo));
            }
            if (criteria.getLfSign() != null) {
                specification = specification.and(buildStringSpecification(criteria.getLfSign(), CheckinTz_.lfSign));
            }
            if (criteria.getGuestname() != null) {
                specification = specification.and(buildStringSpecification(criteria.getGuestname(), CheckinTz_.guestname));
            }
            if (criteria.getBc() != null) {
                specification = specification.and(buildStringSpecification(criteria.getBc(), CheckinTz_.bc));
            }
            if (criteria.getRoomtype() != null) {
                specification = specification.and(buildStringSpecification(criteria.getRoomtype(), CheckinTz_.roomtype));
            }
        }
        return specification;
    }
}
