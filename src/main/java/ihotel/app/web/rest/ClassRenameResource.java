package ihotel.app.web.rest;

import static org.elasticsearch.index.query.QueryBuilders.*;

import ihotel.app.repository.ClassRenameRepository;
import ihotel.app.service.ClassRenameQueryService;
import ihotel.app.service.ClassRenameService;
import ihotel.app.service.criteria.ClassRenameCriteria;
import ihotel.app.service.dto.ClassRenameDTO;
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
 * REST controller for managing {@link ihotel.app.domain.ClassRename}.
 */
@RestController
@RequestMapping("/api")
public class ClassRenameResource {

    private final Logger log = LoggerFactory.getLogger(ClassRenameResource.class);

    private static final String ENTITY_NAME = "classRename";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ClassRenameService classRenameService;

    private final ClassRenameRepository classRenameRepository;

    private final ClassRenameQueryService classRenameQueryService;

    public ClassRenameResource(
        ClassRenameService classRenameService,
        ClassRenameRepository classRenameRepository,
        ClassRenameQueryService classRenameQueryService
    ) {
        this.classRenameService = classRenameService;
        this.classRenameRepository = classRenameRepository;
        this.classRenameQueryService = classRenameQueryService;
    }

    /**
     * {@code POST  /class-renames} : Create a new classRename.
     *
     * @param classRenameDTO the classRenameDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new classRenameDTO, or with status {@code 400 (Bad Request)} if the classRename has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/class-renames")
    public ResponseEntity<ClassRenameDTO> createClassRename(@Valid @RequestBody ClassRenameDTO classRenameDTO) throws URISyntaxException {
        log.debug("REST request to save ClassRename : {}", classRenameDTO);
        if (classRenameDTO.getId() != null) {
            throw new BadRequestAlertException("A new classRename cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ClassRenameDTO result = classRenameService.save(classRenameDTO);
        return ResponseEntity
            .created(new URI("/api/class-renames/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /class-renames/:id} : Updates an existing classRename.
     *
     * @param id the id of the classRenameDTO to save.
     * @param classRenameDTO the classRenameDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated classRenameDTO,
     * or with status {@code 400 (Bad Request)} if the classRenameDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the classRenameDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/class-renames/{id}")
    public ResponseEntity<ClassRenameDTO> updateClassRename(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody ClassRenameDTO classRenameDTO
    ) throws URISyntaxException {
        log.debug("REST request to update ClassRename : {}, {}", id, classRenameDTO);
        if (classRenameDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, classRenameDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!classRenameRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        ClassRenameDTO result = classRenameService.save(classRenameDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, classRenameDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /class-renames/:id} : Partial updates given fields of an existing classRename, field will ignore if it is null
     *
     * @param id the id of the classRenameDTO to save.
     * @param classRenameDTO the classRenameDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated classRenameDTO,
     * or with status {@code 400 (Bad Request)} if the classRenameDTO is not valid,
     * or with status {@code 404 (Not Found)} if the classRenameDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the classRenameDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/class-renames/{id}", consumes = "application/merge-patch+json")
    public ResponseEntity<ClassRenameDTO> partialUpdateClassRename(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody ClassRenameDTO classRenameDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update ClassRename partially : {}, {}", id, classRenameDTO);
        if (classRenameDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, classRenameDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!classRenameRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<ClassRenameDTO> result = classRenameService.partialUpdate(classRenameDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, classRenameDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /class-renames} : get all the classRenames.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of classRenames in body.
     */
    @GetMapping("/class-renames")
    public ResponseEntity<List<ClassRenameDTO>> getAllClassRenames(ClassRenameCriteria criteria, Pageable pageable) {
        log.debug("REST request to get ClassRenames by criteria: {}", criteria);
        Page<ClassRenameDTO> page = classRenameQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /class-renames/count} : count all the classRenames.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/class-renames/count")
    public ResponseEntity<Long> countClassRenames(ClassRenameCriteria criteria) {
        log.debug("REST request to count ClassRenames by criteria: {}", criteria);
        return ResponseEntity.ok().body(classRenameQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /class-renames/:id} : get the "id" classRename.
     *
     * @param id the id of the classRenameDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the classRenameDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/class-renames/{id}")
    public ResponseEntity<ClassRenameDTO> getClassRename(@PathVariable Long id) {
        log.debug("REST request to get ClassRename : {}", id);
        Optional<ClassRenameDTO> classRenameDTO = classRenameService.findOne(id);
        return ResponseUtil.wrapOrNotFound(classRenameDTO);
    }

    /**
     * {@code DELETE  /class-renames/:id} : delete the "id" classRename.
     *
     * @param id the id of the classRenameDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/class-renames/{id}")
    public ResponseEntity<Void> deleteClassRename(@PathVariable Long id) {
        log.debug("REST request to delete ClassRename : {}", id);
        classRenameService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }

    /**
     * {@code SEARCH  /_search/class-renames?query=:query} : search for the classRename corresponding
     * to the query.
     *
     * @param query the query of the classRename search.
     * @param pageable the pagination information.
     * @return the result of the search.
     */
    @GetMapping("/_search/class-renames")
    public ResponseEntity<List<ClassRenameDTO>> searchClassRenames(@RequestParam String query, Pageable pageable) {
        log.debug("REST request to search for a page of ClassRenames for query {}", query);
        Page<ClassRenameDTO> page = classRenameService.search(query, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }
}
