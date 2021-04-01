package ihotel.app.web.rest;

import static org.elasticsearch.index.query.QueryBuilders.*;

import ihotel.app.repository.CheckinBakRepository;
import ihotel.app.service.CheckinBakQueryService;
import ihotel.app.service.CheckinBakService;
import ihotel.app.service.criteria.CheckinBakCriteria;
import ihotel.app.service.dto.CheckinBakDTO;
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
 * REST controller for managing {@link ihotel.app.domain.CheckinBak}.
 */
@RestController
@RequestMapping("/api")
public class CheckinBakResource {

    private final Logger log = LoggerFactory.getLogger(CheckinBakResource.class);

    private static final String ENTITY_NAME = "checkinBak";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final CheckinBakService checkinBakService;

    private final CheckinBakRepository checkinBakRepository;

    private final CheckinBakQueryService checkinBakQueryService;

    public CheckinBakResource(
        CheckinBakService checkinBakService,
        CheckinBakRepository checkinBakRepository,
        CheckinBakQueryService checkinBakQueryService
    ) {
        this.checkinBakService = checkinBakService;
        this.checkinBakRepository = checkinBakRepository;
        this.checkinBakQueryService = checkinBakQueryService;
    }

    /**
     * {@code POST  /checkin-baks} : Create a new checkinBak.
     *
     * @param checkinBakDTO the checkinBakDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new checkinBakDTO, or with status {@code 400 (Bad Request)} if the checkinBak has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/checkin-baks")
    public ResponseEntity<CheckinBakDTO> createCheckinBak(@Valid @RequestBody CheckinBakDTO checkinBakDTO) throws URISyntaxException {
        log.debug("REST request to save CheckinBak : {}", checkinBakDTO);
        if (checkinBakDTO.getId() != null) {
            throw new BadRequestAlertException("A new checkinBak cannot already have an ID", ENTITY_NAME, "idexists");
        }
        CheckinBakDTO result = checkinBakService.save(checkinBakDTO);
        return ResponseEntity
            .created(new URI("/api/checkin-baks/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /checkin-baks/:id} : Updates an existing checkinBak.
     *
     * @param id the id of the checkinBakDTO to save.
     * @param checkinBakDTO the checkinBakDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated checkinBakDTO,
     * or with status {@code 400 (Bad Request)} if the checkinBakDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the checkinBakDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/checkin-baks/{id}")
    public ResponseEntity<CheckinBakDTO> updateCheckinBak(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody CheckinBakDTO checkinBakDTO
    ) throws URISyntaxException {
        log.debug("REST request to update CheckinBak : {}, {}", id, checkinBakDTO);
        if (checkinBakDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, checkinBakDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!checkinBakRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        CheckinBakDTO result = checkinBakService.save(checkinBakDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, checkinBakDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /checkin-baks/:id} : Partial updates given fields of an existing checkinBak, field will ignore if it is null
     *
     * @param id the id of the checkinBakDTO to save.
     * @param checkinBakDTO the checkinBakDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated checkinBakDTO,
     * or with status {@code 400 (Bad Request)} if the checkinBakDTO is not valid,
     * or with status {@code 404 (Not Found)} if the checkinBakDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the checkinBakDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/checkin-baks/{id}", consumes = "application/merge-patch+json")
    public ResponseEntity<CheckinBakDTO> partialUpdateCheckinBak(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody CheckinBakDTO checkinBakDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update CheckinBak partially : {}, {}", id, checkinBakDTO);
        if (checkinBakDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, checkinBakDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!checkinBakRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<CheckinBakDTO> result = checkinBakService.partialUpdate(checkinBakDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, checkinBakDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /checkin-baks} : get all the checkinBaks.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of checkinBaks in body.
     */
    @GetMapping("/checkin-baks")
    public ResponseEntity<List<CheckinBakDTO>> getAllCheckinBaks(CheckinBakCriteria criteria, Pageable pageable) {
        log.debug("REST request to get CheckinBaks by criteria: {}", criteria);
        Page<CheckinBakDTO> page = checkinBakQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /checkin-baks/count} : count all the checkinBaks.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/checkin-baks/count")
    public ResponseEntity<Long> countCheckinBaks(CheckinBakCriteria criteria) {
        log.debug("REST request to count CheckinBaks by criteria: {}", criteria);
        return ResponseEntity.ok().body(checkinBakQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /checkin-baks/:id} : get the "id" checkinBak.
     *
     * @param id the id of the checkinBakDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the checkinBakDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/checkin-baks/{id}")
    public ResponseEntity<CheckinBakDTO> getCheckinBak(@PathVariable Long id) {
        log.debug("REST request to get CheckinBak : {}", id);
        Optional<CheckinBakDTO> checkinBakDTO = checkinBakService.findOne(id);
        return ResponseUtil.wrapOrNotFound(checkinBakDTO);
    }

    /**
     * {@code DELETE  /checkin-baks/:id} : delete the "id" checkinBak.
     *
     * @param id the id of the checkinBakDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/checkin-baks/{id}")
    public ResponseEntity<Void> deleteCheckinBak(@PathVariable Long id) {
        log.debug("REST request to delete CheckinBak : {}", id);
        checkinBakService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }

    /**
     * {@code SEARCH  /_search/checkin-baks?query=:query} : search for the checkinBak corresponding
     * to the query.
     *
     * @param query the query of the checkinBak search.
     * @param pageable the pagination information.
     * @return the result of the search.
     */
    @GetMapping("/_search/checkin-baks")
    public ResponseEntity<List<CheckinBakDTO>> searchCheckinBaks(@RequestParam String query, Pageable pageable) {
        log.debug("REST request to search for a page of CheckinBaks for query {}", query);
        Page<CheckinBakDTO> page = checkinBakService.search(query, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }
}
