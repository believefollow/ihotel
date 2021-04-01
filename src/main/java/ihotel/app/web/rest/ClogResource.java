package ihotel.app.web.rest;

import static org.elasticsearch.index.query.QueryBuilders.*;

import ihotel.app.repository.ClogRepository;
import ihotel.app.service.ClogQueryService;
import ihotel.app.service.ClogService;
import ihotel.app.service.criteria.ClogCriteria;
import ihotel.app.service.dto.ClogDTO;
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
 * REST controller for managing {@link ihotel.app.domain.Clog}.
 */
@RestController
@RequestMapping("/api")
public class ClogResource {

    private final Logger log = LoggerFactory.getLogger(ClogResource.class);

    private static final String ENTITY_NAME = "clog";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ClogService clogService;

    private final ClogRepository clogRepository;

    private final ClogQueryService clogQueryService;

    public ClogResource(ClogService clogService, ClogRepository clogRepository, ClogQueryService clogQueryService) {
        this.clogService = clogService;
        this.clogRepository = clogRepository;
        this.clogQueryService = clogQueryService;
    }

    /**
     * {@code POST  /clogs} : Create a new clog.
     *
     * @param clogDTO the clogDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new clogDTO, or with status {@code 400 (Bad Request)} if the clog has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/clogs")
    public ResponseEntity<ClogDTO> createClog(@Valid @RequestBody ClogDTO clogDTO) throws URISyntaxException {
        log.debug("REST request to save Clog : {}", clogDTO);
        if (clogDTO.getId() != null) {
            throw new BadRequestAlertException("A new clog cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ClogDTO result = clogService.save(clogDTO);
        return ResponseEntity
            .created(new URI("/api/clogs/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /clogs/:id} : Updates an existing clog.
     *
     * @param id the id of the clogDTO to save.
     * @param clogDTO the clogDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated clogDTO,
     * or with status {@code 400 (Bad Request)} if the clogDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the clogDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/clogs/{id}")
    public ResponseEntity<ClogDTO> updateClog(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody ClogDTO clogDTO
    ) throws URISyntaxException {
        log.debug("REST request to update Clog : {}, {}", id, clogDTO);
        if (clogDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, clogDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!clogRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        ClogDTO result = clogService.save(clogDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, clogDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /clogs/:id} : Partial updates given fields of an existing clog, field will ignore if it is null
     *
     * @param id the id of the clogDTO to save.
     * @param clogDTO the clogDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated clogDTO,
     * or with status {@code 400 (Bad Request)} if the clogDTO is not valid,
     * or with status {@code 404 (Not Found)} if the clogDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the clogDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/clogs/{id}", consumes = "application/merge-patch+json")
    public ResponseEntity<ClogDTO> partialUpdateClog(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody ClogDTO clogDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update Clog partially : {}, {}", id, clogDTO);
        if (clogDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, clogDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!clogRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<ClogDTO> result = clogService.partialUpdate(clogDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, clogDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /clogs} : get all the clogs.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of clogs in body.
     */
    @GetMapping("/clogs")
    public ResponseEntity<List<ClogDTO>> getAllClogs(ClogCriteria criteria, Pageable pageable) {
        log.debug("REST request to get Clogs by criteria: {}", criteria);
        Page<ClogDTO> page = clogQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /clogs/count} : count all the clogs.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/clogs/count")
    public ResponseEntity<Long> countClogs(ClogCriteria criteria) {
        log.debug("REST request to count Clogs by criteria: {}", criteria);
        return ResponseEntity.ok().body(clogQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /clogs/:id} : get the "id" clog.
     *
     * @param id the id of the clogDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the clogDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/clogs/{id}")
    public ResponseEntity<ClogDTO> getClog(@PathVariable Long id) {
        log.debug("REST request to get Clog : {}", id);
        Optional<ClogDTO> clogDTO = clogService.findOne(id);
        return ResponseUtil.wrapOrNotFound(clogDTO);
    }

    /**
     * {@code DELETE  /clogs/:id} : delete the "id" clog.
     *
     * @param id the id of the clogDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/clogs/{id}")
    public ResponseEntity<Void> deleteClog(@PathVariable Long id) {
        log.debug("REST request to delete Clog : {}", id);
        clogService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }

    /**
     * {@code SEARCH  /_search/clogs?query=:query} : search for the clog corresponding
     * to the query.
     *
     * @param query the query of the clog search.
     * @param pageable the pagination information.
     * @return the result of the search.
     */
    @GetMapping("/_search/clogs")
    public ResponseEntity<List<ClogDTO>> searchClogs(@RequestParam String query, Pageable pageable) {
        log.debug("REST request to search for a page of Clogs for query {}", query);
        Page<ClogDTO> page = clogService.search(query, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }
}
