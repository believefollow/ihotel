package ihotel.app.service;

import ihotel.app.domain.*; // for static metamodels
import ihotel.app.domain.FtXzs;
import ihotel.app.repository.FtXzsRepository;
import ihotel.app.repository.search.FtXzsSearchRepository;
import ihotel.app.service.criteria.FtXzsCriteria;
import ihotel.app.service.dto.FtXzsDTO;
import ihotel.app.service.mapper.FtXzsMapper;
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
 * Service for executing complex queries for {@link FtXzs} entities in the database.
 * The main input is a {@link FtXzsCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link FtXzsDTO} or a {@link Page} of {@link FtXzsDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class FtXzsQueryService extends QueryService<FtXzs> {

    private final Logger log = LoggerFactory.getLogger(FtXzsQueryService.class);

    private final FtXzsRepository ftXzsRepository;

    private final FtXzsMapper ftXzsMapper;

    private final FtXzsSearchRepository ftXzsSearchRepository;

    public FtXzsQueryService(FtXzsRepository ftXzsRepository, FtXzsMapper ftXzsMapper, FtXzsSearchRepository ftXzsSearchRepository) {
        this.ftXzsRepository = ftXzsRepository;
        this.ftXzsMapper = ftXzsMapper;
        this.ftXzsSearchRepository = ftXzsSearchRepository;
    }

    /**
     * Return a {@link List} of {@link FtXzsDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<FtXzsDTO> findByCriteria(FtXzsCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<FtXzs> specification = createSpecification(criteria);
        return ftXzsMapper.toDto(ftXzsRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link FtXzsDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<FtXzsDTO> findByCriteria(FtXzsCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<FtXzs> specification = createSpecification(criteria);
        return ftXzsRepository.findAll(specification, page).map(ftXzsMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(FtXzsCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<FtXzs> specification = createSpecification(criteria);
        return ftXzsRepository.count(specification);
    }

    /**
     * Function to convert {@link FtXzsCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<FtXzs> createSpecification(FtXzsCriteria criteria) {
        Specification<FtXzs> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), FtXzs_.id));
            }
            if (criteria.getRoomn() != null) {
                specification = specification.and(buildStringSpecification(criteria.getRoomn(), FtXzs_.roomn));
            }
        }
        return specification;
    }
}
