package ihotel.app.web.rest;

import static org.elasticsearch.index.query.QueryBuilders.*;

import ihotel.app.repository.FtXzRepository;
import ihotel.app.service.FtXzQueryService;
import ihotel.app.service.FtXzService;
import ihotel.app.service.criteria.FtXzCriteria;
import ihotel.app.service.dto.FtXzDTO;
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
 * REST controller for managing {@link ihotel.app.domain.FtXz}.
 */
@RestController
@RequestMapping("/api")
public class FtXzResource {

    private final Logger log = LoggerFactory.getLogger(FtXzResource.class);

    private static final String ENTITY_NAME = "ftXz";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final FtXzService ftXzService;

    private final FtXzRepository ftXzRepository;

    private final FtXzQueryService ftXzQueryService;

    public FtXzResource(FtXzService ftXzService, FtXzRepository ftXzRepository, FtXzQueryService ftXzQueryService) {
        this.ftXzService = ftXzService;
        this.ftXzRepository = ftXzRepository;
        this.ftXzQueryService = ftXzQueryService;
    }

    /**
     * {@code POST  /ft-xzs} : Create a new ftXz.
     *
     * @param ftXzDTO the ftXzDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new ftXzDTO, or with status {@code 400 (Bad Request)} if the ftXz has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/ft-xzs")
    public ResponseEntity<FtXzDTO> createFtXz(@Valid @RequestBody FtXzDTO ftXzDTO) throws URISyntaxException {
        log.debug("REST request to save FtXz : {}", ftXzDTO);
        if (ftXzDTO.getId() != null) {
            throw new BadRequestAlertException("A new ftXz cannot already have an ID", ENTITY_NAME, "idexists");
        }
        FtXzDTO result = ftXzService.save(ftXzDTO);
        return ResponseEntity
            .created(new URI("/api/ft-xzs/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /ft-xzs/:id} : Updates an existing ftXz.
     *
     * @param id the id of the ftXzDTO to save.
     * @param ftXzDTO the ftXzDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated ftXzDTO,
     * or with status {@code 400 (Bad Request)} if the ftXzDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the ftXzDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/ft-xzs/{id}")
    public ResponseEntity<FtXzDTO> updateFtXz(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody FtXzDTO ftXzDTO
    ) throws URISyntaxException {
        log.debug("REST request to update FtXz : {}, {}", id, ftXzDTO);
        if (ftXzDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, ftXzDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!ftXzRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        FtXzDTO result = ftXzService.save(ftXzDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, ftXzDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /ft-xzs/:id} : Partial updates given fields of an existing ftXz, field will ignore if it is null
     *
     * @param id the id of the ftXzDTO to save.
     * @param ftXzDTO the ftXzDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated ftXzDTO,
     * or with status {@code 400 (Bad Request)} if the ftXzDTO is not valid,
     * or with status {@code 404 (Not Found)} if the ftXzDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the ftXzDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/ft-xzs/{id}", consumes = "application/merge-patch+json")
    public ResponseEntity<FtXzDTO> partialUpdateFtXz(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody FtXzDTO ftXzDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update FtXz partially : {}, {}", id, ftXzDTO);
        if (ftXzDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, ftXzDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!ftXzRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<FtXzDTO> result = ftXzService.partialUpdate(ftXzDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, ftXzDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /ft-xzs} : get all the ftXzs.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of ftXzs in body.
     */
    @GetMapping("/ft-xzs")
    public ResponseEntity<List<FtXzDTO>> getAllFtXzs(FtXzCriteria criteria, Pageable pageable) {
        log.debug("REST request to get FtXzs by criteria: {}", criteria);
        Page<FtXzDTO> page = ftXzQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /ft-xzs/count} : count all the ftXzs.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/ft-xzs/count")
    public ResponseEntity<Long> countFtXzs(FtXzCriteria criteria) {
        log.debug("REST request to count FtXzs by criteria: {}", criteria);
        return ResponseEntity.ok().body(ftXzQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /ft-xzs/:id} : get the "id" ftXz.
     *
     * @param id the id of the ftXzDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the ftXzDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/ft-xzs/{id}")
    public ResponseEntity<FtXzDTO> getFtXz(@PathVariable Long id) {
        log.debug("REST request to get FtXz : {}", id);
        Optional<FtXzDTO> ftXzDTO = ftXzService.findOne(id);
        return ResponseUtil.wrapOrNotFound(ftXzDTO);
    }

    /**
     * {@code DELETE  /ft-xzs/:id} : delete the "id" ftXz.
     *
     * @param id the id of the ftXzDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/ft-xzs/{id}")
    public ResponseEntity<Void> deleteFtXz(@PathVariable Long id) {
        log.debug("REST request to delete FtXz : {}", id);
        ftXzService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }

    /**
     * {@code SEARCH  /_search/ft-xzs?query=:query} : search for the ftXz corresponding
     * to the query.
     *
     * @param query the query of the ftXz search.
     * @param pageable the pagination information.
     * @return the result of the search.
     */
    @GetMapping("/_search/ft-xzs")
    public ResponseEntity<List<FtXzDTO>> searchFtXzs(@RequestParam String query, Pageable pageable) {
        log.debug("REST request to search for a page of FtXzs for query {}", query);
        Page<FtXzDTO> page = ftXzService.search(query, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }
}
