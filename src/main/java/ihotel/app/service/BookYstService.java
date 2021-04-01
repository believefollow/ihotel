package ihotel.app.service;

import static org.elasticsearch.index.query.QueryBuilders.*;

import ihotel.app.domain.BookYst;
import ihotel.app.repository.BookYstRepository;
import ihotel.app.repository.search.BookYstSearchRepository;
import ihotel.app.service.dto.BookYstDTO;
import ihotel.app.service.mapper.BookYstMapper;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link BookYst}.
 */
@Service
@Transactional
public class BookYstService {

    private final Logger log = LoggerFactory.getLogger(BookYstService.class);

    private final BookYstRepository bookYstRepository;

    private final BookYstMapper bookYstMapper;

    private final BookYstSearchRepository bookYstSearchRepository;

    public BookYstService(
        BookYstRepository bookYstRepository,
        BookYstMapper bookYstMapper,
        BookYstSearchRepository bookYstSearchRepository
    ) {
        this.bookYstRepository = bookYstRepository;
        this.bookYstMapper = bookYstMapper;
        this.bookYstSearchRepository = bookYstSearchRepository;
    }

    /**
     * Save a bookYst.
     *
     * @param bookYstDTO the entity to save.
     * @return the persisted entity.
     */
    public BookYstDTO save(BookYstDTO bookYstDTO) {
        log.debug("Request to save BookYst : {}", bookYstDTO);
        BookYst bookYst = bookYstMapper.toEntity(bookYstDTO);
        bookYst = bookYstRepository.save(bookYst);
        BookYstDTO result = bookYstMapper.toDto(bookYst);
        bookYstSearchRepository.save(bookYst);
        return result;
    }

    /**
     * Partially update a bookYst.
     *
     * @param bookYstDTO the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<BookYstDTO> partialUpdate(BookYstDTO bookYstDTO) {
        log.debug("Request to partially update BookYst : {}", bookYstDTO);

        return bookYstRepository
            .findById(bookYstDTO.getId())
            .map(
                existingBookYst -> {
                    bookYstMapper.partialUpdate(existingBookYst, bookYstDTO);
                    return existingBookYst;
                }
            )
            .map(bookYstRepository::save)
            .map(
                savedBookYst -> {
                    bookYstSearchRepository.save(savedBookYst);

                    return savedBookYst;
                }
            )
            .map(bookYstMapper::toDto);
    }

    /**
     * Get all the bookYsts.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<BookYstDTO> findAll(Pageable pageable) {
        log.debug("Request to get all BookYsts");
        return bookYstRepository.findAll(pageable).map(bookYstMapper::toDto);
    }

    /**
     * Get one bookYst by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<BookYstDTO> findOne(Long id) {
        log.debug("Request to get BookYst : {}", id);
        return bookYstRepository.findById(id).map(bookYstMapper::toDto);
    }

    /**
     * Delete the bookYst by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete BookYst : {}", id);
        bookYstRepository.deleteById(id);
        bookYstSearchRepository.deleteById(id);
    }

    /**
     * Search for the bookYst corresponding to the query.
     *
     * @param query the query of the search.
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<BookYstDTO> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of BookYsts for query {}", query);
        return bookYstSearchRepository.search(queryStringQuery(query), pageable).map(bookYstMapper::toDto);
    }
}
