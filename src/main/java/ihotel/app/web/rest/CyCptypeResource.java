package ihotel.app.web.rest;

import static org.elasticsearch.index.query.QueryBuilders.*;

import ihotel.app.repository.CyCptypeRepository;
import ihotel.app.service.CyCptypeQueryService;
import ihotel.app.service.CyCptypeService;
import ihotel.app.service.criteria.CyCptypeCriteria;
import ihotel.app.service.dto.CyCptypeDTO;
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
 * REST controller for managing {@link ihotel.app.domain.CyCptype}.
 */
@RestController
@RequestMapping("/api")
public class CyCptypeResource {

    private final Logger log = LoggerFactory.getLogger(CyCptypeResource.class);

    private static final String ENTITY_NAME = "cyCptype";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final CyCptypeService cyCptypeService;

    private final CyCptypeRepository cyCptypeRepository;

    private final CyCptypeQueryService cyCptypeQueryService;

    public CyCptypeResource(
        CyCptypeService cyCptypeService,
        CyCptypeRepository cyCptypeRepository,
        CyCptypeQueryService cyCptypeQueryService
    ) {
        this.cyCptypeService = cyCptypeService;
        this.cyCptypeRepository = cyCptypeRepository;
        this.cyCptypeQueryService = cyCptypeQueryService;
    }

    /**
     * {@code POST  /cy-cptypes} : Create a new cyCptype.
     *
     * @param cyCptypeDTO the cyCptypeDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new cyCptypeDTO, or with status {@code 400 (Bad Request)} if the cyCptype has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/cy-cptypes")
    public ResponseEntity<CyCptypeDTO> createCyCptype(@Valid @RequestBody CyCptypeDTO cyCptypeDTO) throws URISyntaxException {
        log.debug("REST request to save CyCptype : {}", cyCptypeDTO);
        if (cyCptypeDTO.getId() != null) {
            throw new BadRequestAlertException("A new cyCptype cannot already have an ID", ENTITY_NAME, "idexists");
        }
        CyCptypeDTO result = cyCptypeService.save(cyCptypeDTO);
        return ResponseEntity
            .created(new URI("/api/cy-cptypes/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /cy-cptypes/:id} : Updates an existing cyCptype.
     *
     * @param id the id of the cyCptypeDTO to save.
     * @param cyCptypeDTO the cyCptypeDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated cyCptypeDTO,
     * or with status {@code 400 (Bad Request)} if the cyCptypeDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the cyCptypeDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/cy-cptypes/{id}")
    public ResponseEntity<CyCptypeDTO> updateCyCptype(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody CyCptypeDTO cyCptypeDTO
    ) throws URISyntaxException {
        log.debug("REST request to update CyCptype : {}, {}", id, cyCptypeDTO);
        if (cyCptypeDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, cyCptypeDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!cyCptypeRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        CyCptypeDTO result = cyCptypeService.save(cyCptypeDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, cyCptypeDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /cy-cptypes/:id} : Partial updates given fields of an existing cyCptype, field will ignore if it is null
     *
     * @param id the id of the cyCptypeDTO to save.
     * @param cyCptypeDTO the cyCptypeDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated cyCptypeDTO,
     * or with status {@code 400 (Bad Request)} if the cyCptypeDTO is not valid,
     * or with status {@code 404 (Not Found)} if the cyCptypeDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the cyCptypeDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/cy-cptypes/{id}", consumes = "application/merge-patch+json")
    public ResponseEntity<CyCptypeDTO> partialUpdateCyCptype(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody CyCptypeDTO cyCptypeDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update CyCptype partially : {}, {}", id, cyCptypeDTO);
        if (cyCptypeDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, cyCptypeDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!cyCptypeRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<CyCptypeDTO> result = cyCptypeService.partialUpdate(cyCptypeDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, cyCptypeDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /cy-cptypes} : get all the cyCptypes.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of cyCptypes in body.
     */
    @GetMapping("/cy-cptypes")
    public ResponseEntity<List<CyCptypeDTO>> getAllCyCptypes(CyCptypeCriteria criteria, Pageable pageable) {
        log.debug("REST request to get CyCptypes by criteria: {}", criteria);
        Page<CyCptypeDTO> page = cyCptypeQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /cy-cptypes/count} : count all the cyCptypes.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/cy-cptypes/count")
    public ResponseEntity<Long> countCyCptypes(CyCptypeCriteria criteria) {
        log.debug("REST request to count CyCptypes by criteria: {}", criteria);
        return ResponseEntity.ok().body(cyCptypeQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /cy-cptypes/:id} : get the "id" cyCptype.
     *
     * @param id the id of the cyCptypeDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the cyCptypeDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/cy-cptypes/{id}")
    public ResponseEntity<CyCptypeDTO> getCyCptype(@PathVariable Long id) {
        log.debug("REST request to get CyCptype : {}", id);
        Optional<CyCptypeDTO> cyCptypeDTO = cyCptypeService.findOne(id);
        return ResponseUtil.wrapOrNotFound(cyCptypeDTO);
    }

    /**
     * {@code DELETE  /cy-cptypes/:id} : delete the "id" cyCptype.
     *
     * @param id the id of the cyCptypeDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/cy-cptypes/{id}")
    public ResponseEntity<Void> deleteCyCptype(@PathVariable Long id) {
        log.debug("REST request to delete CyCptype : {}", id);
        cyCptypeService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }

    /**
     * {@code SEARCH  /_search/cy-cptypes?query=:query} : search for the cyCptype corresponding
     * to the query.
     *
     * @param query the query of the cyCptype search.
     * @param pageable the pagination information.
     * @return the result of the search.
     */
    @GetMapping("/_search/cy-cptypes")
    public ResponseEntity<List<CyCptypeDTO>> searchCyCptypes(@RequestParam String query, Pageable pageable) {
        log.debug("REST request to search for a page of CyCptypes for query {}", query);
        Page<CyCptypeDTO> page = cyCptypeService.search(query, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }
}
