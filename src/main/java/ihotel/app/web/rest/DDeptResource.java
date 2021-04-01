package ihotel.app.web.rest;

import static org.elasticsearch.index.query.QueryBuilders.*;

import ihotel.app.repository.DDeptRepository;
import ihotel.app.service.DDeptQueryService;
import ihotel.app.service.DDeptService;
import ihotel.app.service.criteria.DDeptCriteria;
import ihotel.app.service.dto.DDeptDTO;
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
 * REST controller for managing {@link ihotel.app.domain.DDept}.
 */
@RestController
@RequestMapping("/api")
public class DDeptResource {

    private final Logger log = LoggerFactory.getLogger(DDeptResource.class);

    private static final String ENTITY_NAME = "dDept";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final DDeptService dDeptService;

    private final DDeptRepository dDeptRepository;

    private final DDeptQueryService dDeptQueryService;

    public DDeptResource(DDeptService dDeptService, DDeptRepository dDeptRepository, DDeptQueryService dDeptQueryService) {
        this.dDeptService = dDeptService;
        this.dDeptRepository = dDeptRepository;
        this.dDeptQueryService = dDeptQueryService;
    }

    /**
     * {@code POST  /d-depts} : Create a new dDept.
     *
     * @param dDeptDTO the dDeptDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new dDeptDTO, or with status {@code 400 (Bad Request)} if the dDept has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/d-depts")
    public ResponseEntity<DDeptDTO> createDDept(@Valid @RequestBody DDeptDTO dDeptDTO) throws URISyntaxException {
        log.debug("REST request to save DDept : {}", dDeptDTO);
        if (dDeptDTO.getId() != null) {
            throw new BadRequestAlertException("A new dDept cannot already have an ID", ENTITY_NAME, "idexists");
        }
        DDeptDTO result = dDeptService.save(dDeptDTO);
        return ResponseEntity
            .created(new URI("/api/d-depts/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /d-depts/:id} : Updates an existing dDept.
     *
     * @param id the id of the dDeptDTO to save.
     * @param dDeptDTO the dDeptDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated dDeptDTO,
     * or with status {@code 400 (Bad Request)} if the dDeptDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the dDeptDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/d-depts/{id}")
    public ResponseEntity<DDeptDTO> updateDDept(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody DDeptDTO dDeptDTO
    ) throws URISyntaxException {
        log.debug("REST request to update DDept : {}, {}", id, dDeptDTO);
        if (dDeptDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, dDeptDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!dDeptRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        DDeptDTO result = dDeptService.save(dDeptDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, dDeptDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /d-depts/:id} : Partial updates given fields of an existing dDept, field will ignore if it is null
     *
     * @param id the id of the dDeptDTO to save.
     * @param dDeptDTO the dDeptDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated dDeptDTO,
     * or with status {@code 400 (Bad Request)} if the dDeptDTO is not valid,
     * or with status {@code 404 (Not Found)} if the dDeptDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the dDeptDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/d-depts/{id}", consumes = "application/merge-patch+json")
    public ResponseEntity<DDeptDTO> partialUpdateDDept(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody DDeptDTO dDeptDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update DDept partially : {}, {}", id, dDeptDTO);
        if (dDeptDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, dDeptDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!dDeptRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<DDeptDTO> result = dDeptService.partialUpdate(dDeptDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, dDeptDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /d-depts} : get all the dDepts.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of dDepts in body.
     */
    @GetMapping("/d-depts")
    public ResponseEntity<List<DDeptDTO>> getAllDDepts(DDeptCriteria criteria, Pageable pageable) {
        log.debug("REST request to get DDepts by criteria: {}", criteria);
        Page<DDeptDTO> page = dDeptQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /d-depts/count} : count all the dDepts.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/d-depts/count")
    public ResponseEntity<Long> countDDepts(DDeptCriteria criteria) {
        log.debug("REST request to count DDepts by criteria: {}", criteria);
        return ResponseEntity.ok().body(dDeptQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /d-depts/:id} : get the "id" dDept.
     *
     * @param id the id of the dDeptDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the dDeptDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/d-depts/{id}")
    public ResponseEntity<DDeptDTO> getDDept(@PathVariable Long id) {
        log.debug("REST request to get DDept : {}", id);
        Optional<DDeptDTO> dDeptDTO = dDeptService.findOne(id);
        return ResponseUtil.wrapOrNotFound(dDeptDTO);
    }

    /**
     * {@code DELETE  /d-depts/:id} : delete the "id" dDept.
     *
     * @param id the id of the dDeptDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/d-depts/{id}")
    public ResponseEntity<Void> deleteDDept(@PathVariable Long id) {
        log.debug("REST request to delete DDept : {}", id);
        dDeptService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }

    /**
     * {@code SEARCH  /_search/d-depts?query=:query} : search for the dDept corresponding
     * to the query.
     *
     * @param query the query of the dDept search.
     * @param pageable the pagination information.
     * @return the result of the search.
     */
    @GetMapping("/_search/d-depts")
    public ResponseEntity<List<DDeptDTO>> searchDDepts(@RequestParam String query, Pageable pageable) {
        log.debug("REST request to search for a page of DDepts for query {}", query);
        Page<DDeptDTO> page = dDeptService.search(query, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }
}
