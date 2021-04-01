package ihotel.app.web.rest;

import static org.elasticsearch.index.query.QueryBuilders.*;

import ihotel.app.repository.DxSedinfoRepository;
import ihotel.app.service.DxSedinfoQueryService;
import ihotel.app.service.DxSedinfoService;
import ihotel.app.service.criteria.DxSedinfoCriteria;
import ihotel.app.service.dto.DxSedinfoDTO;
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
 * REST controller for managing {@link ihotel.app.domain.DxSedinfo}.
 */
@RestController
@RequestMapping("/api")
public class DxSedinfoResource {

    private final Logger log = LoggerFactory.getLogger(DxSedinfoResource.class);

    private static final String ENTITY_NAME = "dxSedinfo";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final DxSedinfoService dxSedinfoService;

    private final DxSedinfoRepository dxSedinfoRepository;

    private final DxSedinfoQueryService dxSedinfoQueryService;

    public DxSedinfoResource(
        DxSedinfoService dxSedinfoService,
        DxSedinfoRepository dxSedinfoRepository,
        DxSedinfoQueryService dxSedinfoQueryService
    ) {
        this.dxSedinfoService = dxSedinfoService;
        this.dxSedinfoRepository = dxSedinfoRepository;
        this.dxSedinfoQueryService = dxSedinfoQueryService;
    }

    /**
     * {@code POST  /dx-sedinfos} : Create a new dxSedinfo.
     *
     * @param dxSedinfoDTO the dxSedinfoDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new dxSedinfoDTO, or with status {@code 400 (Bad Request)} if the dxSedinfo has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/dx-sedinfos")
    public ResponseEntity<DxSedinfoDTO> createDxSedinfo(@Valid @RequestBody DxSedinfoDTO dxSedinfoDTO) throws URISyntaxException {
        log.debug("REST request to save DxSedinfo : {}", dxSedinfoDTO);
        if (dxSedinfoDTO.getId() != null) {
            throw new BadRequestAlertException("A new dxSedinfo cannot already have an ID", ENTITY_NAME, "idexists");
        }
        DxSedinfoDTO result = dxSedinfoService.save(dxSedinfoDTO);
        return ResponseEntity
            .created(new URI("/api/dx-sedinfos/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /dx-sedinfos/:id} : Updates an existing dxSedinfo.
     *
     * @param id the id of the dxSedinfoDTO to save.
     * @param dxSedinfoDTO the dxSedinfoDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated dxSedinfoDTO,
     * or with status {@code 400 (Bad Request)} if the dxSedinfoDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the dxSedinfoDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/dx-sedinfos/{id}")
    public ResponseEntity<DxSedinfoDTO> updateDxSedinfo(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody DxSedinfoDTO dxSedinfoDTO
    ) throws URISyntaxException {
        log.debug("REST request to update DxSedinfo : {}, {}", id, dxSedinfoDTO);
        if (dxSedinfoDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, dxSedinfoDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!dxSedinfoRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        DxSedinfoDTO result = dxSedinfoService.save(dxSedinfoDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, dxSedinfoDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /dx-sedinfos/:id} : Partial updates given fields of an existing dxSedinfo, field will ignore if it is null
     *
     * @param id the id of the dxSedinfoDTO to save.
     * @param dxSedinfoDTO the dxSedinfoDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated dxSedinfoDTO,
     * or with status {@code 400 (Bad Request)} if the dxSedinfoDTO is not valid,
     * or with status {@code 404 (Not Found)} if the dxSedinfoDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the dxSedinfoDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/dx-sedinfos/{id}", consumes = "application/merge-patch+json")
    public ResponseEntity<DxSedinfoDTO> partialUpdateDxSedinfo(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody DxSedinfoDTO dxSedinfoDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update DxSedinfo partially : {}, {}", id, dxSedinfoDTO);
        if (dxSedinfoDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, dxSedinfoDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!dxSedinfoRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<DxSedinfoDTO> result = dxSedinfoService.partialUpdate(dxSedinfoDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, dxSedinfoDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /dx-sedinfos} : get all the dxSedinfos.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of dxSedinfos in body.
     */
    @GetMapping("/dx-sedinfos")
    public ResponseEntity<List<DxSedinfoDTO>> getAllDxSedinfos(DxSedinfoCriteria criteria, Pageable pageable) {
        log.debug("REST request to get DxSedinfos by criteria: {}", criteria);
        Page<DxSedinfoDTO> page = dxSedinfoQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /dx-sedinfos/count} : count all the dxSedinfos.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/dx-sedinfos/count")
    public ResponseEntity<Long> countDxSedinfos(DxSedinfoCriteria criteria) {
        log.debug("REST request to count DxSedinfos by criteria: {}", criteria);
        return ResponseEntity.ok().body(dxSedinfoQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /dx-sedinfos/:id} : get the "id" dxSedinfo.
     *
     * @param id the id of the dxSedinfoDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the dxSedinfoDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/dx-sedinfos/{id}")
    public ResponseEntity<DxSedinfoDTO> getDxSedinfo(@PathVariable Long id) {
        log.debug("REST request to get DxSedinfo : {}", id);
        Optional<DxSedinfoDTO> dxSedinfoDTO = dxSedinfoService.findOne(id);
        return ResponseUtil.wrapOrNotFound(dxSedinfoDTO);
    }

    /**
     * {@code DELETE  /dx-sedinfos/:id} : delete the "id" dxSedinfo.
     *
     * @param id the id of the dxSedinfoDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/dx-sedinfos/{id}")
    public ResponseEntity<Void> deleteDxSedinfo(@PathVariable Long id) {
        log.debug("REST request to delete DxSedinfo : {}", id);
        dxSedinfoService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }

    /**
     * {@code SEARCH  /_search/dx-sedinfos?query=:query} : search for the dxSedinfo corresponding
     * to the query.
     *
     * @param query the query of the dxSedinfo search.
     * @param pageable the pagination information.
     * @return the result of the search.
     */
    @GetMapping("/_search/dx-sedinfos")
    public ResponseEntity<List<DxSedinfoDTO>> searchDxSedinfos(@RequestParam String query, Pageable pageable) {
        log.debug("REST request to search for a page of DxSedinfos for query {}", query);
        Page<DxSedinfoDTO> page = dxSedinfoService.search(query, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }
}
