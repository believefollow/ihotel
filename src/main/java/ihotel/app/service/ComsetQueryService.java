package ihotel.app.service;

import ihotel.app.domain.*; // for static metamodels
import ihotel.app.domain.Comset;
import ihotel.app.repository.ComsetRepository;
import ihotel.app.repository.search.ComsetSearchRepository;
import ihotel.app.service.criteria.ComsetCriteria;
import ihotel.app.service.dto.ComsetDTO;
import ihotel.app.service.mapper.ComsetMapper;
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
 * Service for executing complex queries for {@link Comset} entities in the database.
 * The main input is a {@link ComsetCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link ComsetDTO} or a {@link Page} of {@link ComsetDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class ComsetQueryService extends QueryService<Comset> {

    private final Logger log = LoggerFactory.getLogger(ComsetQueryService.class);

    private final ComsetRepository comsetRepository;

    private final ComsetMapper comsetMapper;

    private final ComsetSearchRepository comsetSearchRepository;

    public ComsetQueryService(ComsetRepository comsetRepository, ComsetMapper comsetMapper, ComsetSearchRepository comsetSearchRepository) {
        this.comsetRepository = comsetRepository;
        this.comsetMapper = comsetMapper;
        this.comsetSearchRepository = comsetSearchRepository;
    }

    /**
     * Return a {@link List} of {@link ComsetDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<ComsetDTO> findByCriteria(ComsetCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<Comset> specification = createSpecification(criteria);
        return comsetMapper.toDto(comsetRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link ComsetDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<ComsetDTO> findByCriteria(ComsetCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<Comset> specification = createSpecification(criteria);
        return comsetRepository.findAll(specification, page).map(comsetMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(ComsetCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<Comset> specification = createSpecification(criteria);
        return comsetRepository.count(specification);
    }

    /**
     * Function to convert {@link ComsetCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<Comset> createSpecification(ComsetCriteria criteria) {
        Specification<Comset> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), Comset_.id));
            }
            if (criteria.getComNum() != null) {
                specification = specification.and(buildStringSpecification(criteria.getComNum(), Comset_.comNum));
            }
            if (criteria.getComBytes() != null) {
                specification = specification.and(buildStringSpecification(criteria.getComBytes(), Comset_.comBytes));
            }
            if (criteria.getComDatabit() != null) {
                specification = specification.and(buildStringSpecification(criteria.getComDatabit(), Comset_.comDatabit));
            }
            if (criteria.getComParitycheck() != null) {
                specification = specification.and(buildStringSpecification(criteria.getComParitycheck(), Comset_.comParitycheck));
            }
            if (criteria.getComStopbit() != null) {
                specification = specification.and(buildStringSpecification(criteria.getComStopbit(), Comset_.comStopbit));
            }
            if (criteria.getComFunction() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getComFunction(), Comset_.comFunction));
            }
        }
        return specification;
    }
}
