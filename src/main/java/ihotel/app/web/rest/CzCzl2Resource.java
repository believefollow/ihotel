package ihotel.app.web.rest;

import static org.elasticsearch.index.query.QueryBuilders.*;

import ihotel.app.repository.CzCzl2Repository;
import ihotel.app.service.CzCzl2QueryService;
import ihotel.app.service.CzCzl2Service;
import ihotel.app.service.criteria.CzCzl2Criteria;
import ihotel.app.service.dto.CzCzl2DTO;
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
 * REST controller for managing {@link ihotel.app.domain.CzCzl2}.
 */
@RestController
@RequestMapping("/api")
public class CzCzl2Resource {

    private final Logger log = LoggerFactory.getLogger(CzCzl2Resource.class);

    private static final String ENTITY_NAME = "czCzl2";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final CzCzl2Service czCzl2Service;

    private final CzCzl2Repository czCzl2Repository;

    private final CzCzl2QueryService czCzl2QueryService;

    public CzCzl2Resource(CzCzl2Service czCzl2Service, CzCzl2Repository czCzl2Repository, CzCzl2QueryService czCzl2QueryService) {
        this.czCzl2Service = czCzl2Service;
        this.czCzl2Repository = czCzl2Repository;
        this.czCzl2QueryService = czCzl2QueryService;
    }

    /**
     * {@code POST  /cz-czl-2-s} : Create a new czCzl2.
     *
     * @param czCzl2DTO the czCzl2DTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new czCzl2DTO, or with status {@code 400 (Bad Request)} if the czCzl2 has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/cz-czl-2-s")
    public ResponseEntity<CzCzl2DTO> createCzCzl2(@Valid @RequestBody CzCzl2DTO czCzl2DTO) throws URISyntaxException {
        log.debug("REST request to save CzCzl2 : {}", czCzl2DTO);
        if (czCzl2DTO.getId() != null) {
            throw new BadRequestAlertException("A new czCzl2 cannot already have an ID", ENTITY_NAME, "idexists");
        }
        CzCzl2DTO result = czCzl2Service.save(czCzl2DTO);
        return ResponseEntity
            .created(new URI("/api/cz-czl-2-s/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /cz-czl-2-s/:id} : Updates an existing czCzl2.
     *
     * @param id the id of the czCzl2DTO to save.
     * @param czCzl2DTO the czCzl2DTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated czCzl2DTO,
     * or with status {@code 400 (Bad Request)} if the czCzl2DTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the czCzl2DTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/cz-czl-2-s/{id}")
    public ResponseEntity<CzCzl2DTO> updateCzCzl2(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody CzCzl2DTO czCzl2DTO
    ) throws URISyntaxException {
        log.debug("REST request to update CzCzl2 : {}, {}", id, czCzl2DTO);
        if (czCzl2DTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, czCzl2DTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!czCzl2Repository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        CzCzl2DTO result = czCzl2Service.save(czCzl2DTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, czCzl2DTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /cz-czl-2-s/:id} : Partial updates given fields of an existing czCzl2, field will ignore if it is null
     *
     * @param id the id of the czCzl2DTO to save.
     * @param czCzl2DTO the czCzl2DTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated czCzl2DTO,
     * or with status {@code 400 (Bad Request)} if the czCzl2DTO is not valid,
     * or with status {@code 404 (Not Found)} if the czCzl2DTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the czCzl2DTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/cz-czl-2-s/{id}", consumes = "application/merge-patch+json")
    public ResponseEntity<CzCzl2DTO> partialUpdateCzCzl2(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody CzCzl2DTO czCzl2DTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update CzCzl2 partially : {}, {}", id, czCzl2DTO);
        if (czCzl2DTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, czCzl2DTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!czCzl2Repository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<CzCzl2DTO> result = czCzl2Service.partialUpdate(czCzl2DTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, czCzl2DTO.getId().toString())
        );
    }

    /**
     * {@code GET  /cz-czl-2-s} : get all the czCzl2s.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of czCzl2s in body.
     */
    @GetMapping("/cz-czl-2-s")
    public ResponseEntity<List<CzCzl2DTO>> getAllCzCzl2s(CzCzl2Criteria criteria, Pageable pageable) {
        log.debug("REST request to get CzCzl2s by criteria: {}", criteria);
        Page<CzCzl2DTO> page = czCzl2QueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /cz-czl-2-s/count} : count all the czCzl2s.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/cz-czl-2-s/count")
    public ResponseEntity<Long> countCzCzl2s(CzCzl2Criteria criteria) {
        log.debug("REST request to count CzCzl2s by criteria: {}", criteria);
        return ResponseEntity.ok().body(czCzl2QueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /cz-czl-2-s/:id} : get the "id" czCzl2.
     *
     * @param id the id of the czCzl2DTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the czCzl2DTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/cz-czl-2-s/{id}")
    public ResponseEntity<CzCzl2DTO> getCzCzl2(@PathVariable Long id) {
        log.debug("REST request to get CzCzl2 : {}", id);
        Optional<CzCzl2DTO> czCzl2DTO = czCzl2Service.findOne(id);
        return ResponseUtil.wrapOrNotFound(czCzl2DTO);
    }

    /**
     * {@code DELETE  /cz-czl-2-s/:id} : delete the "id" czCzl2.
     *
     * @param id the id of the czCzl2DTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/cz-czl-2-s/{id}")
    public ResponseEntity<Void> deleteCzCzl2(@PathVariable Long id) {
        log.debug("REST request to delete CzCzl2 : {}", id);
        czCzl2Service.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }

    /**
     * {@code SEARCH  /_search/cz-czl-2-s?query=:query} : search for the czCzl2 corresponding
     * to the query.
     *
     * @param query the query of the czCzl2 search.
     * @param pageable the pagination information.
     * @return the result of the search.
     */
    @GetMapping("/_search/cz-czl-2-s")
    public ResponseEntity<List<CzCzl2DTO>> searchCzCzl2s(@RequestParam String query, Pageable pageable) {
        log.debug("REST request to search for a page of CzCzl2s for query {}", query);
        Page<CzCzl2DTO> page = czCzl2Service.search(query, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }
}
