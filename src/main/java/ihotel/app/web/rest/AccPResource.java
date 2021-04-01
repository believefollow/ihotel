package ihotel.app.web.rest;

import static org.elasticsearch.index.query.QueryBuilders.*;

import ihotel.app.repository.AccPRepository;
import ihotel.app.service.AccPQueryService;
import ihotel.app.service.AccPService;
import ihotel.app.service.criteria.AccPCriteria;
import ihotel.app.service.dto.AccPDTO;
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
 * REST controller for managing {@link ihotel.app.domain.AccP}.
 */
@RestController
@RequestMapping("/api")
public class AccPResource {

    private final Logger log = LoggerFactory.getLogger(AccPResource.class);

    private static final String ENTITY_NAME = "accP";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final AccPService accPService;

    private final AccPRepository accPRepository;

    private final AccPQueryService accPQueryService;

    public AccPResource(AccPService accPService, AccPRepository accPRepository, AccPQueryService accPQueryService) {
        this.accPService = accPService;
        this.accPRepository = accPRepository;
        this.accPQueryService = accPQueryService;
    }

    /**
     * {@code POST  /acc-ps} : Create a new accP.
     *
     * @param accPDTO the accPDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new accPDTO, or with status {@code 400 (Bad Request)} if the accP has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/acc-ps")
    public ResponseEntity<AccPDTO> createAccP(@Valid @RequestBody AccPDTO accPDTO) throws URISyntaxException {
        log.debug("REST request to save AccP : {}", accPDTO);
        if (accPDTO.getId() != null) {
            throw new BadRequestAlertException("A new accP cannot already have an ID", ENTITY_NAME, "idexists");
        }
        AccPDTO result = accPService.save(accPDTO);
        return ResponseEntity
            .created(new URI("/api/acc-ps/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /acc-ps/:id} : Updates an existing accP.
     *
     * @param id the id of the accPDTO to save.
     * @param accPDTO the accPDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated accPDTO,
     * or with status {@code 400 (Bad Request)} if the accPDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the accPDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/acc-ps/{id}")
    public ResponseEntity<AccPDTO> updateAccP(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody AccPDTO accPDTO
    ) throws URISyntaxException {
        log.debug("REST request to update AccP : {}, {}", id, accPDTO);
        if (accPDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, accPDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!accPRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        AccPDTO result = accPService.save(accPDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, accPDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /acc-ps/:id} : Partial updates given fields of an existing accP, field will ignore if it is null
     *
     * @param id the id of the accPDTO to save.
     * @param accPDTO the accPDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated accPDTO,
     * or with status {@code 400 (Bad Request)} if the accPDTO is not valid,
     * or with status {@code 404 (Not Found)} if the accPDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the accPDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/acc-ps/{id}", consumes = "application/merge-patch+json")
    public ResponseEntity<AccPDTO> partialUpdateAccP(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody AccPDTO accPDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update AccP partially : {}, {}", id, accPDTO);
        if (accPDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, accPDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!accPRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<AccPDTO> result = accPService.partialUpdate(accPDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, accPDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /acc-ps} : get all the accPS.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of accPS in body.
     */
    @GetMapping("/acc-ps")
    public ResponseEntity<List<AccPDTO>> getAllAccPS(AccPCriteria criteria, Pageable pageable) {
        log.debug("REST request to get AccPS by criteria: {}", criteria);
        Page<AccPDTO> page = accPQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /acc-ps/count} : count all the accPS.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/acc-ps/count")
    public ResponseEntity<Long> countAccPS(AccPCriteria criteria) {
        log.debug("REST request to count AccPS by criteria: {}", criteria);
        return ResponseEntity.ok().body(accPQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /acc-ps/:id} : get the "id" accP.
     *
     * @param id the id of the accPDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the accPDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/acc-ps/{id}")
    public ResponseEntity<AccPDTO> getAccP(@PathVariable Long id) {
        log.debug("REST request to get AccP : {}", id);
        Optional<AccPDTO> accPDTO = accPService.findOne(id);
        return ResponseUtil.wrapOrNotFound(accPDTO);
    }

    /**
     * {@code DELETE  /acc-ps/:id} : delete the "id" accP.
     *
     * @param id the id of the accPDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/acc-ps/{id}")
    public ResponseEntity<Void> deleteAccP(@PathVariable Long id) {
        log.debug("REST request to delete AccP : {}", id);
        accPService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }

    /**
     * {@code SEARCH  /_search/acc-ps?query=:query} : search for the accP corresponding
     * to the query.
     *
     * @param query the query of the accP search.
     * @param pageable the pagination information.
     * @return the result of the search.
     */
    @GetMapping("/_search/acc-ps")
    public ResponseEntity<List<AccPDTO>> searchAccPS(@RequestParam String query, Pageable pageable) {
        log.debug("REST request to search for a page of AccPS for query {}", query);
        Page<AccPDTO> page = accPService.search(query, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }
}
