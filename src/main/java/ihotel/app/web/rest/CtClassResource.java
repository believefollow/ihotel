package ihotel.app.web.rest;

import static org.elasticsearch.index.query.QueryBuilders.*;

import ihotel.app.repository.CtClassRepository;
import ihotel.app.service.CtClassQueryService;
import ihotel.app.service.CtClassService;
import ihotel.app.service.criteria.CtClassCriteria;
import ihotel.app.service.dto.CtClassDTO;
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
 * REST controller for managing {@link ihotel.app.domain.CtClass}.
 */
@RestController
@RequestMapping("/api")
public class CtClassResource {

    private final Logger log = LoggerFactory.getLogger(CtClassResource.class);

    private static final String ENTITY_NAME = "ctClass";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final CtClassService ctClassService;

    private final CtClassRepository ctClassRepository;

    private final CtClassQueryService ctClassQueryService;

    public CtClassResource(CtClassService ctClassService, CtClassRepository ctClassRepository, CtClassQueryService ctClassQueryService) {
        this.ctClassService = ctClassService;
        this.ctClassRepository = ctClassRepository;
        this.ctClassQueryService = ctClassQueryService;
    }

    /**
     * {@code POST  /ct-classes} : Create a new ctClass.
     *
     * @param ctClassDTO the ctClassDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new ctClassDTO, or with status {@code 400 (Bad Request)} if the ctClass has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/ct-classes")
    public ResponseEntity<CtClassDTO> createCtClass(@Valid @RequestBody CtClassDTO ctClassDTO) throws URISyntaxException {
        log.debug("REST request to save CtClass : {}", ctClassDTO);
        if (ctClassDTO.getId() != null) {
            throw new BadRequestAlertException("A new ctClass cannot already have an ID", ENTITY_NAME, "idexists");
        }
        CtClassDTO result = ctClassService.save(ctClassDTO);
        return ResponseEntity
            .created(new URI("/api/ct-classes/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /ct-classes/:id} : Updates an existing ctClass.
     *
     * @param id the id of the ctClassDTO to save.
     * @param ctClassDTO the ctClassDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated ctClassDTO,
     * or with status {@code 400 (Bad Request)} if the ctClassDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the ctClassDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/ct-classes/{id}")
    public ResponseEntity<CtClassDTO> updateCtClass(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody CtClassDTO ctClassDTO
    ) throws URISyntaxException {
        log.debug("REST request to update CtClass : {}, {}", id, ctClassDTO);
        if (ctClassDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, ctClassDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!ctClassRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        CtClassDTO result = ctClassService.save(ctClassDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, ctClassDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /ct-classes/:id} : Partial updates given fields of an existing ctClass, field will ignore if it is null
     *
     * @param id the id of the ctClassDTO to save.
     * @param ctClassDTO the ctClassDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated ctClassDTO,
     * or with status {@code 400 (Bad Request)} if the ctClassDTO is not valid,
     * or with status {@code 404 (Not Found)} if the ctClassDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the ctClassDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/ct-classes/{id}", consumes = "application/merge-patch+json")
    public ResponseEntity<CtClassDTO> partialUpdateCtClass(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody CtClassDTO ctClassDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update CtClass partially : {}, {}", id, ctClassDTO);
        if (ctClassDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, ctClassDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!ctClassRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<CtClassDTO> result = ctClassService.partialUpdate(ctClassDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, ctClassDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /ct-classes} : get all the ctClasses.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of ctClasses in body.
     */
    @GetMapping("/ct-classes")
    public ResponseEntity<List<CtClassDTO>> getAllCtClasses(CtClassCriteria criteria, Pageable pageable) {
        log.debug("REST request to get CtClasses by criteria: {}", criteria);
        Page<CtClassDTO> page = ctClassQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /ct-classes/count} : count all the ctClasses.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/ct-classes/count")
    public ResponseEntity<Long> countCtClasses(CtClassCriteria criteria) {
        log.debug("REST request to count CtClasses by criteria: {}", criteria);
        return ResponseEntity.ok().body(ctClassQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /ct-classes/:id} : get the "id" ctClass.
     *
     * @param id the id of the ctClassDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the ctClassDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/ct-classes/{id}")
    public ResponseEntity<CtClassDTO> getCtClass(@PathVariable Long id) {
        log.debug("REST request to get CtClass : {}", id);
        Optional<CtClassDTO> ctClassDTO = ctClassService.findOne(id);
        return ResponseUtil.wrapOrNotFound(ctClassDTO);
    }

    /**
     * {@code DELETE  /ct-classes/:id} : delete the "id" ctClass.
     *
     * @param id the id of the ctClassDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/ct-classes/{id}")
    public ResponseEntity<Void> deleteCtClass(@PathVariable Long id) {
        log.debug("REST request to delete CtClass : {}", id);
        ctClassService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }

    /**
     * {@code SEARCH  /_search/ct-classes?query=:query} : search for the ctClass corresponding
     * to the query.
     *
     * @param query the query of the ctClass search.
     * @param pageable the pagination information.
     * @return the result of the search.
     */
    @GetMapping("/_search/ct-classes")
    public ResponseEntity<List<CtClassDTO>> searchCtClasses(@RequestParam String query, Pageable pageable) {
        log.debug("REST request to search for a page of CtClasses for query {}", query);
        Page<CtClassDTO> page = ctClassService.search(query, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }
}
