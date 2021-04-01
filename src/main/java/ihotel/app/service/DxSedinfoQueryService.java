package ihotel.app.service;

import ihotel.app.domain.*; // for static metamodels
import ihotel.app.domain.DxSedinfo;
import ihotel.app.repository.DxSedinfoRepository;
import ihotel.app.repository.search.DxSedinfoSearchRepository;
import ihotel.app.service.criteria.DxSedinfoCriteria;
import ihotel.app.service.dto.DxSedinfoDTO;
import ihotel.app.service.mapper.DxSedinfoMapper;
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
 * Service for executing complex queries for {@link DxSedinfo} entities in the database.
 * The main input is a {@link DxSedinfoCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link DxSedinfoDTO} or a {@link Page} of {@link DxSedinfoDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class DxSedinfoQueryService extends QueryService<DxSedinfo> {

    private final Logger log = LoggerFactory.getLogger(DxSedinfoQueryService.class);

    private final DxSedinfoRepository dxSedinfoRepository;

    private final DxSedinfoMapper dxSedinfoMapper;

    private final DxSedinfoSearchRepository dxSedinfoSearchRepository;

    public DxSedinfoQueryService(
        DxSedinfoRepository dxSedinfoRepository,
        DxSedinfoMapper dxSedinfoMapper,
        DxSedinfoSearchRepository dxSedinfoSearchRepository
    ) {
        this.dxSedinfoRepository = dxSedinfoRepository;
        this.dxSedinfoMapper = dxSedinfoMapper;
        this.dxSedinfoSearchRepository = dxSedinfoSearchRepository;
    }

    /**
     * Return a {@link List} of {@link DxSedinfoDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<DxSedinfoDTO> findByCriteria(DxSedinfoCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<DxSedinfo> specification = createSpecification(criteria);
        return dxSedinfoMapper.toDto(dxSedinfoRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link DxSedinfoDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<DxSedinfoDTO> findByCriteria(DxSedinfoCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<DxSedinfo> specification = createSpecification(criteria);
        return dxSedinfoRepository.findAll(specification, page).map(dxSedinfoMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(DxSedinfoCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<DxSedinfo> specification = createSpecification(criteria);
        return dxSedinfoRepository.count(specification);
    }

    /**
     * Function to convert {@link DxSedinfoCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<DxSedinfo> createSpecification(DxSedinfoCriteria criteria) {
        Specification<DxSedinfo> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), DxSedinfo_.id));
            }
            if (criteria.getYddx() != null) {
                specification = specification.and(buildStringSpecification(criteria.getYddx(), DxSedinfo_.yddx));
            }
            if (criteria.getYddxmemo() != null) {
                specification = specification.and(buildStringSpecification(criteria.getYddxmemo(), DxSedinfo_.yddxmemo));
            }
            if (criteria.getQxyddx() != null) {
                specification = specification.and(buildStringSpecification(criteria.getQxyddx(), DxSedinfo_.qxyddx));
            }
            if (criteria.getQxydmemo() != null) {
                specification = specification.and(buildStringSpecification(criteria.getQxydmemo(), DxSedinfo_.qxydmemo));
            }
            if (criteria.getCzdx() != null) {
                specification = specification.and(buildStringSpecification(criteria.getCzdx(), DxSedinfo_.czdx));
            }
            if (criteria.getCzmemo() != null) {
                specification = specification.and(buildStringSpecification(criteria.getCzmemo(), DxSedinfo_.czmemo));
            }
            if (criteria.getQxczdx() != null) {
                specification = specification.and(buildStringSpecification(criteria.getQxczdx(), DxSedinfo_.qxczdx));
            }
            if (criteria.getQxczmemo() != null) {
                specification = specification.and(buildStringSpecification(criteria.getQxczmemo(), DxSedinfo_.qxczmemo));
            }
            if (criteria.getYyedx() != null) {
                specification = specification.and(buildStringSpecification(criteria.getYyedx(), DxSedinfo_.yyedx));
            }
            if (criteria.getYyememo() != null) {
                specification = specification.and(buildStringSpecification(criteria.getYyememo(), DxSedinfo_.yyememo));
            }
            if (criteria.getFstime() != null) {
                specification = specification.and(buildStringSpecification(criteria.getFstime(), DxSedinfo_.fstime));
            }
            if (criteria.getSffshm() != null) {
                specification = specification.and(buildStringSpecification(criteria.getSffshm(), DxSedinfo_.sffshm));
            }
            if (criteria.getRzdx() != null) {
                specification = specification.and(buildStringSpecification(criteria.getRzdx(), DxSedinfo_.rzdx));
            }
            if (criteria.getRzdxroomn() != null) {
                specification = specification.and(buildStringSpecification(criteria.getRzdxroomn(), DxSedinfo_.rzdxroomn));
            }
            if (criteria.getJfdz() != null) {
                specification = specification.and(buildStringSpecification(criteria.getJfdz(), DxSedinfo_.jfdz));
            }
            if (criteria.getBlhy() != null) {
                specification = specification.and(buildStringSpecification(criteria.getBlhy(), DxSedinfo_.blhy));
            }
            if (criteria.getRzmemo() != null) {
                specification = specification.and(buildStringSpecification(criteria.getRzmemo(), DxSedinfo_.rzmemo));
            }
            if (criteria.getBlhymemo() != null) {
                specification = specification.and(buildStringSpecification(criteria.getBlhymemo(), DxSedinfo_.blhymemo));
            }
            if (criteria.getTfdx() != null) {
                specification = specification.and(buildStringSpecification(criteria.getTfdx(), DxSedinfo_.tfdx));
            }
            if (criteria.getTfdxmemo() != null) {
                specification = specification.and(buildStringSpecification(criteria.getTfdxmemo(), DxSedinfo_.tfdxmemo));
            }
            if (criteria.getFslb() != null) {
                specification = specification.and(buildStringSpecification(criteria.getFslb(), DxSedinfo_.fslb));
            }
            if (criteria.getFslbmemo() != null) {
                specification = specification.and(buildStringSpecification(criteria.getFslbmemo(), DxSedinfo_.fslbmemo));
            }
        }
        return specification;
    }
}
