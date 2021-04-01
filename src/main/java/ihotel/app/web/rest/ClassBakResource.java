package ihotel.app.web.rest;

import static org.elasticsearch.index.query.QueryBuilders.*;

import ihotel.app.repository.ClassBakRepository;
import ihotel.app.service.ClassBakQueryService;
import ihotel.app.service.ClassBakService;
import ihotel.app.service.criteria.ClassBakCriteria;
import ihotel.app.service.dto.ClassBakDTO;
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
 * REST controller for managing {@link ihotel.app.domain.ClassBak}.
 */
@RestController
@RequestMapping("/api")
public class ClassBakResource {

    private final Logger log = LoggerFactory.getLogger(ClassBakResource.class);

    private static final String ENTITY_NAME = "classBak";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ClassBakService classBakService;

    private final ClassBakRepository classBakRepository;

    private final ClassBakQueryService classBakQueryService;

    public ClassBakResource(
        ClassBakService classBakService,
        ClassBakRepository classBakRepository,
        ClassBakQueryService classBakQueryService
    ) {
        this.classBakService = classBakService;
        this.classBakRepository = classBakRepository;
        this.classBakQueryService = classBakQueryService;
    }

    /**
     * {@code POST  /class-baks} : Create a new classBak.
     *
     * @param classBakDTO the classBakDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new classBakDTO, or with status {@code 400 (Bad Request)} if the classBak has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/class-baks")
    public ResponseEntity<ClassBakDTO> createClassBak(@Valid @RequestBody ClassBakDTO classBakDTO) throws URISyntaxException {
        log.debug("REST request to save ClassBak : {}", classBakDTO);
        if (classBakDTO.getId() != null) {
            throw new BadRequestAlertException("A new classBak cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ClassBakDTO result = classBakService.save(classBakDTO);
        return ResponseEntity
            .created(new URI("/api/class-baks/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /class-baks/:id} : Updates an existing classBak.
     *
     * @param id the id of the classBakDTO to save.
     * @param classBakDTO the classBakDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated classBakDTO,
     * or with status {@code 400 (Bad Request)} if the classBakDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the classBakDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/class-baks/{id}")
    public ResponseEntity<ClassBakDTO> updateClassBak(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody ClassBakDTO classBakDTO
    ) throws URISyntaxException {
        log.debug("REST request to update ClassBak : {}, {}", id, classBakDTO);
        if (classBakDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, classBakDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!classBakRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        ClassBakDTO result = classBakService.save(classBakDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, classBakDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /class-baks/:id} : Partial updates given fields of an existing classBak, field will ignore if it is null
     *
     * @param id the id of the classBakDTO to save.
     * @param classBakDTO the classBakDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated classBakDTO,
     * or with status {@code 400 (Bad Request)} if the classBakDTO is not valid,
     * or with status {@code 404 (Not Found)} if the classBakDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the classBakDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/class-baks/{id}", consumes = "application/merge-patch+json")
    public ResponseEntity<ClassBakDTO> partialUpdateClassBak(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody ClassBakDTO classBakDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update ClassBak partially : {}, {}", id, classBakDTO);
        if (classBakDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, classBakDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!classBakRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<ClassBakDTO> result = classBakService.partialUpdate(classBakDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, classBakDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /class-baks} : get all the classBaks.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of classBaks in body.
     */
    @GetMapping("/class-baks")
    public ResponseEntity<List<ClassBakDTO>> getAllClassBaks(ClassBakCriteria criteria, Pageable pageable) {
        log.debug("REST request to get ClassBaks by criteria: {}", criteria);
        Page<ClassBakDTO> page = classBakQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /class-baks/count} : count all the classBaks.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/class-baks/count")
    public ResponseEntity<Long> countClassBaks(ClassBakCriteria criteria) {
        log.debug("REST request to count ClassBaks by criteria: {}", criteria);
        return ResponseEntity.ok().body(classBakQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /class-baks/:id} : get the "id" classBak.
     *
     * @param id the id of the classBakDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the classBakDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/class-baks/{id}")
    public ResponseEntity<ClassBakDTO> getClassBak(@PathVariable Long id) {
        log.debug("REST request to get ClassBak : {}", id);
        Optional<ClassBakDTO> classBakDTO = classBakService.findOne(id);
        return ResponseUtil.wrapOrNotFound(classBakDTO);
    }

    /**
     * {@code DELETE  /class-baks/:id} : delete the "id" classBak.
     *
     * @param id the id of the classBakDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/class-baks/{id}")
    public ResponseEntity<Void> deleteClassBak(@PathVariable Long id) {
        log.debug("REST request to delete ClassBak : {}", id);
        classBakService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }

    /**
     * {@code SEARCH  /_search/class-baks?query=:query} : search for the classBak corresponding
     * to the query.
     *
     * @param query the query of the classBak search.
     * @param pageable the pagination information.
     * @return the result of the search.
     */
    @GetMapping("/_search/class-baks")
    public ResponseEntity<List<ClassBakDTO>> searchClassBaks(@RequestParam String query, Pageable pageable) {
        log.debug("REST request to search for a page of ClassBaks for query {}", query);
        Page<ClassBakDTO> page = classBakService.search(query, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }
}
