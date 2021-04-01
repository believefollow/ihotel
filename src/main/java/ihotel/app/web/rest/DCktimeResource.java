package ihotel.app.web.rest;

import static org.elasticsearch.index.query.QueryBuilders.*;

import ihotel.app.repository.DCktimeRepository;
import ihotel.app.service.DCktimeQueryService;
import ihotel.app.service.DCktimeService;
import ihotel.app.service.criteria.DCktimeCriteria;
import ihotel.app.service.dto.DCktimeDTO;
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
 * REST controller for managing {@link ihotel.app.domain.DCktime}.
 */
@RestController
@RequestMapping("/api")
public class DCktimeResource {

    private final Logger log = LoggerFactory.getLogger(DCktimeResource.class);

    private static final String ENTITY_NAME = "dCktime";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final DCktimeService dCktimeService;

    private final DCktimeRepository dCktimeRepository;

    private final DCktimeQueryService dCktimeQueryService;

    public DCktimeResource(DCktimeService dCktimeService, DCktimeRepository dCktimeRepository, DCktimeQueryService dCktimeQueryService) {
        this.dCktimeService = dCktimeService;
        this.dCktimeRepository = dCktimeRepository;
        this.dCktimeQueryService = dCktimeQueryService;
    }

    /**
     * {@code POST  /d-cktimes} : Create a new dCktime.
     *
     * @param dCktimeDTO the dCktimeDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new dCktimeDTO, or with status {@code 400 (Bad Request)} if the dCktime has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/d-cktimes")
    public ResponseEntity<DCktimeDTO> createDCktime(@Valid @RequestBody DCktimeDTO dCktimeDTO) throws URISyntaxException {
        log.debug("REST request to save DCktime : {}", dCktimeDTO);
        if (dCktimeDTO.getId() != null) {
            throw new BadRequestAlertException("A new dCktime cannot already have an ID", ENTITY_NAME, "idexists");
        }
        DCktimeDTO result = dCktimeService.save(dCktimeDTO);
        return ResponseEntity
            .created(new URI("/api/d-cktimes/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /d-cktimes/:id} : Updates an existing dCktime.
     *
     * @param id the id of the dCktimeDTO to save.
     * @param dCktimeDTO the dCktimeDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated dCktimeDTO,
     * or with status {@code 400 (Bad Request)} if the dCktimeDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the dCktimeDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/d-cktimes/{id}")
    public ResponseEntity<DCktimeDTO> updateDCktime(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody DCktimeDTO dCktimeDTO
    ) throws URISyntaxException {
        log.debug("REST request to update DCktime : {}, {}", id, dCktimeDTO);
        if (dCktimeDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, dCktimeDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!dCktimeRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        DCktimeDTO result = dCktimeService.save(dCktimeDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, dCktimeDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /d-cktimes/:id} : Partial updates given fields of an existing dCktime, field will ignore if it is null
     *
     * @param id the id of the dCktimeDTO to save.
     * @param dCktimeDTO the dCktimeDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated dCktimeDTO,
     * or with status {@code 400 (Bad Request)} if the dCktimeDTO is not valid,
     * or with status {@code 404 (Not Found)} if the dCktimeDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the dCktimeDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/d-cktimes/{id}", consumes = "application/merge-patch+json")
    public ResponseEntity<DCktimeDTO> partialUpdateDCktime(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody DCktimeDTO dCktimeDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update DCktime partially : {}, {}", id, dCktimeDTO);
        if (dCktimeDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, dCktimeDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!dCktimeRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<DCktimeDTO> result = dCktimeService.partialUpdate(dCktimeDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, dCktimeDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /d-cktimes} : get all the dCktimes.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of dCktimes in body.
     */
    @GetMapping("/d-cktimes")
    public ResponseEntity<List<DCktimeDTO>> getAllDCktimes(DCktimeCriteria criteria, Pageable pageable) {
        log.debug("REST request to get DCktimes by criteria: {}", criteria);
        Page<DCktimeDTO> page = dCktimeQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /d-cktimes/count} : count all the dCktimes.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/d-cktimes/count")
    public ResponseEntity<Long> countDCktimes(DCktimeCriteria criteria) {
        log.debug("REST request to count DCktimes by criteria: {}", criteria);
        return ResponseEntity.ok().body(dCktimeQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /d-cktimes/:id} : get the "id" dCktime.
     *
     * @param id the id of the dCktimeDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the dCktimeDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/d-cktimes/{id}")
    public ResponseEntity<DCktimeDTO> getDCktime(@PathVariable Long id) {
        log.debug("REST request to get DCktime : {}", id);
        Optional<DCktimeDTO> dCktimeDTO = dCktimeService.findOne(id);
        return ResponseUtil.wrapOrNotFound(dCktimeDTO);
    }

    /**
     * {@code DELETE  /d-cktimes/:id} : delete the "id" dCktime.
     *
     * @param id the id of the dCktimeDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/d-cktimes/{id}")
    public ResponseEntity<Void> deleteDCktime(@PathVariable Long id) {
        log.debug("REST request to delete DCktime : {}", id);
        dCktimeService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }

    /**
     * {@code SEARCH  /_search/d-cktimes?query=:query} : search for the dCktime corresponding
     * to the query.
     *
     * @param query the query of the dCktime search.
     * @param pageable the pagination information.
     * @return the result of the search.
     */
    @GetMapping("/_search/d-cktimes")
    public ResponseEntity<List<DCktimeDTO>> searchDCktimes(@RequestParam String query, Pageable pageable) {
        log.debug("REST request to search for a page of DCktimes for query {}", query);
        Page<DCktimeDTO> page = dCktimeService.search(query, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }
}
