package ihotel.app.web.rest;

import static org.elasticsearch.index.query.QueryBuilders.*;

import ihotel.app.repository.DDepotRepository;
import ihotel.app.service.DDepotQueryService;
import ihotel.app.service.DDepotService;
import ihotel.app.service.criteria.DDepotCriteria;
import ihotel.app.service.dto.DDepotDTO;
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
 * REST controller for managing {@link ihotel.app.domain.DDepot}.
 */
@RestController
@RequestMapping("/api")
public class DDepotResource {

    private final Logger log = LoggerFactory.getLogger(DDepotResource.class);

    private static final String ENTITY_NAME = "dDepot";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final DDepotService dDepotService;

    private final DDepotRepository dDepotRepository;

    private final DDepotQueryService dDepotQueryService;

    public DDepotResource(DDepotService dDepotService, DDepotRepository dDepotRepository, DDepotQueryService dDepotQueryService) {
        this.dDepotService = dDepotService;
        this.dDepotRepository = dDepotRepository;
        this.dDepotQueryService = dDepotQueryService;
    }

    /**
     * {@code POST  /d-depots} : Create a new dDepot.
     *
     * @param dDepotDTO the dDepotDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new dDepotDTO, or with status {@code 400 (Bad Request)} if the dDepot has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/d-depots")
    public ResponseEntity<DDepotDTO> createDDepot(@Valid @RequestBody DDepotDTO dDepotDTO) throws URISyntaxException {
        log.debug("REST request to save DDepot : {}", dDepotDTO);
        if (dDepotDTO.getId() != null) {
            throw new BadRequestAlertException("A new dDepot cannot already have an ID", ENTITY_NAME, "idexists");
        }
        DDepotDTO result = dDepotService.save(dDepotDTO);
        return ResponseEntity
            .created(new URI("/api/d-depots/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /d-depots/:id} : Updates an existing dDepot.
     *
     * @param id the id of the dDepotDTO to save.
     * @param dDepotDTO the dDepotDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated dDepotDTO,
     * or with status {@code 400 (Bad Request)} if the dDepotDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the dDepotDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/d-depots/{id}")
    public ResponseEntity<DDepotDTO> updateDDepot(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody DDepotDTO dDepotDTO
    ) throws URISyntaxException {
        log.debug("REST request to update DDepot : {}, {}", id, dDepotDTO);
        if (dDepotDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, dDepotDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!dDepotRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        DDepotDTO result = dDepotService.save(dDepotDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, dDepotDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /d-depots/:id} : Partial updates given fields of an existing dDepot, field will ignore if it is null
     *
     * @param id the id of the dDepotDTO to save.
     * @param dDepotDTO the dDepotDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated dDepotDTO,
     * or with status {@code 400 (Bad Request)} if the dDepotDTO is not valid,
     * or with status {@code 404 (Not Found)} if the dDepotDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the dDepotDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/d-depots/{id}", consumes = "application/merge-patch+json")
    public ResponseEntity<DDepotDTO> partialUpdateDDepot(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody DDepotDTO dDepotDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update DDepot partially : {}, {}", id, dDepotDTO);
        if (dDepotDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, dDepotDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!dDepotRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<DDepotDTO> result = dDepotService.partialUpdate(dDepotDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, dDepotDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /d-depots} : get all the dDepots.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of dDepots in body.
     */
    @GetMapping("/d-depots")
    public ResponseEntity<List<DDepotDTO>> getAllDDepots(DDepotCriteria criteria, Pageable pageable) {
        log.debug("REST request to get DDepots by criteria: {}", criteria);
        Page<DDepotDTO> page = dDepotQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /d-depots/count} : count all the dDepots.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/d-depots/count")
    public ResponseEntity<Long> countDDepots(DDepotCriteria criteria) {
        log.debug("REST request to count DDepots by criteria: {}", criteria);
        return ResponseEntity.ok().body(dDepotQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /d-depots/:id} : get the "id" dDepot.
     *
     * @param id the id of the dDepotDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the dDepotDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/d-depots/{id}")
    public ResponseEntity<DDepotDTO> getDDepot(@PathVariable Long id) {
        log.debug("REST request to get DDepot : {}", id);
        Optional<DDepotDTO> dDepotDTO = dDepotService.findOne(id);
        return ResponseUtil.wrapOrNotFound(dDepotDTO);
    }

    /**
     * {@code DELETE  /d-depots/:id} : delete the "id" dDepot.
     *
     * @param id the id of the dDepotDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/d-depots/{id}")
    public ResponseEntity<Void> deleteDDepot(@PathVariable Long id) {
        log.debug("REST request to delete DDepot : {}", id);
        dDepotService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }

    /**
     * {@code SEARCH  /_search/d-depots?query=:query} : search for the dDepot corresponding
     * to the query.
     *
     * @param query the query of the dDepot search.
     * @param pageable the pagination information.
     * @return the result of the search.
     */
    @GetMapping("/_search/d-depots")
    public ResponseEntity<List<DDepotDTO>> searchDDepots(@RequestParam String query, Pageable pageable) {
        log.debug("REST request to search for a page of DDepots for query {}", query);
        Page<DDepotDTO> page = dDepotService.search(query, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }
}
