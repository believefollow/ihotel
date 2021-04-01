package ihotel.app.web.rest;

import static org.elasticsearch.index.query.QueryBuilders.*;

import ihotel.app.repository.FwEmpnRepository;
import ihotel.app.service.FwEmpnQueryService;
import ihotel.app.service.FwEmpnService;
import ihotel.app.service.criteria.FwEmpnCriteria;
import ihotel.app.service.dto.FwEmpnDTO;
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
 * REST controller for managing {@link ihotel.app.domain.FwEmpn}.
 */
@RestController
@RequestMapping("/api")
public class FwEmpnResource {

    private final Logger log = LoggerFactory.getLogger(FwEmpnResource.class);

    private static final String ENTITY_NAME = "fwEmpn";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final FwEmpnService fwEmpnService;

    private final FwEmpnRepository fwEmpnRepository;

    private final FwEmpnQueryService fwEmpnQueryService;

    public FwEmpnResource(FwEmpnService fwEmpnService, FwEmpnRepository fwEmpnRepository, FwEmpnQueryService fwEmpnQueryService) {
        this.fwEmpnService = fwEmpnService;
        this.fwEmpnRepository = fwEmpnRepository;
        this.fwEmpnQueryService = fwEmpnQueryService;
    }

    /**
     * {@code POST  /fw-empns} : Create a new fwEmpn.
     *
     * @param fwEmpnDTO the fwEmpnDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new fwEmpnDTO, or with status {@code 400 (Bad Request)} if the fwEmpn has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/fw-empns")
    public ResponseEntity<FwEmpnDTO> createFwEmpn(@Valid @RequestBody FwEmpnDTO fwEmpnDTO) throws URISyntaxException {
        log.debug("REST request to save FwEmpn : {}", fwEmpnDTO);
        if (fwEmpnDTO.getId() != null) {
            throw new BadRequestAlertException("A new fwEmpn cannot already have an ID", ENTITY_NAME, "idexists");
        }
        FwEmpnDTO result = fwEmpnService.save(fwEmpnDTO);
        return ResponseEntity
            .created(new URI("/api/fw-empns/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /fw-empns/:id} : Updates an existing fwEmpn.
     *
     * @param id the id of the fwEmpnDTO to save.
     * @param fwEmpnDTO the fwEmpnDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated fwEmpnDTO,
     * or with status {@code 400 (Bad Request)} if the fwEmpnDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the fwEmpnDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/fw-empns/{id}")
    public ResponseEntity<FwEmpnDTO> updateFwEmpn(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody FwEmpnDTO fwEmpnDTO
    ) throws URISyntaxException {
        log.debug("REST request to update FwEmpn : {}, {}", id, fwEmpnDTO);
        if (fwEmpnDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, fwEmpnDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!fwEmpnRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        FwEmpnDTO result = fwEmpnService.save(fwEmpnDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, fwEmpnDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /fw-empns/:id} : Partial updates given fields of an existing fwEmpn, field will ignore if it is null
     *
     * @param id the id of the fwEmpnDTO to save.
     * @param fwEmpnDTO the fwEmpnDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated fwEmpnDTO,
     * or with status {@code 400 (Bad Request)} if the fwEmpnDTO is not valid,
     * or with status {@code 404 (Not Found)} if the fwEmpnDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the fwEmpnDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/fw-empns/{id}", consumes = "application/merge-patch+json")
    public ResponseEntity<FwEmpnDTO> partialUpdateFwEmpn(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody FwEmpnDTO fwEmpnDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update FwEmpn partially : {}, {}", id, fwEmpnDTO);
        if (fwEmpnDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, fwEmpnDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!fwEmpnRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<FwEmpnDTO> result = fwEmpnService.partialUpdate(fwEmpnDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, fwEmpnDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /fw-empns} : get all the fwEmpns.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of fwEmpns in body.
     */
    @GetMapping("/fw-empns")
    public ResponseEntity<List<FwEmpnDTO>> getAllFwEmpns(FwEmpnCriteria criteria, Pageable pageable) {
        log.debug("REST request to get FwEmpns by criteria: {}", criteria);
        Page<FwEmpnDTO> page = fwEmpnQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /fw-empns/count} : count all the fwEmpns.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/fw-empns/count")
    public ResponseEntity<Long> countFwEmpns(FwEmpnCriteria criteria) {
        log.debug("REST request to count FwEmpns by criteria: {}", criteria);
        return ResponseEntity.ok().body(fwEmpnQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /fw-empns/:id} : get the "id" fwEmpn.
     *
     * @param id the id of the fwEmpnDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the fwEmpnDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/fw-empns/{id}")
    public ResponseEntity<FwEmpnDTO> getFwEmpn(@PathVariable Long id) {
        log.debug("REST request to get FwEmpn : {}", id);
        Optional<FwEmpnDTO> fwEmpnDTO = fwEmpnService.findOne(id);
        return ResponseUtil.wrapOrNotFound(fwEmpnDTO);
    }

    /**
     * {@code DELETE  /fw-empns/:id} : delete the "id" fwEmpn.
     *
     * @param id the id of the fwEmpnDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/fw-empns/{id}")
    public ResponseEntity<Void> deleteFwEmpn(@PathVariable Long id) {
        log.debug("REST request to delete FwEmpn : {}", id);
        fwEmpnService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }

    /**
     * {@code SEARCH  /_search/fw-empns?query=:query} : search for the fwEmpn corresponding
     * to the query.
     *
     * @param query the query of the fwEmpn search.
     * @param pageable the pagination information.
     * @return the result of the search.
     */
    @GetMapping("/_search/fw-empns")
    public ResponseEntity<List<FwEmpnDTO>> searchFwEmpns(@RequestParam String query, Pageable pageable) {
        log.debug("REST request to search for a page of FwEmpns for query {}", query);
        Page<FwEmpnDTO> page = fwEmpnService.search(query, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }
}
