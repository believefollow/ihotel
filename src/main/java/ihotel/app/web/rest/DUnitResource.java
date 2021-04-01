package ihotel.app.web.rest;

import static org.elasticsearch.index.query.QueryBuilders.*;

import ihotel.app.repository.DUnitRepository;
import ihotel.app.service.DUnitQueryService;
import ihotel.app.service.DUnitService;
import ihotel.app.service.criteria.DUnitCriteria;
import ihotel.app.service.dto.DUnitDTO;
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
 * REST controller for managing {@link ihotel.app.domain.DUnit}.
 */
@RestController
@RequestMapping("/api")
public class DUnitResource {

    private final Logger log = LoggerFactory.getLogger(DUnitResource.class);

    private static final String ENTITY_NAME = "dUnit";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final DUnitService dUnitService;

    private final DUnitRepository dUnitRepository;

    private final DUnitQueryService dUnitQueryService;

    public DUnitResource(DUnitService dUnitService, DUnitRepository dUnitRepository, DUnitQueryService dUnitQueryService) {
        this.dUnitService = dUnitService;
        this.dUnitRepository = dUnitRepository;
        this.dUnitQueryService = dUnitQueryService;
    }

    /**
     * {@code POST  /d-units} : Create a new dUnit.
     *
     * @param dUnitDTO the dUnitDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new dUnitDTO, or with status {@code 400 (Bad Request)} if the dUnit has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/d-units")
    public ResponseEntity<DUnitDTO> createDUnit(@Valid @RequestBody DUnitDTO dUnitDTO) throws URISyntaxException {
        log.debug("REST request to save DUnit : {}", dUnitDTO);
        if (dUnitDTO.getId() != null) {
            throw new BadRequestAlertException("A new dUnit cannot already have an ID", ENTITY_NAME, "idexists");
        }
        DUnitDTO result = dUnitService.save(dUnitDTO);
        return ResponseEntity
            .created(new URI("/api/d-units/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /d-units/:id} : Updates an existing dUnit.
     *
     * @param id the id of the dUnitDTO to save.
     * @param dUnitDTO the dUnitDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated dUnitDTO,
     * or with status {@code 400 (Bad Request)} if the dUnitDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the dUnitDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/d-units/{id}")
    public ResponseEntity<DUnitDTO> updateDUnit(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody DUnitDTO dUnitDTO
    ) throws URISyntaxException {
        log.debug("REST request to update DUnit : {}, {}", id, dUnitDTO);
        if (dUnitDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, dUnitDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!dUnitRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        DUnitDTO result = dUnitService.save(dUnitDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, dUnitDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /d-units/:id} : Partial updates given fields of an existing dUnit, field will ignore if it is null
     *
     * @param id the id of the dUnitDTO to save.
     * @param dUnitDTO the dUnitDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated dUnitDTO,
     * or with status {@code 400 (Bad Request)} if the dUnitDTO is not valid,
     * or with status {@code 404 (Not Found)} if the dUnitDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the dUnitDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/d-units/{id}", consumes = "application/merge-patch+json")
    public ResponseEntity<DUnitDTO> partialUpdateDUnit(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody DUnitDTO dUnitDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update DUnit partially : {}, {}", id, dUnitDTO);
        if (dUnitDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, dUnitDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!dUnitRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<DUnitDTO> result = dUnitService.partialUpdate(dUnitDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, dUnitDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /d-units} : get all the dUnits.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of dUnits in body.
     */
    @GetMapping("/d-units")
    public ResponseEntity<List<DUnitDTO>> getAllDUnits(DUnitCriteria criteria, Pageable pageable) {
        log.debug("REST request to get DUnits by criteria: {}", criteria);
        Page<DUnitDTO> page = dUnitQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /d-units/count} : count all the dUnits.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/d-units/count")
    public ResponseEntity<Long> countDUnits(DUnitCriteria criteria) {
        log.debug("REST request to count DUnits by criteria: {}", criteria);
        return ResponseEntity.ok().body(dUnitQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /d-units/:id} : get the "id" dUnit.
     *
     * @param id the id of the dUnitDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the dUnitDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/d-units/{id}")
    public ResponseEntity<DUnitDTO> getDUnit(@PathVariable Long id) {
        log.debug("REST request to get DUnit : {}", id);
        Optional<DUnitDTO> dUnitDTO = dUnitService.findOne(id);
        return ResponseUtil.wrapOrNotFound(dUnitDTO);
    }

    /**
     * {@code DELETE  /d-units/:id} : delete the "id" dUnit.
     *
     * @param id the id of the dUnitDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/d-units/{id}")
    public ResponseEntity<Void> deleteDUnit(@PathVariable Long id) {
        log.debug("REST request to delete DUnit : {}", id);
        dUnitService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }

    /**
     * {@code SEARCH  /_search/d-units?query=:query} : search for the dUnit corresponding
     * to the query.
     *
     * @param query the query of the dUnit search.
     * @param pageable the pagination information.
     * @return the result of the search.
     */
    @GetMapping("/_search/d-units")
    public ResponseEntity<List<DUnitDTO>> searchDUnits(@RequestParam String query, Pageable pageable) {
        log.debug("REST request to search for a page of DUnits for query {}", query);
        Page<DUnitDTO> page = dUnitService.search(query, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }
}
