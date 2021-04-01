package ihotel.app.web.rest;

import static org.elasticsearch.index.query.QueryBuilders.*;

import ihotel.app.repository.AuditinfoRepository;
import ihotel.app.service.AuditinfoQueryService;
import ihotel.app.service.AuditinfoService;
import ihotel.app.service.criteria.AuditinfoCriteria;
import ihotel.app.service.dto.AuditinfoDTO;
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
 * REST controller for managing {@link ihotel.app.domain.Auditinfo}.
 */
@RestController
@RequestMapping("/api")
public class AuditinfoResource {

    private final Logger log = LoggerFactory.getLogger(AuditinfoResource.class);

    private static final String ENTITY_NAME = "auditinfo";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final AuditinfoService auditinfoService;

    private final AuditinfoRepository auditinfoRepository;

    private final AuditinfoQueryService auditinfoQueryService;

    public AuditinfoResource(
        AuditinfoService auditinfoService,
        AuditinfoRepository auditinfoRepository,
        AuditinfoQueryService auditinfoQueryService
    ) {
        this.auditinfoService = auditinfoService;
        this.auditinfoRepository = auditinfoRepository;
        this.auditinfoQueryService = auditinfoQueryService;
    }

    /**
     * {@code POST  /auditinfos} : Create a new auditinfo.
     *
     * @param auditinfoDTO the auditinfoDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new auditinfoDTO, or with status {@code 400 (Bad Request)} if the auditinfo has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/auditinfos")
    public ResponseEntity<AuditinfoDTO> createAuditinfo(@Valid @RequestBody AuditinfoDTO auditinfoDTO) throws URISyntaxException {
        log.debug("REST request to save Auditinfo : {}", auditinfoDTO);
        if (auditinfoDTO.getId() != null) {
            throw new BadRequestAlertException("A new auditinfo cannot already have an ID", ENTITY_NAME, "idexists");
        }
        AuditinfoDTO result = auditinfoService.save(auditinfoDTO);
        return ResponseEntity
            .created(new URI("/api/auditinfos/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /auditinfos/:id} : Updates an existing auditinfo.
     *
     * @param id the id of the auditinfoDTO to save.
     * @param auditinfoDTO the auditinfoDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated auditinfoDTO,
     * or with status {@code 400 (Bad Request)} if the auditinfoDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the auditinfoDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/auditinfos/{id}")
    public ResponseEntity<AuditinfoDTO> updateAuditinfo(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody AuditinfoDTO auditinfoDTO
    ) throws URISyntaxException {
        log.debug("REST request to update Auditinfo : {}, {}", id, auditinfoDTO);
        if (auditinfoDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, auditinfoDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!auditinfoRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        AuditinfoDTO result = auditinfoService.save(auditinfoDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, auditinfoDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /auditinfos/:id} : Partial updates given fields of an existing auditinfo, field will ignore if it is null
     *
     * @param id the id of the auditinfoDTO to save.
     * @param auditinfoDTO the auditinfoDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated auditinfoDTO,
     * or with status {@code 400 (Bad Request)} if the auditinfoDTO is not valid,
     * or with status {@code 404 (Not Found)} if the auditinfoDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the auditinfoDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/auditinfos/{id}", consumes = "application/merge-patch+json")
    public ResponseEntity<AuditinfoDTO> partialUpdateAuditinfo(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody AuditinfoDTO auditinfoDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update Auditinfo partially : {}, {}", id, auditinfoDTO);
        if (auditinfoDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, auditinfoDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!auditinfoRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<AuditinfoDTO> result = auditinfoService.partialUpdate(auditinfoDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, auditinfoDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /auditinfos} : get all the auditinfos.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of auditinfos in body.
     */
    @GetMapping("/auditinfos")
    public ResponseEntity<List<AuditinfoDTO>> getAllAuditinfos(AuditinfoCriteria criteria, Pageable pageable) {
        log.debug("REST request to get Auditinfos by criteria: {}", criteria);
        Page<AuditinfoDTO> page = auditinfoQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /auditinfos/count} : count all the auditinfos.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/auditinfos/count")
    public ResponseEntity<Long> countAuditinfos(AuditinfoCriteria criteria) {
        log.debug("REST request to count Auditinfos by criteria: {}", criteria);
        return ResponseEntity.ok().body(auditinfoQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /auditinfos/:id} : get the "id" auditinfo.
     *
     * @param id the id of the auditinfoDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the auditinfoDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/auditinfos/{id}")
    public ResponseEntity<AuditinfoDTO> getAuditinfo(@PathVariable Long id) {
        log.debug("REST request to get Auditinfo : {}", id);
        Optional<AuditinfoDTO> auditinfoDTO = auditinfoService.findOne(id);
        return ResponseUtil.wrapOrNotFound(auditinfoDTO);
    }

    /**
     * {@code DELETE  /auditinfos/:id} : delete the "id" auditinfo.
     *
     * @param id the id of the auditinfoDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/auditinfos/{id}")
    public ResponseEntity<Void> deleteAuditinfo(@PathVariable Long id) {
        log.debug("REST request to delete Auditinfo : {}", id);
        auditinfoService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }

    /**
     * {@code SEARCH  /_search/auditinfos?query=:query} : search for the auditinfo corresponding
     * to the query.
     *
     * @param query the query of the auditinfo search.
     * @param pageable the pagination information.
     * @return the result of the search.
     */
    @GetMapping("/_search/auditinfos")
    public ResponseEntity<List<AuditinfoDTO>> searchAuditinfos(@RequestParam String query, Pageable pageable) {
        log.debug("REST request to search for a page of Auditinfos for query {}", query);
        Page<AuditinfoDTO> page = auditinfoService.search(query, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }
}
