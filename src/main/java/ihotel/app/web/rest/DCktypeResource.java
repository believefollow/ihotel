package ihotel.app.web.rest;

import static org.elasticsearch.index.query.QueryBuilders.*;

import ihotel.app.repository.DCktypeRepository;
import ihotel.app.service.DCktypeQueryService;
import ihotel.app.service.DCktypeService;
import ihotel.app.service.criteria.DCktypeCriteria;
import ihotel.app.service.dto.DCktypeDTO;
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
 * REST controller for managing {@link ihotel.app.domain.DCktype}.
 */
@RestController
@RequestMapping("/api")
public class DCktypeResource {

    private final Logger log = LoggerFactory.getLogger(DCktypeResource.class);

    private static final String ENTITY_NAME = "dCktype";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final DCktypeService dCktypeService;

    private final DCktypeRepository dCktypeRepository;

    private final DCktypeQueryService dCktypeQueryService;

    public DCktypeResource(DCktypeService dCktypeService, DCktypeRepository dCktypeRepository, DCktypeQueryService dCktypeQueryService) {
        this.dCktypeService = dCktypeService;
        this.dCktypeRepository = dCktypeRepository;
        this.dCktypeQueryService = dCktypeQueryService;
    }

    /**
     * {@code POST  /d-cktypes} : Create a new dCktype.
     *
     * @param dCktypeDTO the dCktypeDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new dCktypeDTO, or with status {@code 400 (Bad Request)} if the dCktype has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/d-cktypes")
    public ResponseEntity<DCktypeDTO> createDCktype(@Valid @RequestBody DCktypeDTO dCktypeDTO) throws URISyntaxException {
        log.debug("REST request to save DCktype : {}", dCktypeDTO);
        if (dCktypeDTO.getId() != null) {
            throw new BadRequestAlertException("A new dCktype cannot already have an ID", ENTITY_NAME, "idexists");
        }
        DCktypeDTO result = dCktypeService.save(dCktypeDTO);
        return ResponseEntity
            .created(new URI("/api/d-cktypes/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /d-cktypes/:id} : Updates an existing dCktype.
     *
     * @param id the id of the dCktypeDTO to save.
     * @param dCktypeDTO the dCktypeDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated dCktypeDTO,
     * or with status {@code 400 (Bad Request)} if the dCktypeDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the dCktypeDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/d-cktypes/{id}")
    public ResponseEntity<DCktypeDTO> updateDCktype(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody DCktypeDTO dCktypeDTO
    ) throws URISyntaxException {
        log.debug("REST request to update DCktype : {}, {}", id, dCktypeDTO);
        if (dCktypeDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, dCktypeDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!dCktypeRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        DCktypeDTO result = dCktypeService.save(dCktypeDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, dCktypeDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /d-cktypes/:id} : Partial updates given fields of an existing dCktype, field will ignore if it is null
     *
     * @param id the id of the dCktypeDTO to save.
     * @param dCktypeDTO the dCktypeDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated dCktypeDTO,
     * or with status {@code 400 (Bad Request)} if the dCktypeDTO is not valid,
     * or with status {@code 404 (Not Found)} if the dCktypeDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the dCktypeDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/d-cktypes/{id}", consumes = "application/merge-patch+json")
    public ResponseEntity<DCktypeDTO> partialUpdateDCktype(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody DCktypeDTO dCktypeDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update DCktype partially : {}, {}", id, dCktypeDTO);
        if (dCktypeDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, dCktypeDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!dCktypeRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<DCktypeDTO> result = dCktypeService.partialUpdate(dCktypeDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, dCktypeDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /d-cktypes} : get all the dCktypes.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of dCktypes in body.
     */
    @GetMapping("/d-cktypes")
    public ResponseEntity<List<DCktypeDTO>> getAllDCktypes(DCktypeCriteria criteria, Pageable pageable) {
        log.debug("REST request to get DCktypes by criteria: {}", criteria);
        Page<DCktypeDTO> page = dCktypeQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /d-cktypes/count} : count all the dCktypes.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/d-cktypes/count")
    public ResponseEntity<Long> countDCktypes(DCktypeCriteria criteria) {
        log.debug("REST request to count DCktypes by criteria: {}", criteria);
        return ResponseEntity.ok().body(dCktypeQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /d-cktypes/:id} : get the "id" dCktype.
     *
     * @param id the id of the dCktypeDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the dCktypeDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/d-cktypes/{id}")
    public ResponseEntity<DCktypeDTO> getDCktype(@PathVariable Long id) {
        log.debug("REST request to get DCktype : {}", id);
        Optional<DCktypeDTO> dCktypeDTO = dCktypeService.findOne(id);
        return ResponseUtil.wrapOrNotFound(dCktypeDTO);
    }

    /**
     * {@code DELETE  /d-cktypes/:id} : delete the "id" dCktype.
     *
     * @param id the id of the dCktypeDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/d-cktypes/{id}")
    public ResponseEntity<Void> deleteDCktype(@PathVariable Long id) {
        log.debug("REST request to delete DCktype : {}", id);
        dCktypeService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }

    /**
     * {@code SEARCH  /_search/d-cktypes?query=:query} : search for the dCktype corresponding
     * to the query.
     *
     * @param query the query of the dCktype search.
     * @param pageable the pagination information.
     * @return the result of the search.
     */
    @GetMapping("/_search/d-cktypes")
    public ResponseEntity<List<DCktypeDTO>> searchDCktypes(@RequestParam String query, Pageable pageable) {
        log.debug("REST request to search for a page of DCktypes for query {}", query);
        Page<DCktypeDTO> page = dCktypeService.search(query, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }
}
