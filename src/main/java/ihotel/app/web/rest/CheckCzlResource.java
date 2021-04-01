package ihotel.app.web.rest;

import static org.elasticsearch.index.query.QueryBuilders.*;

import ihotel.app.repository.CheckCzlRepository;
import ihotel.app.service.CheckCzlQueryService;
import ihotel.app.service.CheckCzlService;
import ihotel.app.service.criteria.CheckCzlCriteria;
import ihotel.app.service.dto.CheckCzlDTO;
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
 * REST controller for managing {@link ihotel.app.domain.CheckCzl}.
 */
@RestController
@RequestMapping("/api")
public class CheckCzlResource {

    private final Logger log = LoggerFactory.getLogger(CheckCzlResource.class);

    private static final String ENTITY_NAME = "checkCzl";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final CheckCzlService checkCzlService;

    private final CheckCzlRepository checkCzlRepository;

    private final CheckCzlQueryService checkCzlQueryService;

    public CheckCzlResource(
        CheckCzlService checkCzlService,
        CheckCzlRepository checkCzlRepository,
        CheckCzlQueryService checkCzlQueryService
    ) {
        this.checkCzlService = checkCzlService;
        this.checkCzlRepository = checkCzlRepository;
        this.checkCzlQueryService = checkCzlQueryService;
    }

    /**
     * {@code POST  /check-czls} : Create a new checkCzl.
     *
     * @param checkCzlDTO the checkCzlDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new checkCzlDTO, or with status {@code 400 (Bad Request)} if the checkCzl has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/check-czls")
    public ResponseEntity<CheckCzlDTO> createCheckCzl(@Valid @RequestBody CheckCzlDTO checkCzlDTO) throws URISyntaxException {
        log.debug("REST request to save CheckCzl : {}", checkCzlDTO);
        if (checkCzlDTO.getId() != null) {
            throw new BadRequestAlertException("A new checkCzl cannot already have an ID", ENTITY_NAME, "idexists");
        }
        CheckCzlDTO result = checkCzlService.save(checkCzlDTO);
        return ResponseEntity
            .created(new URI("/api/check-czls/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /check-czls/:id} : Updates an existing checkCzl.
     *
     * @param id the id of the checkCzlDTO to save.
     * @param checkCzlDTO the checkCzlDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated checkCzlDTO,
     * or with status {@code 400 (Bad Request)} if the checkCzlDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the checkCzlDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/check-czls/{id}")
    public ResponseEntity<CheckCzlDTO> updateCheckCzl(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody CheckCzlDTO checkCzlDTO
    ) throws URISyntaxException {
        log.debug("REST request to update CheckCzl : {}, {}", id, checkCzlDTO);
        if (checkCzlDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, checkCzlDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!checkCzlRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        CheckCzlDTO result = checkCzlService.save(checkCzlDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, checkCzlDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /check-czls/:id} : Partial updates given fields of an existing checkCzl, field will ignore if it is null
     *
     * @param id the id of the checkCzlDTO to save.
     * @param checkCzlDTO the checkCzlDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated checkCzlDTO,
     * or with status {@code 400 (Bad Request)} if the checkCzlDTO is not valid,
     * or with status {@code 404 (Not Found)} if the checkCzlDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the checkCzlDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/check-czls/{id}", consumes = "application/merge-patch+json")
    public ResponseEntity<CheckCzlDTO> partialUpdateCheckCzl(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody CheckCzlDTO checkCzlDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update CheckCzl partially : {}, {}", id, checkCzlDTO);
        if (checkCzlDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, checkCzlDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!checkCzlRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<CheckCzlDTO> result = checkCzlService.partialUpdate(checkCzlDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, checkCzlDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /check-czls} : get all the checkCzls.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of checkCzls in body.
     */
    @GetMapping("/check-czls")
    public ResponseEntity<List<CheckCzlDTO>> getAllCheckCzls(CheckCzlCriteria criteria, Pageable pageable) {
        log.debug("REST request to get CheckCzls by criteria: {}", criteria);
        Page<CheckCzlDTO> page = checkCzlQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /check-czls/count} : count all the checkCzls.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/check-czls/count")
    public ResponseEntity<Long> countCheckCzls(CheckCzlCriteria criteria) {
        log.debug("REST request to count CheckCzls by criteria: {}", criteria);
        return ResponseEntity.ok().body(checkCzlQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /check-czls/:id} : get the "id" checkCzl.
     *
     * @param id the id of the checkCzlDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the checkCzlDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/check-czls/{id}")
    public ResponseEntity<CheckCzlDTO> getCheckCzl(@PathVariable Long id) {
        log.debug("REST request to get CheckCzl : {}", id);
        Optional<CheckCzlDTO> checkCzlDTO = checkCzlService.findOne(id);
        return ResponseUtil.wrapOrNotFound(checkCzlDTO);
    }

    /**
     * {@code DELETE  /check-czls/:id} : delete the "id" checkCzl.
     *
     * @param id the id of the checkCzlDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/check-czls/{id}")
    public ResponseEntity<Void> deleteCheckCzl(@PathVariable Long id) {
        log.debug("REST request to delete CheckCzl : {}", id);
        checkCzlService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }

    /**
     * {@code SEARCH  /_search/check-czls?query=:query} : search for the checkCzl corresponding
     * to the query.
     *
     * @param query the query of the checkCzl search.
     * @param pageable the pagination information.
     * @return the result of the search.
     */
    @GetMapping("/_search/check-czls")
    public ResponseEntity<List<CheckCzlDTO>> searchCheckCzls(@RequestParam String query, Pageable pageable) {
        log.debug("REST request to search for a page of CheckCzls for query {}", query);
        Page<CheckCzlDTO> page = checkCzlService.search(query, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }
}
