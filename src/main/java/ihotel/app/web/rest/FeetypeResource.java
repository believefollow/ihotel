package ihotel.app.web.rest;

import static org.elasticsearch.index.query.QueryBuilders.*;

import ihotel.app.repository.FeetypeRepository;
import ihotel.app.service.FeetypeQueryService;
import ihotel.app.service.FeetypeService;
import ihotel.app.service.criteria.FeetypeCriteria;
import ihotel.app.service.dto.FeetypeDTO;
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
 * REST controller for managing {@link ihotel.app.domain.Feetype}.
 */
@RestController
@RequestMapping("/api")
public class FeetypeResource {

    private final Logger log = LoggerFactory.getLogger(FeetypeResource.class);

    private static final String ENTITY_NAME = "feetype";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final FeetypeService feetypeService;

    private final FeetypeRepository feetypeRepository;

    private final FeetypeQueryService feetypeQueryService;

    public FeetypeResource(FeetypeService feetypeService, FeetypeRepository feetypeRepository, FeetypeQueryService feetypeQueryService) {
        this.feetypeService = feetypeService;
        this.feetypeRepository = feetypeRepository;
        this.feetypeQueryService = feetypeQueryService;
    }

    /**
     * {@code POST  /feetypes} : Create a new feetype.
     *
     * @param feetypeDTO the feetypeDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new feetypeDTO, or with status {@code 400 (Bad Request)} if the feetype has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/feetypes")
    public ResponseEntity<FeetypeDTO> createFeetype(@Valid @RequestBody FeetypeDTO feetypeDTO) throws URISyntaxException {
        log.debug("REST request to save Feetype : {}", feetypeDTO);
        if (feetypeDTO.getId() != null) {
            throw new BadRequestAlertException("A new feetype cannot already have an ID", ENTITY_NAME, "idexists");
        }
        FeetypeDTO result = feetypeService.save(feetypeDTO);
        return ResponseEntity
            .created(new URI("/api/feetypes/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /feetypes/:id} : Updates an existing feetype.
     *
     * @param id the id of the feetypeDTO to save.
     * @param feetypeDTO the feetypeDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated feetypeDTO,
     * or with status {@code 400 (Bad Request)} if the feetypeDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the feetypeDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/feetypes/{id}")
    public ResponseEntity<FeetypeDTO> updateFeetype(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody FeetypeDTO feetypeDTO
    ) throws URISyntaxException {
        log.debug("REST request to update Feetype : {}, {}", id, feetypeDTO);
        if (feetypeDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, feetypeDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!feetypeRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        FeetypeDTO result = feetypeService.save(feetypeDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, feetypeDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /feetypes/:id} : Partial updates given fields of an existing feetype, field will ignore if it is null
     *
     * @param id the id of the feetypeDTO to save.
     * @param feetypeDTO the feetypeDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated feetypeDTO,
     * or with status {@code 400 (Bad Request)} if the feetypeDTO is not valid,
     * or with status {@code 404 (Not Found)} if the feetypeDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the feetypeDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/feetypes/{id}", consumes = "application/merge-patch+json")
    public ResponseEntity<FeetypeDTO> partialUpdateFeetype(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody FeetypeDTO feetypeDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update Feetype partially : {}, {}", id, feetypeDTO);
        if (feetypeDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, feetypeDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!feetypeRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<FeetypeDTO> result = feetypeService.partialUpdate(feetypeDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, feetypeDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /feetypes} : get all the feetypes.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of feetypes in body.
     */
    @GetMapping("/feetypes")
    public ResponseEntity<List<FeetypeDTO>> getAllFeetypes(FeetypeCriteria criteria, Pageable pageable) {
        log.debug("REST request to get Feetypes by criteria: {}", criteria);
        Page<FeetypeDTO> page = feetypeQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /feetypes/count} : count all the feetypes.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/feetypes/count")
    public ResponseEntity<Long> countFeetypes(FeetypeCriteria criteria) {
        log.debug("REST request to count Feetypes by criteria: {}", criteria);
        return ResponseEntity.ok().body(feetypeQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /feetypes/:id} : get the "id" feetype.
     *
     * @param id the id of the feetypeDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the feetypeDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/feetypes/{id}")
    public ResponseEntity<FeetypeDTO> getFeetype(@PathVariable Long id) {
        log.debug("REST request to get Feetype : {}", id);
        Optional<FeetypeDTO> feetypeDTO = feetypeService.findOne(id);
        return ResponseUtil.wrapOrNotFound(feetypeDTO);
    }

    /**
     * {@code DELETE  /feetypes/:id} : delete the "id" feetype.
     *
     * @param id the id of the feetypeDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/feetypes/{id}")
    public ResponseEntity<Void> deleteFeetype(@PathVariable Long id) {
        log.debug("REST request to delete Feetype : {}", id);
        feetypeService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }

    /**
     * {@code SEARCH  /_search/feetypes?query=:query} : search for the feetype corresponding
     * to the query.
     *
     * @param query the query of the feetype search.
     * @param pageable the pagination information.
     * @return the result of the search.
     */
    @GetMapping("/_search/feetypes")
    public ResponseEntity<List<FeetypeDTO>> searchFeetypes(@RequestParam String query, Pageable pageable) {
        log.debug("REST request to search for a page of Feetypes for query {}", query);
        Page<FeetypeDTO> page = feetypeService.search(query, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }
}
