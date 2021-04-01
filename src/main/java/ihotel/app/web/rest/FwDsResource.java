package ihotel.app.web.rest;

import static org.elasticsearch.index.query.QueryBuilders.*;

import ihotel.app.repository.FwDsRepository;
import ihotel.app.service.FwDsQueryService;
import ihotel.app.service.FwDsService;
import ihotel.app.service.criteria.FwDsCriteria;
import ihotel.app.service.dto.FwDsDTO;
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
 * REST controller for managing {@link ihotel.app.domain.FwDs}.
 */
@RestController
@RequestMapping("/api")
public class FwDsResource {

    private final Logger log = LoggerFactory.getLogger(FwDsResource.class);

    private static final String ENTITY_NAME = "fwDs";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final FwDsService fwDsService;

    private final FwDsRepository fwDsRepository;

    private final FwDsQueryService fwDsQueryService;

    public FwDsResource(FwDsService fwDsService, FwDsRepository fwDsRepository, FwDsQueryService fwDsQueryService) {
        this.fwDsService = fwDsService;
        this.fwDsRepository = fwDsRepository;
        this.fwDsQueryService = fwDsQueryService;
    }

    /**
     * {@code POST  /fw-ds} : Create a new fwDs.
     *
     * @param fwDsDTO the fwDsDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new fwDsDTO, or with status {@code 400 (Bad Request)} if the fwDs has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/fw-ds")
    public ResponseEntity<FwDsDTO> createFwDs(@Valid @RequestBody FwDsDTO fwDsDTO) throws URISyntaxException {
        log.debug("REST request to save FwDs : {}", fwDsDTO);
        if (fwDsDTO.getId() != null) {
            throw new BadRequestAlertException("A new fwDs cannot already have an ID", ENTITY_NAME, "idexists");
        }
        FwDsDTO result = fwDsService.save(fwDsDTO);
        return ResponseEntity
            .created(new URI("/api/fw-ds/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /fw-ds/:id} : Updates an existing fwDs.
     *
     * @param id the id of the fwDsDTO to save.
     * @param fwDsDTO the fwDsDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated fwDsDTO,
     * or with status {@code 400 (Bad Request)} if the fwDsDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the fwDsDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/fw-ds/{id}")
    public ResponseEntity<FwDsDTO> updateFwDs(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody FwDsDTO fwDsDTO
    ) throws URISyntaxException {
        log.debug("REST request to update FwDs : {}, {}", id, fwDsDTO);
        if (fwDsDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, fwDsDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!fwDsRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        FwDsDTO result = fwDsService.save(fwDsDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, fwDsDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /fw-ds/:id} : Partial updates given fields of an existing fwDs, field will ignore if it is null
     *
     * @param id the id of the fwDsDTO to save.
     * @param fwDsDTO the fwDsDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated fwDsDTO,
     * or with status {@code 400 (Bad Request)} if the fwDsDTO is not valid,
     * or with status {@code 404 (Not Found)} if the fwDsDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the fwDsDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/fw-ds/{id}", consumes = "application/merge-patch+json")
    public ResponseEntity<FwDsDTO> partialUpdateFwDs(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody FwDsDTO fwDsDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update FwDs partially : {}, {}", id, fwDsDTO);
        if (fwDsDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, fwDsDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!fwDsRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<FwDsDTO> result = fwDsService.partialUpdate(fwDsDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, fwDsDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /fw-ds} : get all the fwDs.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of fwDs in body.
     */
    @GetMapping("/fw-ds")
    public ResponseEntity<List<FwDsDTO>> getAllFwDs(FwDsCriteria criteria, Pageable pageable) {
        log.debug("REST request to get FwDs by criteria: {}", criteria);
        Page<FwDsDTO> page = fwDsQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /fw-ds/count} : count all the fwDs.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/fw-ds/count")
    public ResponseEntity<Long> countFwDs(FwDsCriteria criteria) {
        log.debug("REST request to count FwDs by criteria: {}", criteria);
        return ResponseEntity.ok().body(fwDsQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /fw-ds/:id} : get the "id" fwDs.
     *
     * @param id the id of the fwDsDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the fwDsDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/fw-ds/{id}")
    public ResponseEntity<FwDsDTO> getFwDs(@PathVariable Long id) {
        log.debug("REST request to get FwDs : {}", id);
        Optional<FwDsDTO> fwDsDTO = fwDsService.findOne(id);
        return ResponseUtil.wrapOrNotFound(fwDsDTO);
    }

    /**
     * {@code DELETE  /fw-ds/:id} : delete the "id" fwDs.
     *
     * @param id the id of the fwDsDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/fw-ds/{id}")
    public ResponseEntity<Void> deleteFwDs(@PathVariable Long id) {
        log.debug("REST request to delete FwDs : {}", id);
        fwDsService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }

    /**
     * {@code SEARCH  /_search/fw-ds?query=:query} : search for the fwDs corresponding
     * to the query.
     *
     * @param query the query of the fwDs search.
     * @param pageable the pagination information.
     * @return the result of the search.
     */
    @GetMapping("/_search/fw-ds")
    public ResponseEntity<List<FwDsDTO>> searchFwDs(@RequestParam String query, Pageable pageable) {
        log.debug("REST request to search for a page of FwDs for query {}", query);
        Page<FwDsDTO> page = fwDsService.search(query, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }
}
