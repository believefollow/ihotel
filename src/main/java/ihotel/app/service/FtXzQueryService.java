package ihotel.app.service;

import ihotel.app.domain.*; // for static metamodels
import ihotel.app.domain.FtXz;
import ihotel.app.repository.FtXzRepository;
import ihotel.app.repository.search.FtXzSearchRepository;
import ihotel.app.service.criteria.FtXzCriteria;
import ihotel.app.service.dto.FtXzDTO;
import ihotel.app.service.mapper.FtXzMapper;
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
 * Service for executing complex queries for {@link FtXz} entities in the database.
 * The main input is a {@link FtXzCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link FtXzDTO} or a {@link Page} of {@link FtXzDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class FtXzQueryService extends QueryService<FtXz> {

    private final Logger log = LoggerFactory.getLogger(FtXzQueryService.class);

    private final FtXzRepository ftXzRepository;

    private final FtXzMapper ftXzMapper;

    private final FtXzSearchRepository ftXzSearchRepository;

    public FtXzQueryService(FtXzRepository ftXzRepository, FtXzMapper ftXzMapper, FtXzSearchRepository ftXzSearchRepository) {
        this.ftXzRepository = ftXzRepository;
        this.ftXzMapper = ftXzMapper;
        this.ftXzSearchRepository = ftXzSearchRepository;
    }

    /**
     * Return a {@link List} of {@link FtXzDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<FtXzDTO> findByCriteria(FtXzCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<FtXz> specification = createSpecification(criteria);
        return ftXzMapper.toDto(ftXzRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link FtXzDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<FtXzDTO> findByCriteria(FtXzCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<FtXz> specification = createSpecification(criteria);
        return ftXzRepository.findAll(specification, page).map(ftXzMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(FtXzCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<FtXz> specification = createSpecification(criteria);
        return ftXzRepository.count(specification);
    }

    /**
     * Function to convert {@link FtXzCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<FtXz> createSpecification(FtXzCriteria criteria) {
        Specification<FtXz> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), FtXz_.id));
            }
            if (criteria.getRoomn() != null) {
                specification = specification.and(buildStringSpecification(criteria.getRoomn(), FtXz_.roomn));
            }
        }
        return specification;
    }
}
