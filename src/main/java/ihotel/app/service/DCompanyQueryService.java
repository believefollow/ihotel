package ihotel.app.service;

import ihotel.app.domain.*; // for static metamodels
import ihotel.app.domain.DCompany;
import ihotel.app.repository.DCompanyRepository;
import ihotel.app.repository.search.DCompanySearchRepository;
import ihotel.app.service.criteria.DCompanyCriteria;
import ihotel.app.service.dto.DCompanyDTO;
import ihotel.app.service.mapper.DCompanyMapper;
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
 * Service for executing complex queries for {@link DCompany} entities in the database.
 * The main input is a {@link DCompanyCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link DCompanyDTO} or a {@link Page} of {@link DCompanyDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class DCompanyQueryService extends QueryService<DCompany> {

    private final Logger log = LoggerFactory.getLogger(DCompanyQueryService.class);

    private final DCompanyRepository dCompanyRepository;

    private final DCompanyMapper dCompanyMapper;

    private final DCompanySearchRepository dCompanySearchRepository;

    public DCompanyQueryService(
        DCompanyRepository dCompanyRepository,
        DCompanyMapper dCompanyMapper,
        DCompanySearchRepository dCompanySearchRepository
    ) {
        this.dCompanyRepository = dCompanyRepository;
        this.dCompanyMapper = dCompanyMapper;
        this.dCompanySearchRepository = dCompanySearchRepository;
    }

    /**
     * Return a {@link List} of {@link DCompanyDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<DCompanyDTO> findByCriteria(DCompanyCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<DCompany> specification = createSpecification(criteria);
        return dCompanyMapper.toDto(dCompanyRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link DCompanyDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<DCompanyDTO> findByCriteria(DCompanyCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<DCompany> specification = createSpecification(criteria);
        return dCompanyRepository.findAll(specification, page).map(dCompanyMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(DCompanyCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<DCompany> specification = createSpecification(criteria);
        return dCompanyRepository.count(specification);
    }

    /**
     * Function to convert {@link DCompanyCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<DCompany> createSpecification(DCompanyCriteria criteria) {
        Specification<DCompany> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), DCompany_.id));
            }
            if (criteria.getCompany() != null) {
                specification = specification.and(buildStringSpecification(criteria.getCompany(), DCompany_.company));
            }
            if (criteria.getLinkman() != null) {
                specification = specification.and(buildStringSpecification(criteria.getLinkman(), DCompany_.linkman));
            }
            if (criteria.getPhone() != null) {
                specification = specification.and(buildStringSpecification(criteria.getPhone(), DCompany_.phone));
            }
            if (criteria.getAddress() != null) {
                specification = specification.and(buildStringSpecification(criteria.getAddress(), DCompany_.address));
            }
            if (criteria.getRemark() != null) {
                specification = specification.and(buildStringSpecification(criteria.getRemark(), DCompany_.remark));
            }
            if (criteria.getFax() != null) {
                specification = specification.and(buildStringSpecification(criteria.getFax(), DCompany_.fax));
            }
        }
        return specification;
    }
}
