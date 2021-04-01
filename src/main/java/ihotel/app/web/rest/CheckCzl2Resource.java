package ihotel.app.web.rest;

import static org.elasticsearch.index.query.QueryBuilders.*;

import ihotel.app.repository.CheckCzl2Repository;
import ihotel.app.service.CheckCzl2QueryService;
import ihotel.app.service.CheckCzl2Service;
import ihotel.app.service.criteria.CheckCzl2Criteria;
import ihotel.app.service.dto.CheckCzl2DTO;
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
 * REST controller for managing {@link ihotel.app.domain.CheckCzl2}.
 */
@RestController
@RequestMapping("/api")
public class CheckCzl2Resource {

    private final Logger log = LoggerFactory.getLogger(CheckCzl2Resource.class);

    private static final String ENTITY_NAME = "checkCzl2";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final CheckCzl2Service checkCzl2Service;

    private final CheckCzl2Repository checkCzl2Repository;

    private final CheckCzl2QueryService checkCzl2QueryService;

    public CheckCzl2Resource(
        CheckCzl2Service checkCzl2Service,
        CheckCzl2Repository checkCzl2Repository,
        CheckCzl2QueryService checkCzl2QueryService
    ) {
        this.checkCzl2Service = checkCzl2Service;
        this.checkCzl2Repository = checkCzl2Repository;
        this.checkCzl2QueryService = checkCzl2QueryService;
    }

    /**
     * {@code POST  /check-czl-2-s} : Create a new checkCzl2.
     *
     * @param checkCzl2DTO the checkCzl2DTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new checkCzl2DTO, or with status {@code 400 (Bad Request)} if the checkCzl2 has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/check-czl-2-s")
    public ResponseEntity<CheckCzl2DTO> createCheckCzl2(@Valid @RequestBody CheckCzl2DTO checkCzl2DTO) throws URISyntaxException {
        log.debug("REST request to save CheckCzl2 : {}", checkCzl2DTO);
        if (checkCzl2DTO.getId() != null) {
            throw new BadRequestAlertException("A new checkCzl2 cannot already have an ID", ENTITY_NAME, "idexists");
        }
        CheckCzl2DTO result = checkCzl2Service.save(checkCzl2DTO);
        return ResponseEntity
            .created(new URI("/api/check-czl-2-s/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /check-czl-2-s/:id} : Updates an existing checkCzl2.
     *
     * @param id the id of the checkCzl2DTO to save.
     * @param checkCzl2DTO the checkCzl2DTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated checkCzl2DTO,
     * or with status {@code 400 (Bad Request)} if the checkCzl2DTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the checkCzl2DTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/check-czl-2-s/{id}")
    public ResponseEntity<CheckCzl2DTO> updateCheckCzl2(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody CheckCzl2DTO checkCzl2DTO
    ) throws URISyntaxException {
        log.debug("REST request to update CheckCzl2 : {}, {}", id, checkCzl2DTO);
        if (checkCzl2DTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, checkCzl2DTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!checkCzl2Repository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        CheckCzl2DTO result = checkCzl2Service.save(checkCzl2DTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, checkCzl2DTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /check-czl-2-s/:id} : Partial updates given fields of an existing checkCzl2, field will ignore if it is null
     *
     * @param id the id of the checkCzl2DTO to save.
     * @param checkCzl2DTO the checkCzl2DTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated checkCzl2DTO,
     * or with status {@code 400 (Bad Request)} if the checkCzl2DTO is not valid,
     * or with status {@code 404 (Not Found)} if the checkCzl2DTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the checkCzl2DTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/check-czl-2-s/{id}", consumes = "application/merge-patch+json")
    public ResponseEntity<CheckCzl2DTO> partialUpdateCheckCzl2(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody CheckCzl2DTO checkCzl2DTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update CheckCzl2 partially : {}, {}", id, checkCzl2DTO);
        if (checkCzl2DTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, checkCzl2DTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!checkCzl2Repository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<CheckCzl2DTO> result = checkCzl2Service.partialUpdate(checkCzl2DTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, checkCzl2DTO.getId().toString())
        );
    }

    /**
     * {@code GET  /check-czl-2-s} : get all the checkCzl2s.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of checkCzl2s in body.
     */
    @GetMapping("/check-czl-2-s")
    public ResponseEntity<List<CheckCzl2DTO>> getAllCheckCzl2s(CheckCzl2Criteria criteria, Pageable pageable) {
        log.debug("REST request to get CheckCzl2s by criteria: {}", criteria);
        Page<CheckCzl2DTO> page = checkCzl2QueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /check-czl-2-s/count} : count all the checkCzl2s.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/check-czl-2-s/count")
    public ResponseEntity<Long> countCheckCzl2s(CheckCzl2Criteria criteria) {
        log.debug("REST request to count CheckCzl2s by criteria: {}", criteria);
        return ResponseEntity.ok().body(checkCzl2QueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /check-czl-2-s/:id} : get the "id" checkCzl2.
     *
     * @param id the id of the checkCzl2DTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the checkCzl2DTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/check-czl-2-s/{id}")
    public ResponseEntity<CheckCzl2DTO> getCheckCzl2(@PathVariable Long id) {
        log.debug("REST request to get CheckCzl2 : {}", id);
        Optional<CheckCzl2DTO> checkCzl2DTO = checkCzl2Service.findOne(id);
        return ResponseUtil.wrapOrNotFound(checkCzl2DTO);
    }

    /**
     * {@code DELETE  /check-czl-2-s/:id} : delete the "id" checkCzl2.
     *
     * @param id the id of the checkCzl2DTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/check-czl-2-s/{id}")
    public ResponseEntity<Void> deleteCheckCzl2(@PathVariable Long id) {
        log.debug("REST request to delete CheckCzl2 : {}", id);
        checkCzl2Service.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }

    /**
     * {@code SEARCH  /_search/check-czl-2-s?query=:query} : search for the checkCzl2 corresponding
     * to the query.
     *
     * @param query the query of the checkCzl2 search.
     * @param pageable the pagination information.
     * @return the result of the search.
     */
    @GetMapping("/_search/check-czl-2-s")
    public ResponseEntity<List<CheckCzl2DTO>> searchCheckCzl2s(@RequestParam String query, Pageable pageable) {
        log.debug("REST request to search for a page of CheckCzl2s for query {}", query);
        Page<CheckCzl2DTO> page = checkCzl2Service.search(query, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }
}
