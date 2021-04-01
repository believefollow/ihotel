package ihotel.app.web.rest;

import static org.elasticsearch.index.query.QueryBuilders.*;

import ihotel.app.repository.DCkRepository;
import ihotel.app.service.DCkQueryService;
import ihotel.app.service.DCkService;
import ihotel.app.service.criteria.DCkCriteria;
import ihotel.app.service.dto.DCkDTO;
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
 * REST controller for managing {@link ihotel.app.domain.DCk}.
 */
@RestController
@RequestMapping("/api")
public class DCkResource {

    private final Logger log = LoggerFactory.getLogger(DCkResource.class);

    private static final String ENTITY_NAME = "dCk";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final DCkService dCkService;

    private final DCkRepository dCkRepository;

    private final DCkQueryService dCkQueryService;

    public DCkResource(DCkService dCkService, DCkRepository dCkRepository, DCkQueryService dCkQueryService) {
        this.dCkService = dCkService;
        this.dCkRepository = dCkRepository;
        this.dCkQueryService = dCkQueryService;
    }

    /**
     * {@code POST  /d-cks} : Create a new dCk.
     *
     * @param dCkDTO the dCkDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new dCkDTO, or with status {@code 400 (Bad Request)} if the dCk has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/d-cks")
    public ResponseEntity<DCkDTO> createDCk(@Valid @RequestBody DCkDTO dCkDTO) throws URISyntaxException {
        log.debug("REST request to save DCk : {}", dCkDTO);
        if (dCkDTO.getId() != null) {
            throw new BadRequestAlertException("A new dCk cannot already have an ID", ENTITY_NAME, "idexists");
        }
        DCkDTO result = dCkService.save(dCkDTO);
        return ResponseEntity
            .created(new URI("/api/d-cks/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /d-cks/:id} : Updates an existing dCk.
     *
     * @param id the id of the dCkDTO to save.
     * @param dCkDTO the dCkDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated dCkDTO,
     * or with status {@code 400 (Bad Request)} if the dCkDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the dCkDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/d-cks/{id}")
    public ResponseEntity<DCkDTO> updateDCk(@PathVariable(value = "id", required = false) final Long id, @Valid @RequestBody DCkDTO dCkDTO)
        throws URISyntaxException {
        log.debug("REST request to update DCk : {}, {}", id, dCkDTO);
        if (dCkDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, dCkDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!dCkRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        DCkDTO result = dCkService.save(dCkDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, dCkDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /d-cks/:id} : Partial updates given fields of an existing dCk, field will ignore if it is null
     *
     * @param id the id of the dCkDTO to save.
     * @param dCkDTO the dCkDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated dCkDTO,
     * or with status {@code 400 (Bad Request)} if the dCkDTO is not valid,
     * or with status {@code 404 (Not Found)} if the dCkDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the dCkDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/d-cks/{id}", consumes = "application/merge-patch+json")
    public ResponseEntity<DCkDTO> partialUpdateDCk(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody DCkDTO dCkDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update DCk partially : {}, {}", id, dCkDTO);
        if (dCkDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, dCkDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!dCkRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<DCkDTO> result = dCkService.partialUpdate(dCkDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, dCkDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /d-cks} : get all the dCks.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of dCks in body.
     */
    @GetMapping("/d-cks")
    public ResponseEntity<List<DCkDTO>> getAllDCks(DCkCriteria criteria, Pageable pageable) {
        log.debug("REST request to get DCks by criteria: {}", criteria);
        Page<DCkDTO> page = dCkQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /d-cks/count} : count all the dCks.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/d-cks/count")
    public ResponseEntity<Long> countDCks(DCkCriteria criteria) {
        log.debug("REST request to count DCks by criteria: {}", criteria);
        return ResponseEntity.ok().body(dCkQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /d-cks/:id} : get the "id" dCk.
     *
     * @param id the id of the dCkDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the dCkDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/d-cks/{id}")
    public ResponseEntity<DCkDTO> getDCk(@PathVariable Long id) {
        log.debug("REST request to get DCk : {}", id);
        Optional<DCkDTO> dCkDTO = dCkService.findOne(id);
        return ResponseUtil.wrapOrNotFound(dCkDTO);
    }

    /**
     * {@code DELETE  /d-cks/:id} : delete the "id" dCk.
     *
     * @param id the id of the dCkDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/d-cks/{id}")
    public ResponseEntity<Void> deleteDCk(@PathVariable Long id) {
        log.debug("REST request to delete DCk : {}", id);
        dCkService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }

    /**
     * {@code SEARCH  /_search/d-cks?query=:query} : search for the dCk corresponding
     * to the query.
     *
     * @param query the query of the dCk search.
     * @param pageable the pagination information.
     * @return the result of the search.
     */
    @GetMapping("/_search/d-cks")
    public ResponseEntity<List<DCkDTO>> searchDCks(@RequestParam String query, Pageable pageable) {
        log.debug("REST request to search for a page of DCks for query {}", query);
        Page<DCkDTO> page = dCkService.search(query, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }
}
