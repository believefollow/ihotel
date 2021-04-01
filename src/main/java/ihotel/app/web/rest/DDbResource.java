package ihotel.app.web.rest;

import static org.elasticsearch.index.query.QueryBuilders.*;

import ihotel.app.repository.DDbRepository;
import ihotel.app.service.DDbQueryService;
import ihotel.app.service.DDbService;
import ihotel.app.service.criteria.DDbCriteria;
import ihotel.app.service.dto.DDbDTO;
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
 * REST controller for managing {@link ihotel.app.domain.DDb}.
 */
@RestController
@RequestMapping("/api")
public class DDbResource {

    private final Logger log = LoggerFactory.getLogger(DDbResource.class);

    private static final String ENTITY_NAME = "dDb";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final DDbService dDbService;

    private final DDbRepository dDbRepository;

    private final DDbQueryService dDbQueryService;

    public DDbResource(DDbService dDbService, DDbRepository dDbRepository, DDbQueryService dDbQueryService) {
        this.dDbService = dDbService;
        this.dDbRepository = dDbRepository;
        this.dDbQueryService = dDbQueryService;
    }

    /**
     * {@code POST  /d-dbs} : Create a new dDb.
     *
     * @param dDbDTO the dDbDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new dDbDTO, or with status {@code 400 (Bad Request)} if the dDb has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/d-dbs")
    public ResponseEntity<DDbDTO> createDDb(@Valid @RequestBody DDbDTO dDbDTO) throws URISyntaxException {
        log.debug("REST request to save DDb : {}", dDbDTO);
        if (dDbDTO.getId() != null) {
            throw new BadRequestAlertException("A new dDb cannot already have an ID", ENTITY_NAME, "idexists");
        }
        DDbDTO result = dDbService.save(dDbDTO);
        return ResponseEntity
            .created(new URI("/api/d-dbs/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /d-dbs/:id} : Updates an existing dDb.
     *
     * @param id the id of the dDbDTO to save.
     * @param dDbDTO the dDbDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated dDbDTO,
     * or with status {@code 400 (Bad Request)} if the dDbDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the dDbDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/d-dbs/{id}")
    public ResponseEntity<DDbDTO> updateDDb(@PathVariable(value = "id", required = false) final Long id, @Valid @RequestBody DDbDTO dDbDTO)
        throws URISyntaxException {
        log.debug("REST request to update DDb : {}, {}", id, dDbDTO);
        if (dDbDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, dDbDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!dDbRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        DDbDTO result = dDbService.save(dDbDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, dDbDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /d-dbs/:id} : Partial updates given fields of an existing dDb, field will ignore if it is null
     *
     * @param id the id of the dDbDTO to save.
     * @param dDbDTO the dDbDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated dDbDTO,
     * or with status {@code 400 (Bad Request)} if the dDbDTO is not valid,
     * or with status {@code 404 (Not Found)} if the dDbDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the dDbDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/d-dbs/{id}", consumes = "application/merge-patch+json")
    public ResponseEntity<DDbDTO> partialUpdateDDb(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody DDbDTO dDbDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update DDb partially : {}, {}", id, dDbDTO);
        if (dDbDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, dDbDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!dDbRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<DDbDTO> result = dDbService.partialUpdate(dDbDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, dDbDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /d-dbs} : get all the dDbs.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of dDbs in body.
     */
    @GetMapping("/d-dbs")
    public ResponseEntity<List<DDbDTO>> getAllDDbs(DDbCriteria criteria, Pageable pageable) {
        log.debug("REST request to get DDbs by criteria: {}", criteria);
        Page<DDbDTO> page = dDbQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /d-dbs/count} : count all the dDbs.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/d-dbs/count")
    public ResponseEntity<Long> countDDbs(DDbCriteria criteria) {
        log.debug("REST request to count DDbs by criteria: {}", criteria);
        return ResponseEntity.ok().body(dDbQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /d-dbs/:id} : get the "id" dDb.
     *
     * @param id the id of the dDbDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the dDbDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/d-dbs/{id}")
    public ResponseEntity<DDbDTO> getDDb(@PathVariable Long id) {
        log.debug("REST request to get DDb : {}", id);
        Optional<DDbDTO> dDbDTO = dDbService.findOne(id);
        return ResponseUtil.wrapOrNotFound(dDbDTO);
    }

    /**
     * {@code DELETE  /d-dbs/:id} : delete the "id" dDb.
     *
     * @param id the id of the dDbDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/d-dbs/{id}")
    public ResponseEntity<Void> deleteDDb(@PathVariable Long id) {
        log.debug("REST request to delete DDb : {}", id);
        dDbService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }

    /**
     * {@code SEARCH  /_search/d-dbs?query=:query} : search for the dDb corresponding
     * to the query.
     *
     * @param query the query of the dDb search.
     * @param pageable the pagination information.
     * @return the result of the search.
     */
    @GetMapping("/_search/d-dbs")
    public ResponseEntity<List<DDbDTO>> searchDDbs(@RequestParam String query, Pageable pageable) {
        log.debug("REST request to search for a page of DDbs for query {}", query);
        Page<DDbDTO> page = dDbService.search(query, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }
}
