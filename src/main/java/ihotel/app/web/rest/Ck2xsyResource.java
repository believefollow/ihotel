package ihotel.app.web.rest;

import static org.elasticsearch.index.query.QueryBuilders.*;

import ihotel.app.repository.Ck2xsyRepository;
import ihotel.app.service.Ck2xsyQueryService;
import ihotel.app.service.Ck2xsyService;
import ihotel.app.service.criteria.Ck2xsyCriteria;
import ihotel.app.service.dto.Ck2xsyDTO;
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
 * REST controller for managing {@link ihotel.app.domain.Ck2xsy}.
 */
@RestController
@RequestMapping("/api")
public class Ck2xsyResource {

    private final Logger log = LoggerFactory.getLogger(Ck2xsyResource.class);

    private static final String ENTITY_NAME = "ck2xsy";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final Ck2xsyService ck2xsyService;

    private final Ck2xsyRepository ck2xsyRepository;

    private final Ck2xsyQueryService ck2xsyQueryService;

    public Ck2xsyResource(Ck2xsyService ck2xsyService, Ck2xsyRepository ck2xsyRepository, Ck2xsyQueryService ck2xsyQueryService) {
        this.ck2xsyService = ck2xsyService;
        this.ck2xsyRepository = ck2xsyRepository;
        this.ck2xsyQueryService = ck2xsyQueryService;
    }

    /**
     * {@code POST  /ck-2-xsies} : Create a new ck2xsy.
     *
     * @param ck2xsyDTO the ck2xsyDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new ck2xsyDTO, or with status {@code 400 (Bad Request)} if the ck2xsy has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/ck-2-xsies")
    public ResponseEntity<Ck2xsyDTO> createCk2xsy(@Valid @RequestBody Ck2xsyDTO ck2xsyDTO) throws URISyntaxException {
        log.debug("REST request to save Ck2xsy : {}", ck2xsyDTO);
        if (ck2xsyDTO.getId() != null) {
            throw new BadRequestAlertException("A new ck2xsy cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Ck2xsyDTO result = ck2xsyService.save(ck2xsyDTO);
        return ResponseEntity
            .created(new URI("/api/ck-2-xsies/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /ck-2-xsies/:id} : Updates an existing ck2xsy.
     *
     * @param id the id of the ck2xsyDTO to save.
     * @param ck2xsyDTO the ck2xsyDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated ck2xsyDTO,
     * or with status {@code 400 (Bad Request)} if the ck2xsyDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the ck2xsyDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/ck-2-xsies/{id}")
    public ResponseEntity<Ck2xsyDTO> updateCk2xsy(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody Ck2xsyDTO ck2xsyDTO
    ) throws URISyntaxException {
        log.debug("REST request to update Ck2xsy : {}, {}", id, ck2xsyDTO);
        if (ck2xsyDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, ck2xsyDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!ck2xsyRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Ck2xsyDTO result = ck2xsyService.save(ck2xsyDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, ck2xsyDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /ck-2-xsies/:id} : Partial updates given fields of an existing ck2xsy, field will ignore if it is null
     *
     * @param id the id of the ck2xsyDTO to save.
     * @param ck2xsyDTO the ck2xsyDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated ck2xsyDTO,
     * or with status {@code 400 (Bad Request)} if the ck2xsyDTO is not valid,
     * or with status {@code 404 (Not Found)} if the ck2xsyDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the ck2xsyDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/ck-2-xsies/{id}", consumes = "application/merge-patch+json")
    public ResponseEntity<Ck2xsyDTO> partialUpdateCk2xsy(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody Ck2xsyDTO ck2xsyDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update Ck2xsy partially : {}, {}", id, ck2xsyDTO);
        if (ck2xsyDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, ck2xsyDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!ck2xsyRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<Ck2xsyDTO> result = ck2xsyService.partialUpdate(ck2xsyDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, ck2xsyDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /ck-2-xsies} : get all the ck2xsies.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of ck2xsies in body.
     */
    @GetMapping("/ck-2-xsies")
    public ResponseEntity<List<Ck2xsyDTO>> getAllCk2xsies(Ck2xsyCriteria criteria, Pageable pageable) {
        log.debug("REST request to get Ck2xsies by criteria: {}", criteria);
        Page<Ck2xsyDTO> page = ck2xsyQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /ck-2-xsies/count} : count all the ck2xsies.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/ck-2-xsies/count")
    public ResponseEntity<Long> countCk2xsies(Ck2xsyCriteria criteria) {
        log.debug("REST request to count Ck2xsies by criteria: {}", criteria);
        return ResponseEntity.ok().body(ck2xsyQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /ck-2-xsies/:id} : get the "id" ck2xsy.
     *
     * @param id the id of the ck2xsyDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the ck2xsyDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/ck-2-xsies/{id}")
    public ResponseEntity<Ck2xsyDTO> getCk2xsy(@PathVariable Long id) {
        log.debug("REST request to get Ck2xsy : {}", id);
        Optional<Ck2xsyDTO> ck2xsyDTO = ck2xsyService.findOne(id);
        return ResponseUtil.wrapOrNotFound(ck2xsyDTO);
    }

    /**
     * {@code DELETE  /ck-2-xsies/:id} : delete the "id" ck2xsy.
     *
     * @param id the id of the ck2xsyDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/ck-2-xsies/{id}")
    public ResponseEntity<Void> deleteCk2xsy(@PathVariable Long id) {
        log.debug("REST request to delete Ck2xsy : {}", id);
        ck2xsyService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }

    /**
     * {@code SEARCH  /_search/ck-2-xsies?query=:query} : search for the ck2xsy corresponding
     * to the query.
     *
     * @param query the query of the ck2xsy search.
     * @param pageable the pagination information.
     * @return the result of the search.
     */
    @GetMapping("/_search/ck-2-xsies")
    public ResponseEntity<List<Ck2xsyDTO>> searchCk2xsies(@RequestParam String query, Pageable pageable) {
        log.debug("REST request to search for a page of Ck2xsies for query {}", query);
        Page<Ck2xsyDTO> page = ck2xsyService.search(query, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }
}
