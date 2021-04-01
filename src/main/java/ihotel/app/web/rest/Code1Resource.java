package ihotel.app.web.rest;

import static org.elasticsearch.index.query.QueryBuilders.*;

import ihotel.app.repository.Code1Repository;
import ihotel.app.service.Code1QueryService;
import ihotel.app.service.Code1Service;
import ihotel.app.service.criteria.Code1Criteria;
import ihotel.app.service.dto.Code1DTO;
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
 * REST controller for managing {@link ihotel.app.domain.Code1}.
 */
@RestController
@RequestMapping("/api")
public class Code1Resource {

    private final Logger log = LoggerFactory.getLogger(Code1Resource.class);

    private static final String ENTITY_NAME = "code1";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final Code1Service code1Service;

    private final Code1Repository code1Repository;

    private final Code1QueryService code1QueryService;

    public Code1Resource(Code1Service code1Service, Code1Repository code1Repository, Code1QueryService code1QueryService) {
        this.code1Service = code1Service;
        this.code1Repository = code1Repository;
        this.code1QueryService = code1QueryService;
    }

    /**
     * {@code POST  /code-1-s} : Create a new code1.
     *
     * @param code1DTO the code1DTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new code1DTO, or with status {@code 400 (Bad Request)} if the code1 has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/code-1-s")
    public ResponseEntity<Code1DTO> createCode1(@Valid @RequestBody Code1DTO code1DTO) throws URISyntaxException {
        log.debug("REST request to save Code1 : {}", code1DTO);
        if (code1DTO.getId() != null) {
            throw new BadRequestAlertException("A new code1 cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Code1DTO result = code1Service.save(code1DTO);
        return ResponseEntity
            .created(new URI("/api/code-1-s/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /code-1-s/:id} : Updates an existing code1.
     *
     * @param id the id of the code1DTO to save.
     * @param code1DTO the code1DTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated code1DTO,
     * or with status {@code 400 (Bad Request)} if the code1DTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the code1DTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/code-1-s/{id}")
    public ResponseEntity<Code1DTO> updateCode1(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody Code1DTO code1DTO
    ) throws URISyntaxException {
        log.debug("REST request to update Code1 : {}, {}", id, code1DTO);
        if (code1DTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, code1DTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!code1Repository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Code1DTO result = code1Service.save(code1DTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, code1DTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /code-1-s/:id} : Partial updates given fields of an existing code1, field will ignore if it is null
     *
     * @param id the id of the code1DTO to save.
     * @param code1DTO the code1DTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated code1DTO,
     * or with status {@code 400 (Bad Request)} if the code1DTO is not valid,
     * or with status {@code 404 (Not Found)} if the code1DTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the code1DTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/code-1-s/{id}", consumes = "application/merge-patch+json")
    public ResponseEntity<Code1DTO> partialUpdateCode1(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody Code1DTO code1DTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update Code1 partially : {}, {}", id, code1DTO);
        if (code1DTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, code1DTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!code1Repository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<Code1DTO> result = code1Service.partialUpdate(code1DTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, code1DTO.getId().toString())
        );
    }

    /**
     * {@code GET  /code-1-s} : get all the code1s.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of code1s in body.
     */
    @GetMapping("/code-1-s")
    public ResponseEntity<List<Code1DTO>> getAllCode1s(Code1Criteria criteria, Pageable pageable) {
        log.debug("REST request to get Code1s by criteria: {}", criteria);
        Page<Code1DTO> page = code1QueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /code-1-s/count} : count all the code1s.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/code-1-s/count")
    public ResponseEntity<Long> countCode1s(Code1Criteria criteria) {
        log.debug("REST request to count Code1s by criteria: {}", criteria);
        return ResponseEntity.ok().body(code1QueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /code-1-s/:id} : get the "id" code1.
     *
     * @param id the id of the code1DTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the code1DTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/code-1-s/{id}")
    public ResponseEntity<Code1DTO> getCode1(@PathVariable Long id) {
        log.debug("REST request to get Code1 : {}", id);
        Optional<Code1DTO> code1DTO = code1Service.findOne(id);
        return ResponseUtil.wrapOrNotFound(code1DTO);
    }

    /**
     * {@code DELETE  /code-1-s/:id} : delete the "id" code1.
     *
     * @param id the id of the code1DTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/code-1-s/{id}")
    public ResponseEntity<Void> deleteCode1(@PathVariable Long id) {
        log.debug("REST request to delete Code1 : {}", id);
        code1Service.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }

    /**
     * {@code SEARCH  /_search/code-1-s?query=:query} : search for the code1 corresponding
     * to the query.
     *
     * @param query the query of the code1 search.
     * @param pageable the pagination information.
     * @return the result of the search.
     */
    @GetMapping("/_search/code-1-s")
    public ResponseEntity<List<Code1DTO>> searchCode1s(@RequestParam String query, Pageable pageable) {
        log.debug("REST request to search for a page of Code1s for query {}", query);
        Page<Code1DTO> page = code1Service.search(query, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }
}
