package ihotel.app.web.rest;

import static org.elasticsearch.index.query.QueryBuilders.*;

import ihotel.app.repository.EeRepository;
import ihotel.app.service.EeQueryService;
import ihotel.app.service.EeService;
import ihotel.app.service.criteria.EeCriteria;
import ihotel.app.service.dto.EeDTO;
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
 * REST controller for managing {@link ihotel.app.domain.Ee}.
 */
@RestController
@RequestMapping("/api")
public class EeResource {

    private final Logger log = LoggerFactory.getLogger(EeResource.class);

    private static final String ENTITY_NAME = "ee";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final EeService eeService;

    private final EeRepository eeRepository;

    private final EeQueryService eeQueryService;

    public EeResource(EeService eeService, EeRepository eeRepository, EeQueryService eeQueryService) {
        this.eeService = eeService;
        this.eeRepository = eeRepository;
        this.eeQueryService = eeQueryService;
    }

    /**
     * {@code POST  /ees} : Create a new ee.
     *
     * @param eeDTO the eeDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new eeDTO, or with status {@code 400 (Bad Request)} if the ee has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/ees")
    public ResponseEntity<EeDTO> createEe(@Valid @RequestBody EeDTO eeDTO) throws URISyntaxException {
        log.debug("REST request to save Ee : {}", eeDTO);
        if (eeDTO.getId() != null) {
            throw new BadRequestAlertException("A new ee cannot already have an ID", ENTITY_NAME, "idexists");
        }
        EeDTO result = eeService.save(eeDTO);
        return ResponseEntity
            .created(new URI("/api/ees/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /ees/:id} : Updates an existing ee.
     *
     * @param id the id of the eeDTO to save.
     * @param eeDTO the eeDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated eeDTO,
     * or with status {@code 400 (Bad Request)} if the eeDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the eeDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/ees/{id}")
    public ResponseEntity<EeDTO> updateEe(@PathVariable(value = "id", required = false) final Long id, @Valid @RequestBody EeDTO eeDTO)
        throws URISyntaxException {
        log.debug("REST request to update Ee : {}, {}", id, eeDTO);
        if (eeDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, eeDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!eeRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        EeDTO result = eeService.save(eeDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, eeDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /ees/:id} : Partial updates given fields of an existing ee, field will ignore if it is null
     *
     * @param id the id of the eeDTO to save.
     * @param eeDTO the eeDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated eeDTO,
     * or with status {@code 400 (Bad Request)} if the eeDTO is not valid,
     * or with status {@code 404 (Not Found)} if the eeDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the eeDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/ees/{id}", consumes = "application/merge-patch+json")
    public ResponseEntity<EeDTO> partialUpdateEe(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody EeDTO eeDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update Ee partially : {}, {}", id, eeDTO);
        if (eeDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, eeDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!eeRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<EeDTO> result = eeService.partialUpdate(eeDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, eeDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /ees} : get all the ees.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of ees in body.
     */
    @GetMapping("/ees")
    public ResponseEntity<List<EeDTO>> getAllEes(EeCriteria criteria, Pageable pageable) {
        log.debug("REST request to get Ees by criteria: {}", criteria);
        Page<EeDTO> page = eeQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /ees/count} : count all the ees.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/ees/count")
    public ResponseEntity<Long> countEes(EeCriteria criteria) {
        log.debug("REST request to count Ees by criteria: {}", criteria);
        return ResponseEntity.ok().body(eeQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /ees/:id} : get the "id" ee.
     *
     * @param id the id of the eeDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the eeDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/ees/{id}")
    public ResponseEntity<EeDTO> getEe(@PathVariable Long id) {
        log.debug("REST request to get Ee : {}", id);
        Optional<EeDTO> eeDTO = eeService.findOne(id);
        return ResponseUtil.wrapOrNotFound(eeDTO);
    }

    /**
     * {@code DELETE  /ees/:id} : delete the "id" ee.
     *
     * @param id the id of the eeDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/ees/{id}")
    public ResponseEntity<Void> deleteEe(@PathVariable Long id) {
        log.debug("REST request to delete Ee : {}", id);
        eeService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }

    /**
     * {@code SEARCH  /_search/ees?query=:query} : search for the ee corresponding
     * to the query.
     *
     * @param query the query of the ee search.
     * @param pageable the pagination information.
     * @return the result of the search.
     */
    @GetMapping("/_search/ees")
    public ResponseEntity<List<EeDTO>> searchEes(@RequestParam String query, Pageable pageable) {
        log.debug("REST request to search for a page of Ees for query {}", query);
        Page<EeDTO> page = eeService.search(query, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }
}
