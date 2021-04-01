package ihotel.app.web.rest;

import static org.elasticsearch.index.query.QueryBuilders.*;

import ihotel.app.repository.DXsRepository;
import ihotel.app.service.DXsQueryService;
import ihotel.app.service.DXsService;
import ihotel.app.service.criteria.DXsCriteria;
import ihotel.app.service.dto.DXsDTO;
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
 * REST controller for managing {@link ihotel.app.domain.DXs}.
 */
@RestController
@RequestMapping("/api")
public class DXsResource {

    private final Logger log = LoggerFactory.getLogger(DXsResource.class);

    private static final String ENTITY_NAME = "dXs";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final DXsService dXsService;

    private final DXsRepository dXsRepository;

    private final DXsQueryService dXsQueryService;

    public DXsResource(DXsService dXsService, DXsRepository dXsRepository, DXsQueryService dXsQueryService) {
        this.dXsService = dXsService;
        this.dXsRepository = dXsRepository;
        this.dXsQueryService = dXsQueryService;
    }

    /**
     * {@code POST  /d-xs} : Create a new dXs.
     *
     * @param dXsDTO the dXsDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new dXsDTO, or with status {@code 400 (Bad Request)} if the dXs has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/d-xs")
    public ResponseEntity<DXsDTO> createDXs(@Valid @RequestBody DXsDTO dXsDTO) throws URISyntaxException {
        log.debug("REST request to save DXs : {}", dXsDTO);
        if (dXsDTO.getId() != null) {
            throw new BadRequestAlertException("A new dXs cannot already have an ID", ENTITY_NAME, "idexists");
        }
        DXsDTO result = dXsService.save(dXsDTO);
        return ResponseEntity
            .created(new URI("/api/d-xs/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /d-xs/:id} : Updates an existing dXs.
     *
     * @param id the id of the dXsDTO to save.
     * @param dXsDTO the dXsDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated dXsDTO,
     * or with status {@code 400 (Bad Request)} if the dXsDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the dXsDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/d-xs/{id}")
    public ResponseEntity<DXsDTO> updateDXs(@PathVariable(value = "id", required = false) final Long id, @Valid @RequestBody DXsDTO dXsDTO)
        throws URISyntaxException {
        log.debug("REST request to update DXs : {}, {}", id, dXsDTO);
        if (dXsDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, dXsDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!dXsRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        DXsDTO result = dXsService.save(dXsDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, dXsDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /d-xs/:id} : Partial updates given fields of an existing dXs, field will ignore if it is null
     *
     * @param id the id of the dXsDTO to save.
     * @param dXsDTO the dXsDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated dXsDTO,
     * or with status {@code 400 (Bad Request)} if the dXsDTO is not valid,
     * or with status {@code 404 (Not Found)} if the dXsDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the dXsDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/d-xs/{id}", consumes = "application/merge-patch+json")
    public ResponseEntity<DXsDTO> partialUpdateDXs(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody DXsDTO dXsDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update DXs partially : {}, {}", id, dXsDTO);
        if (dXsDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, dXsDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!dXsRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<DXsDTO> result = dXsService.partialUpdate(dXsDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, dXsDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /d-xs} : get all the dXs.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of dXs in body.
     */
    @GetMapping("/d-xs")
    public ResponseEntity<List<DXsDTO>> getAllDXs(DXsCriteria criteria, Pageable pageable) {
        log.debug("REST request to get DXs by criteria: {}", criteria);
        Page<DXsDTO> page = dXsQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /d-xs/count} : count all the dXs.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/d-xs/count")
    public ResponseEntity<Long> countDXs(DXsCriteria criteria) {
        log.debug("REST request to count DXs by criteria: {}", criteria);
        return ResponseEntity.ok().body(dXsQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /d-xs/:id} : get the "id" dXs.
     *
     * @param id the id of the dXsDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the dXsDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/d-xs/{id}")
    public ResponseEntity<DXsDTO> getDXs(@PathVariable Long id) {
        log.debug("REST request to get DXs : {}", id);
        Optional<DXsDTO> dXsDTO = dXsService.findOne(id);
        return ResponseUtil.wrapOrNotFound(dXsDTO);
    }

    /**
     * {@code DELETE  /d-xs/:id} : delete the "id" dXs.
     *
     * @param id the id of the dXsDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/d-xs/{id}")
    public ResponseEntity<Void> deleteDXs(@PathVariable Long id) {
        log.debug("REST request to delete DXs : {}", id);
        dXsService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }

    /**
     * {@code SEARCH  /_search/d-xs?query=:query} : search for the dXs corresponding
     * to the query.
     *
     * @param query the query of the dXs search.
     * @param pageable the pagination information.
     * @return the result of the search.
     */
    @GetMapping("/_search/d-xs")
    public ResponseEntity<List<DXsDTO>> searchDXs(@RequestParam String query, Pageable pageable) {
        log.debug("REST request to search for a page of DXs for query {}", query);
        Page<DXsDTO> page = dXsService.search(query, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }
}
