package ihotel.app.service;

import ihotel.app.domain.*; // for static metamodels
import ihotel.app.domain.CheckinBak;
import ihotel.app.repository.CheckinBakRepository;
import ihotel.app.repository.search.CheckinBakSearchRepository;
import ihotel.app.service.criteria.CheckinBakCriteria;
import ihotel.app.service.dto.CheckinBakDTO;
import ihotel.app.service.mapper.CheckinBakMapper;
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
 * Service for executing complex queries for {@link CheckinBak} entities in the database.
 * The main input is a {@link CheckinBakCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link CheckinBakDTO} or a {@link Page} of {@link CheckinBakDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class CheckinBakQueryService extends QueryService<CheckinBak> {

    private final Logger log = LoggerFactory.getLogger(CheckinBakQueryService.class);

    private final CheckinBakRepository checkinBakRepository;

    private final CheckinBakMapper checkinBakMapper;

    private final CheckinBakSearchRepository checkinBakSearchRepository;

    public CheckinBakQueryService(
        CheckinBakRepository checkinBakRepository,
        CheckinBakMapper checkinBakMapper,
        CheckinBakSearchRepository checkinBakSearchRepository
    ) {
        this.checkinBakRepository = checkinBakRepository;
        this.checkinBakMapper = checkinBakMapper;
        this.checkinBakSearchRepository = checkinBakSearchRepository;
    }

    /**
     * Return a {@link List} of {@link CheckinBakDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<CheckinBakDTO> findByCriteria(CheckinBakCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<CheckinBak> specification = createSpecification(criteria);
        return checkinBakMapper.toDto(checkinBakRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link CheckinBakDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<CheckinBakDTO> findByCriteria(CheckinBakCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<CheckinBak> specification = createSpecification(criteria);
        return checkinBakRepository.findAll(specification, page).map(checkinBakMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(CheckinBakCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<CheckinBak> specification = createSpecification(criteria);
        return checkinBakRepository.count(specification);
    }

    /**
     * Function to convert {@link CheckinBakCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<CheckinBak> createSpecification(CheckinBakCriteria criteria) {
        Specification<CheckinBak> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), CheckinBak_.id));
            }
            if (criteria.getGuestId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getGuestId(), CheckinBak_.guestId));
            }
            if (criteria.getAccount() != null) {
                specification = specification.and(buildStringSpecification(criteria.getAccount(), CheckinBak_.account));
            }
            if (criteria.getHoteltime() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getHoteltime(), CheckinBak_.hoteltime));
            }
            if (criteria.getIndatetime() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getIndatetime(), CheckinBak_.indatetime));
            }
            if (criteria.getResidefate() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getResidefate(), CheckinBak_.residefate));
            }
            if (criteria.getGotime() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getGotime(), CheckinBak_.gotime));
            }
            if (criteria.getEmpn() != null) {
                specification = specification.and(buildStringSpecification(criteria.getEmpn(), CheckinBak_.empn));
            }
            if (criteria.getRoomn() != null) {
                specification = specification.and(buildStringSpecification(criteria.getRoomn(), CheckinBak_.roomn));
            }
            if (criteria.getUname() != null) {
                specification = specification.and(buildStringSpecification(criteria.getUname(), CheckinBak_.uname));
            }
            if (criteria.getRentp() != null) {
                specification = specification.and(buildStringSpecification(criteria.getRentp(), CheckinBak_.rentp));
            }
            if (criteria.getProtocolrent() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getProtocolrent(), CheckinBak_.protocolrent));
            }
            if (criteria.getRemark() != null) {
                specification = specification.and(buildStringSpecification(criteria.getRemark(), CheckinBak_.remark));
            }
            if (criteria.getComeinfo() != null) {
                specification = specification.and(buildStringSpecification(criteria.getComeinfo(), CheckinBak_.comeinfo));
            }
            if (criteria.getGoinfo() != null) {
                specification = specification.and(buildStringSpecification(criteria.getGoinfo(), CheckinBak_.goinfo));
            }
            if (criteria.getPhonen() != null) {
                specification = specification.and(buildStringSpecification(criteria.getPhonen(), CheckinBak_.phonen));
            }
            if (criteria.getEmpn2() != null) {
                specification = specification.and(buildStringSpecification(criteria.getEmpn2(), CheckinBak_.empn2));
            }
            if (criteria.getAdhoc() != null) {
                specification = specification.and(buildStringSpecification(criteria.getAdhoc(), CheckinBak_.adhoc));
            }
            if (criteria.getAuditflag() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getAuditflag(), CheckinBak_.auditflag));
            }
            if (criteria.getGroupn() != null) {
                specification = specification.and(buildStringSpecification(criteria.getGroupn(), CheckinBak_.groupn));
            }
            if (criteria.getPayment() != null) {
                specification = specification.and(buildStringSpecification(criteria.getPayment(), CheckinBak_.payment));
            }
            if (criteria.getMtype() != null) {
                specification = specification.and(buildStringSpecification(criteria.getMtype(), CheckinBak_.mtype));
            }
            if (criteria.getMemo() != null) {
                specification = specification.and(buildStringSpecification(criteria.getMemo(), CheckinBak_.memo));
            }
            if (criteria.getFlight() != null) {
                specification = specification.and(buildStringSpecification(criteria.getFlight(), CheckinBak_.flight));
            }
            if (criteria.getCredit() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getCredit(), CheckinBak_.credit));
            }
            if (criteria.getTalklevel() != null) {
                specification = specification.and(buildStringSpecification(criteria.getTalklevel(), CheckinBak_.talklevel));
            }
            if (criteria.getLfSign() != null) {
                specification = specification.and(buildStringSpecification(criteria.getLfSign(), CheckinBak_.lfSign));
            }
            if (criteria.getKeynum() != null) {
                specification = specification.and(buildStringSpecification(criteria.getKeynum(), CheckinBak_.keynum));
            }
            if (criteria.getIcNum() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getIcNum(), CheckinBak_.icNum));
            }
            if (criteria.getBh() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getBh(), CheckinBak_.bh));
            }
            if (criteria.getIcOwner() != null) {
                specification = specification.and(buildStringSpecification(criteria.getIcOwner(), CheckinBak_.icOwner));
            }
            if (criteria.getMarkId() != null) {
                specification = specification.and(buildStringSpecification(criteria.getMarkId(), CheckinBak_.markId));
            }
            if (criteria.getGj() != null) {
                specification = specification.and(buildStringSpecification(criteria.getGj(), CheckinBak_.gj));
            }
            if (criteria.getYfj() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getYfj(), CheckinBak_.yfj));
            }
            if (criteria.getHoteldate() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getHoteldate(), CheckinBak_.hoteldate));
            }
        }
        return specification;
    }
}
