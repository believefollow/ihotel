package ihotel.app.web.rest;

import static org.elasticsearch.index.query.QueryBuilders.*;

import ihotel.app.repository.FkCzRepository;
import ihotel.app.service.FkCzQueryService;
import ihotel.app.service.FkCzService;
import ihotel.app.service.criteria.FkCzCriteria;
import ihotel.app.service.dto.FkCzDTO;
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
 * REST controller for managing {@link ihotel.app.domain.FkCz}.
 */
@RestController
@RequestMapping("/api")
public class FkCzResource {

    private final Logger log = LoggerFactory.getLogger(FkCzResource.class);

    private static final String ENTITY_NAME = "fkCz";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final FkCzService fkCzService;

    private final FkCzRepository fkCzRepository;

    private final FkCzQueryService fkCzQueryService;

    public FkCzResource(FkCzService fkCzService, FkCzRepository fkCzRepository, FkCzQueryService fkCzQueryService) {
        this.fkCzService = fkCzService;
        this.fkCzRepository = fkCzRepository;
        this.fkCzQueryService = fkCzQueryService;
    }

    /**
     * {@code POST  /fk-czs} : Create a new fkCz.
     *
     * @param fkCzDTO the fkCzDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new fkCzDTO, or with status {@code 400 (Bad Request)} if the fkCz has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/fk-czs")
    public ResponseEntity<FkCzDTO> createFkCz(@Valid @RequestBody FkCzDTO fkCzDTO) throws URISyntaxException {
        log.debug("REST request to save FkCz : {}", fkCzDTO);
        if (fkCzDTO.getId() != null) {
            throw new BadRequestAlertException("A new fkCz cannot already have an ID", ENTITY_NAME, "idexists");
        }
        FkCzDTO result = fkCzService.save(fkCzDTO);
        return ResponseEntity
            .created(new URI("/api/fk-czs/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /fk-czs/:id} : Updates an existing fkCz.
     *
     * @param id the id of the fkCzDTO to save.
     * @param fkCzDTO the fkCzDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated fkCzDTO,
     * or with status {@code 400 (Bad Request)} if the fkCzDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the fkCzDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/fk-czs/{id}")
    public ResponseEntity<FkCzDTO> updateFkCz(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody FkCzDTO fkCzDTO
    ) throws URISyntaxException {
        log.debug("REST request to update FkCz : {}, {}", id, fkCzDTO);
        if (fkCzDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, fkCzDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!fkCzRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        FkCzDTO result = fkCzService.save(fkCzDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, fkCzDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /fk-czs/:id} : Partial updates given fields of an existing fkCz, field will ignore if it is null
     *
     * @param id the id of the fkCzDTO to save.
     * @param fkCzDTO the fkCzDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated fkCzDTO,
     * or with status {@code 400 (Bad Request)} if the fkCzDTO is not valid,
     * or with status {@code 404 (Not Found)} if the fkCzDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the fkCzDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/fk-czs/{id}", consumes = "application/merge-patch+json")
    public ResponseEntity<FkCzDTO> partialUpdateFkCz(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody FkCzDTO fkCzDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update FkCz partially : {}, {}", id, fkCzDTO);
        if (fkCzDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, fkCzDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!fkCzRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<FkCzDTO> result = fkCzService.partialUpdate(fkCzDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, fkCzDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /fk-czs} : get all the fkCzs.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of fkCzs in body.
     */
    @GetMapping("/fk-czs")
    public ResponseEntity<List<FkCzDTO>> getAllFkCzs(FkCzCriteria criteria, Pageable pageable) {
        log.debug("REST request to get FkCzs by criteria: {}", criteria);
        Page<FkCzDTO> page = fkCzQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /fk-czs/count} : count all the fkCzs.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/fk-czs/count")
    public ResponseEntity<Long> countFkCzs(FkCzCriteria criteria) {
        log.debug("REST request to count FkCzs by criteria: {}", criteria);
        return ResponseEntity.ok().body(fkCzQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /fk-czs/:id} : get the "id" fkCz.
     *
     * @param id the id of the fkCzDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the fkCzDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/fk-czs/{id}")
    public ResponseEntity<FkCzDTO> getFkCz(@PathVariable Long id) {
        log.debug("REST request to get FkCz : {}", id);
        Optional<FkCzDTO> fkCzDTO = fkCzService.findOne(id);
        return ResponseUtil.wrapOrNotFound(fkCzDTO);
    }

    /**
     * {@code DELETE  /fk-czs/:id} : delete the "id" fkCz.
     *
     * @param id the id of the fkCzDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/fk-czs/{id}")
    public ResponseEntity<Void> deleteFkCz(@PathVariable Long id) {
        log.debug("REST request to delete FkCz : {}", id);
        fkCzService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }

    /**
     * {@code SEARCH  /_search/fk-czs?query=:query} : search for the fkCz corresponding
     * to the query.
     *
     * @param query the query of the fkCz search.
     * @param pageable the pagination information.
     * @return the result of the search.
     */
    @GetMapping("/_search/fk-czs")
    public ResponseEntity<List<FkCzDTO>> searchFkCzs(@RequestParam String query, Pageable pageable) {
        log.debug("REST request to search for a page of FkCzs for query {}", query);
        Page<FkCzDTO> page = fkCzService.search(query, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }
}
