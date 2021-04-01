package ihotel.app.web.rest;

import static org.elasticsearch.index.query.QueryBuilders.*;

import ihotel.app.repository.AccbillnoRepository;
import ihotel.app.service.AccbillnoQueryService;
import ihotel.app.service.AccbillnoService;
import ihotel.app.service.criteria.AccbillnoCriteria;
import ihotel.app.service.dto.AccbillnoDTO;
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
 * REST controller for managing {@link ihotel.app.domain.Accbillno}.
 */
@RestController
@RequestMapping("/api")
public class AccbillnoResource {

    private final Logger log = LoggerFactory.getLogger(AccbillnoResource.class);

    private static final String ENTITY_NAME = "accbillno";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final AccbillnoService accbillnoService;

    private final AccbillnoRepository accbillnoRepository;

    private final AccbillnoQueryService accbillnoQueryService;

    public AccbillnoResource(
        AccbillnoService accbillnoService,
        AccbillnoRepository accbillnoRepository,
        AccbillnoQueryService accbillnoQueryService
    ) {
        this.accbillnoService = accbillnoService;
        this.accbillnoRepository = accbillnoRepository;
        this.accbillnoQueryService = accbillnoQueryService;
    }

    /**
     * {@code POST  /accbillnos} : Create a new accbillno.
     *
     * @param accbillnoDTO the accbillnoDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new accbillnoDTO, or with status {@code 400 (Bad Request)} if the accbillno has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/accbillnos")
    public ResponseEntity<AccbillnoDTO> createAccbillno(@Valid @RequestBody AccbillnoDTO accbillnoDTO) throws URISyntaxException {
        log.debug("REST request to save Accbillno : {}", accbillnoDTO);
        if (accbillnoDTO.getId() != null) {
            throw new BadRequestAlertException("A new accbillno cannot already have an ID", ENTITY_NAME, "idexists");
        }
        AccbillnoDTO result = accbillnoService.save(accbillnoDTO);
        return ResponseEntity
            .created(new URI("/api/accbillnos/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /accbillnos/:id} : Updates an existing accbillno.
     *
     * @param id the id of the accbillnoDTO to save.
     * @param accbillnoDTO the accbillnoDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated accbillnoDTO,
     * or with status {@code 400 (Bad Request)} if the accbillnoDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the accbillnoDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/accbillnos/{id}")
    public ResponseEntity<AccbillnoDTO> updateAccbillno(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody AccbillnoDTO accbillnoDTO
    ) throws URISyntaxException {
        log.debug("REST request to update Accbillno : {}, {}", id, accbillnoDTO);
        if (accbillnoDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, accbillnoDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!accbillnoRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        AccbillnoDTO result = accbillnoService.save(accbillnoDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, accbillnoDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /accbillnos/:id} : Partial updates given fields of an existing accbillno, field will ignore if it is null
     *
     * @param id the id of the accbillnoDTO to save.
     * @param accbillnoDTO the accbillnoDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated accbillnoDTO,
     * or with status {@code 400 (Bad Request)} if the accbillnoDTO is not valid,
     * or with status {@code 404 (Not Found)} if the accbillnoDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the accbillnoDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/accbillnos/{id}", consumes = "application/merge-patch+json")
    public ResponseEntity<AccbillnoDTO> partialUpdateAccbillno(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody AccbillnoDTO accbillnoDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update Accbillno partially : {}, {}", id, accbillnoDTO);
        if (accbillnoDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, accbillnoDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!accbillnoRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<AccbillnoDTO> result = accbillnoService.partialUpdate(accbillnoDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, accbillnoDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /accbillnos} : get all the accbillnos.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of accbillnos in body.
     */
    @GetMapping("/accbillnos")
    public ResponseEntity<List<AccbillnoDTO>> getAllAccbillnos(AccbillnoCriteria criteria, Pageable pageable) {
        log.debug("REST request to get Accbillnos by criteria: {}", criteria);
        Page<AccbillnoDTO> page = accbillnoQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /accbillnos/count} : count all the accbillnos.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/accbillnos/count")
    public ResponseEntity<Long> countAccbillnos(AccbillnoCriteria criteria) {
        log.debug("REST request to count Accbillnos by criteria: {}", criteria);
        return ResponseEntity.ok().body(accbillnoQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /accbillnos/:id} : get the "id" accbillno.
     *
     * @param id the id of the accbillnoDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the accbillnoDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/accbillnos/{id}")
    public ResponseEntity<AccbillnoDTO> getAccbillno(@PathVariable Long id) {
        log.debug("REST request to get Accbillno : {}", id);
        Optional<AccbillnoDTO> accbillnoDTO = accbillnoService.findOne(id);
        return ResponseUtil.wrapOrNotFound(accbillnoDTO);
    }

    /**
     * {@code DELETE  /accbillnos/:id} : delete the "id" accbillno.
     *
     * @param id the id of the accbillnoDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/accbillnos/{id}")
    public ResponseEntity<Void> deleteAccbillno(@PathVariable Long id) {
        log.debug("REST request to delete Accbillno : {}", id);
        accbillnoService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }

    /**
     * {@code SEARCH  /_search/accbillnos?query=:query} : search for the accbillno corresponding
     * to the query.
     *
     * @param query the query of the accbillno search.
     * @param pageable the pagination information.
     * @return the result of the search.
     */
    @GetMapping("/_search/accbillnos")
    public ResponseEntity<List<AccbillnoDTO>> searchAccbillnos(@RequestParam String query, Pageable pageable) {
        log.debug("REST request to search for a page of Accbillnos for query {}", query);
        Page<AccbillnoDTO> page = accbillnoService.search(query, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }
}
