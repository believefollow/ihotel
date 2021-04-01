package ihotel.app.service;

import ihotel.app.domain.*; // for static metamodels
import ihotel.app.domain.ClassreportRoom;
import ihotel.app.repository.ClassreportRoomRepository;
import ihotel.app.repository.search.ClassreportRoomSearchRepository;
import ihotel.app.service.criteria.ClassreportRoomCriteria;
import ihotel.app.service.dto.ClassreportRoomDTO;
import ihotel.app.service.mapper.ClassreportRoomMapper;
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
 * Service for executing complex queries for {@link ClassreportRoom} entities in the database.
 * The main input is a {@link ClassreportRoomCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link ClassreportRoomDTO} or a {@link Page} of {@link ClassreportRoomDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class ClassreportRoomQueryService extends QueryService<ClassreportRoom> {

    private final Logger log = LoggerFactory.getLogger(ClassreportRoomQueryService.class);

    private final ClassreportRoomRepository classreportRoomRepository;

    private final ClassreportRoomMapper classreportRoomMapper;

    private final ClassreportRoomSearchRepository classreportRoomSearchRepository;

    public ClassreportRoomQueryService(
        ClassreportRoomRepository classreportRoomRepository,
        ClassreportRoomMapper classreportRoomMapper,
        ClassreportRoomSearchRepository classreportRoomSearchRepository
    ) {
        this.classreportRoomRepository = classreportRoomRepository;
        this.classreportRoomMapper = classreportRoomMapper;
        this.classreportRoomSearchRepository = classreportRoomSearchRepository;
    }

    /**
     * Return a {@link List} of {@link ClassreportRoomDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<ClassreportRoomDTO> findByCriteria(ClassreportRoomCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<ClassreportRoom> specification = createSpecification(criteria);
        return classreportRoomMapper.toDto(classreportRoomRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link ClassreportRoomDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<ClassreportRoomDTO> findByCriteria(ClassreportRoomCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<ClassreportRoom> specification = createSpecification(criteria);
        return classreportRoomRepository.findAll(specification, page).map(classreportRoomMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(ClassreportRoomCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<ClassreportRoom> specification = createSpecification(criteria);
        return classreportRoomRepository.count(specification);
    }

    /**
     * Function to convert {@link ClassreportRoomCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<ClassreportRoom> createSpecification(ClassreportRoomCriteria criteria) {
        Specification<ClassreportRoom> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), ClassreportRoom_.id));
            }
            if (criteria.getAccount() != null) {
                specification = specification.and(buildStringSpecification(criteria.getAccount(), ClassreportRoom_.account));
            }
            if (criteria.getRoomn() != null) {
                specification = specification.and(buildStringSpecification(criteria.getRoomn(), ClassreportRoom_.roomn));
            }
            if (criteria.getYfj() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getYfj(), ClassreportRoom_.yfj));
            }
            if (criteria.getYfj9008() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getYfj9008(), ClassreportRoom_.yfj9008));
            }
            if (criteria.getYfj9009() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getYfj9009(), ClassreportRoom_.yfj9009));
            }
            if (criteria.getYfj9007() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getYfj9007(), ClassreportRoom_.yfj9007));
            }
            if (criteria.getGz() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getGz(), ClassreportRoom_.gz));
            }
            if (criteria.getFf() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getFf(), ClassreportRoom_.ff));
            }
            if (criteria.getMinibar() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getMinibar(), ClassreportRoom_.minibar));
            }
            if (criteria.getPhone() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getPhone(), ClassreportRoom_.phone));
            }
            if (criteria.getOther() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getOther(), ClassreportRoom_.other));
            }
            if (criteria.getPc() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getPc(), ClassreportRoom_.pc));
            }
            if (criteria.getCz() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getCz(), ClassreportRoom_.cz));
            }
            if (criteria.getCy() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getCy(), ClassreportRoom_.cy));
            }
            if (criteria.getMd() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getMd(), ClassreportRoom_.md));
            }
            if (criteria.getHuiy() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getHuiy(), ClassreportRoom_.huiy));
            }
            if (criteria.getDtb() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getDtb(), ClassreportRoom_.dtb));
            }
            if (criteria.getSszx() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getSszx(), ClassreportRoom_.sszx));
            }
        }
        return specification;
    }
}
