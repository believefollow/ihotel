package ihotel.app.web.rest;

import static org.elasticsearch.index.query.QueryBuilders.*;

import ihotel.app.repository.AccRepository;
import ihotel.app.service.AccQueryService;
import ihotel.app.service.AccService;
import ihotel.app.service.criteria.AccCriteria;
import ihotel.app.service.dto.AccDTO;
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
 * REST controller for managing {@link ihotel.app.domain.Acc}.
 */
@RestController
@RequestMapping("/api")
public class AccResource {

    private final Logger log = LoggerFactory.getLogger(AccResource.class);

    private static final String ENTITY_NAME = "acc";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final AccService accService;

    private final AccRepository accRepository;

    private final AccQueryService accQueryService;

    public AccResource(AccService accService, AccRepository accRepository, AccQueryService accQueryService) {
        this.accService = accService;
        this.accRepository = accRepository;
        this.accQueryService = accQueryService;
    }

    /**
     * {@code POST  /accs} : Create a new acc.
     *
     * @param accDTO the accDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new accDTO, or with status {@code 400 (Bad Request)} if the acc has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/accs")
    public ResponseEntity<AccDTO> createAcc(@Valid @RequestBody AccDTO accDTO) throws URISyntaxException {
        log.debug("REST request to save Acc : {}", accDTO);
        if (accDTO.getId() != null) {
            throw new BadRequestAlertException("A new acc cannot already have an ID", ENTITY_NAME, "idexists");
        }
        AccDTO result = accService.save(accDTO);
        return ResponseEntity
            .created(new URI("/api/accs/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /accs/:id} : Updates an existing acc.
     *
     * @param id the id of the accDTO to save.
     * @param accDTO the accDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated accDTO,
     * or with status {@code 400 (Bad Request)} if the accDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the accDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/accs/{id}")
    public ResponseEntity<AccDTO> updateAcc(@PathVariable(value = "id", required = false) final Long id, @Valid @RequestBody AccDTO accDTO)
        throws URISyntaxException {
        log.debug("REST request to update Acc : {}, {}", id, accDTO);
        if (accDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, accDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!accRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        AccDTO result = accService.save(accDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, accDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /accs/:id} : Partial updates given fields of an existing acc, field will ignore if it is null
     *
     * @param id the id of the accDTO to save.
     * @param accDTO the accDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated accDTO,
     * or with status {@code 400 (Bad Request)} if the accDTO is not valid,
     * or with status {@code 404 (Not Found)} if the accDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the accDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/accs/{id}", consumes = "application/merge-patch+json")
    public ResponseEntity<AccDTO> partialUpdateAcc(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody AccDTO accDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update Acc partially : {}, {}", id, accDTO);
        if (accDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, accDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!accRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<AccDTO> result = accService.partialUpdate(accDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, accDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /accs} : get all the accs.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of accs in body.
     */
    @GetMapping("/accs")
    public ResponseEntity<List<AccDTO>> getAllAccs(AccCriteria criteria, Pageable pageable) {
        log.debug("REST request to get Accs by criteria: {}", criteria);
        Page<AccDTO> page = accQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /accs/count} : count all the accs.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/accs/count")
    public ResponseEntity<Long> countAccs(AccCriteria criteria) {
        log.debug("REST request to count Accs by criteria: {}", criteria);
        return ResponseEntity.ok().body(accQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /accs/:id} : get the "id" acc.
     *
     * @param id the id of the accDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the accDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/accs/{id}")
    public ResponseEntity<AccDTO> getAcc(@PathVariable Long id) {
        log.debug("REST request to get Acc : {}", id);
        Optional<AccDTO> accDTO = accService.findOne(id);
        return ResponseUtil.wrapOrNotFound(accDTO);
    }

    /**
     * {@code DELETE  /accs/:id} : delete the "id" acc.
     *
     * @param id the id of the accDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/accs/{id}")
    public ResponseEntity<Void> deleteAcc(@PathVariable Long id) {
        log.debug("REST request to delete Acc : {}", id);
        accService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }

    /**
     * {@code SEARCH  /_search/accs?query=:query} : search for the acc corresponding
     * to the query.
     *
     * @param query the query of the acc search.
     * @param pageable the pagination information.
     * @return the result of the search.
     */
    @GetMapping("/_search/accs")
    public ResponseEntity<List<AccDTO>> searchAccs(@RequestParam String query, Pageable pageable) {
        log.debug("REST request to search for a page of Accs for query {}", query);
        Page<AccDTO> page = accService.search(query, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }
}
