package ihotel.app.web.rest;

import static org.elasticsearch.index.query.QueryBuilders.*;

import ihotel.app.repository.DSpczRepository;
import ihotel.app.service.DSpczQueryService;
import ihotel.app.service.DSpczService;
import ihotel.app.service.criteria.DSpczCriteria;
import ihotel.app.service.dto.DSpczDTO;
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
 * REST controller for managing {@link ihotel.app.domain.DSpcz}.
 */
@RestController
@RequestMapping("/api")
public class DSpczResource {

    private final Logger log = LoggerFactory.getLogger(DSpczResource.class);

    private static final String ENTITY_NAME = "dSpcz";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final DSpczService dSpczService;

    private final DSpczRepository dSpczRepository;

    private final DSpczQueryService dSpczQueryService;

    public DSpczResource(DSpczService dSpczService, DSpczRepository dSpczRepository, DSpczQueryService dSpczQueryService) {
        this.dSpczService = dSpczService;
        this.dSpczRepository = dSpczRepository;
        this.dSpczQueryService = dSpczQueryService;
    }

    /**
     * {@code POST  /d-spczs} : Create a new dSpcz.
     *
     * @param dSpczDTO the dSpczDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new dSpczDTO, or with status {@code 400 (Bad Request)} if the dSpcz has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/d-spczs")
    public ResponseEntity<DSpczDTO> createDSpcz(@RequestBody DSpczDTO dSpczDTO) throws URISyntaxException {
        log.debug("REST request to save DSpcz : {}", dSpczDTO);
        if (dSpczDTO.getId() != null) {
            throw new BadRequestAlertException("A new dSpcz cannot already have an ID", ENTITY_NAME, "idexists");
        }
        DSpczDTO result = dSpczService.save(dSpczDTO);
        return ResponseEntity
            .created(new URI("/api/d-spczs/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /d-spczs/:id} : Updates an existing dSpcz.
     *
     * @param id the id of the dSpczDTO to save.
     * @param dSpczDTO the dSpczDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated dSpczDTO,
     * or with status {@code 400 (Bad Request)} if the dSpczDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the dSpczDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/d-spczs/{id}")
    public ResponseEntity<DSpczDTO> updateDSpcz(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody DSpczDTO dSpczDTO
    ) throws URISyntaxException {
        log.debug("REST request to update DSpcz : {}, {}", id, dSpczDTO);
        if (dSpczDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, dSpczDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!dSpczRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        DSpczDTO result = dSpczService.save(dSpczDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, dSpczDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /d-spczs/:id} : Partial updates given fields of an existing dSpcz, field will ignore if it is null
     *
     * @param id the id of the dSpczDTO to save.
     * @param dSpczDTO the dSpczDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated dSpczDTO,
     * or with status {@code 400 (Bad Request)} if the dSpczDTO is not valid,
     * or with status {@code 404 (Not Found)} if the dSpczDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the dSpczDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/d-spczs/{id}", consumes = "application/merge-patch+json")
    public ResponseEntity<DSpczDTO> partialUpdateDSpcz(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody DSpczDTO dSpczDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update DSpcz partially : {}, {}", id, dSpczDTO);
        if (dSpczDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, dSpczDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!dSpczRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<DSpczDTO> result = dSpczService.partialUpdate(dSpczDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, dSpczDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /d-spczs} : get all the dSpczs.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of dSpczs in body.
     */
    @GetMapping("/d-spczs")
    public ResponseEntity<List<DSpczDTO>> getAllDSpczs(DSpczCriteria criteria, Pageable pageable) {
        log.debug("REST request to get DSpczs by criteria: {}", criteria);
        Page<DSpczDTO> page = dSpczQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /d-spczs/count} : count all the dSpczs.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/d-spczs/count")
    public ResponseEntity<Long> countDSpczs(DSpczCriteria criteria) {
        log.debug("REST request to count DSpczs by criteria: {}", criteria);
        return ResponseEntity.ok().body(dSpczQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /d-spczs/:id} : get the "id" dSpcz.
     *
     * @param id the id of the dSpczDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the dSpczDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/d-spczs/{id}")
    public ResponseEntity<DSpczDTO> getDSpcz(@PathVariable Long id) {
        log.debug("REST request to get DSpcz : {}", id);
        Optional<DSpczDTO> dSpczDTO = dSpczService.findOne(id);
        return ResponseUtil.wrapOrNotFound(dSpczDTO);
    }

    /**
     * {@code DELETE  /d-spczs/:id} : delete the "id" dSpcz.
     *
     * @param id the id of the dSpczDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/d-spczs/{id}")
    public ResponseEntity<Void> deleteDSpcz(@PathVariable Long id) {
        log.debug("REST request to delete DSpcz : {}", id);
        dSpczService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }

    /**
     * {@code SEARCH  /_search/d-spczs?query=:query} : search for the dSpcz corresponding
     * to the query.
     *
     * @param query the query of the dSpcz search.
     * @param pageable the pagination information.
     * @return the result of the search.
     */
    @GetMapping("/_search/d-spczs")
    public ResponseEntity<List<DSpczDTO>> searchDSpczs(@RequestParam String query, Pageable pageable) {
        log.debug("REST request to search for a page of DSpczs for query {}", query);
        Page<DSpczDTO> page = dSpczService.search(query, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }
}
