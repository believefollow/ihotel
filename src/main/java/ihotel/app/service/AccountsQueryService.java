package ihotel.app.service;

import ihotel.app.domain.*; // for static metamodels
import ihotel.app.domain.Accounts;
import ihotel.app.repository.AccountsRepository;
import ihotel.app.repository.search.AccountsSearchRepository;
import ihotel.app.service.criteria.AccountsCriteria;
import ihotel.app.service.dto.AccountsDTO;
import ihotel.app.service.mapper.AccountsMapper;
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
 * Service for executing complex queries for {@link Accounts} entities in the database.
 * The main input is a {@link AccountsCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link AccountsDTO} or a {@link Page} of {@link AccountsDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class AccountsQueryService extends QueryService<Accounts> {

    private final Logger log = LoggerFactory.getLogger(AccountsQueryService.class);

    private final AccountsRepository accountsRepository;

    private final AccountsMapper accountsMapper;

    private final AccountsSearchRepository accountsSearchRepository;

    public AccountsQueryService(
        AccountsRepository accountsRepository,
        AccountsMapper accountsMapper,
        AccountsSearchRepository accountsSearchRepository
    ) {
        this.accountsRepository = accountsRepository;
        this.accountsMapper = accountsMapper;
        this.accountsSearchRepository = accountsSearchRepository;
    }

    /**
     * Return a {@link List} of {@link AccountsDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<AccountsDTO> findByCriteria(AccountsCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<Accounts> specification = createSpecification(criteria);
        return accountsMapper.toDto(accountsRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link AccountsDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<AccountsDTO> findByCriteria(AccountsCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<Accounts> specification = createSpecification(criteria);
        return accountsRepository.findAll(specification, page).map(accountsMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(AccountsCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<Accounts> specification = createSpecification(criteria);
        return accountsRepository.count(specification);
    }

    /**
     * Function to convert {@link AccountsCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<Accounts> createSpecification(AccountsCriteria criteria) {
        Specification<Accounts> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), Accounts_.id));
            }
            if (criteria.getAccount() != null) {
                specification = specification.and(buildStringSpecification(criteria.getAccount(), Accounts_.account));
            }
            if (criteria.getConsumetime() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getConsumetime(), Accounts_.consumetime));
            }
            if (criteria.getHoteltime() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getHoteltime(), Accounts_.hoteltime));
            }
            if (criteria.getFeenum() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getFeenum(), Accounts_.feenum));
            }
            if (criteria.getMoney() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getMoney(), Accounts_.money));
            }
            if (criteria.getMemo() != null) {
                specification = specification.and(buildStringSpecification(criteria.getMemo(), Accounts_.memo));
            }
            if (criteria.getEmpn() != null) {
                specification = specification.and(buildStringSpecification(criteria.getEmpn(), Accounts_.empn));
            }
            if (criteria.getImprest() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getImprest(), Accounts_.imprest));
            }
            if (criteria.getPropertiy() != null) {
                specification = specification.and(buildStringSpecification(criteria.getPropertiy(), Accounts_.propertiy));
            }
            if (criteria.getEarntypen() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getEarntypen(), Accounts_.earntypen));
            }
            if (criteria.getPayment() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getPayment(), Accounts_.payment));
            }
            if (criteria.getRoomn() != null) {
                specification = specification.and(buildStringSpecification(criteria.getRoomn(), Accounts_.roomn));
            }
            if (criteria.getUlogogram() != null) {
                specification = specification.and(buildStringSpecification(criteria.getUlogogram(), Accounts_.ulogogram));
            }
            if (criteria.getLk() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getLk(), Accounts_.lk));
            }
            if (criteria.getAcc() != null) {
                specification = specification.and(buildStringSpecification(criteria.getAcc(), Accounts_.acc));
            }
            if (criteria.getJzSign() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getJzSign(), Accounts_.jzSign));
            }
            if (criteria.getJzflag() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getJzflag(), Accounts_.jzflag));
            }
            if (criteria.getSign() != null) {
                specification = specification.and(buildStringSpecification(criteria.getSign(), Accounts_.sign));
            }
            if (criteria.getBs() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getBs(), Accounts_.bs));
            }
            if (criteria.getJzhotel() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getJzhotel(), Accounts_.jzhotel));
            }
            if (criteria.getJzempn() != null) {
                specification = specification.and(buildStringSpecification(criteria.getJzempn(), Accounts_.jzempn));
            }
            if (criteria.getJztime() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getJztime(), Accounts_.jztime));
            }
            if (criteria.getChonghong() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getChonghong(), Accounts_.chonghong));
            }
            if (criteria.getBillno() != null) {
                specification = specification.and(buildStringSpecification(criteria.getBillno(), Accounts_.billno));
            }
            if (criteria.getPrintcount() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getPrintcount(), Accounts_.printcount));
            }
            if (criteria.getVipjf() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getVipjf(), Accounts_.vipjf));
            }
            if (criteria.getHykh() != null) {
                specification = specification.and(buildStringSpecification(criteria.getHykh(), Accounts_.hykh));
            }
            if (criteria.getSl() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getSl(), Accounts_.sl));
            }
            if (criteria.getSgdjh() != null) {
                specification = specification.and(buildStringSpecification(criteria.getSgdjh(), Accounts_.sgdjh));
            }
            if (criteria.getHoteldm() != null) {
                specification = specification.and(buildStringSpecification(criteria.getHoteldm(), Accounts_.hoteldm));
            }
            if (criteria.getIsnew() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getIsnew(), Accounts_.isnew));
            }
            if (criteria.getGuestId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getGuestId(), Accounts_.guestId));
            }
            if (criteria.getYhkh() != null) {
                specification = specification.and(buildStringSpecification(criteria.getYhkh(), Accounts_.yhkh));
            }
            if (criteria.getDjq() != null) {
                specification = specification.and(buildStringSpecification(criteria.getDjq(), Accounts_.djq));
            }
            if (criteria.getYsje() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getYsje(), Accounts_.ysje));
            }
            if (criteria.getBj() != null) {
                specification = specification.and(buildStringSpecification(criteria.getBj(), Accounts_.bj));
            }
            if (criteria.getBjempn() != null) {
                specification = specification.and(buildStringSpecification(criteria.getBjempn(), Accounts_.bjempn));
            }
            if (criteria.getBjtime() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getBjtime(), Accounts_.bjtime));
            }
            if (criteria.getPaper2() != null) {
                specification = specification.and(buildStringSpecification(criteria.getPaper2(), Accounts_.paper2));
            }
            if (criteria.getBc() != null) {
                specification = specification.and(buildStringSpecification(criteria.getBc(), Accounts_.bc));
            }
            if (criteria.getAuto() != null) {
                specification = specification.and(buildStringSpecification(criteria.getAuto(), Accounts_.auto));
            }
            if (criteria.getXsy() != null) {
                specification = specification.and(buildStringSpecification(criteria.getXsy(), Accounts_.xsy));
            }
            if (criteria.getDjkh() != null) {
                specification = specification.and(buildStringSpecification(criteria.getDjkh(), Accounts_.djkh));
            }
            if (criteria.getDjsign() != null) {
                specification = specification.and(buildStringSpecification(criteria.getDjsign(), Accounts_.djsign));
            }
            if (criteria.getClassname() != null) {
                specification = specification.and(buildStringSpecification(criteria.getClassname(), Accounts_.classname));
            }
            if (criteria.getIscy() != null) {
                specification = specification.and(buildStringSpecification(criteria.getIscy(), Accounts_.iscy));
            }
            if (criteria.getBsign() != null) {
                specification = specification.and(buildStringSpecification(criteria.getBsign(), Accounts_.bsign));
            }
            if (criteria.getFx() != null) {
                specification = specification.and(buildStringSpecification(criteria.getFx(), Accounts_.fx));
            }
            if (criteria.getDjlx() != null) {
                specification = specification.and(buildStringSpecification(criteria.getDjlx(), Accounts_.djlx));
            }
            if (criteria.getIsup() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getIsup(), Accounts_.isup));
            }
            if (criteria.getYongjin() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getYongjin(), Accounts_.yongjin));
            }
            if (criteria.getCzpc() != null) {
                specification = specification.and(buildStringSpecification(criteria.getCzpc(), Accounts_.czpc));
            }
            if (criteria.getCxflag() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getCxflag(), Accounts_.cxflag));
            }
            if (criteria.getPmemo() != null) {
                specification = specification.and(buildStringSpecification(criteria.getPmemo(), Accounts_.pmemo));
            }
            if (criteria.getCzbillno() != null) {
                specification = specification.and(buildStringSpecification(criteria.getCzbillno(), Accounts_.czbillno));
            }
            if (criteria.getDjqbz() != null) {
                specification = specification.and(buildStringSpecification(criteria.getDjqbz(), Accounts_.djqbz));
            }
            if (criteria.getYsqmemo() != null) {
                specification = specification.and(buildStringSpecification(criteria.getYsqmemo(), Accounts_.ysqmemo));
            }
            if (criteria.getTransactionId() != null) {
                specification = specification.and(buildStringSpecification(criteria.getTransactionId(), Accounts_.transactionId));
            }
            if (criteria.getOutTradeNo() != null) {
                specification = specification.and(buildStringSpecification(criteria.getOutTradeNo(), Accounts_.outTradeNo));
            }
            if (criteria.getGsname() != null) {
                specification = specification.and(buildStringSpecification(criteria.getGsname(), Accounts_.gsname));
            }
            if (criteria.getRz() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getRz(), Accounts_.rz));
            }
            if (criteria.getGz() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getGz(), Accounts_.gz));
            }
            if (criteria.getTs() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getTs(), Accounts_.ts));
            }
            if (criteria.getKy() != null) {
                specification = specification.and(buildStringSpecification(criteria.getKy(), Accounts_.ky));
            }
            if (criteria.getXy() != null) {
                specification = specification.and(buildStringSpecification(criteria.getXy(), Accounts_.xy));
            }
            if (criteria.getRoomtype() != null) {
                specification = specification.and(buildStringSpecification(criteria.getRoomtype(), Accounts_.roomtype));
            }
            if (criteria.getBkid() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getBkid(), Accounts_.bkid));
            }
        }
        return specification;
    }
}
