package ihotel.app.web.rest;

import static org.elasticsearch.index.query.QueryBuilders.*;

import ihotel.app.repository.ComsetRepository;
import ihotel.app.service.ComsetQueryService;
import ihotel.app.service.ComsetService;
import ihotel.app.service.criteria.ComsetCriteria;
import ihotel.app.service.dto.ComsetDTO;
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
 * REST controller for managing {@link ihotel.app.domain.Comset}.
 */
@RestController
@RequestMapping("/api")
public class ComsetResource {

    private final Logger log = LoggerFactory.getLogger(ComsetResource.class);

    private static final String ENTITY_NAME = "comset";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ComsetService comsetService;

    private final ComsetRepository comsetRepository;

    private final ComsetQueryService comsetQueryService;

    public ComsetResource(ComsetService comsetService, ComsetRepository comsetRepository, ComsetQueryService comsetQueryService) {
        this.comsetService = comsetService;
        this.comsetRepository = comsetRepository;
        this.comsetQueryService = comsetQueryService;
    }

    /**
     * {@code POST  /comsets} : Create a new comset.
     *
     * @param comsetDTO the comsetDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new comsetDTO, or with status {@code 400 (Bad Request)} if the comset has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/comsets")
    public ResponseEntity<ComsetDTO> createComset(@Valid @RequestBody ComsetDTO comsetDTO) throws URISyntaxException {
        log.debug("REST request to save Comset : {}", comsetDTO);
        if (comsetDTO.getId() != null) {
            throw new BadRequestAlertException("A new comset cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ComsetDTO result = comsetService.save(comsetDTO);
        return ResponseEntity
            .created(new URI("/api/comsets/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /comsets/:id} : Updates an existing comset.
     *
     * @param id the id of the comsetDTO to save.
     * @param comsetDTO the comsetDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated comsetDTO,
     * or with status {@code 400 (Bad Request)} if the comsetDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the comsetDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/comsets/{id}")
    public ResponseEntity<ComsetDTO> updateComset(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody ComsetDTO comsetDTO
    ) throws URISyntaxException {
        log.debug("REST request to update Comset : {}, {}", id, comsetDTO);
        if (comsetDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, comsetDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!comsetRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        ComsetDTO result = comsetService.save(comsetDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, comsetDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /comsets/:id} : Partial updates given fields of an existing comset, field will ignore if it is null
     *
     * @param id the id of the comsetDTO to save.
     * @param comsetDTO the comsetDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated comsetDTO,
     * or with status {@code 400 (Bad Request)} if the comsetDTO is not valid,
     * or with status {@code 404 (Not Found)} if the comsetDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the comsetDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/comsets/{id}", consumes = "application/merge-patch+json")
    public ResponseEntity<ComsetDTO> partialUpdateComset(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody ComsetDTO comsetDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update Comset partially : {}, {}", id, comsetDTO);
        if (comsetDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, comsetDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!comsetRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<ComsetDTO> result = comsetService.partialUpdate(comsetDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, comsetDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /comsets} : get all the comsets.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of comsets in body.
     */
    @GetMapping("/comsets")
    public ResponseEntity<List<ComsetDTO>> getAllComsets(ComsetCriteria criteria, Pageable pageable) {
        log.debug("REST request to get Comsets by criteria: {}", criteria);
        Page<ComsetDTO> page = comsetQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /comsets/count} : count all the comsets.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/comsets/count")
    public ResponseEntity<Long> countComsets(ComsetCriteria criteria) {
        log.debug("REST request to count Comsets by criteria: {}", criteria);
        return ResponseEntity.ok().body(comsetQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /comsets/:id} : get the "id" comset.
     *
     * @param id the id of the comsetDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the comsetDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/comsets/{id}")
    public ResponseEntity<ComsetDTO> getComset(@PathVariable Long id) {
        log.debug("REST request to get Comset : {}", id);
        Optional<ComsetDTO> comsetDTO = comsetService.findOne(id);
        return ResponseUtil.wrapOrNotFound(comsetDTO);
    }

    /**
     * {@code DELETE  /comsets/:id} : delete the "id" comset.
     *
     * @param id the id of the comsetDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/comsets/{id}")
    public ResponseEntity<Void> deleteComset(@PathVariable Long id) {
        log.debug("REST request to delete Comset : {}", id);
        comsetService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }

    /**
     * {@code SEARCH  /_search/comsets?query=:query} : search for the comset corresponding
     * to the query.
     *
     * @param query the query of the comset search.
     * @param pageable the pagination information.
     * @return the result of the search.
     */
    @GetMapping("/_search/comsets")
    public ResponseEntity<List<ComsetDTO>> searchComsets(@RequestParam String query, Pageable pageable) {
        log.debug("REST request to search for a page of Comsets for query {}", query);
        Page<ComsetDTO> page = comsetService.search(query, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }
}
