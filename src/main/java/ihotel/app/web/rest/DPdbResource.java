package ihotel.app.web.rest;

import static org.elasticsearch.index.query.QueryBuilders.*;

import ihotel.app.repository.DPdbRepository;
import ihotel.app.service.DPdbQueryService;
import ihotel.app.service.DPdbService;
import ihotel.app.service.criteria.DPdbCriteria;
import ihotel.app.service.dto.DPdbDTO;
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
 * REST controller for managing {@link ihotel.app.domain.DPdb}.
 */
@RestController
@RequestMapping("/api")
public class DPdbResource {

    private final Logger log = LoggerFactory.getLogger(DPdbResource.class);

    private static final String ENTITY_NAME = "dPdb";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final DPdbService dPdbService;

    private final DPdbRepository dPdbRepository;

    private final DPdbQueryService dPdbQueryService;

    public DPdbResource(DPdbService dPdbService, DPdbRepository dPdbRepository, DPdbQueryService dPdbQueryService) {
        this.dPdbService = dPdbService;
        this.dPdbRepository = dPdbRepository;
        this.dPdbQueryService = dPdbQueryService;
    }

    /**
     * {@code POST  /d-pdbs} : Create a new dPdb.
     *
     * @param dPdbDTO the dPdbDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new dPdbDTO, or with status {@code 400 (Bad Request)} if the dPdb has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/d-pdbs")
    public ResponseEntity<DPdbDTO> createDPdb(@Valid @RequestBody DPdbDTO dPdbDTO) throws URISyntaxException {
        log.debug("REST request to save DPdb : {}", dPdbDTO);
        if (dPdbDTO.getId() != null) {
            throw new BadRequestAlertException("A new dPdb cannot already have an ID", ENTITY_NAME, "idexists");
        }
        DPdbDTO result = dPdbService.save(dPdbDTO);
        return ResponseEntity
            .created(new URI("/api/d-pdbs/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /d-pdbs/:id} : Updates an existing dPdb.
     *
     * @param id the id of the dPdbDTO to save.
     * @param dPdbDTO the dPdbDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated dPdbDTO,
     * or with status {@code 400 (Bad Request)} if the dPdbDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the dPdbDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/d-pdbs/{id}")
    public ResponseEntity<DPdbDTO> updateDPdb(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody DPdbDTO dPdbDTO
    ) throws URISyntaxException {
        log.debug("REST request to update DPdb : {}, {}", id, dPdbDTO);
        if (dPdbDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, dPdbDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!dPdbRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        DPdbDTO result = dPdbService.save(dPdbDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, dPdbDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /d-pdbs/:id} : Partial updates given fields of an existing dPdb, field will ignore if it is null
     *
     * @param id the id of the dPdbDTO to save.
     * @param dPdbDTO the dPdbDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated dPdbDTO,
     * or with status {@code 400 (Bad Request)} if the dPdbDTO is not valid,
     * or with status {@code 404 (Not Found)} if the dPdbDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the dPdbDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/d-pdbs/{id}", consumes = "application/merge-patch+json")
    public ResponseEntity<DPdbDTO> partialUpdateDPdb(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody DPdbDTO dPdbDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update DPdb partially : {}, {}", id, dPdbDTO);
        if (dPdbDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, dPdbDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!dPdbRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<DPdbDTO> result = dPdbService.partialUpdate(dPdbDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, dPdbDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /d-pdbs} : get all the dPdbs.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of dPdbs in body.
     */
    @GetMapping("/d-pdbs")
    public ResponseEntity<List<DPdbDTO>> getAllDPdbs(DPdbCriteria criteria, Pageable pageable) {
        log.debug("REST request to get DPdbs by criteria: {}", criteria);
        Page<DPdbDTO> page = dPdbQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /d-pdbs/count} : count all the dPdbs.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/d-pdbs/count")
    public ResponseEntity<Long> countDPdbs(DPdbCriteria criteria) {
        log.debug("REST request to count DPdbs by criteria: {}", criteria);
        return ResponseEntity.ok().body(dPdbQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /d-pdbs/:id} : get the "id" dPdb.
     *
     * @param id the id of the dPdbDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the dPdbDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/d-pdbs/{id}")
    public ResponseEntity<DPdbDTO> getDPdb(@PathVariable Long id) {
        log.debug("REST request to get DPdb : {}", id);
        Optional<DPdbDTO> dPdbDTO = dPdbService.findOne(id);
        return ResponseUtil.wrapOrNotFound(dPdbDTO);
    }

    /**
     * {@code DELETE  /d-pdbs/:id} : delete the "id" dPdb.
     *
     * @param id the id of the dPdbDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/d-pdbs/{id}")
    public ResponseEntity<Void> deleteDPdb(@PathVariable Long id) {
        log.debug("REST request to delete DPdb : {}", id);
        dPdbService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }

    /**
     * {@code SEARCH  /_search/d-pdbs?query=:query} : search for the dPdb corresponding
     * to the query.
     *
     * @param query the query of the dPdb search.
     * @param pageable the pagination information.
     * @return the result of the search.
     */
    @GetMapping("/_search/d-pdbs")
    public ResponseEntity<List<DPdbDTO>> searchDPdbs(@RequestParam String query, Pageable pageable) {
        log.debug("REST request to search for a page of DPdbs for query {}", query);
        Page<DPdbDTO> page = dPdbService.search(query, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }
}
