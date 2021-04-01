package ihotel.app.web.rest;

import static org.elasticsearch.index.query.QueryBuilders.*;

import ihotel.app.repository.BookYstRepository;
import ihotel.app.service.BookYstQueryService;
import ihotel.app.service.BookYstService;
import ihotel.app.service.criteria.BookYstCriteria;
import ihotel.app.service.dto.BookYstDTO;
import ihotel.app.web.rest.errors.BadRequestAlertException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.StreamSupport;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import tech.jhipster.web.util.HeaderUtil;
import tech.jhipster.web.util.PaginationUtil;
import tech.jhipster.web.util.ResponseUtil;

/**
 * REST controller for managing {@link ihotel.app.domain.BookYst}.
 */
@RestController
@RequestMapping("/api")
public class BookYstResource {

    private final Logger log = LoggerFactory.getLogger(BookYstResource.class);

    private static final String ENTITY_NAME = "bookYst";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final BookYstService bookYstService;

    private final BookYstRepository bookYstRepository;

    private final BookYstQueryService bookYstQueryService;

    public BookYstResource(BookYstService bookYstService, BookYstRepository bookYstRepository, BookYstQueryService bookYstQueryService) {
        this.bookYstService = bookYstService;
        this.bookYstRepository = bookYstRepository;
        this.bookYstQueryService = bookYstQueryService;
    }

    /**
     * {@code POST  /book-ysts} : Create a new bookYst.
     *
     * @param bookYstDTO the bookYstDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new bookYstDTO, or with status {@code 400 (Bad Request)} if the bookYst has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/book-ysts")
    public ResponseEntity<BookYstDTO> createBookYst(@Valid @RequestBody BookYstDTO bookYstDTO) throws URISyntaxException {
        log.debug("REST request to save BookYst : {}", bookYstDTO);
        if (bookYstDTO.getId() != null) {
            throw new BadRequestAlertException("A new bookYst cannot already have an ID", ENTITY_NAME, "idexists");
        }
        BookYstDTO result = bookYstService.save(bookYstDTO);
        return ResponseEntity
            .created(new URI("/api/book-ysts/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /book-ysts/:id} : Updates an existing bookYst.
     *
     * @param id the id of the bookYstDTO to save.
     * @param bookYstDTO the bookYstDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated bookYstDTO,
     * or with status {@code 400 (Bad Request)} if the bookYstDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the bookYstDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/book-ysts/{id}")
    public ResponseEntity<BookYstDTO> updateBookYst(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody BookYstDTO bookYstDTO
    ) throws URISyntaxException {
        log.debug("REST request to update BookYst : {}, {}", id, bookYstDTO);
        if (bookYstDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, bookYstDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!bookYstRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        BookYstDTO result = bookYstService.save(bookYstDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, bookYstDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /book-ysts/:id} : Partial updates given fields of an existing bookYst, field will ignore if it is null
     *
     * @param id the id of the bookYstDTO to save.
     * @param bookYstDTO the bookYstDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated bookYstDTO,
     * or with status {@code 400 (Bad Request)} if the bookYstDTO is not valid,
     * or with status {@code 404 (Not Found)} if the bookYstDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the bookYstDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/book-ysts/{id}", consumes = "application/merge-patch+json")
    public ResponseEntity<BookYstDTO> partialUpdateBookYst(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody BookYstDTO bookYstDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update BookYst partially : {}, {}", id, bookYstDTO);
        if (bookYstDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, bookYstDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!bookYstRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<BookYstDTO> result = bookYstService.partialUpdate(bookYstDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, bookYstDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /book-ysts} : get all the bookYsts.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of bookYsts in body.
     */
    @GetMapping("/book-ysts")
    public ResponseEntity<List<BookYstDTO>> getAllBookYsts(BookYstCriteria criteria, Pageable pageable) {
        log.debug("REST request to get BookYsts by criteria: {}", criteria);
        Page<BookYstDTO> page = bookYstQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /book-ysts/count} : count all the bookYsts.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/book-ysts/count")
    public ResponseEntity<Long> countBookYsts(BookYstCriteria criteria) {
        log.debug("REST request to count BookYsts by criteria: {}", criteria);
        return ResponseEntity.ok().body(bookYstQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /book-ysts/:id} : get the "id" bookYst.
     *
     * @param id the id of the bookYstDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the bookYstDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/book-ysts/{id}")
    public ResponseEntity<BookYstDTO> getBookYst(@PathVariable Long id) {
        log.debug("REST request to get BookYst : {}", id);
        Optional<BookYstDTO> bookYstDTO = bookYstService.findOne(id);
        return ResponseUtil.wrapOrNotFound(bookYstDTO);
    }

    /**
     * {@code DELETE  /book-ysts/:id} : delete the "id" bookYst.
     *
     * @param id the id of the bookYstDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/book-ysts/{id}")
    public ResponseEntity<Void> deleteBookYst(@PathVariable Long id) {
        log.debug("REST request to delete BookYst : {}", id);
        bookYstService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }

    /**
     * {@code SEARCH  /_search/book-ysts?query=:query} : search for the bookYst corresponding
     * to the query.
     *
     * @param query the query of the bookYst search.
     * @param pageable the pagination information.
     * @return the result of the search.
     */
    @GetMapping("/_search/book-ysts")
    public ResponseEntity<List<BookYstDTO>> searchBookYsts(@RequestParam String query, Pageable pageable) {
        log.debug("REST request to search for a page of BookYsts for query {}", query);
        Page<BookYstDTO> page = bookYstService.search(query, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }
}
