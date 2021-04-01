package ihotel.app.service;

import ihotel.app.domain.*; // for static metamodels
import ihotel.app.domain.Checkin;
import ihotel.app.repository.CheckinRepository;
import ihotel.app.repository.search.CheckinSearchRepository;
import ihotel.app.service.criteria.CheckinCriteria;
import ihotel.app.service.dto.CheckinDTO;
import ihotel.app.service.mapper.CheckinMapper;
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
 * Service for executing complex queries for {@link Checkin} entities in the database.
 * The main input is a {@link CheckinCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link CheckinDTO} or a {@link Page} of {@link CheckinDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class CheckinQueryService extends QueryService<Checkin> {

    private final Logger log = LoggerFactory.getLogger(CheckinQueryService.class);

    private final CheckinRepository checkinRepository;

    private final CheckinMapper checkinMapper;

    private final CheckinSearchRepository checkinSearchRepository;

    public CheckinQueryService(
        CheckinRepository checkinRepository,
        CheckinMapper checkinMapper,
        CheckinSearchRepository checkinSearchRepository
    ) {
        this.checkinRepository = checkinRepository;
        this.checkinMapper = checkinMapper;
        this.checkinSearchRepository = checkinSearchRepository;
    }

    /**
     * Return a {@link List} of {@link CheckinDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<CheckinDTO> findByCriteria(CheckinCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<Checkin> specification = createSpecification(criteria);
        return checkinMapper.toDto(checkinRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link CheckinDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<CheckinDTO> findByCriteria(CheckinCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<Checkin> specification = createSpecification(criteria);
        return checkinRepository.findAll(specification, page).map(checkinMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(CheckinCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<Checkin> specification = createSpecification(criteria);
        return checkinRepository.count(specification);
    }

    /**
     * Function to convert {@link CheckinCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<Checkin> createSpecification(CheckinCriteria criteria) {
        Specification<Checkin> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), Checkin_.id));
            }
            if (criteria.getBkid() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getBkid(), Checkin_.bkid));
            }
            if (criteria.getGuestId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getGuestId(), Checkin_.guestId));
            }
            if (criteria.getAccount() != null) {
                specification = specification.and(buildStringSpecification(criteria.getAccount(), Checkin_.account));
            }
            if (criteria.getHoteltime() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getHoteltime(), Checkin_.hoteltime));
            }
            if (criteria.getIndatetime() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getIndatetime(), Checkin_.indatetime));
            }
            if (criteria.getResidefate() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getResidefate(), Checkin_.residefate));
            }
            if (criteria.getGotime() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getGotime(), Checkin_.gotime));
            }
            if (criteria.getEmpn() != null) {
                specification = specification.and(buildStringSpecification(criteria.getEmpn(), Checkin_.empn));
            }
            if (criteria.getRoomn() != null) {
                specification = specification.and(buildStringSpecification(criteria.getRoomn(), Checkin_.roomn));
            }
            if (criteria.getUname() != null) {
                specification = specification.and(buildStringSpecification(criteria.getUname(), Checkin_.uname));
            }
            if (criteria.getRentp() != null) {
                specification = specification.and(buildStringSpecification(criteria.getRentp(), Checkin_.rentp));
            }
            if (criteria.getProtocolrent() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getProtocolrent(), Checkin_.protocolrent));
            }
            if (criteria.getRemark() != null) {
                specification = specification.and(buildStringSpecification(criteria.getRemark(), Checkin_.remark));
            }
            if (criteria.getPhonen() != null) {
                specification = specification.and(buildStringSpecification(criteria.getPhonen(), Checkin_.phonen));
            }
            if (criteria.getEmpn2() != null) {
                specification = specification.and(buildStringSpecification(criteria.getEmpn2(), Checkin_.empn2));
            }
            if (criteria.getAdhoc() != null) {
                specification = specification.and(buildStringSpecification(criteria.getAdhoc(), Checkin_.adhoc));
            }
            if (criteria.getAuditflag() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getAuditflag(), Checkin_.auditflag));
            }
            if (criteria.getGroupn() != null) {
                specification = specification.and(buildStringSpecification(criteria.getGroupn(), Checkin_.groupn));
            }
            if (criteria.getMemo() != null) {
                specification = specification.and(buildStringSpecification(criteria.getMemo(), Checkin_.memo));
            }
            if (criteria.getLfSign() != null) {
                specification = specification.and(buildStringSpecification(criteria.getLfSign(), Checkin_.lfSign));
            }
            if (criteria.getKeynum() != null) {
                specification = specification.and(buildStringSpecification(criteria.getKeynum(), Checkin_.keynum));
            }
            if (criteria.getHykh() != null) {
                specification = specification.and(buildStringSpecification(criteria.getHykh(), Checkin_.hykh));
            }
            if (criteria.getBm() != null) {
                specification = specification.and(buildStringSpecification(criteria.getBm(), Checkin_.bm));
            }
            if (criteria.getFlag() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getFlag(), Checkin_.flag));
            }
            if (criteria.getJxtime() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getJxtime(), Checkin_.jxtime));
            }
            if (criteria.getJxflag() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getJxflag(), Checkin_.jxflag));
            }
            if (criteria.getCheckf() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getCheckf(), Checkin_.checkf));
            }
            if (criteria.getGuestname() != null) {
                specification = specification.and(buildStringSpecification(criteria.getGuestname(), Checkin_.guestname));
            }
            if (criteria.getFgf() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getFgf(), Checkin_.fgf));
            }
            if (criteria.getFgxx() != null) {
                specification = specification.and(buildStringSpecification(criteria.getFgxx(), Checkin_.fgxx));
            }
            if (criteria.getHourSign() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getHourSign(), Checkin_.hourSign));
            }
            if (criteria.getXsy() != null) {
                specification = specification.and(buildStringSpecification(criteria.getXsy(), Checkin_.xsy));
            }
            if (criteria.getRzsign() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getRzsign(), Checkin_.rzsign));
            }
            if (criteria.getJf() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getJf(), Checkin_.jf));
            }
            if (criteria.getGname() != null) {
                specification = specification.and(buildStringSpecification(criteria.getGname(), Checkin_.gname));
            }
            if (criteria.getZcsign() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getZcsign(), Checkin_.zcsign));
            }
            if (criteria.getCqsl() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getCqsl(), Checkin_.cqsl));
            }
            if (criteria.getSfjf() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getSfjf(), Checkin_.sfjf));
            }
            if (criteria.getYwly() != null) {
                specification = specification.and(buildStringSpecification(criteria.getYwly(), Checkin_.ywly));
            }
            if (criteria.getFk() != null) {
                specification = specification.and(buildStringSpecification(criteria.getFk(), Checkin_.fk));
            }
            if (criteria.getFkrq() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getFkrq(), Checkin_.fkrq));
            }
            if (criteria.getBc() != null) {
                specification = specification.and(buildStringSpecification(criteria.getBc(), Checkin_.bc));
            }
            if (criteria.getJxremark() != null) {
                specification = specification.and(buildStringSpecification(criteria.getJxremark(), Checkin_.jxremark));
            }
            if (criteria.getTxid() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getTxid(), Checkin_.txid));
            }
            if (criteria.getCfr() != null) {
                specification = specification.and(buildStringSpecification(criteria.getCfr(), Checkin_.cfr));
            }
            if (criteria.getFjbm() != null) {
                specification = specification.and(buildStringSpecification(criteria.getFjbm(), Checkin_.fjbm));
            }
            if (criteria.getDjlx() != null) {
                specification = specification.and(buildStringSpecification(criteria.getDjlx(), Checkin_.djlx));
            }
            if (criteria.getWlddh() != null) {
                specification = specification.and(buildStringSpecification(criteria.getWlddh(), Checkin_.wlddh));
            }
            if (criteria.getFksl() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getFksl(), Checkin_.fksl));
            }
            if (criteria.getDqtx() != null) {
                specification = specification.and(buildStringSpecification(criteria.getDqtx(), Checkin_.dqtx));
            }
        }
        return specification;
    }
}
