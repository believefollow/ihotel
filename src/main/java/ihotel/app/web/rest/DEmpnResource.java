package ihotel.app.web.rest;

import static org.elasticsearch.index.query.QueryBuilders.*;

import ihotel.app.repository.DEmpnRepository;
import ihotel.app.service.DEmpnQueryService;
import ihotel.app.service.DEmpnService;
import ihotel.app.service.criteria.DEmpnCriteria;
import ihotel.app.service.dto.DEmpnDTO;
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
 * REST controller for managing {@link ihotel.app.domain.DEmpn}.
 */
@RestController
@RequestMapping("/api")
public class DEmpnResource {

    private final Logger log = LoggerFactory.getLogger(DEmpnResource.class);

    private static final String ENTITY_NAME = "dEmpn";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final DEmpnService dEmpnService;

    private final DEmpnRepository dEmpnRepository;

    private final DEmpnQueryService dEmpnQueryService;

    public DEmpnResource(DEmpnService dEmpnService, DEmpnRepository dEmpnRepository, DEmpnQueryService dEmpnQueryService) {
        this.dEmpnService = dEmpnService;
        this.dEmpnRepository = dEmpnRepository;
        this.dEmpnQueryService = dEmpnQueryService;
    }

    /**
     * {@code POST  /d-empns} : Create a new dEmpn.
     *
     * @param dEmpnDTO the dEmpnDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new dEmpnDTO, or with status {@code 400 (Bad Request)} if the dEmpn has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/d-empns")
    public ResponseEntity<DEmpnDTO> createDEmpn(@Valid @RequestBody DEmpnDTO dEmpnDTO) throws URISyntaxException {
        log.debug("REST request to save DEmpn : {}", dEmpnDTO);
        if (dEmpnDTO.getId() != null) {
            throw new BadRequestAlertException("A new dEmpn cannot already have an ID", ENTITY_NAME, "idexists");
        }
        DEmpnDTO result = dEmpnService.save(dEmpnDTO);
        return ResponseEntity
            .created(new URI("/api/d-empns/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /d-empns/:id} : Updates an existing dEmpn.
     *
     * @param id the id of the dEmpnDTO to save.
     * @param dEmpnDTO the dEmpnDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated dEmpnDTO,
     * or with status {@code 400 (Bad Request)} if the dEmpnDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the dEmpnDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/d-empns/{id}")
    public ResponseEntity<DEmpnDTO> updateDEmpn(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody DEmpnDTO dEmpnDTO
    ) throws URISyntaxException {
        log.debug("REST request to update DEmpn : {}, {}", id, dEmpnDTO);
        if (dEmpnDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, dEmpnDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!dEmpnRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        DEmpnDTO result = dEmpnService.save(dEmpnDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, dEmpnDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /d-empns/:id} : Partial updates given fields of an existing dEmpn, field will ignore if it is null
     *
     * @param id the id of the dEmpnDTO to save.
     * @param dEmpnDTO the dEmpnDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated dEmpnDTO,
     * or with status {@code 400 (Bad Request)} if the dEmpnDTO is not valid,
     * or with status {@code 404 (Not Found)} if the dEmpnDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the dEmpnDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/d-empns/{id}", consumes = "application/merge-patch+json")
    public ResponseEntity<DEmpnDTO> partialUpdateDEmpn(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody DEmpnDTO dEmpnDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update DEmpn partially : {}, {}", id, dEmpnDTO);
        if (dEmpnDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, dEmpnDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!dEmpnRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<DEmpnDTO> result = dEmpnService.partialUpdate(dEmpnDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, dEmpnDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /d-empns} : get all the dEmpns.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of dEmpns in body.
     */
    @GetMapping("/d-empns")
    public ResponseEntity<List<DEmpnDTO>> getAllDEmpns(DEmpnCriteria criteria, Pageable pageable) {
        log.debug("REST request to get DEmpns by criteria: {}", criteria);
        Page<DEmpnDTO> page = dEmpnQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /d-empns/count} : count all the dEmpns.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/d-empns/count")
    public ResponseEntity<Long> countDEmpns(DEmpnCriteria criteria) {
        log.debug("REST request to count DEmpns by criteria: {}", criteria);
        return ResponseEntity.ok().body(dEmpnQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /d-empns/:id} : get the "id" dEmpn.
     *
     * @param id the id of the dEmpnDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the dEmpnDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/d-empns/{id}")
    public ResponseEntity<DEmpnDTO> getDEmpn(@PathVariable Long id) {
        log.debug("REST request to get DEmpn : {}", id);
        Optional<DEmpnDTO> dEmpnDTO = dEmpnService.findOne(id);
        return ResponseUtil.wrapOrNotFound(dEmpnDTO);
    }

    /**
     * {@code DELETE  /d-empns/:id} : delete the "id" dEmpn.
     *
     * @param id the id of the dEmpnDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/d-empns/{id}")
    public ResponseEntity<Void> deleteDEmpn(@PathVariable Long id) {
        log.debug("REST request to delete DEmpn : {}", id);
        dEmpnService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }

    /**
     * {@code SEARCH  /_search/d-empns?query=:query} : search for the dEmpn corresponding
     * to the query.
     *
     * @param query the query of the dEmpn search.
     * @param pageable the pagination information.
     * @return the result of the search.
     */
    @GetMapping("/_search/d-empns")
    public ResponseEntity<List<DEmpnDTO>> searchDEmpns(@RequestParam String query, Pageable pageable) {
        log.debug("REST request to search for a page of DEmpns for query {}", query);
        Page<DEmpnDTO> page = dEmpnService.search(query, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }
}
