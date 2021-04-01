package ihotel.app.web.rest;

import static org.elasticsearch.index.query.QueryBuilders.*;

import ihotel.app.repository.CzlCzRepository;
import ihotel.app.service.CzlCzQueryService;
import ihotel.app.service.CzlCzService;
import ihotel.app.service.criteria.CzlCzCriteria;
import ihotel.app.service.dto.CzlCzDTO;
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
 * REST controller for managing {@link ihotel.app.domain.CzlCz}.
 */
@RestController
@RequestMapping("/api")
public class CzlCzResource {

    private final Logger log = LoggerFactory.getLogger(CzlCzResource.class);

    private static final String ENTITY_NAME = "czlCz";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final CzlCzService czlCzService;

    private final CzlCzRepository czlCzRepository;

    private final CzlCzQueryService czlCzQueryService;

    public CzlCzResource(CzlCzService czlCzService, CzlCzRepository czlCzRepository, CzlCzQueryService czlCzQueryService) {
        this.czlCzService = czlCzService;
        this.czlCzRepository = czlCzRepository;
        this.czlCzQueryService = czlCzQueryService;
    }

    /**
     * {@code POST  /czl-czs} : Create a new czlCz.
     *
     * @param czlCzDTO the czlCzDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new czlCzDTO, or with status {@code 400 (Bad Request)} if the czlCz has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/czl-czs")
    public ResponseEntity<CzlCzDTO> createCzlCz(@Valid @RequestBody CzlCzDTO czlCzDTO) throws URISyntaxException {
        log.debug("REST request to save CzlCz : {}", czlCzDTO);
        if (czlCzDTO.getId() != null) {
            throw new BadRequestAlertException("A new czlCz cannot already have an ID", ENTITY_NAME, "idexists");
        }
        CzlCzDTO result = czlCzService.save(czlCzDTO);
        return ResponseEntity
            .created(new URI("/api/czl-czs/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /czl-czs/:id} : Updates an existing czlCz.
     *
     * @param id the id of the czlCzDTO to save.
     * @param czlCzDTO the czlCzDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated czlCzDTO,
     * or with status {@code 400 (Bad Request)} if the czlCzDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the czlCzDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/czl-czs/{id}")
    public ResponseEntity<CzlCzDTO> updateCzlCz(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody CzlCzDTO czlCzDTO
    ) throws URISyntaxException {
        log.debug("REST request to update CzlCz : {}, {}", id, czlCzDTO);
        if (czlCzDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, czlCzDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!czlCzRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        CzlCzDTO result = czlCzService.save(czlCzDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, czlCzDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /czl-czs/:id} : Partial updates given fields of an existing czlCz, field will ignore if it is null
     *
     * @param id the id of the czlCzDTO to save.
     * @param czlCzDTO the czlCzDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated czlCzDTO,
     * or with status {@code 400 (Bad Request)} if the czlCzDTO is not valid,
     * or with status {@code 404 (Not Found)} if the czlCzDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the czlCzDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/czl-czs/{id}", consumes = "application/merge-patch+json")
    public ResponseEntity<CzlCzDTO> partialUpdateCzlCz(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody CzlCzDTO czlCzDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update CzlCz partially : {}, {}", id, czlCzDTO);
        if (czlCzDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, czlCzDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!czlCzRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<CzlCzDTO> result = czlCzService.partialUpdate(czlCzDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, czlCzDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /czl-czs} : get all the czlCzs.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of czlCzs in body.
     */
    @GetMapping("/czl-czs")
    public ResponseEntity<List<CzlCzDTO>> getAllCzlCzs(CzlCzCriteria criteria, Pageable pageable) {
        log.debug("REST request to get CzlCzs by criteria: {}", criteria);
        Page<CzlCzDTO> page = czlCzQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /czl-czs/count} : count all the czlCzs.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/czl-czs/count")
    public ResponseEntity<Long> countCzlCzs(CzlCzCriteria criteria) {
        log.debug("REST request to count CzlCzs by criteria: {}", criteria);
        return ResponseEntity.ok().body(czlCzQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /czl-czs/:id} : get the "id" czlCz.
     *
     * @param id the id of the czlCzDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the czlCzDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/czl-czs/{id}")
    public ResponseEntity<CzlCzDTO> getCzlCz(@PathVariable Long id) {
        log.debug("REST request to get CzlCz : {}", id);
        Optional<CzlCzDTO> czlCzDTO = czlCzService.findOne(id);
        return ResponseUtil.wrapOrNotFound(czlCzDTO);
    }

    /**
     * {@code DELETE  /czl-czs/:id} : delete the "id" czlCz.
     *
     * @param id the id of the czlCzDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/czl-czs/{id}")
    public ResponseEntity<Void> deleteCzlCz(@PathVariable Long id) {
        log.debug("REST request to delete CzlCz : {}", id);
        czlCzService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }

    /**
     * {@code SEARCH  /_search/czl-czs?query=:query} : search for the czlCz corresponding
     * to the query.
     *
     * @param query the query of the czlCz search.
     * @param pageable the pagination information.
     * @return the result of the search.
     */
    @GetMapping("/_search/czl-czs")
    public ResponseEntity<List<CzlCzDTO>> searchCzlCzs(@RequestParam String query, Pageable pageable) {
        log.debug("REST request to search for a page of CzlCzs for query {}", query);
        Page<CzlCzDTO> page = czlCzService.search(query, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }
}
