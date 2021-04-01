package ihotel.app.web.rest;

import static org.elasticsearch.index.query.QueryBuilders.*;

import ihotel.app.repository.DKcRepository;
import ihotel.app.service.DKcQueryService;
import ihotel.app.service.DKcService;
import ihotel.app.service.criteria.DKcCriteria;
import ihotel.app.service.dto.DKcDTO;
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
 * REST controller for managing {@link ihotel.app.domain.DKc}.
 */
@RestController
@RequestMapping("/api")
public class DKcResource {

    private final Logger log = LoggerFactory.getLogger(DKcResource.class);

    private static final String ENTITY_NAME = "dKc";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final DKcService dKcService;

    private final DKcRepository dKcRepository;

    private final DKcQueryService dKcQueryService;

    public DKcResource(DKcService dKcService, DKcRepository dKcRepository, DKcQueryService dKcQueryService) {
        this.dKcService = dKcService;
        this.dKcRepository = dKcRepository;
        this.dKcQueryService = dKcQueryService;
    }

    /**
     * {@code POST  /d-kcs} : Create a new dKc.
     *
     * @param dKcDTO the dKcDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new dKcDTO, or with status {@code 400 (Bad Request)} if the dKc has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/d-kcs")
    public ResponseEntity<DKcDTO> createDKc(@Valid @RequestBody DKcDTO dKcDTO) throws URISyntaxException {
        log.debug("REST request to save DKc : {}", dKcDTO);
        if (dKcDTO.getId() != null) {
            throw new BadRequestAlertException("A new dKc cannot already have an ID", ENTITY_NAME, "idexists");
        }
        DKcDTO result = dKcService.save(dKcDTO);
        return ResponseEntity
            .created(new URI("/api/d-kcs/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /d-kcs/:id} : Updates an existing dKc.
     *
     * @param id the id of the dKcDTO to save.
     * @param dKcDTO the dKcDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated dKcDTO,
     * or with status {@code 400 (Bad Request)} if the dKcDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the dKcDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/d-kcs/{id}")
    public ResponseEntity<DKcDTO> updateDKc(@PathVariable(value = "id", required = false) final Long id, @Valid @RequestBody DKcDTO dKcDTO)
        throws URISyntaxException {
        log.debug("REST request to update DKc : {}, {}", id, dKcDTO);
        if (dKcDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, dKcDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!dKcRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        DKcDTO result = dKcService.save(dKcDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, dKcDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /d-kcs/:id} : Partial updates given fields of an existing dKc, field will ignore if it is null
     *
     * @param id the id of the dKcDTO to save.
     * @param dKcDTO the dKcDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated dKcDTO,
     * or with status {@code 400 (Bad Request)} if the dKcDTO is not valid,
     * or with status {@code 404 (Not Found)} if the dKcDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the dKcDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/d-kcs/{id}", consumes = "application/merge-patch+json")
    public ResponseEntity<DKcDTO> partialUpdateDKc(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody DKcDTO dKcDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update DKc partially : {}, {}", id, dKcDTO);
        if (dKcDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, dKcDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!dKcRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<DKcDTO> result = dKcService.partialUpdate(dKcDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, dKcDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /d-kcs} : get all the dKcs.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of dKcs in body.
     */
    @GetMapping("/d-kcs")
    public ResponseEntity<List<DKcDTO>> getAllDKcs(DKcCriteria criteria, Pageable pageable) {
        log.debug("REST request to get DKcs by criteria: {}", criteria);
        Page<DKcDTO> page = dKcQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /d-kcs/count} : count all the dKcs.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/d-kcs/count")
    public ResponseEntity<Long> countDKcs(DKcCriteria criteria) {
        log.debug("REST request to count DKcs by criteria: {}", criteria);
        return ResponseEntity.ok().body(dKcQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /d-kcs/:id} : get the "id" dKc.
     *
     * @param id the id of the dKcDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the dKcDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/d-kcs/{id}")
    public ResponseEntity<DKcDTO> getDKc(@PathVariable Long id) {
        log.debug("REST request to get DKc : {}", id);
        Optional<DKcDTO> dKcDTO = dKcService.findOne(id);
        return ResponseUtil.wrapOrNotFound(dKcDTO);
    }

    /**
     * {@code DELETE  /d-kcs/:id} : delete the "id" dKc.
     *
     * @param id the id of the dKcDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/d-kcs/{id}")
    public ResponseEntity<Void> deleteDKc(@PathVariable Long id) {
        log.debug("REST request to delete DKc : {}", id);
        dKcService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }

    /**
     * {@code SEARCH  /_search/d-kcs?query=:query} : search for the dKc corresponding
     * to the query.
     *
     * @param query the query of the dKc search.
     * @param pageable the pagination information.
     * @return the result of the search.
     */
    @GetMapping("/_search/d-kcs")
    public ResponseEntity<List<DKcDTO>> searchDKcs(@RequestParam String query, Pageable pageable) {
        log.debug("REST request to search for a page of DKcs for query {}", query);
        Page<DKcDTO> page = dKcService.search(query, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }
}
