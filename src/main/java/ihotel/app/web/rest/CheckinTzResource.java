package ihotel.app.web.rest;

import static org.elasticsearch.index.query.QueryBuilders.*;

import ihotel.app.repository.CheckinTzRepository;
import ihotel.app.service.CheckinTzQueryService;
import ihotel.app.service.CheckinTzService;
import ihotel.app.service.criteria.CheckinTzCriteria;
import ihotel.app.service.dto.CheckinTzDTO;
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
 * REST controller for managing {@link ihotel.app.domain.CheckinTz}.
 */
@RestController
@RequestMapping("/api")
public class CheckinTzResource {

    private final Logger log = LoggerFactory.getLogger(CheckinTzResource.class);

    private static final String ENTITY_NAME = "checkinTz";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final CheckinTzService checkinTzService;

    private final CheckinTzRepository checkinTzRepository;

    private final CheckinTzQueryService checkinTzQueryService;

    public CheckinTzResource(
        CheckinTzService checkinTzService,
        CheckinTzRepository checkinTzRepository,
        CheckinTzQueryService checkinTzQueryService
    ) {
        this.checkinTzService = checkinTzService;
        this.checkinTzRepository = checkinTzRepository;
        this.checkinTzQueryService = checkinTzQueryService;
    }

    /**
     * {@code POST  /checkin-tzs} : Create a new checkinTz.
     *
     * @param checkinTzDTO the checkinTzDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new checkinTzDTO, or with status {@code 400 (Bad Request)} if the checkinTz has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/checkin-tzs")
    public ResponseEntity<CheckinTzDTO> createCheckinTz(@Valid @RequestBody CheckinTzDTO checkinTzDTO) throws URISyntaxException {
        log.debug("REST request to save CheckinTz : {}", checkinTzDTO);
        if (checkinTzDTO.getId() != null) {
            throw new BadRequestAlertException("A new checkinTz cannot already have an ID", ENTITY_NAME, "idexists");
        }
        CheckinTzDTO result = checkinTzService.save(checkinTzDTO);
        return ResponseEntity
            .created(new URI("/api/checkin-tzs/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /checkin-tzs/:id} : Updates an existing checkinTz.
     *
     * @param id the id of the checkinTzDTO to save.
     * @param checkinTzDTO the checkinTzDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated checkinTzDTO,
     * or with status {@code 400 (Bad Request)} if the checkinTzDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the checkinTzDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/checkin-tzs/{id}")
    public ResponseEntity<CheckinTzDTO> updateCheckinTz(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody CheckinTzDTO checkinTzDTO
    ) throws URISyntaxException {
        log.debug("REST request to update CheckinTz : {}, {}", id, checkinTzDTO);
        if (checkinTzDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, checkinTzDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!checkinTzRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        CheckinTzDTO result = checkinTzService.save(checkinTzDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, checkinTzDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /checkin-tzs/:id} : Partial updates given fields of an existing checkinTz, field will ignore if it is null
     *
     * @param id the id of the checkinTzDTO to save.
     * @param checkinTzDTO the checkinTzDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated checkinTzDTO,
     * or with status {@code 400 (Bad Request)} if the checkinTzDTO is not valid,
     * or with status {@code 404 (Not Found)} if the checkinTzDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the checkinTzDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/checkin-tzs/{id}", consumes = "application/merge-patch+json")
    public ResponseEntity<CheckinTzDTO> partialUpdateCheckinTz(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody CheckinTzDTO checkinTzDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update CheckinTz partially : {}, {}", id, checkinTzDTO);
        if (checkinTzDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, checkinTzDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!checkinTzRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<CheckinTzDTO> result = checkinTzService.partialUpdate(checkinTzDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, checkinTzDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /checkin-tzs} : get all the checkinTzs.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of checkinTzs in body.
     */
    @GetMapping("/checkin-tzs")
    public ResponseEntity<List<CheckinTzDTO>> getAllCheckinTzs(CheckinTzCriteria criteria, Pageable pageable) {
        log.debug("REST request to get CheckinTzs by criteria: {}", criteria);
        Page<CheckinTzDTO> page = checkinTzQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /checkin-tzs/count} : count all the checkinTzs.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/checkin-tzs/count")
    public ResponseEntity<Long> countCheckinTzs(CheckinTzCriteria criteria) {
        log.debug("REST request to count CheckinTzs by criteria: {}", criteria);
        return ResponseEntity.ok().body(checkinTzQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /checkin-tzs/:id} : get the "id" checkinTz.
     *
     * @param id the id of the checkinTzDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the checkinTzDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/checkin-tzs/{id}")
    public ResponseEntity<CheckinTzDTO> getCheckinTz(@PathVariable Long id) {
        log.debug("REST request to get CheckinTz : {}", id);
        Optional<CheckinTzDTO> checkinTzDTO = checkinTzService.findOne(id);
        return ResponseUtil.wrapOrNotFound(checkinTzDTO);
    }

    /**
     * {@code DELETE  /checkin-tzs/:id} : delete the "id" checkinTz.
     *
     * @param id the id of the checkinTzDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/checkin-tzs/{id}")
    public ResponseEntity<Void> deleteCheckinTz(@PathVariable Long id) {
        log.debug("REST request to delete CheckinTz : {}", id);
        checkinTzService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }

    /**
     * {@code SEARCH  /_search/checkin-tzs?query=:query} : search for the checkinTz corresponding
     * to the query.
     *
     * @param query the query of the checkinTz search.
     * @param pageable the pagination information.
     * @return the result of the search.
     */
    @GetMapping("/_search/checkin-tzs")
    public ResponseEntity<List<CheckinTzDTO>> searchCheckinTzs(@RequestParam String query, Pageable pageable) {
        log.debug("REST request to search for a page of CheckinTzs for query {}", query);
        Page<CheckinTzDTO> page = checkinTzService.search(query, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }
}
