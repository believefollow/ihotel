package ihotel.app.web.rest;

import static org.elasticsearch.index.query.QueryBuilders.*;

import ihotel.app.repository.CzCzl3Repository;
import ihotel.app.service.CzCzl3QueryService;
import ihotel.app.service.CzCzl3Service;
import ihotel.app.service.criteria.CzCzl3Criteria;
import ihotel.app.service.dto.CzCzl3DTO;
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
 * REST controller for managing {@link ihotel.app.domain.CzCzl3}.
 */
@RestController
@RequestMapping("/api")
public class CzCzl3Resource {

    private final Logger log = LoggerFactory.getLogger(CzCzl3Resource.class);

    private static final String ENTITY_NAME = "czCzl3";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final CzCzl3Service czCzl3Service;

    private final CzCzl3Repository czCzl3Repository;

    private final CzCzl3QueryService czCzl3QueryService;

    public CzCzl3Resource(CzCzl3Service czCzl3Service, CzCzl3Repository czCzl3Repository, CzCzl3QueryService czCzl3QueryService) {
        this.czCzl3Service = czCzl3Service;
        this.czCzl3Repository = czCzl3Repository;
        this.czCzl3QueryService = czCzl3QueryService;
    }

    /**
     * {@code POST  /cz-czl-3-s} : Create a new czCzl3.
     *
     * @param czCzl3DTO the czCzl3DTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new czCzl3DTO, or with status {@code 400 (Bad Request)} if the czCzl3 has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/cz-czl-3-s")
    public ResponseEntity<CzCzl3DTO> createCzCzl3(@Valid @RequestBody CzCzl3DTO czCzl3DTO) throws URISyntaxException {
        log.debug("REST request to save CzCzl3 : {}", czCzl3DTO);
        if (czCzl3DTO.getId() != null) {
            throw new BadRequestAlertException("A new czCzl3 cannot already have an ID", ENTITY_NAME, "idexists");
        }
        CzCzl3DTO result = czCzl3Service.save(czCzl3DTO);
        return ResponseEntity
            .created(new URI("/api/cz-czl-3-s/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /cz-czl-3-s/:id} : Updates an existing czCzl3.
     *
     * @param id the id of the czCzl3DTO to save.
     * @param czCzl3DTO the czCzl3DTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated czCzl3DTO,
     * or with status {@code 400 (Bad Request)} if the czCzl3DTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the czCzl3DTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/cz-czl-3-s/{id}")
    public ResponseEntity<CzCzl3DTO> updateCzCzl3(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody CzCzl3DTO czCzl3DTO
    ) throws URISyntaxException {
        log.debug("REST request to update CzCzl3 : {}, {}", id, czCzl3DTO);
        if (czCzl3DTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, czCzl3DTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!czCzl3Repository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        CzCzl3DTO result = czCzl3Service.save(czCzl3DTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, czCzl3DTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /cz-czl-3-s/:id} : Partial updates given fields of an existing czCzl3, field will ignore if it is null
     *
     * @param id the id of the czCzl3DTO to save.
     * @param czCzl3DTO the czCzl3DTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated czCzl3DTO,
     * or with status {@code 400 (Bad Request)} if the czCzl3DTO is not valid,
     * or with status {@code 404 (Not Found)} if the czCzl3DTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the czCzl3DTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/cz-czl-3-s/{id}", consumes = "application/merge-patch+json")
    public ResponseEntity<CzCzl3DTO> partialUpdateCzCzl3(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody CzCzl3DTO czCzl3DTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update CzCzl3 partially : {}, {}", id, czCzl3DTO);
        if (czCzl3DTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, czCzl3DTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!czCzl3Repository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<CzCzl3DTO> result = czCzl3Service.partialUpdate(czCzl3DTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, czCzl3DTO.getId().toString())
        );
    }

    /**
     * {@code GET  /cz-czl-3-s} : get all the czCzl3s.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of czCzl3s in body.
     */
    @GetMapping("/cz-czl-3-s")
    public ResponseEntity<List<CzCzl3DTO>> getAllCzCzl3s(CzCzl3Criteria criteria, Pageable pageable) {
        log.debug("REST request to get CzCzl3s by criteria: {}", criteria);
        Page<CzCzl3DTO> page = czCzl3QueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /cz-czl-3-s/count} : count all the czCzl3s.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/cz-czl-3-s/count")
    public ResponseEntity<Long> countCzCzl3s(CzCzl3Criteria criteria) {
        log.debug("REST request to count CzCzl3s by criteria: {}", criteria);
        return ResponseEntity.ok().body(czCzl3QueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /cz-czl-3-s/:id} : get the "id" czCzl3.
     *
     * @param id the id of the czCzl3DTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the czCzl3DTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/cz-czl-3-s/{id}")
    public ResponseEntity<CzCzl3DTO> getCzCzl3(@PathVariable Long id) {
        log.debug("REST request to get CzCzl3 : {}", id);
        Optional<CzCzl3DTO> czCzl3DTO = czCzl3Service.findOne(id);
        return ResponseUtil.wrapOrNotFound(czCzl3DTO);
    }

    /**
     * {@code DELETE  /cz-czl-3-s/:id} : delete the "id" czCzl3.
     *
     * @param id the id of the czCzl3DTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/cz-czl-3-s/{id}")
    public ResponseEntity<Void> deleteCzCzl3(@PathVariable Long id) {
        log.debug("REST request to delete CzCzl3 : {}", id);
        czCzl3Service.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }

    /**
     * {@code SEARCH  /_search/cz-czl-3-s?query=:query} : search for the czCzl3 corresponding
     * to the query.
     *
     * @param query the query of the czCzl3 search.
     * @param pageable the pagination information.
     * @return the result of the search.
     */
    @GetMapping("/_search/cz-czl-3-s")
    public ResponseEntity<List<CzCzl3DTO>> searchCzCzl3s(@RequestParam String query, Pageable pageable) {
        log.debug("REST request to search for a page of CzCzl3s for query {}", query);
        Page<CzCzl3DTO> page = czCzl3Service.search(query, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }
}
