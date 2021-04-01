package ihotel.app.web.rest;

import static org.elasticsearch.index.query.QueryBuilders.*;

import ihotel.app.repository.DTypeRepository;
import ihotel.app.service.DTypeQueryService;
import ihotel.app.service.DTypeService;
import ihotel.app.service.criteria.DTypeCriteria;
import ihotel.app.service.dto.DTypeDTO;
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
 * REST controller for managing {@link ihotel.app.domain.DType}.
 */
@RestController
@RequestMapping("/api")
public class DTypeResource {

    private final Logger log = LoggerFactory.getLogger(DTypeResource.class);

    private static final String ENTITY_NAME = "dType";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final DTypeService dTypeService;

    private final DTypeRepository dTypeRepository;

    private final DTypeQueryService dTypeQueryService;

    public DTypeResource(DTypeService dTypeService, DTypeRepository dTypeRepository, DTypeQueryService dTypeQueryService) {
        this.dTypeService = dTypeService;
        this.dTypeRepository = dTypeRepository;
        this.dTypeQueryService = dTypeQueryService;
    }

    /**
     * {@code POST  /d-types} : Create a new dType.
     *
     * @param dTypeDTO the dTypeDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new dTypeDTO, or with status {@code 400 (Bad Request)} if the dType has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/d-types")
    public ResponseEntity<DTypeDTO> createDType(@Valid @RequestBody DTypeDTO dTypeDTO) throws URISyntaxException {
        log.debug("REST request to save DType : {}", dTypeDTO);
        if (dTypeDTO.getId() != null) {
            throw new BadRequestAlertException("A new dType cannot already have an ID", ENTITY_NAME, "idexists");
        }
        DTypeDTO result = dTypeService.save(dTypeDTO);
        return ResponseEntity
            .created(new URI("/api/d-types/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /d-types/:id} : Updates an existing dType.
     *
     * @param id the id of the dTypeDTO to save.
     * @param dTypeDTO the dTypeDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated dTypeDTO,
     * or with status {@code 400 (Bad Request)} if the dTypeDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the dTypeDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/d-types/{id}")
    public ResponseEntity<DTypeDTO> updateDType(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody DTypeDTO dTypeDTO
    ) throws URISyntaxException {
        log.debug("REST request to update DType : {}, {}", id, dTypeDTO);
        if (dTypeDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, dTypeDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!dTypeRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        DTypeDTO result = dTypeService.save(dTypeDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, dTypeDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /d-types/:id} : Partial updates given fields of an existing dType, field will ignore if it is null
     *
     * @param id the id of the dTypeDTO to save.
     * @param dTypeDTO the dTypeDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated dTypeDTO,
     * or with status {@code 400 (Bad Request)} if the dTypeDTO is not valid,
     * or with status {@code 404 (Not Found)} if the dTypeDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the dTypeDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/d-types/{id}", consumes = "application/merge-patch+json")
    public ResponseEntity<DTypeDTO> partialUpdateDType(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody DTypeDTO dTypeDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update DType partially : {}, {}", id, dTypeDTO);
        if (dTypeDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, dTypeDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!dTypeRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<DTypeDTO> result = dTypeService.partialUpdate(dTypeDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, dTypeDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /d-types} : get all the dTypes.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of dTypes in body.
     */
    @GetMapping("/d-types")
    public ResponseEntity<List<DTypeDTO>> getAllDTypes(DTypeCriteria criteria, Pageable pageable) {
        log.debug("REST request to get DTypes by criteria: {}", criteria);
        Page<DTypeDTO> page = dTypeQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /d-types/count} : count all the dTypes.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/d-types/count")
    public ResponseEntity<Long> countDTypes(DTypeCriteria criteria) {
        log.debug("REST request to count DTypes by criteria: {}", criteria);
        return ResponseEntity.ok().body(dTypeQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /d-types/:id} : get the "id" dType.
     *
     * @param id the id of the dTypeDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the dTypeDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/d-types/{id}")
    public ResponseEntity<DTypeDTO> getDType(@PathVariable Long id) {
        log.debug("REST request to get DType : {}", id);
        Optional<DTypeDTO> dTypeDTO = dTypeService.findOne(id);
        return ResponseUtil.wrapOrNotFound(dTypeDTO);
    }

    /**
     * {@code DELETE  /d-types/:id} : delete the "id" dType.
     *
     * @param id the id of the dTypeDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/d-types/{id}")
    public ResponseEntity<Void> deleteDType(@PathVariable Long id) {
        log.debug("REST request to delete DType : {}", id);
        dTypeService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }

    /**
     * {@code SEARCH  /_search/d-types?query=:query} : search for the dType corresponding
     * to the query.
     *
     * @param query the query of the dType search.
     * @param pageable the pagination information.
     * @return the result of the search.
     */
    @GetMapping("/_search/d-types")
    public ResponseEntity<List<DTypeDTO>> searchDTypes(@RequestParam String query, Pageable pageable) {
        log.debug("REST request to search for a page of DTypes for query {}", query);
        Page<DTypeDTO> page = dTypeService.search(query, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }
}
