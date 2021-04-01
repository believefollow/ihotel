package ihotel.app.web.rest;

import static org.elasticsearch.index.query.QueryBuilders.*;

import ihotel.app.repository.AccPpRepository;
import ihotel.app.service.AccPpQueryService;
import ihotel.app.service.AccPpService;
import ihotel.app.service.criteria.AccPpCriteria;
import ihotel.app.service.dto.AccPpDTO;
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
 * REST controller for managing {@link ihotel.app.domain.AccPp}.
 */
@RestController
@RequestMapping("/api")
public class AccPpResource {

    private final Logger log = LoggerFactory.getLogger(AccPpResource.class);

    private static final String ENTITY_NAME = "accPp";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final AccPpService accPpService;

    private final AccPpRepository accPpRepository;

    private final AccPpQueryService accPpQueryService;

    public AccPpResource(AccPpService accPpService, AccPpRepository accPpRepository, AccPpQueryService accPpQueryService) {
        this.accPpService = accPpService;
        this.accPpRepository = accPpRepository;
        this.accPpQueryService = accPpQueryService;
    }

    /**
     * {@code POST  /acc-pps} : Create a new accPp.
     *
     * @param accPpDTO the accPpDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new accPpDTO, or with status {@code 400 (Bad Request)} if the accPp has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/acc-pps")
    public ResponseEntity<AccPpDTO> createAccPp(@Valid @RequestBody AccPpDTO accPpDTO) throws URISyntaxException {
        log.debug("REST request to save AccPp : {}", accPpDTO);
        if (accPpDTO.getId() != null) {
            throw new BadRequestAlertException("A new accPp cannot already have an ID", ENTITY_NAME, "idexists");
        }
        AccPpDTO result = accPpService.save(accPpDTO);
        return ResponseEntity
            .created(new URI("/api/acc-pps/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /acc-pps/:id} : Updates an existing accPp.
     *
     * @param id the id of the accPpDTO to save.
     * @param accPpDTO the accPpDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated accPpDTO,
     * or with status {@code 400 (Bad Request)} if the accPpDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the accPpDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/acc-pps/{id}")
    public ResponseEntity<AccPpDTO> updateAccPp(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody AccPpDTO accPpDTO
    ) throws URISyntaxException {
        log.debug("REST request to update AccPp : {}, {}", id, accPpDTO);
        if (accPpDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, accPpDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!accPpRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        AccPpDTO result = accPpService.save(accPpDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, accPpDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /acc-pps/:id} : Partial updates given fields of an existing accPp, field will ignore if it is null
     *
     * @param id the id of the accPpDTO to save.
     * @param accPpDTO the accPpDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated accPpDTO,
     * or with status {@code 400 (Bad Request)} if the accPpDTO is not valid,
     * or with status {@code 404 (Not Found)} if the accPpDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the accPpDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/acc-pps/{id}", consumes = "application/merge-patch+json")
    public ResponseEntity<AccPpDTO> partialUpdateAccPp(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody AccPpDTO accPpDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update AccPp partially : {}, {}", id, accPpDTO);
        if (accPpDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, accPpDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!accPpRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<AccPpDTO> result = accPpService.partialUpdate(accPpDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, accPpDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /acc-pps} : get all the accPps.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of accPps in body.
     */
    @GetMapping("/acc-pps")
    public ResponseEntity<List<AccPpDTO>> getAllAccPps(AccPpCriteria criteria, Pageable pageable) {
        log.debug("REST request to get AccPps by criteria: {}", criteria);
        Page<AccPpDTO> page = accPpQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /acc-pps/count} : count all the accPps.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/acc-pps/count")
    public ResponseEntity<Long> countAccPps(AccPpCriteria criteria) {
        log.debug("REST request to count AccPps by criteria: {}", criteria);
        return ResponseEntity.ok().body(accPpQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /acc-pps/:id} : get the "id" accPp.
     *
     * @param id the id of the accPpDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the accPpDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/acc-pps/{id}")
    public ResponseEntity<AccPpDTO> getAccPp(@PathVariable Long id) {
        log.debug("REST request to get AccPp : {}", id);
        Optional<AccPpDTO> accPpDTO = accPpService.findOne(id);
        return ResponseUtil.wrapOrNotFound(accPpDTO);
    }

    /**
     * {@code DELETE  /acc-pps/:id} : delete the "id" accPp.
     *
     * @param id the id of the accPpDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/acc-pps/{id}")
    public ResponseEntity<Void> deleteAccPp(@PathVariable Long id) {
        log.debug("REST request to delete AccPp : {}", id);
        accPpService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }

    /**
     * {@code SEARCH  /_search/acc-pps?query=:query} : search for the accPp corresponding
     * to the query.
     *
     * @param query the query of the accPp search.
     * @param pageable the pagination information.
     * @return the result of the search.
     */
    @GetMapping("/_search/acc-pps")
    public ResponseEntity<List<AccPpDTO>> searchAccPps(@RequestParam String query, Pageable pageable) {
        log.debug("REST request to search for a page of AccPps for query {}", query);
        Page<AccPpDTO> page = accPpService.search(query, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }
}
