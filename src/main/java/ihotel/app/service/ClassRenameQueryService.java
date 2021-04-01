package ihotel.app.service;

import ihotel.app.domain.*; // for static metamodels
import ihotel.app.domain.ClassRename;
import ihotel.app.repository.ClassRenameRepository;
import ihotel.app.repository.search.ClassRenameSearchRepository;
import ihotel.app.service.criteria.ClassRenameCriteria;
import ihotel.app.service.dto.ClassRenameDTO;
import ihotel.app.service.mapper.ClassRenameMapper;
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
 * Service for executing complex queries for {@link ClassRename} entities in the database.
 * The main input is a {@link ClassRenameCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link ClassRenameDTO} or a {@link Page} of {@link ClassRenameDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class ClassRenameQueryService extends QueryService<ClassRename> {

    private final Logger log = LoggerFactory.getLogger(ClassRenameQueryService.class);

    private final ClassRenameRepository classRenameRepository;

    private final ClassRenameMapper classRenameMapper;

    private final ClassRenameSearchRepository classRenameSearchRepository;

    public ClassRenameQueryService(
        ClassRenameRepository classRenameRepository,
        ClassRenameMapper classRenameMapper,
        ClassRenameSearchRepository classRenameSearchRepository
    ) {
        this.classRenameRepository = classRenameRepository;
        this.classRenameMapper = classRenameMapper;
        this.classRenameSearchRepository = classRenameSearchRepository;
    }

    /**
     * Return a {@link List} of {@link ClassRenameDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<ClassRenameDTO> findByCriteria(ClassRenameCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<ClassRename> specification = createSpecification(criteria);
        return classRenameMapper.toDto(classRenameRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link ClassRenameDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<ClassRenameDTO> findByCriteria(ClassRenameCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<ClassRename> specification = createSpecification(criteria);
        return classRenameRepository.findAll(specification, page).map(classRenameMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(ClassRenameCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<ClassRename> specification = createSpecification(criteria);
        return classRenameRepository.count(specification);
    }

    /**
     * Function to convert {@link ClassRenameCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<ClassRename> createSpecification(ClassRenameCriteria criteria) {
        Specification<ClassRename> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), ClassRename_.id));
            }
            if (criteria.getDt() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getDt(), ClassRename_.dt));
            }
            if (criteria.getEmpn() != null) {
                specification = specification.and(buildStringSpecification(criteria.getEmpn(), ClassRename_.empn));
            }
            if (criteria.getOldmoney() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getOldmoney(), ClassRename_.oldmoney));
            }
            if (criteria.getGetmoney() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getGetmoney(), ClassRename_.getmoney));
            }
            if (criteria.getToup() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getToup(), ClassRename_.toup));
            }
            if (criteria.getDownempn() != null) {
                specification = specification.and(buildStringSpecification(criteria.getDownempn(), ClassRename_.downempn));
            }
            if (criteria.getTodown() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getTodown(), ClassRename_.todown));
            }
            if (criteria.getFlag() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getFlag(), ClassRename_.flag));
            }
            if (criteria.getOld2() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getOld2(), ClassRename_.old2));
            }
            if (criteria.getGet2() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getGet2(), ClassRename_.get2));
            }
            if (criteria.getToup2() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getToup2(), ClassRename_.toup2));
            }
            if (criteria.getTodown2() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getTodown2(), ClassRename_.todown2));
            }
            if (criteria.getUpempn2() != null) {
                specification = specification.and(buildStringSpecification(criteria.getUpempn2(), ClassRename_.upempn2));
            }
            if (criteria.getIm9008() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getIm9008(), ClassRename_.im9008));
            }
            if (criteria.getIm9009() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getIm9009(), ClassRename_.im9009));
            }
            if (criteria.getCo9991() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getCo9991(), ClassRename_.co9991));
            }
            if (criteria.getCo9992() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getCo9992(), ClassRename_.co9992));
            }
            if (criteria.getCo9993() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getCo9993(), ClassRename_.co9993));
            }
            if (criteria.getCo9994() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getCo9994(), ClassRename_.co9994));
            }
            if (criteria.getCo9995() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getCo9995(), ClassRename_.co9995));
            }
            if (criteria.getCo9998() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getCo9998(), ClassRename_.co9998));
            }
            if (criteria.getIm9007() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getIm9007(), ClassRename_.im9007));
            }
            if (criteria.getGotime() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getGotime(), ClassRename_.gotime));
            }
            if (criteria.getCo9999() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getCo9999(), ClassRename_.co9999));
            }
            if (criteria.getCm9008() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getCm9008(), ClassRename_.cm9008));
            }
            if (criteria.getCm9009() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getCm9009(), ClassRename_.cm9009));
            }
            if (criteria.getCo99910() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getCo99910(), ClassRename_.co99910));
            }
            if (criteria.getCheckSign() != null) {
                specification = specification.and(buildStringSpecification(criteria.getCheckSign(), ClassRename_.checkSign));
            }
            if (criteria.getClassPb() != null) {
                specification = specification.and(buildStringSpecification(criteria.getClassPb(), ClassRename_.classPb));
            }
            if (criteria.getCk() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getCk(), ClassRename_.ck));
            }
            if (criteria.getDk() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getDk(), ClassRename_.dk));
            }
            if (criteria.getSjrq() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getSjrq(), ClassRename_.sjrq));
            }
            if (criteria.getQsjrq() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getQsjrq(), ClassRename_.qsjrq));
            }
            if (criteria.getByje() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getByje(), ClassRename_.byje));
            }
            if (criteria.getXfcw() != null) {
                specification = specification.and(buildStringSpecification(criteria.getXfcw(), ClassRename_.xfcw));
            }
            if (criteria.getHoteldm() != null) {
                specification = specification.and(buildStringSpecification(criteria.getHoteldm(), ClassRename_.hoteldm));
            }
            if (criteria.getIsnew() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getIsnew(), ClassRename_.isnew));
            }
            if (criteria.getCo99912() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getCo99912(), ClassRename_.co99912));
            }
            if (criteria.getXj() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getXj(), ClassRename_.xj));
            }
            if (criteria.getClassname() != null) {
                specification = specification.and(buildStringSpecification(criteria.getClassname(), ClassRename_.classname));
            }
            if (criteria.getCo9010() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getCo9010(), ClassRename_.co9010));
            }
            if (criteria.getCo9012() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getCo9012(), ClassRename_.co9012));
            }
            if (criteria.getCo9013() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getCo9013(), ClassRename_.co9013));
            }
            if (criteria.getCo9014() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getCo9014(), ClassRename_.co9014));
            }
            if (criteria.getCo99915() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getCo99915(), ClassRename_.co99915));
            }
            if (criteria.getHyxj() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getHyxj(), ClassRename_.hyxj));
            }
            if (criteria.getHysk() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getHysk(), ClassRename_.hysk));
            }
            if (criteria.getHyqt() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getHyqt(), ClassRename_.hyqt));
            }
            if (criteria.getHkxj() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getHkxj(), ClassRename_.hkxj));
            }
            if (criteria.getHksk() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getHksk(), ClassRename_.hksk));
            }
            if (criteria.getHkqt() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getHkqt(), ClassRename_.hkqt));
            }
            if (criteria.getQtwt() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getQtwt(), ClassRename_.qtwt));
            }
            if (criteria.getQtysq() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getQtysq(), ClassRename_.qtysq));
            }
            if (criteria.getBbysj() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getBbysj(), ClassRename_.bbysj));
            }
            if (criteria.getZfbje() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getZfbje(), ClassRename_.zfbje));
            }
            if (criteria.getWxje() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getWxje(), ClassRename_.wxje));
            }
            if (criteria.getw99920() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getw99920(), ClassRename_.w99920));
            }
            if (criteria.getz99921() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getz99921(), ClassRename_.z99921));
            }
        }
        return specification;
    }
}
