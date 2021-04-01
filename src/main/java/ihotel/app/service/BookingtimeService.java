package ihotel.app.service;

import static org.elasticsearch.index.query.QueryBuilders.*;

import ihotel.app.domain.Bookingtime;
import ihotel.app.repository.BookingtimeRepository;
import ihotel.app.repository.search.BookingtimeSearchRepository;
import ihotel.app.service.dto.BookingtimeDTO;
import ihotel.app.service.mapper.BookingtimeMapper;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link Bookingtime}.
 */
@Service
@Transactional
public class BookingtimeService {

    private final Logger log = LoggerFactory.getLogger(BookingtimeService.class);

    private final BookingtimeRepository bookingtimeRepository;

    private final BookingtimeMapper bookingtimeMapper;

    private final BookingtimeSearchRepository bookingtimeSearchRepository;

    public BookingtimeService(
        BookingtimeRepository bookingtimeRepository,
        BookingtimeMapper bookingtimeMapper,
        BookingtimeSearchRepository bookingtimeSearchRepository
    ) {
        this.bookingtimeRepository = bookingtimeRepository;
        this.bookingtimeMapper = bookingtimeMapper;
        this.bookingtimeSearchRepository = bookingtimeSearchRepository;
    }

    /**
     * Save a bookingtime.
     *
     * @param bookingtimeDTO the entity to save.
     * @return the persisted entity.
     */
    public BookingtimeDTO save(BookingtimeDTO bookingtimeDTO) {
        log.debug("Request to save Bookingtime : {}", bookingtimeDTO);
        Bookingtime bookingtime = bookingtimeMapper.toEntity(bookingtimeDTO);
        bookingtime = bookingtimeRepository.save(bookingtime);
        BookingtimeDTO result = bookingtimeMapper.toDto(bookingtime);
        bookingtimeSearchRepository.save(bookingtime);
        return result;
    }

    /**
     * Partially update a bookingtime.
     *
     * @param bookingtimeDTO the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<BookingtimeDTO> partialUpdate(BookingtimeDTO bookingtimeDTO) {
        log.debug("Request to partially update Bookingtime : {}", bookingtimeDTO);

        return bookingtimeRepository
            .findById(bookingtimeDTO.getId())
            .map(
                existingBookingtime -> {
                    bookingtimeMapper.partialUpdate(existingBookingtime, bookingtimeDTO);
                    return existingBookingtime;
                }
            )
            .map(bookingtimeRepository::save)
            .map(
                savedBookingtime -> {
                    bookingtimeSearchRepository.save(savedBookingtime);

                    return savedBookingtime;
                }
            )
            .map(bookingtimeMapper::toDto);
    }

    /**
     * Get all the bookingtimes.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<BookingtimeDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Bookingtimes");
        return bookingtimeRepository.findAll(pageable).map(bookingtimeMapper::toDto);
    }

    /**
     * Get one bookingtime by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<BookingtimeDTO> findOne(Long id) {
        log.debug("Request to get Bookingtime : {}", id);
        return bookingtimeRepository.findById(id).map(bookingtimeMapper::toDto);
    }

    /**
     * Delete the bookingtime by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete Bookingtime : {}", id);
        bookingtimeRepository.deleteById(id);
        bookingtimeSearchRepository.deleteById(id);
    }

    /**
     * Search for the bookingtime corresponding to the query.
     *
     * @param query the query of the search.
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<BookingtimeDTO> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of Bookingtimes for query {}", query);
        return bookingtimeSearchRepository.search(queryStringQuery(query), pageable).map(bookingtimeMapper::toDto);
    }
}
