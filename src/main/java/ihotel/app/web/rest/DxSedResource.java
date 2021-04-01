package ihotel.app.web.rest;

import static org.elasticsearch.index.query.QueryBuilders.*;

import ihotel.app.repository.DxSedRepository;
import ihotel.app.service.DxSedQueryService;
import ihotel.app.service.DxSedService;
import ihotel.app.service.criteria.DxSedCriteria;
import ihotel.app.service.dto.DxSedDTO;
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
 * REST controller for managing {@link ihotel.app.domain.DxSed}.
 */
@RestController
@RequestMapping("/api")
public class DxSedResource {

    private final Logger log = LoggerFactory.getLogger(DxSedResource.class);

    private static final String ENTITY_NAME = "dxSed";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final DxSedService dxSedService;

    private final DxSedRepository dxSedRepository;

    private final DxSedQueryService dxSedQueryService;

    public DxSedResource(DxSedService dxSedService, DxSedRepository dxSedRepository, DxSedQueryService dxSedQueryService) {
        this.dxSedService = dxSedService;
        this.dxSedRepository = dxSedRepository;
        this.dxSedQueryService = dxSedQueryService;
    }

    /**
     * {@code POST  /dx-seds} : Create a new dxSed.
     *
     * @param dxSedDTO the dxSedDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new dxSedDTO, or with status {@code 400 (Bad Request)} if the dxSed has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/dx-seds")
    public ResponseEntity<DxSedDTO> createDxSed(@Valid @RequestBody DxSedDTO dxSedDTO) throws URISyntaxException {
        log.debug("REST request to save DxSed : {}", dxSedDTO);
        if (dxSedDTO.getId() != null) {
            throw new BadRequestAlertException("A new dxSed cannot already have an ID", ENTITY_NAME, "idexists");
        }
        DxSedDTO result = dxSedService.save(dxSedDTO);
        return ResponseEntity
            .created(new URI("/api/dx-seds/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /dx-seds/:id} : Updates an existing dxSed.
     *
     * @param id the id of the dxSedDTO to save.
     * @param dxSedDTO the dxSedDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated dxSedDTO,
     * or with status {@code 400 (Bad Request)} if the dxSedDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the dxSedDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/dx-seds/{id}")
    public ResponseEntity<DxSedDTO> updateDxSed(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody DxSedDTO dxSedDTO
    ) throws URISyntaxException {
        log.debug("REST request to update DxSed : {}, {}", id, dxSedDTO);
        if (dxSedDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, dxSedDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!dxSedRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        DxSedDTO result = dxSedService.save(dxSedDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, dxSedDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /dx-seds/:id} : Partial updates given fields of an existing dxSed, field will ignore if it is null
     *
     * @param id the id of the dxSedDTO to save.
     * @param dxSedDTO the dxSedDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated dxSedDTO,
     * or with status {@code 400 (Bad Request)} if the dxSedDTO is not valid,
     * or with status {@code 404 (Not Found)} if the dxSedDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the dxSedDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/dx-seds/{id}", consumes = "application/merge-patch+json")
    public ResponseEntity<DxSedDTO> partialUpdateDxSed(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody DxSedDTO dxSedDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update DxSed partially : {}, {}", id, dxSedDTO);
        if (dxSedDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, dxSedDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!dxSedRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<DxSedDTO> result = dxSedService.partialUpdate(dxSedDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, dxSedDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /dx-seds} : get all the dxSeds.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of dxSeds in body.
     */
    @GetMapping("/dx-seds")
    public ResponseEntity<List<DxSedDTO>> getAllDxSeds(DxSedCriteria criteria, Pageable pageable) {
        log.debug("REST request to get DxSeds by criteria: {}", criteria);
        Page<DxSedDTO> page = dxSedQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /dx-seds/count} : count all the dxSeds.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/dx-seds/count")
    public ResponseEntity<Long> countDxSeds(DxSedCriteria criteria) {
        log.debug("REST request to count DxSeds by criteria: {}", criteria);
        return ResponseEntity.ok().body(dxSedQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /dx-seds/:id} : get the "id" dxSed.
     *
     * @param id the id of the dxSedDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the dxSedDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/dx-seds/{id}")
    public ResponseEntity<DxSedDTO> getDxSed(@PathVariable Long id) {
        log.debug("REST request to get DxSed : {}", id);
        Optional<DxSedDTO> dxSedDTO = dxSedService.findOne(id);
        return ResponseUtil.wrapOrNotFound(dxSedDTO);
    }

    /**
     * {@code DELETE  /dx-seds/:id} : delete the "id" dxSed.
     *
     * @param id the id of the dxSedDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/dx-seds/{id}")
    public ResponseEntity<Void> deleteDxSed(@PathVariable Long id) {
        log.debug("REST request to delete DxSed : {}", id);
        dxSedService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }

    /**
     * {@code SEARCH  /_search/dx-seds?query=:query} : search for the dxSed corresponding
     * to the query.
     *
     * @param query the query of the dxSed search.
     * @param pageable the pagination information.
     * @return the result of the search.
     */
    @GetMapping("/_search/dx-seds")
    public ResponseEntity<List<DxSedDTO>> searchDxSeds(@RequestParam String query, Pageable pageable) {
        log.debug("REST request to search for a page of DxSeds for query {}", query);
        Page<DxSedDTO> page = dxSedService.search(query, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }
}
