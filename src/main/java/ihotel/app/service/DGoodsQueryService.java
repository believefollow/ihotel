package ihotel.app.service;

import ihotel.app.domain.*; // for static metamodels
import ihotel.app.domain.DGoods;
import ihotel.app.repository.DGoodsRepository;
import ihotel.app.repository.search.DGoodsSearchRepository;
import ihotel.app.service.criteria.DGoodsCriteria;
import ihotel.app.service.dto.DGoodsDTO;
import ihotel.app.service.mapper.DGoodsMapper;
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
 * Service for executing complex queries for {@link DGoods} entities in the database.
 * The main input is a {@link DGoodsCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link DGoodsDTO} or a {@link Page} of {@link DGoodsDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class DGoodsQueryService extends QueryService<DGoods> {

    private final Logger log = LoggerFactory.getLogger(DGoodsQueryService.class);

    private final DGoodsRepository dGoodsRepository;

    private final DGoodsMapper dGoodsMapper;

    private final DGoodsSearchRepository dGoodsSearchRepository;

    public DGoodsQueryService(DGoodsRepository dGoodsRepository, DGoodsMapper dGoodsMapper, DGoodsSearchRepository dGoodsSearchRepository) {
        this.dGoodsRepository = dGoodsRepository;
        this.dGoodsMapper = dGoodsMapper;
        this.dGoodsSearchRepository = dGoodsSearchRepository;
    }

    /**
     * Return a {@link List} of {@link DGoodsDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<DGoodsDTO> findByCriteria(DGoodsCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<DGoods> specification = createSpecification(criteria);
        return dGoodsMapper.toDto(dGoodsRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link DGoodsDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<DGoodsDTO> findByCriteria(DGoodsCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<DGoods> specification = createSpecification(criteria);
        return dGoodsRepository.findAll(specification, page).map(dGoodsMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(DGoodsCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<DGoods> specification = createSpecification(criteria);
        return dGoodsRepository.count(specification);
    }

    /**
     * Function to convert {@link DGoodsCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<DGoods> createSpecification(DGoodsCriteria criteria) {
        Specification<DGoods> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), DGoods_.id));
            }
            if (criteria.getTypeid() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getTypeid(), DGoods_.typeid));
            }
            if (criteria.getGoodsname() != null) {
                specification = specification.and(buildStringSpecification(criteria.getGoodsname(), DGoods_.goodsname));
            }
            if (criteria.getGoodsid() != null) {
                specification = specification.and(buildStringSpecification(criteria.getGoodsid(), DGoods_.goodsid));
            }
            if (criteria.getGgxh() != null) {
                specification = specification.and(buildStringSpecification(criteria.getGgxh(), DGoods_.ggxh));
            }
            if (criteria.getPysj() != null) {
                specification = specification.and(buildStringSpecification(criteria.getPysj(), DGoods_.pysj));
            }
            if (criteria.getWbsj() != null) {
                specification = specification.and(buildStringSpecification(criteria.getWbsj(), DGoods_.wbsj));
            }
            if (criteria.getUnit() != null) {
                specification = specification.and(buildStringSpecification(criteria.getUnit(), DGoods_.unit));
            }
            if (criteria.getGcsl() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getGcsl(), DGoods_.gcsl));
            }
            if (criteria.getDcsl() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getDcsl(), DGoods_.dcsl));
            }
            if (criteria.getRemark() != null) {
                specification = specification.and(buildStringSpecification(criteria.getRemark(), DGoods_.remark));
            }
        }
        return specification;
    }
}
