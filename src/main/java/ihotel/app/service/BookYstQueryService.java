package ihotel.app.service;

import ihotel.app.domain.*; // for static metamodels
import ihotel.app.domain.BookYst;
import ihotel.app.repository.BookYstRepository;
import ihotel.app.repository.search.BookYstSearchRepository;
import ihotel.app.service.criteria.BookYstCriteria;
import ihotel.app.service.dto.BookYstDTO;
import ihotel.app.service.mapper.BookYstMapper;
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
 * Service for executing complex queries for {@link BookYst} entities in the database.
 * The main input is a {@link BookYstCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link BookYstDTO} or a {@link Page} of {@link BookYstDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class BookYstQueryService extends QueryService<BookYst> {

    private final Logger log = LoggerFactory.getLogger(BookYstQueryService.class);

    private final BookYstRepository bookYstRepository;

    private final BookYstMapper bookYstMapper;

    private final BookYstSearchRepository bookYstSearchRepository;

    public BookYstQueryService(
        BookYstRepository bookYstRepository,
        BookYstMapper bookYstMapper,
        BookYstSearchRepository bookYstSearchRepository
    ) {
        this.bookYstRepository = bookYstRepository;
        this.bookYstMapper = bookYstMapper;
        this.bookYstSearchRepository = bookYstSearchRepository;
    }

    /**
     * Return a {@link List} of {@link BookYstDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<BookYstDTO> findByCriteria(BookYstCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<BookYst> specification = createSpecification(criteria);
        return bookYstMapper.toDto(bookYstRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link BookYstDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<BookYstDTO> findByCriteria(BookYstCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<BookYst> specification = createSpecification(criteria);
        return bookYstRepository.findAll(specification, page).map(bookYstMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(BookYstCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<BookYst> specification = createSpecification(criteria);
        return bookYstRepository.count(specification);
    }

    /**
     * Function to convert {@link BookYstCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<BookYst> createSpecification(BookYstCriteria criteria) {
        Specification<BookYst> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), BookYst_.id));
            }
            if (criteria.getRoomcode() != null) {
                specification = specification.and(buildStringSpecification(criteria.getRoomcode(), BookYst_.roomcode));
            }
            if (criteria.getRoomname() != null) {
                specification = specification.and(buildStringSpecification(criteria.getRoomname(), BookYst_.roomname));
            }
            if (criteria.getRoomnum() != null) {
                specification = specification.and(buildStringSpecification(criteria.getRoomnum(), BookYst_.roomnum));
            }
            if (criteria.getRoomseparatenum() != null) {
                specification = specification.and(buildStringSpecification(criteria.getRoomseparatenum(), BookYst_.roomseparatenum));
            }
            if (criteria.getBedids() != null) {
                specification = specification.and(buildStringSpecification(criteria.getBedids(), BookYst_.bedids));
            }
            if (criteria.getBedsimpledesc() != null) {
                specification = specification.and(buildStringSpecification(criteria.getBedsimpledesc(), BookYst_.bedsimpledesc));
            }
            if (criteria.getBednum() != null) {
                specification = specification.and(buildStringSpecification(criteria.getBednum(), BookYst_.bednum));
            }
            if (criteria.getRoomsize() != null) {
                specification = specification.and(buildStringSpecification(criteria.getRoomsize(), BookYst_.roomsize));
            }
            if (criteria.getRoomfloor() != null) {
                specification = specification.and(buildStringSpecification(criteria.getRoomfloor(), BookYst_.roomfloor));
            }
            if (criteria.getNetservice() != null) {
                specification = specification.and(buildStringSpecification(criteria.getNetservice(), BookYst_.netservice));
            }
            if (criteria.getNettype() != null) {
                specification = specification.and(buildStringSpecification(criteria.getNettype(), BookYst_.nettype));
            }
            if (criteria.getIswindow() != null) {
                specification = specification.and(buildStringSpecification(criteria.getIswindow(), BookYst_.iswindow));
            }
            if (criteria.getRemark() != null) {
                specification = specification.and(buildStringSpecification(criteria.getRemark(), BookYst_.remark));
            }
            if (criteria.getSortid() != null) {
                specification = specification.and(buildStringSpecification(criteria.getSortid(), BookYst_.sortid));
            }
            if (criteria.getRoomstate() != null) {
                specification = specification.and(buildStringSpecification(criteria.getRoomstate(), BookYst_.roomstate));
            }
            if (criteria.getSource() != null) {
                specification = specification.and(buildStringSpecification(criteria.getSource(), BookYst_.source));
            }
            if (criteria.getRoomamenities() != null) {
                specification = specification.and(buildStringSpecification(criteria.getRoomamenities(), BookYst_.roomamenities));
            }
            if (criteria.getMaxguestnums() != null) {
                specification = specification.and(buildStringSpecification(criteria.getMaxguestnums(), BookYst_.maxguestnums));
            }
            if (criteria.getRoomdistribution() != null) {
                specification = specification.and(buildStringSpecification(criteria.getRoomdistribution(), BookYst_.roomdistribution));
            }
            if (criteria.getConditionbeforedays() != null) {
                specification =
                    specification.and(buildStringSpecification(criteria.getConditionbeforedays(), BookYst_.conditionbeforedays));
            }
            if (criteria.getConditionleastdays() != null) {
                specification = specification.and(buildStringSpecification(criteria.getConditionleastdays(), BookYst_.conditionleastdays));
            }
            if (criteria.getConditionleastroomnum() != null) {
                specification =
                    specification.and(buildStringSpecification(criteria.getConditionleastroomnum(), BookYst_.conditionleastroomnum));
            }
            if (criteria.getPaymentype() != null) {
                specification = specification.and(buildStringSpecification(criteria.getPaymentype(), BookYst_.paymentype));
            }
            if (criteria.getRateplandesc() != null) {
                specification = specification.and(buildStringSpecification(criteria.getRateplandesc(), BookYst_.rateplandesc));
            }
            if (criteria.getRateplanname() != null) {
                specification = specification.and(buildStringSpecification(criteria.getRateplanname(), BookYst_.rateplanname));
            }
            if (criteria.getRateplanstate() != null) {
                specification = specification.and(buildStringSpecification(criteria.getRateplanstate(), BookYst_.rateplanstate));
            }
            if (criteria.getAddvaluebednum() != null) {
                specification = specification.and(buildStringSpecification(criteria.getAddvaluebednum(), BookYst_.addvaluebednum));
            }
            if (criteria.getAddvaluebedprice() != null) {
                specification = specification.and(buildStringSpecification(criteria.getAddvaluebedprice(), BookYst_.addvaluebedprice));
            }
            if (criteria.getAddvaluebreakfastnum() != null) {
                specification =
                    specification.and(buildStringSpecification(criteria.getAddvaluebreakfastnum(), BookYst_.addvaluebreakfastnum));
            }
            if (criteria.getAddvaluebreakfastprice() != null) {
                specification =
                    specification.and(buildStringSpecification(criteria.getAddvaluebreakfastprice(), BookYst_.addvaluebreakfastprice));
            }
            if (criteria.getBaseprice() != null) {
                specification = specification.and(buildStringSpecification(criteria.getBaseprice(), BookYst_.baseprice));
            }
            if (criteria.getSaleprice() != null) {
                specification = specification.and(buildStringSpecification(criteria.getSaleprice(), BookYst_.saleprice));
            }
            if (criteria.getMarketprice() != null) {
                specification = specification.and(buildStringSpecification(criteria.getMarketprice(), BookYst_.marketprice));
            }
            if (criteria.getHotelproductservice() != null) {
                specification =
                    specification.and(buildStringSpecification(criteria.getHotelproductservice(), BookYst_.hotelproductservice));
            }
            if (criteria.getHotelproductservicedesc() != null) {
                specification =
                    specification.and(buildStringSpecification(criteria.getHotelproductservicedesc(), BookYst_.hotelproductservicedesc));
            }
            if (criteria.getHotelproductid() != null) {
                specification = specification.and(buildStringSpecification(criteria.getHotelproductid(), BookYst_.hotelproductid));
            }
            if (criteria.getRoomid() != null) {
                specification = specification.and(buildStringSpecification(criteria.getRoomid(), BookYst_.roomid));
            }
            if (criteria.getHotelid() != null) {
                specification = specification.and(buildStringSpecification(criteria.getHotelid(), BookYst_.hotelid));
            }
        }
        return specification;
    }
}
