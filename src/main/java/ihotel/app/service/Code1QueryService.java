package ihotel.app.service;

import ihotel.app.domain.*; // for static metamodels
import ihotel.app.domain.Code1;
import ihotel.app.repository.Code1Repository;
import ihotel.app.repository.search.Code1SearchRepository;
import ihotel.app.service.criteria.Code1Criteria;
import ihotel.app.service.dto.Code1DTO;
import ihotel.app.service.mapper.Code1Mapper;
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
 * Service for executing complex queries for {@link Code1} entities in the database.
 * The main input is a {@link Code1Criteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link Code1DTO} or a {@link Page} of {@link Code1DTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class Code1QueryService extends QueryService<Code1> {

    private final Logger log = LoggerFactory.getLogger(Code1QueryService.class);

    private final Code1Repository code1Repository;

    private final Code1Mapper code1Mapper;

    private final Code1SearchRepository code1SearchRepository;

    public Code1QueryService(Code1Repository code1Repository, Code1Mapper code1Mapper, Code1SearchRepository code1SearchRepository) {
        this.code1Repository = code1Repository;
        this.code1Mapper = code1Mapper;
        this.code1SearchRepository = code1SearchRepository;
    }

    /**
     * Return a {@link List} of {@link Code1DTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<Code1DTO> findByCriteria(Code1Criteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<Code1> specification = createSpecification(criteria);
        return code1Mapper.toDto(code1Repository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link Code1DTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<Code1DTO> findByCriteria(Code1Criteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<Code1> specification = createSpecification(criteria);
        return code1Repository.findAll(specification, page).map(code1Mapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(Code1Criteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<Code1> specification = createSpecification(criteria);
        return code1Repository.count(specification);
    }

    /**
     * Function to convert {@link Code1Criteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<Code1> createSpecification(Code1Criteria criteria) {
        Specification<Code1> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), Code1_.id));
            }
            if (criteria.getCode1() != null) {
                specification = specification.and(buildStringSpecification(criteria.getCode1(), Code1_.code1));
            }
            if (criteria.getCode2() != null) {
                specification = specification.and(buildStringSpecification(criteria.getCode2(), Code1_.code2));
            }
        }
        return specification;
    }
}
