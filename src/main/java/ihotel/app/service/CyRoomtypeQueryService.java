package ihotel.app.service;

import ihotel.app.domain.*; // for static metamodels
import ihotel.app.domain.CyRoomtype;
import ihotel.app.repository.CyRoomtypeRepository;
import ihotel.app.repository.search.CyRoomtypeSearchRepository;
import ihotel.app.service.criteria.CyRoomtypeCriteria;
import ihotel.app.service.dto.CyRoomtypeDTO;
import ihotel.app.service.mapper.CyRoomtypeMapper;
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
 * Service for executing complex queries for {@link CyRoomtype} entities in the database.
 * The main input is a {@link CyRoomtypeCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link CyRoomtypeDTO} or a {@link Page} of {@link CyRoomtypeDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class CyRoomtypeQueryService extends QueryService<CyRoomtype> {

    private final Logger log = LoggerFactory.getLogger(CyRoomtypeQueryService.class);

    private final CyRoomtypeRepository cyRoomtypeRepository;

    private final CyRoomtypeMapper cyRoomtypeMapper;

    private final CyRoomtypeSearchRepository cyRoomtypeSearchRepository;

    public CyRoomtypeQueryService(
        CyRoomtypeRepository cyRoomtypeRepository,
        CyRoomtypeMapper cyRoomtypeMapper,
        CyRoomtypeSearchRepository cyRoomtypeSearchRepository
    ) {
        this.cyRoomtypeRepository = cyRoomtypeRepository;
        this.cyRoomtypeMapper = cyRoomtypeMapper;
        this.cyRoomtypeSearchRepository = cyRoomtypeSearchRepository;
    }

    /**
     * Return a {@link List} of {@link CyRoomtypeDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<CyRoomtypeDTO> findByCriteria(CyRoomtypeCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<CyRoomtype> specification = createSpecification(criteria);
        return cyRoomtypeMapper.toDto(cyRoomtypeRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link CyRoomtypeDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<CyRoomtypeDTO> findByCriteria(CyRoomtypeCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<CyRoomtype> specification = createSpecification(criteria);
        return cyRoomtypeRepository.findAll(specification, page).map(cyRoomtypeMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(CyRoomtypeCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<CyRoomtype> specification = createSpecification(criteria);
        return cyRoomtypeRepository.count(specification);
    }

    /**
     * Function to convert {@link CyRoomtypeCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<CyRoomtype> createSpecification(CyRoomtypeCriteria criteria) {
        Specification<CyRoomtype> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), CyRoomtype_.id));
            }
            if (criteria.getRtdm() != null) {
                specification = specification.and(buildStringSpecification(criteria.getRtdm(), CyRoomtype_.rtdm));
            }
            if (criteria.getMinc() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getMinc(), CyRoomtype_.minc));
            }
            if (criteria.getServicerate() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getServicerate(), CyRoomtype_.servicerate));
            }
            if (criteria.getPrinter() != null) {
                specification = specification.and(buildStringSpecification(criteria.getPrinter(), CyRoomtype_.printer));
            }
            if (criteria.getPrintnum() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getPrintnum(), CyRoomtype_.printnum));
            }
        }
        return specification;
    }
}
