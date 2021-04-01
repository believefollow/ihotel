package ihotel.app.web.rest;

import static org.elasticsearch.index.query.QueryBuilders.*;

import ihotel.app.repository.CzBqzRepository;
import ihotel.app.service.CzBqzQueryService;
import ihotel.app.service.CzBqzService;
import ihotel.app.service.criteria.CzBqzCriteria;
import ihotel.app.service.dto.CzBqzDTO;
import ihotel.app.web.rest.errors.BadRequestAlertException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.StreamSupport;
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
 * REST controller for managing {@link ihotel.app.domain.CzBqz}.
 */
@RestController
@RequestMapping("/api")
public class CzBqzResource {

    private final Logger log = LoggerFactory.getLogger(CzBqzResource.class);

    private static final String ENTITY_NAME = "czBqz";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final CzBqzService czBqzService;

    private final CzBqzRepository czBqzRepository;

    private final CzBqzQueryService czBqzQueryService;

    public CzBqzResource(CzBqzService czBqzService, CzBqzRepository czBqzRepository, CzBqzQueryService czBqzQueryService) {
        this.czBqzService = czBqzService;
        this.czBqzRepository = czBqzRepository;
        this.czBqzQueryService = czBqzQueryService;
    }

    /**
     * {@code POST  /cz-bqzs} : Create a new czBqz.
     *
     * @param czBqzDTO the czBqzDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new czBqzDTO, or with status {@code 400 (Bad Request)} if the czBqz has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/cz-bqzs")
    public ResponseEntity<CzBqzDTO> createCzBqz(@RequestBody CzBqzDTO czBqzDTO) throws URISyntaxException {
        log.debug("REST request to save CzBqz : {}", czBqzDTO);
        if (czBqzDTO.getId() != null) {
            throw new BadRequestAlertException("A new czBqz cannot already have an ID", ENTITY_NAME, "idexists");
        }
        CzBqzDTO result = czBqzService.save(czBqzDTO);
        return ResponseEntity
            .created(new URI("/api/cz-bqzs/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /cz-bqzs/:id} : Updates an existing czBqz.
     *
     * @param id the id of the czBqzDTO to save.
     * @param czBqzDTO the czBqzDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated czBqzDTO,
     * or with status {@code 400 (Bad Request)} if the czBqzDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the czBqzDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/cz-bqzs/{id}")
    public ResponseEntity<CzBqzDTO> updateCzBqz(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody CzBqzDTO czBqzDTO
    ) throws URISyntaxException {
        log.debug("REST request to update CzBqz : {}, {}", id, czBqzDTO);
        if (czBqzDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, czBqzDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!czBqzRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        CzBqzDTO result = czBqzService.save(czBqzDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, czBqzDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /cz-bqzs/:id} : Partial updates given fields of an existing czBqz, field will ignore if it is null
     *
     * @param id the id of the czBqzDTO to save.
     * @param czBqzDTO the czBqzDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated czBqzDTO,
     * or with status {@code 400 (Bad Request)} if the czBqzDTO is not valid,
     * or with status {@code 404 (Not Found)} if the czBqzDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the czBqzDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/cz-bqzs/{id}", consumes = "application/merge-patch+json")
    public ResponseEntity<CzBqzDTO> partialUpdateCzBqz(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody CzBqzDTO czBqzDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update CzBqz partially : {}, {}", id, czBqzDTO);
        if (czBqzDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, czBqzDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!czBqzRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<CzBqzDTO> result = czBqzService.partialUpdate(czBqzDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, czBqzDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /cz-bqzs} : get all the czBqzs.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of czBqzs in body.
     */
    @GetMapping("/cz-bqzs")
    public ResponseEntity<List<CzBqzDTO>> getAllCzBqzs(CzBqzCriteria criteria, Pageable pageable) {
        log.debug("REST request to get CzBqzs by criteria: {}", criteria);
        Page<CzBqzDTO> page = czBqzQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /cz-bqzs/count} : count all the czBqzs.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/cz-bqzs/count")
    public ResponseEntity<Long> countCzBqzs(CzBqzCriteria criteria) {
        log.debug("REST request to count CzBqzs by criteria: {}", criteria);
        return ResponseEntity.ok().body(czBqzQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /cz-bqzs/:id} : get the "id" czBqz.
     *
     * @param id the id of the czBqzDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the czBqzDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/cz-bqzs/{id}")
    public ResponseEntity<CzBqzDTO> getCzBqz(@PathVariable Long id) {
        log.debug("REST request to get CzBqz : {}", id);
        Optional<CzBqzDTO> czBqzDTO = czBqzService.findOne(id);
        return ResponseUtil.wrapOrNotFound(czBqzDTO);
    }

    /**
     * {@code DELETE  /cz-bqzs/:id} : delete the "id" czBqz.
     *
     * @param id the id of the czBqzDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/cz-bqzs/{id}")
    public ResponseEntity<Void> deleteCzBqz(@PathVariable Long id) {
        log.debug("REST request to delete CzBqz : {}", id);
        czBqzService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }

    /**
     * {@code SEARCH  /_search/cz-bqzs?query=:query} : search for the czBqz corresponding
     * to the query.
     *
     * @param query the query of the czBqz search.
     * @param pageable the pagination information.
     * @return the result of the search.
     */
    @GetMapping("/_search/cz-bqzs")
    public ResponseEntity<List<CzBqzDTO>> searchCzBqzs(@RequestParam String query, Pageable pageable) {
        log.debug("REST request to search for a page of CzBqzs for query {}", query);
        Page<CzBqzDTO> page = czBqzService.search(query, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }
}
