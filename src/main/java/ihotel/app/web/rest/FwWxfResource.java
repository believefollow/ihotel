package ihotel.app.web.rest;

import static org.elasticsearch.index.query.QueryBuilders.*;

import ihotel.app.repository.FwWxfRepository;
import ihotel.app.service.FwWxfQueryService;
import ihotel.app.service.FwWxfService;
import ihotel.app.service.criteria.FwWxfCriteria;
import ihotel.app.service.dto.FwWxfDTO;
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
 * REST controller for managing {@link ihotel.app.domain.FwWxf}.
 */
@RestController
@RequestMapping("/api")
public class FwWxfResource {

    private final Logger log = LoggerFactory.getLogger(FwWxfResource.class);

    private static final String ENTITY_NAME = "fwWxf";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final FwWxfService fwWxfService;

    private final FwWxfRepository fwWxfRepository;

    private final FwWxfQueryService fwWxfQueryService;

    public FwWxfResource(FwWxfService fwWxfService, FwWxfRepository fwWxfRepository, FwWxfQueryService fwWxfQueryService) {
        this.fwWxfService = fwWxfService;
        this.fwWxfRepository = fwWxfRepository;
        this.fwWxfQueryService = fwWxfQueryService;
    }

    /**
     * {@code POST  /fw-wxfs} : Create a new fwWxf.
     *
     * @param fwWxfDTO the fwWxfDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new fwWxfDTO, or with status {@code 400 (Bad Request)} if the fwWxf has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/fw-wxfs")
    public ResponseEntity<FwWxfDTO> createFwWxf(@Valid @RequestBody FwWxfDTO fwWxfDTO) throws URISyntaxException {
        log.debug("REST request to save FwWxf : {}", fwWxfDTO);
        if (fwWxfDTO.getId() != null) {
            throw new BadRequestAlertException("A new fwWxf cannot already have an ID", ENTITY_NAME, "idexists");
        }
        FwWxfDTO result = fwWxfService.save(fwWxfDTO);
        return ResponseEntity
            .created(new URI("/api/fw-wxfs/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /fw-wxfs/:id} : Updates an existing fwWxf.
     *
     * @param id the id of the fwWxfDTO to save.
     * @param fwWxfDTO the fwWxfDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated fwWxfDTO,
     * or with status {@code 400 (Bad Request)} if the fwWxfDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the fwWxfDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/fw-wxfs/{id}")
    public ResponseEntity<FwWxfDTO> updateFwWxf(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody FwWxfDTO fwWxfDTO
    ) throws URISyntaxException {
        log.debug("REST request to update FwWxf : {}, {}", id, fwWxfDTO);
        if (fwWxfDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, fwWxfDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!fwWxfRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        FwWxfDTO result = fwWxfService.save(fwWxfDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, fwWxfDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /fw-wxfs/:id} : Partial updates given fields of an existing fwWxf, field will ignore if it is null
     *
     * @param id the id of the fwWxfDTO to save.
     * @param fwWxfDTO the fwWxfDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated fwWxfDTO,
     * or with status {@code 400 (Bad Request)} if the fwWxfDTO is not valid,
     * or with status {@code 404 (Not Found)} if the fwWxfDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the fwWxfDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/fw-wxfs/{id}", consumes = "application/merge-patch+json")
    public ResponseEntity<FwWxfDTO> partialUpdateFwWxf(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody FwWxfDTO fwWxfDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update FwWxf partially : {}, {}", id, fwWxfDTO);
        if (fwWxfDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, fwWxfDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!fwWxfRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<FwWxfDTO> result = fwWxfService.partialUpdate(fwWxfDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, fwWxfDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /fw-wxfs} : get all the fwWxfs.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of fwWxfs in body.
     */
    @GetMapping("/fw-wxfs")
    public ResponseEntity<List<FwWxfDTO>> getAllFwWxfs(FwWxfCriteria criteria, Pageable pageable) {
        log.debug("REST request to get FwWxfs by criteria: {}", criteria);
        Page<FwWxfDTO> page = fwWxfQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /fw-wxfs/count} : count all the fwWxfs.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/fw-wxfs/count")
    public ResponseEntity<Long> countFwWxfs(FwWxfCriteria criteria) {
        log.debug("REST request to count FwWxfs by criteria: {}", criteria);
        return ResponseEntity.ok().body(fwWxfQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /fw-wxfs/:id} : get the "id" fwWxf.
     *
     * @param id the id of the fwWxfDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the fwWxfDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/fw-wxfs/{id}")
    public ResponseEntity<FwWxfDTO> getFwWxf(@PathVariable Long id) {
        log.debug("REST request to get FwWxf : {}", id);
        Optional<FwWxfDTO> fwWxfDTO = fwWxfService.findOne(id);
        return ResponseUtil.wrapOrNotFound(fwWxfDTO);
    }

    /**
     * {@code DELETE  /fw-wxfs/:id} : delete the "id" fwWxf.
     *
     * @param id the id of the fwWxfDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/fw-wxfs/{id}")
    public ResponseEntity<Void> deleteFwWxf(@PathVariable Long id) {
        log.debug("REST request to delete FwWxf : {}", id);
        fwWxfService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }

    /**
     * {@code SEARCH  /_search/fw-wxfs?query=:query} : search for the fwWxf corresponding
     * to the query.
     *
     * @param query the query of the fwWxf search.
     * @param pageable the pagination information.
     * @return the result of the search.
     */
    @GetMapping("/_search/fw-wxfs")
    public ResponseEntity<List<FwWxfDTO>> searchFwWxfs(@RequestParam String query, Pageable pageable) {
        log.debug("REST request to search for a page of FwWxfs for query {}", query);
        Page<FwWxfDTO> page = fwWxfService.search(query, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }
}
