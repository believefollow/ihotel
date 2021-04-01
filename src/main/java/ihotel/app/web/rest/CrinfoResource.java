package ihotel.app.web.rest;

import static org.elasticsearch.index.query.QueryBuilders.*;

import ihotel.app.repository.CrinfoRepository;
import ihotel.app.service.CrinfoQueryService;
import ihotel.app.service.CrinfoService;
import ihotel.app.service.criteria.CrinfoCriteria;
import ihotel.app.service.dto.CrinfoDTO;
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
 * REST controller for managing {@link ihotel.app.domain.Crinfo}.
 */
@RestController
@RequestMapping("/api")
public class CrinfoResource {

    private final Logger log = LoggerFactory.getLogger(CrinfoResource.class);

    private static final String ENTITY_NAME = "crinfo";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final CrinfoService crinfoService;

    private final CrinfoRepository crinfoRepository;

    private final CrinfoQueryService crinfoQueryService;

    public CrinfoResource(CrinfoService crinfoService, CrinfoRepository crinfoRepository, CrinfoQueryService crinfoQueryService) {
        this.crinfoService = crinfoService;
        this.crinfoRepository = crinfoRepository;
        this.crinfoQueryService = crinfoQueryService;
    }

    /**
     * {@code POST  /crinfos} : Create a new crinfo.
     *
     * @param crinfoDTO the crinfoDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new crinfoDTO, or with status {@code 400 (Bad Request)} if the crinfo has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/crinfos")
    public ResponseEntity<CrinfoDTO> createCrinfo(@Valid @RequestBody CrinfoDTO crinfoDTO) throws URISyntaxException {
        log.debug("REST request to save Crinfo : {}", crinfoDTO);
        if (crinfoDTO.getId() != null) {
            throw new BadRequestAlertException("A new crinfo cannot already have an ID", ENTITY_NAME, "idexists");
        }
        CrinfoDTO result = crinfoService.save(crinfoDTO);
        return ResponseEntity
            .created(new URI("/api/crinfos/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /crinfos/:id} : Updates an existing crinfo.
     *
     * @param id the id of the crinfoDTO to save.
     * @param crinfoDTO the crinfoDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated crinfoDTO,
     * or with status {@code 400 (Bad Request)} if the crinfoDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the crinfoDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/crinfos/{id}")
    public ResponseEntity<CrinfoDTO> updateCrinfo(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody CrinfoDTO crinfoDTO
    ) throws URISyntaxException {
        log.debug("REST request to update Crinfo : {}, {}", id, crinfoDTO);
        if (crinfoDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, crinfoDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!crinfoRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        CrinfoDTO result = crinfoService.save(crinfoDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, crinfoDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /crinfos/:id} : Partial updates given fields of an existing crinfo, field will ignore if it is null
     *
     * @param id the id of the crinfoDTO to save.
     * @param crinfoDTO the crinfoDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated crinfoDTO,
     * or with status {@code 400 (Bad Request)} if the crinfoDTO is not valid,
     * or with status {@code 404 (Not Found)} if the crinfoDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the crinfoDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/crinfos/{id}", consumes = "application/merge-patch+json")
    public ResponseEntity<CrinfoDTO> partialUpdateCrinfo(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody CrinfoDTO crinfoDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update Crinfo partially : {}, {}", id, crinfoDTO);
        if (crinfoDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, crinfoDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!crinfoRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<CrinfoDTO> result = crinfoService.partialUpdate(crinfoDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, crinfoDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /crinfos} : get all the crinfos.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of crinfos in body.
     */
    @GetMapping("/crinfos")
    public ResponseEntity<List<CrinfoDTO>> getAllCrinfos(CrinfoCriteria criteria, Pageable pageable) {
        log.debug("REST request to get Crinfos by criteria: {}", criteria);
        Page<CrinfoDTO> page = crinfoQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /crinfos/count} : count all the crinfos.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/crinfos/count")
    public ResponseEntity<Long> countCrinfos(CrinfoCriteria criteria) {
        log.debug("REST request to count Crinfos by criteria: {}", criteria);
        return ResponseEntity.ok().body(crinfoQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /crinfos/:id} : get the "id" crinfo.
     *
     * @param id the id of the crinfoDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the crinfoDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/crinfos/{id}")
    public ResponseEntity<CrinfoDTO> getCrinfo(@PathVariable Long id) {
        log.debug("REST request to get Crinfo : {}", id);
        Optional<CrinfoDTO> crinfoDTO = crinfoService.findOne(id);
        return ResponseUtil.wrapOrNotFound(crinfoDTO);
    }

    /**
     * {@code DELETE  /crinfos/:id} : delete the "id" crinfo.
     *
     * @param id the id of the crinfoDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/crinfos/{id}")
    public ResponseEntity<Void> deleteCrinfo(@PathVariable Long id) {
        log.debug("REST request to delete Crinfo : {}", id);
        crinfoService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }

    /**
     * {@code SEARCH  /_search/crinfos?query=:query} : search for the crinfo corresponding
     * to the query.
     *
     * @param query the query of the crinfo search.
     * @param pageable the pagination information.
     * @return the result of the search.
     */
    @GetMapping("/_search/crinfos")
    public ResponseEntity<List<CrinfoDTO>> searchCrinfos(@RequestParam String query, Pageable pageable) {
        log.debug("REST request to search for a page of Crinfos for query {}", query);
        Page<CrinfoDTO> page = crinfoService.search(query, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }
}
