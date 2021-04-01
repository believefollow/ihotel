package ihotel.app.web.rest;

import static org.elasticsearch.index.query.QueryBuilders.*;

import ihotel.app.repository.ErrlogRepository;
import ihotel.app.service.ErrlogQueryService;
import ihotel.app.service.ErrlogService;
import ihotel.app.service.criteria.ErrlogCriteria;
import ihotel.app.service.dto.ErrlogDTO;
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
 * REST controller for managing {@link ihotel.app.domain.Errlog}.
 */
@RestController
@RequestMapping("/api")
public class ErrlogResource {

    private final Logger log = LoggerFactory.getLogger(ErrlogResource.class);

    private static final String ENTITY_NAME = "errlog";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ErrlogService errlogService;

    private final ErrlogRepository errlogRepository;

    private final ErrlogQueryService errlogQueryService;

    public ErrlogResource(ErrlogService errlogService, ErrlogRepository errlogRepository, ErrlogQueryService errlogQueryService) {
        this.errlogService = errlogService;
        this.errlogRepository = errlogRepository;
        this.errlogQueryService = errlogQueryService;
    }

    /**
     * {@code POST  /errlogs} : Create a new errlog.
     *
     * @param errlogDTO the errlogDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new errlogDTO, or with status {@code 400 (Bad Request)} if the errlog has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/errlogs")
    public ResponseEntity<ErrlogDTO> createErrlog(@Valid @RequestBody ErrlogDTO errlogDTO) throws URISyntaxException {
        log.debug("REST request to save Errlog : {}", errlogDTO);
        if (errlogDTO.getId() != null) {
            throw new BadRequestAlertException("A new errlog cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ErrlogDTO result = errlogService.save(errlogDTO);
        return ResponseEntity
            .created(new URI("/api/errlogs/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /errlogs/:id} : Updates an existing errlog.
     *
     * @param id the id of the errlogDTO to save.
     * @param errlogDTO the errlogDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated errlogDTO,
     * or with status {@code 400 (Bad Request)} if the errlogDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the errlogDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/errlogs/{id}")
    public ResponseEntity<ErrlogDTO> updateErrlog(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody ErrlogDTO errlogDTO
    ) throws URISyntaxException {
        log.debug("REST request to update Errlog : {}, {}", id, errlogDTO);
        if (errlogDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, errlogDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!errlogRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        ErrlogDTO result = errlogService.save(errlogDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, errlogDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /errlogs/:id} : Partial updates given fields of an existing errlog, field will ignore if it is null
     *
     * @param id the id of the errlogDTO to save.
     * @param errlogDTO the errlogDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated errlogDTO,
     * or with status {@code 400 (Bad Request)} if the errlogDTO is not valid,
     * or with status {@code 404 (Not Found)} if the errlogDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the errlogDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/errlogs/{id}", consumes = "application/merge-patch+json")
    public ResponseEntity<ErrlogDTO> partialUpdateErrlog(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody ErrlogDTO errlogDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update Errlog partially : {}, {}", id, errlogDTO);
        if (errlogDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, errlogDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!errlogRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<ErrlogDTO> result = errlogService.partialUpdate(errlogDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, errlogDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /errlogs} : get all the errlogs.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of errlogs in body.
     */
    @GetMapping("/errlogs")
    public ResponseEntity<List<ErrlogDTO>> getAllErrlogs(ErrlogCriteria criteria, Pageable pageable) {
        log.debug("REST request to get Errlogs by criteria: {}", criteria);
        Page<ErrlogDTO> page = errlogQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /errlogs/count} : count all the errlogs.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/errlogs/count")
    public ResponseEntity<Long> countErrlogs(ErrlogCriteria criteria) {
        log.debug("REST request to count Errlogs by criteria: {}", criteria);
        return ResponseEntity.ok().body(errlogQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /errlogs/:id} : get the "id" errlog.
     *
     * @param id the id of the errlogDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the errlogDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/errlogs/{id}")
    public ResponseEntity<ErrlogDTO> getErrlog(@PathVariable Long id) {
        log.debug("REST request to get Errlog : {}", id);
        Optional<ErrlogDTO> errlogDTO = errlogService.findOne(id);
        return ResponseUtil.wrapOrNotFound(errlogDTO);
    }

    /**
     * {@code DELETE  /errlogs/:id} : delete the "id" errlog.
     *
     * @param id the id of the errlogDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/errlogs/{id}")
    public ResponseEntity<Void> deleteErrlog(@PathVariable Long id) {
        log.debug("REST request to delete Errlog : {}", id);
        errlogService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }

    /**
     * {@code SEARCH  /_search/errlogs?query=:query} : search for the errlog corresponding
     * to the query.
     *
     * @param query the query of the errlog search.
     * @param pageable the pagination information.
     * @return the result of the search.
     */
    @GetMapping("/_search/errlogs")
    public ResponseEntity<List<ErrlogDTO>> searchErrlogs(@RequestParam String query, Pageable pageable) {
        log.debug("REST request to search for a page of Errlogs for query {}", query);
        Page<ErrlogDTO> page = errlogService.search(query, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }
}
