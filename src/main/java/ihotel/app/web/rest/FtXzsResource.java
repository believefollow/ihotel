package ihotel.app.web.rest;

import static org.elasticsearch.index.query.QueryBuilders.*;

import ihotel.app.repository.FtXzsRepository;
import ihotel.app.service.FtXzsQueryService;
import ihotel.app.service.FtXzsService;
import ihotel.app.service.criteria.FtXzsCriteria;
import ihotel.app.service.dto.FtXzsDTO;
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
 * REST controller for managing {@link ihotel.app.domain.FtXzs}.
 */
@RestController
@RequestMapping("/api")
public class FtXzsResource {

    private final Logger log = LoggerFactory.getLogger(FtXzsResource.class);

    private static final String ENTITY_NAME = "ftXzs";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final FtXzsService ftXzsService;

    private final FtXzsRepository ftXzsRepository;

    private final FtXzsQueryService ftXzsQueryService;

    public FtXzsResource(FtXzsService ftXzsService, FtXzsRepository ftXzsRepository, FtXzsQueryService ftXzsQueryService) {
        this.ftXzsService = ftXzsService;
        this.ftXzsRepository = ftXzsRepository;
        this.ftXzsQueryService = ftXzsQueryService;
    }

    /**
     * {@code POST  /ft-xzs} : Create a new ftXzs.
     *
     * @param ftXzsDTO the ftXzsDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new ftXzsDTO, or with status {@code 400 (Bad Request)} if the ftXzs has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/ft-xzs")
    public ResponseEntity<FtXzsDTO> createFtXzs(@Valid @RequestBody FtXzsDTO ftXzsDTO) throws URISyntaxException {
        log.debug("REST request to save FtXzs : {}", ftXzsDTO);
        if (ftXzsDTO.getId() != null) {
            throw new BadRequestAlertException("A new ftXzs cannot already have an ID", ENTITY_NAME, "idexists");
        }
        FtXzsDTO result = ftXzsService.save(ftXzsDTO);
        return ResponseEntity
            .created(new URI("/api/ft-xzs/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /ft-xzs/:id} : Updates an existing ftXzs.
     *
     * @param id the id of the ftXzsDTO to save.
     * @param ftXzsDTO the ftXzsDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated ftXzsDTO,
     * or with status {@code 400 (Bad Request)} if the ftXzsDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the ftXzsDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/ft-xzs/{id}")
    public ResponseEntity<FtXzsDTO> updateFtXzs(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody FtXzsDTO ftXzsDTO
    ) throws URISyntaxException {
        log.debug("REST request to update FtXzs : {}, {}", id, ftXzsDTO);
        if (ftXzsDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, ftXzsDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!ftXzsRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        FtXzsDTO result = ftXzsService.save(ftXzsDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, ftXzsDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /ft-xzs/:id} : Partial updates given fields of an existing ftXzs, field will ignore if it is null
     *
     * @param id the id of the ftXzsDTO to save.
     * @param ftXzsDTO the ftXzsDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated ftXzsDTO,
     * or with status {@code 400 (Bad Request)} if the ftXzsDTO is not valid,
     * or with status {@code 404 (Not Found)} if the ftXzsDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the ftXzsDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/ft-xzs/{id}", consumes = "application/merge-patch+json")
    public ResponseEntity<FtXzsDTO> partialUpdateFtXzs(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody FtXzsDTO ftXzsDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update FtXzs partially : {}, {}", id, ftXzsDTO);
        if (ftXzsDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, ftXzsDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!ftXzsRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<FtXzsDTO> result = ftXzsService.partialUpdate(ftXzsDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, ftXzsDTO.getId().toString())
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
    public ResponseEntity<List<FtXzsDTO>> getAllFtXzs(FtXzsCriteria criteria, Pageable pageable) {
        log.debug("REST request to get FtXzs by criteria: {}", criteria);
        Page<FtXzsDTO> page = ftXzsQueryService.findByCriteria(criteria, pageable);
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
    public ResponseEntity<Long> countFtXzs(FtXzsCriteria criteria) {
        log.debug("REST request to count FtXzs by criteria: {}", criteria);
        return ResponseEntity.ok().body(ftXzsQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /ft-xzs/:id} : get the "id" ftXzs.
     *
     * @param id the id of the ftXzsDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the ftXzsDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/ft-xzs/{id}")
    public ResponseEntity<FtXzsDTO> getFtXzs(@PathVariable Long id) {
        log.debug("REST request to get FtXzs : {}", id);
        Optional<FtXzsDTO> ftXzsDTO = ftXzsService.findOne(id);
        return ResponseUtil.wrapOrNotFound(ftXzsDTO);
    }

    /**
     * {@code DELETE  /ft-xzs/:id} : delete the "id" ftXzs.
     *
     * @param id the id of the ftXzsDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/ft-xzs/{id}")
    public ResponseEntity<Void> deleteFtXzs(@PathVariable Long id) {
        log.debug("REST request to delete FtXzs : {}", id);
        ftXzsService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }

    /**
     * {@code SEARCH  /_search/ft-xzs?query=:query} : search for the ftXzs corresponding
     * to the query.
     *
     * @param query the query of the ftXzs search.
     * @param pageable the pagination information.
     * @return the result of the search.
     */
    @GetMapping("/_search/ft-xzs")
    public ResponseEntity<List<FtXzsDTO>> searchFtXzs(@RequestParam String query, Pageable pageable) {
        log.debug("REST request to search for a page of FtXzs for query {}", query);
        Page<FtXzsDTO> page = ftXzsService.search(query, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }
}
