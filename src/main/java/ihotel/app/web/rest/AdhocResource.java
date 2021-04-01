package ihotel.app.web.rest;

import static org.elasticsearch.index.query.QueryBuilders.*;

import ihotel.app.repository.AdhocRepository;
import ihotel.app.service.AdhocQueryService;
import ihotel.app.service.AdhocService;
import ihotel.app.service.criteria.AdhocCriteria;
import ihotel.app.service.dto.AdhocDTO;
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
 * REST controller for managing {@link ihotel.app.domain.Adhoc}.
 */
@RestController
@RequestMapping("/api")
public class AdhocResource {

    private final Logger log = LoggerFactory.getLogger(AdhocResource.class);

    private static final String ENTITY_NAME = "adhoc";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final AdhocService adhocService;

    private final AdhocRepository adhocRepository;

    private final AdhocQueryService adhocQueryService;

    public AdhocResource(AdhocService adhocService, AdhocRepository adhocRepository, AdhocQueryService adhocQueryService) {
        this.adhocService = adhocService;
        this.adhocRepository = adhocRepository;
        this.adhocQueryService = adhocQueryService;
    }

    /**
     * {@code POST  /adhocs} : Create a new adhoc.
     *
     * @param adhocDTO the adhocDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new adhocDTO, or with status {@code 400 (Bad Request)} if the adhoc has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/adhocs")
    public ResponseEntity<AdhocDTO> createAdhoc(@Valid @RequestBody AdhocDTO adhocDTO) throws URISyntaxException {
        log.debug("REST request to save Adhoc : {}", adhocDTO);
        if (adhocDTO.getId() != null) {
            throw new BadRequestAlertException("A new adhoc cannot already have an ID", ENTITY_NAME, "idexists");
        }
        AdhocDTO result = adhocService.save(adhocDTO);
        return ResponseEntity
            .created(new URI("/api/adhocs/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId()))
            .body(result);
    }

    /**
     * {@code PUT  /adhocs/:id} : Updates an existing adhoc.
     *
     * @param id the id of the adhocDTO to save.
     * @param adhocDTO the adhocDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated adhocDTO,
     * or with status {@code 400 (Bad Request)} if the adhocDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the adhocDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/adhocs/{id}")
    public ResponseEntity<AdhocDTO> updateAdhoc(
        @PathVariable(value = "id", required = false) final String id,
        @Valid @RequestBody AdhocDTO adhocDTO
    ) throws URISyntaxException {
        log.debug("REST request to update Adhoc : {}, {}", id, adhocDTO);
        if (adhocDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, adhocDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!adhocRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        AdhocDTO result = adhocService.save(adhocDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, adhocDTO.getId()))
            .body(result);
    }

    /**
     * {@code PATCH  /adhocs/:id} : Partial updates given fields of an existing adhoc, field will ignore if it is null
     *
     * @param id the id of the adhocDTO to save.
     * @param adhocDTO the adhocDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated adhocDTO,
     * or with status {@code 400 (Bad Request)} if the adhocDTO is not valid,
     * or with status {@code 404 (Not Found)} if the adhocDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the adhocDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/adhocs/{id}", consumes = "application/merge-patch+json")
    public ResponseEntity<AdhocDTO> partialUpdateAdhoc(
        @PathVariable(value = "id", required = false) final String id,
        @NotNull @RequestBody AdhocDTO adhocDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update Adhoc partially : {}, {}", id, adhocDTO);
        if (adhocDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, adhocDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!adhocRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<AdhocDTO> result = adhocService.partialUpdate(adhocDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, adhocDTO.getId())
        );
    }

    /**
     * {@code GET  /adhocs} : get all the adhocs.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of adhocs in body.
     */
    @GetMapping("/adhocs")
    public ResponseEntity<List<AdhocDTO>> getAllAdhocs(AdhocCriteria criteria, Pageable pageable) {
        log.debug("REST request to get Adhocs by criteria: {}", criteria);
        Page<AdhocDTO> page = adhocQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /adhocs/count} : count all the adhocs.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/adhocs/count")
    public ResponseEntity<Long> countAdhocs(AdhocCriteria criteria) {
        log.debug("REST request to count Adhocs by criteria: {}", criteria);
        return ResponseEntity.ok().body(adhocQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /adhocs/:id} : get the "id" adhoc.
     *
     * @param id the id of the adhocDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the adhocDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/adhocs/{id}")
    public ResponseEntity<AdhocDTO> getAdhoc(@PathVariable String id) {
        log.debug("REST request to get Adhoc : {}", id);
        Optional<AdhocDTO> adhocDTO = adhocService.findOne(id);
        return ResponseUtil.wrapOrNotFound(adhocDTO);
    }

    /**
     * {@code DELETE  /adhocs/:id} : delete the "id" adhoc.
     *
     * @param id the id of the adhocDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/adhocs/{id}")
    public ResponseEntity<Void> deleteAdhoc(@PathVariable String id) {
        log.debug("REST request to delete Adhoc : {}", id);
        adhocService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id)).build();
    }

    /**
     * {@code SEARCH  /_search/adhocs?query=:query} : search for the adhoc corresponding
     * to the query.
     *
     * @param query the query of the adhoc search.
     * @param pageable the pagination information.
     * @return the result of the search.
     */
    @GetMapping("/_search/adhocs")
    public ResponseEntity<List<AdhocDTO>> searchAdhocs(@RequestParam String query, Pageable pageable) {
        log.debug("REST request to search for a page of Adhocs for query {}", query);
        Page<AdhocDTO> page = adhocService.search(query, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }
}
