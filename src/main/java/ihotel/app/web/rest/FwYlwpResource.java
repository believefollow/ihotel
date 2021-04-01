package ihotel.app.web.rest;

import static org.elasticsearch.index.query.QueryBuilders.*;

import ihotel.app.repository.FwYlwpRepository;
import ihotel.app.service.FwYlwpQueryService;
import ihotel.app.service.FwYlwpService;
import ihotel.app.service.criteria.FwYlwpCriteria;
import ihotel.app.service.dto.FwYlwpDTO;
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
 * REST controller for managing {@link ihotel.app.domain.FwYlwp}.
 */
@RestController
@RequestMapping("/api")
public class FwYlwpResource {

    private final Logger log = LoggerFactory.getLogger(FwYlwpResource.class);

    private static final String ENTITY_NAME = "fwYlwp";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final FwYlwpService fwYlwpService;

    private final FwYlwpRepository fwYlwpRepository;

    private final FwYlwpQueryService fwYlwpQueryService;

    public FwYlwpResource(FwYlwpService fwYlwpService, FwYlwpRepository fwYlwpRepository, FwYlwpQueryService fwYlwpQueryService) {
        this.fwYlwpService = fwYlwpService;
        this.fwYlwpRepository = fwYlwpRepository;
        this.fwYlwpQueryService = fwYlwpQueryService;
    }

    /**
     * {@code POST  /fw-ylwps} : Create a new fwYlwp.
     *
     * @param fwYlwpDTO the fwYlwpDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new fwYlwpDTO, or with status {@code 400 (Bad Request)} if the fwYlwp has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/fw-ylwps")
    public ResponseEntity<FwYlwpDTO> createFwYlwp(@Valid @RequestBody FwYlwpDTO fwYlwpDTO) throws URISyntaxException {
        log.debug("REST request to save FwYlwp : {}", fwYlwpDTO);
        if (fwYlwpDTO.getId() != null) {
            throw new BadRequestAlertException("A new fwYlwp cannot already have an ID", ENTITY_NAME, "idexists");
        }
        FwYlwpDTO result = fwYlwpService.save(fwYlwpDTO);
        return ResponseEntity
            .created(new URI("/api/fw-ylwps/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /fw-ylwps/:id} : Updates an existing fwYlwp.
     *
     * @param id the id of the fwYlwpDTO to save.
     * @param fwYlwpDTO the fwYlwpDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated fwYlwpDTO,
     * or with status {@code 400 (Bad Request)} if the fwYlwpDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the fwYlwpDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/fw-ylwps/{id}")
    public ResponseEntity<FwYlwpDTO> updateFwYlwp(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody FwYlwpDTO fwYlwpDTO
    ) throws URISyntaxException {
        log.debug("REST request to update FwYlwp : {}, {}", id, fwYlwpDTO);
        if (fwYlwpDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, fwYlwpDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!fwYlwpRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        FwYlwpDTO result = fwYlwpService.save(fwYlwpDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, fwYlwpDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /fw-ylwps/:id} : Partial updates given fields of an existing fwYlwp, field will ignore if it is null
     *
     * @param id the id of the fwYlwpDTO to save.
     * @param fwYlwpDTO the fwYlwpDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated fwYlwpDTO,
     * or with status {@code 400 (Bad Request)} if the fwYlwpDTO is not valid,
     * or with status {@code 404 (Not Found)} if the fwYlwpDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the fwYlwpDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/fw-ylwps/{id}", consumes = "application/merge-patch+json")
    public ResponseEntity<FwYlwpDTO> partialUpdateFwYlwp(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody FwYlwpDTO fwYlwpDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update FwYlwp partially : {}, {}", id, fwYlwpDTO);
        if (fwYlwpDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, fwYlwpDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!fwYlwpRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<FwYlwpDTO> result = fwYlwpService.partialUpdate(fwYlwpDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, fwYlwpDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /fw-ylwps} : get all the fwYlwps.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of fwYlwps in body.
     */
    @GetMapping("/fw-ylwps")
    public ResponseEntity<List<FwYlwpDTO>> getAllFwYlwps(FwYlwpCriteria criteria, Pageable pageable) {
        log.debug("REST request to get FwYlwps by criteria: {}", criteria);
        Page<FwYlwpDTO> page = fwYlwpQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /fw-ylwps/count} : count all the fwYlwps.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/fw-ylwps/count")
    public ResponseEntity<Long> countFwYlwps(FwYlwpCriteria criteria) {
        log.debug("REST request to count FwYlwps by criteria: {}", criteria);
        return ResponseEntity.ok().body(fwYlwpQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /fw-ylwps/:id} : get the "id" fwYlwp.
     *
     * @param id the id of the fwYlwpDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the fwYlwpDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/fw-ylwps/{id}")
    public ResponseEntity<FwYlwpDTO> getFwYlwp(@PathVariable Long id) {
        log.debug("REST request to get FwYlwp : {}", id);
        Optional<FwYlwpDTO> fwYlwpDTO = fwYlwpService.findOne(id);
        return ResponseUtil.wrapOrNotFound(fwYlwpDTO);
    }

    /**
     * {@code DELETE  /fw-ylwps/:id} : delete the "id" fwYlwp.
     *
     * @param id the id of the fwYlwpDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/fw-ylwps/{id}")
    public ResponseEntity<Void> deleteFwYlwp(@PathVariable Long id) {
        log.debug("REST request to delete FwYlwp : {}", id);
        fwYlwpService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }

    /**
     * {@code SEARCH  /_search/fw-ylwps?query=:query} : search for the fwYlwp corresponding
     * to the query.
     *
     * @param query the query of the fwYlwp search.
     * @param pageable the pagination information.
     * @return the result of the search.
     */
    @GetMapping("/_search/fw-ylwps")
    public ResponseEntity<List<FwYlwpDTO>> searchFwYlwps(@RequestParam String query, Pageable pageable) {
        log.debug("REST request to search for a page of FwYlwps for query {}", query);
        Page<FwYlwpDTO> page = fwYlwpService.search(query, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }
}
