package ihotel.app.web.rest;

import static org.elasticsearch.index.query.QueryBuilders.*;

import ihotel.app.repository.CyRoomtypeRepository;
import ihotel.app.service.CyRoomtypeQueryService;
import ihotel.app.service.CyRoomtypeService;
import ihotel.app.service.criteria.CyRoomtypeCriteria;
import ihotel.app.service.dto.CyRoomtypeDTO;
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
 * REST controller for managing {@link ihotel.app.domain.CyRoomtype}.
 */
@RestController
@RequestMapping("/api")
public class CyRoomtypeResource {

    private final Logger log = LoggerFactory.getLogger(CyRoomtypeResource.class);

    private static final String ENTITY_NAME = "cyRoomtype";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final CyRoomtypeService cyRoomtypeService;

    private final CyRoomtypeRepository cyRoomtypeRepository;

    private final CyRoomtypeQueryService cyRoomtypeQueryService;

    public CyRoomtypeResource(
        CyRoomtypeService cyRoomtypeService,
        CyRoomtypeRepository cyRoomtypeRepository,
        CyRoomtypeQueryService cyRoomtypeQueryService
    ) {
        this.cyRoomtypeService = cyRoomtypeService;
        this.cyRoomtypeRepository = cyRoomtypeRepository;
        this.cyRoomtypeQueryService = cyRoomtypeQueryService;
    }

    /**
     * {@code POST  /cy-roomtypes} : Create a new cyRoomtype.
     *
     * @param cyRoomtypeDTO the cyRoomtypeDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new cyRoomtypeDTO, or with status {@code 400 (Bad Request)} if the cyRoomtype has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/cy-roomtypes")
    public ResponseEntity<CyRoomtypeDTO> createCyRoomtype(@Valid @RequestBody CyRoomtypeDTO cyRoomtypeDTO) throws URISyntaxException {
        log.debug("REST request to save CyRoomtype : {}", cyRoomtypeDTO);
        if (cyRoomtypeDTO.getId() != null) {
            throw new BadRequestAlertException("A new cyRoomtype cannot already have an ID", ENTITY_NAME, "idexists");
        }
        CyRoomtypeDTO result = cyRoomtypeService.save(cyRoomtypeDTO);
        return ResponseEntity
            .created(new URI("/api/cy-roomtypes/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /cy-roomtypes/:id} : Updates an existing cyRoomtype.
     *
     * @param id the id of the cyRoomtypeDTO to save.
     * @param cyRoomtypeDTO the cyRoomtypeDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated cyRoomtypeDTO,
     * or with status {@code 400 (Bad Request)} if the cyRoomtypeDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the cyRoomtypeDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/cy-roomtypes/{id}")
    public ResponseEntity<CyRoomtypeDTO> updateCyRoomtype(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody CyRoomtypeDTO cyRoomtypeDTO
    ) throws URISyntaxException {
        log.debug("REST request to update CyRoomtype : {}, {}", id, cyRoomtypeDTO);
        if (cyRoomtypeDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, cyRoomtypeDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!cyRoomtypeRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        CyRoomtypeDTO result = cyRoomtypeService.save(cyRoomtypeDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, cyRoomtypeDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /cy-roomtypes/:id} : Partial updates given fields of an existing cyRoomtype, field will ignore if it is null
     *
     * @param id the id of the cyRoomtypeDTO to save.
     * @param cyRoomtypeDTO the cyRoomtypeDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated cyRoomtypeDTO,
     * or with status {@code 400 (Bad Request)} if the cyRoomtypeDTO is not valid,
     * or with status {@code 404 (Not Found)} if the cyRoomtypeDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the cyRoomtypeDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/cy-roomtypes/{id}", consumes = "application/merge-patch+json")
    public ResponseEntity<CyRoomtypeDTO> partialUpdateCyRoomtype(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody CyRoomtypeDTO cyRoomtypeDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update CyRoomtype partially : {}, {}", id, cyRoomtypeDTO);
        if (cyRoomtypeDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, cyRoomtypeDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!cyRoomtypeRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<CyRoomtypeDTO> result = cyRoomtypeService.partialUpdate(cyRoomtypeDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, cyRoomtypeDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /cy-roomtypes} : get all the cyRoomtypes.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of cyRoomtypes in body.
     */
    @GetMapping("/cy-roomtypes")
    public ResponseEntity<List<CyRoomtypeDTO>> getAllCyRoomtypes(CyRoomtypeCriteria criteria, Pageable pageable) {
        log.debug("REST request to get CyRoomtypes by criteria: {}", criteria);
        Page<CyRoomtypeDTO> page = cyRoomtypeQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /cy-roomtypes/count} : count all the cyRoomtypes.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/cy-roomtypes/count")
    public ResponseEntity<Long> countCyRoomtypes(CyRoomtypeCriteria criteria) {
        log.debug("REST request to count CyRoomtypes by criteria: {}", criteria);
        return ResponseEntity.ok().body(cyRoomtypeQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /cy-roomtypes/:id} : get the "id" cyRoomtype.
     *
     * @param id the id of the cyRoomtypeDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the cyRoomtypeDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/cy-roomtypes/{id}")
    public ResponseEntity<CyRoomtypeDTO> getCyRoomtype(@PathVariable Long id) {
        log.debug("REST request to get CyRoomtype : {}", id);
        Optional<CyRoomtypeDTO> cyRoomtypeDTO = cyRoomtypeService.findOne(id);
        return ResponseUtil.wrapOrNotFound(cyRoomtypeDTO);
    }

    /**
     * {@code DELETE  /cy-roomtypes/:id} : delete the "id" cyRoomtype.
     *
     * @param id the id of the cyRoomtypeDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/cy-roomtypes/{id}")
    public ResponseEntity<Void> deleteCyRoomtype(@PathVariable Long id) {
        log.debug("REST request to delete CyRoomtype : {}", id);
        cyRoomtypeService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }

    /**
     * {@code SEARCH  /_search/cy-roomtypes?query=:query} : search for the cyRoomtype corresponding
     * to the query.
     *
     * @param query the query of the cyRoomtype search.
     * @param pageable the pagination information.
     * @return the result of the search.
     */
    @GetMapping("/_search/cy-roomtypes")
    public ResponseEntity<List<CyRoomtypeDTO>> searchCyRoomtypes(@RequestParam String query, Pageable pageable) {
        log.debug("REST request to search for a page of CyRoomtypes for query {}", query);
        Page<CyRoomtypeDTO> page = cyRoomtypeService.search(query, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }
}
