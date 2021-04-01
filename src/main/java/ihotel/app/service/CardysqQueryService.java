package ihotel.app.service;

import ihotel.app.domain.*; // for static metamodels
import ihotel.app.domain.Cardysq;
import ihotel.app.repository.CardysqRepository;
import ihotel.app.repository.search.CardysqSearchRepository;
import ihotel.app.service.criteria.CardysqCriteria;
import ihotel.app.service.dto.CardysqDTO;
import ihotel.app.service.mapper.CardysqMapper;
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
 * Service for executing complex queries for {@link Cardysq} entities in the database.
 * The main input is a {@link CardysqCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link CardysqDTO} or a {@link Page} of {@link CardysqDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class CardysqQueryService extends QueryService<Cardysq> {

    private final Logger log = LoggerFactory.getLogger(CardysqQueryService.class);

    private final CardysqRepository cardysqRepository;

    private final CardysqMapper cardysqMapper;

    private final CardysqSearchRepository cardysqSearchRepository;

    public CardysqQueryService(
        CardysqRepository cardysqRepository,
        CardysqMapper cardysqMapper,
        CardysqSearchRepository cardysqSearchRepository
    ) {
        this.cardysqRepository = cardysqRepository;
        this.cardysqMapper = cardysqMapper;
        this.cardysqSearchRepository = cardysqSearchRepository;
    }

    /**
     * Return a {@link List} of {@link CardysqDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<CardysqDTO> findByCriteria(CardysqCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<Cardysq> specification = createSpecification(criteria);
        return cardysqMapper.toDto(cardysqRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link CardysqDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<CardysqDTO> findByCriteria(CardysqCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<Cardysq> specification = createSpecification(criteria);
        return cardysqRepository.findAll(specification, page).map(cardysqMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(CardysqCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<Cardysq> specification = createSpecification(criteria);
        return cardysqRepository.count(specification);
    }

    /**
     * Function to convert {@link CardysqCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<Cardysq> createSpecification(CardysqCriteria criteria) {
        Specification<Cardysq> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), Cardysq_.id));
            }
            if (criteria.getRoomn() != null) {
                specification = specification.and(buildStringSpecification(criteria.getRoomn(), Cardysq_.roomn));
            }
            if (criteria.getGuestname() != null) {
                specification = specification.and(buildStringSpecification(criteria.getGuestname(), Cardysq_.guestname));
            }
            if (criteria.getAccount() != null) {
                specification = specification.and(buildStringSpecification(criteria.getAccount(), Cardysq_.account));
            }
            if (criteria.getRq() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getRq(), Cardysq_.rq));
            }
            if (criteria.getCardid() != null) {
                specification = specification.and(buildStringSpecification(criteria.getCardid(), Cardysq_.cardid));
            }
            if (criteria.getDjh() != null) {
                specification = specification.and(buildStringSpecification(criteria.getDjh(), Cardysq_.djh));
            }
            if (criteria.getSqh() != null) {
                specification = specification.and(buildStringSpecification(criteria.getSqh(), Cardysq_.sqh));
            }
            if (criteria.getEmpn() != null) {
                specification = specification.and(buildStringSpecification(criteria.getEmpn(), Cardysq_.empn));
            }
            if (criteria.getSign() != null) {
                specification = specification.and(buildStringSpecification(criteria.getSign(), Cardysq_.sign));
            }
            if (criteria.getHoteltime() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getHoteltime(), Cardysq_.hoteltime));
            }
            if (criteria.getYxrq() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getYxrq(), Cardysq_.yxrq));
            }
            if (criteria.getJe() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getJe(), Cardysq_.je));
            }
            if (criteria.getYsqmemo() != null) {
                specification = specification.and(buildStringSpecification(criteria.getYsqmemo(), Cardysq_.ysqmemo));
            }
        }
        return specification;
    }
}
