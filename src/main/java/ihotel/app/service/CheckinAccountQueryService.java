package ihotel.app.service;

import ihotel.app.domain.*; // for static metamodels
import ihotel.app.domain.CheckinAccount;
import ihotel.app.repository.CheckinAccountRepository;
import ihotel.app.repository.search.CheckinAccountSearchRepository;
import ihotel.app.service.criteria.CheckinAccountCriteria;
import ihotel.app.service.dto.CheckinAccountDTO;
import ihotel.app.service.mapper.CheckinAccountMapper;
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
 * Service for executing complex queries for {@link CheckinAccount} entities in the database.
 * The main input is a {@link CheckinAccountCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link CheckinAccountDTO} or a {@link Page} of {@link CheckinAccountDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class CheckinAccountQueryService extends QueryService<CheckinAccount> {

    private final Logger log = LoggerFactory.getLogger(CheckinAccountQueryService.class);

    private final CheckinAccountRepository checkinAccountRepository;

    private final CheckinAccountMapper checkinAccountMapper;

    private final CheckinAccountSearchRepository checkinAccountSearchRepository;

    public CheckinAccountQueryService(
        CheckinAccountRepository checkinAccountRepository,
        CheckinAccountMapper checkinAccountMapper,
        CheckinAccountSearchRepository checkinAccountSearchRepository
    ) {
        this.checkinAccountRepository = checkinAccountRepository;
        this.checkinAccountMapper = checkinAccountMapper;
        this.checkinAccountSearchRepository = checkinAccountSearchRepository;
    }

    /**
     * Return a {@link List} of {@link CheckinAccountDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<CheckinAccountDTO> findByCriteria(CheckinAccountCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<CheckinAccount> specification = createSpecification(criteria);
        return checkinAccountMapper.toDto(checkinAccountRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link CheckinAccountDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<CheckinAccountDTO> findByCriteria(CheckinAccountCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<CheckinAccount> specification = createSpecification(criteria);
        return checkinAccountRepository.findAll(specification, page).map(checkinAccountMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(CheckinAccountCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<CheckinAccount> specification = createSpecification(criteria);
        return checkinAccountRepository.count(specification);
    }

    /**
     * Function to convert {@link CheckinAccountCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<CheckinAccount> createSpecification(CheckinAccountCriteria criteria) {
        Specification<CheckinAccount> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), CheckinAccount_.id));
            }
            if (criteria.getAccount() != null) {
                specification = specification.and(buildStringSpecification(criteria.getAccount(), CheckinAccount_.account));
            }
            if (criteria.getRoomn() != null) {
                specification = specification.and(buildStringSpecification(criteria.getRoomn(), CheckinAccount_.roomn));
            }
            if (criteria.getIndatetime() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getIndatetime(), CheckinAccount_.indatetime));
            }
            if (criteria.getGotime() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getGotime(), CheckinAccount_.gotime));
            }
            if (criteria.getKfang() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getKfang(), CheckinAccount_.kfang));
            }
            if (criteria.getDhua() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getDhua(), CheckinAccount_.dhua));
            }
            if (criteria.getMinin() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getMinin(), CheckinAccount_.minin));
            }
            if (criteria.getPeich() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getPeich(), CheckinAccount_.peich));
            }
            if (criteria.getQit() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getQit(), CheckinAccount_.qit));
            }
            if (criteria.getTotal() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getTotal(), CheckinAccount_.total));
            }
        }
        return specification;
    }
}
