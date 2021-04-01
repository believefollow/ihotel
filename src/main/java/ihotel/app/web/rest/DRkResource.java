package ihotel.app.web.rest;

import static org.elasticsearch.index.query.QueryBuilders.*;

import ihotel.app.repository.DRkRepository;
import ihotel.app.service.DRkQueryService;
import ihotel.app.service.DRkService;
import ihotel.app.service.criteria.DRkCriteria;
import ihotel.app.service.dto.DRkDTO;
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
 * REST controller for managing {@link ihotel.app.domain.DRk}.
 */
@RestController
@RequestMapping("/api")
public class DRkResource {

    private final Logger log = LoggerFactory.getLogger(DRkResource.class);

    private static final String ENTITY_NAME = "dRk";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final DRkService dRkService;

    private final DRkRepository dRkRepository;

    private final DRkQueryService dRkQueryService;

    public DRkResource(DRkService dRkService, DRkRepository dRkRepository, DRkQueryService dRkQueryService) {
        this.dRkService = dRkService;
        this.dRkRepository = dRkRepository;
        this.dRkQueryService = dRkQueryService;
    }

    /**
     * {@code POST  /d-rks} : Create a new dRk.
     *
     * @param dRkDTO the dRkDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new dRkDTO, or with status {@code 400 (Bad Request)} if the dRk has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/d-rks")
    public ResponseEntity<DRkDTO> createDRk(@Valid @RequestBody DRkDTO dRkDTO) throws URISyntaxException {
        log.debug("REST request to save DRk : {}", dRkDTO);
        if (dRkDTO.getId() != null) {
            throw new BadRequestAlertException("A new dRk cannot already have an ID", ENTITY_NAME, "idexists");
        }
        DRkDTO result = dRkService.save(dRkDTO);
        return ResponseEntity
            .created(new URI("/api/d-rks/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /d-rks/:id} : Updates an existing dRk.
     *
     * @param id the id of the dRkDTO to save.
     * @param dRkDTO the dRkDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated dRkDTO,
     * or with status {@code 400 (Bad Request)} if the dRkDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the dRkDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/d-rks/{id}")
    public ResponseEntity<DRkDTO> updateDRk(@PathVariable(value = "id", required = false) final Long id, @Valid @RequestBody DRkDTO dRkDTO)
        throws URISyntaxException {
        log.debug("REST request to update DRk : {}, {}", id, dRkDTO);
        if (dRkDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, dRkDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!dRkRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        DRkDTO result = dRkService.save(dRkDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, dRkDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /d-rks/:id} : Partial updates given fields of an existing dRk, field will ignore if it is null
     *
     * @param id the id of the dRkDTO to save.
     * @param dRkDTO the dRkDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated dRkDTO,
     * or with status {@code 400 (Bad Request)} if the dRkDTO is not valid,
     * or with status {@code 404 (Not Found)} if the dRkDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the dRkDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/d-rks/{id}", consumes = "application/merge-patch+json")
    public ResponseEntity<DRkDTO> partialUpdateDRk(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody DRkDTO dRkDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update DRk partially : {}, {}", id, dRkDTO);
        if (dRkDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, dRkDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!dRkRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<DRkDTO> result = dRkService.partialUpdate(dRkDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, dRkDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /d-rks} : get all the dRks.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of dRks in body.
     */
    @GetMapping("/d-rks")
    public ResponseEntity<List<DRkDTO>> getAllDRks(DRkCriteria criteria, Pageable pageable) {
        log.debug("REST request to get DRks by criteria: {}", criteria);
        Page<DRkDTO> page = dRkQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /d-rks/count} : count all the dRks.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/d-rks/count")
    public ResponseEntity<Long> countDRks(DRkCriteria criteria) {
        log.debug("REST request to count DRks by criteria: {}", criteria);
        return ResponseEntity.ok().body(dRkQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /d-rks/:id} : get the "id" dRk.
     *
     * @param id the id of the dRkDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the dRkDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/d-rks/{id}")
    public ResponseEntity<DRkDTO> getDRk(@PathVariable Long id) {
        log.debug("REST request to get DRk : {}", id);
        Optional<DRkDTO> dRkDTO = dRkService.findOne(id);
        return ResponseUtil.wrapOrNotFound(dRkDTO);
    }

    /**
     * {@code DELETE  /d-rks/:id} : delete the "id" dRk.
     *
     * @param id the id of the dRkDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/d-rks/{id}")
    public ResponseEntity<Void> deleteDRk(@PathVariable Long id) {
        log.debug("REST request to delete DRk : {}", id);
        dRkService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }

    /**
     * {@code SEARCH  /_search/d-rks?query=:query} : search for the dRk corresponding
     * to the query.
     *
     * @param query the query of the dRk search.
     * @param pageable the pagination information.
     * @return the result of the search.
     */
    @GetMapping("/_search/d-rks")
    public ResponseEntity<List<DRkDTO>> searchDRks(@RequestParam String query, Pageable pageable) {
        log.debug("REST request to search for a page of DRks for query {}", query);
        Page<DRkDTO> page = dRkService.search(query, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }
}
