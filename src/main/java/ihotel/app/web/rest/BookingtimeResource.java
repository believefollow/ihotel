package ihotel.app.web.rest;

import static org.elasticsearch.index.query.QueryBuilders.*;

import ihotel.app.repository.BookingtimeRepository;
import ihotel.app.service.BookingtimeQueryService;
import ihotel.app.service.BookingtimeService;
import ihotel.app.service.criteria.BookingtimeCriteria;
import ihotel.app.service.dto.BookingtimeDTO;
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
 * REST controller for managing {@link ihotel.app.domain.Bookingtime}.
 */
@RestController
@RequestMapping("/api")
public class BookingtimeResource {

    private final Logger log = LoggerFactory.getLogger(BookingtimeResource.class);

    private static final String ENTITY_NAME = "bookingtime";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final BookingtimeService bookingtimeService;

    private final BookingtimeRepository bookingtimeRepository;

    private final BookingtimeQueryService bookingtimeQueryService;

    public BookingtimeResource(
        BookingtimeService bookingtimeService,
        BookingtimeRepository bookingtimeRepository,
        BookingtimeQueryService bookingtimeQueryService
    ) {
        this.bookingtimeService = bookingtimeService;
        this.bookingtimeRepository = bookingtimeRepository;
        this.bookingtimeQueryService = bookingtimeQueryService;
    }

    /**
     * {@code POST  /bookingtimes} : Create a new bookingtime.
     *
     * @param bookingtimeDTO the bookingtimeDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new bookingtimeDTO, or with status {@code 400 (Bad Request)} if the bookingtime has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/bookingtimes")
    public ResponseEntity<BookingtimeDTO> createBookingtime(@Valid @RequestBody BookingtimeDTO bookingtimeDTO) throws URISyntaxException {
        log.debug("REST request to save Bookingtime : {}", bookingtimeDTO);
        if (bookingtimeDTO.getId() != null) {
            throw new BadRequestAlertException("A new bookingtime cannot already have an ID", ENTITY_NAME, "idexists");
        }
        BookingtimeDTO result = bookingtimeService.save(bookingtimeDTO);
        return ResponseEntity
            .created(new URI("/api/bookingtimes/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /bookingtimes/:id} : Updates an existing bookingtime.
     *
     * @param id the id of the bookingtimeDTO to save.
     * @param bookingtimeDTO the bookingtimeDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated bookingtimeDTO,
     * or with status {@code 400 (Bad Request)} if the bookingtimeDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the bookingtimeDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/bookingtimes/{id}")
    public ResponseEntity<BookingtimeDTO> updateBookingtime(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody BookingtimeDTO bookingtimeDTO
    ) throws URISyntaxException {
        log.debug("REST request to update Bookingtime : {}, {}", id, bookingtimeDTO);
        if (bookingtimeDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, bookingtimeDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!bookingtimeRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        BookingtimeDTO result = bookingtimeService.save(bookingtimeDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, bookingtimeDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /bookingtimes/:id} : Partial updates given fields of an existing bookingtime, field will ignore if it is null
     *
     * @param id the id of the bookingtimeDTO to save.
     * @param bookingtimeDTO the bookingtimeDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated bookingtimeDTO,
     * or with status {@code 400 (Bad Request)} if the bookingtimeDTO is not valid,
     * or with status {@code 404 (Not Found)} if the bookingtimeDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the bookingtimeDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/bookingtimes/{id}", consumes = "application/merge-patch+json")
    public ResponseEntity<BookingtimeDTO> partialUpdateBookingtime(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody BookingtimeDTO bookingtimeDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update Bookingtime partially : {}, {}", id, bookingtimeDTO);
        if (bookingtimeDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, bookingtimeDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!bookingtimeRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<BookingtimeDTO> result = bookingtimeService.partialUpdate(bookingtimeDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, bookingtimeDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /bookingtimes} : get all the bookingtimes.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of bookingtimes in body.
     */
    @GetMapping("/bookingtimes")
    public ResponseEntity<List<BookingtimeDTO>> getAllBookingtimes(BookingtimeCriteria criteria, Pageable pageable) {
        log.debug("REST request to get Bookingtimes by criteria: {}", criteria);
        Page<BookingtimeDTO> page = bookingtimeQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /bookingtimes/count} : count all the bookingtimes.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/bookingtimes/count")
    public ResponseEntity<Long> countBookingtimes(BookingtimeCriteria criteria) {
        log.debug("REST request to count Bookingtimes by criteria: {}", criteria);
        return ResponseEntity.ok().body(bookingtimeQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /bookingtimes/:id} : get the "id" bookingtime.
     *
     * @param id the id of the bookingtimeDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the bookingtimeDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/bookingtimes/{id}")
    public ResponseEntity<BookingtimeDTO> getBookingtime(@PathVariable Long id) {
        log.debug("REST request to get Bookingtime : {}", id);
        Optional<BookingtimeDTO> bookingtimeDTO = bookingtimeService.findOne(id);
        return ResponseUtil.wrapOrNotFound(bookingtimeDTO);
    }

    /**
     * {@code DELETE  /bookingtimes/:id} : delete the "id" bookingtime.
     *
     * @param id the id of the bookingtimeDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/bookingtimes/{id}")
    public ResponseEntity<Void> deleteBookingtime(@PathVariable Long id) {
        log.debug("REST request to delete Bookingtime : {}", id);
        bookingtimeService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }

    /**
     * {@code SEARCH  /_search/bookingtimes?query=:query} : search for the bookingtime corresponding
     * to the query.
     *
     * @param query the query of the bookingtime search.
     * @param pageable the pagination information.
     * @return the result of the search.
     */
    @GetMapping("/_search/bookingtimes")
    public ResponseEntity<List<BookingtimeDTO>> searchBookingtimes(@RequestParam String query, Pageable pageable) {
        log.debug("REST request to search for a page of Bookingtimes for query {}", query);
        Page<BookingtimeDTO> page = bookingtimeService.search(query, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }
}
