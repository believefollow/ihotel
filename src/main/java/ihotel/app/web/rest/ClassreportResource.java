package ihotel.app.web.rest;

import static org.elasticsearch.index.query.QueryBuilders.*;

import ihotel.app.repository.ClassreportRepository;
import ihotel.app.service.ClassreportQueryService;
import ihotel.app.service.ClassreportService;
import ihotel.app.service.criteria.ClassreportCriteria;
import ihotel.app.service.dto.ClassreportDTO;
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
 * REST controller for managing {@link ihotel.app.domain.Classreport}.
 */
@RestController
@RequestMapping("/api")
public class ClassreportResource {

    private final Logger log = LoggerFactory.getLogger(ClassreportResource.class);

    private static final String ENTITY_NAME = "classreport";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ClassreportService classreportService;

    private final ClassreportRepository classreportRepository;

    private final ClassreportQueryService classreportQueryService;

    public ClassreportResource(
        ClassreportService classreportService,
        ClassreportRepository classreportRepository,
        ClassreportQueryService classreportQueryService
    ) {
        this.classreportService = classreportService;
        this.classreportRepository = classreportRepository;
        this.classreportQueryService = classreportQueryService;
    }

    /**
     * {@code POST  /classreports} : Create a new classreport.
     *
     * @param classreportDTO the classreportDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new classreportDTO, or with status {@code 400 (Bad Request)} if the classreport has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/classreports")
    public ResponseEntity<ClassreportDTO> createClassreport(@Valid @RequestBody ClassreportDTO classreportDTO) throws URISyntaxException {
        log.debug("REST request to save Classreport : {}", classreportDTO);
        if (classreportDTO.getId() != null) {
            throw new BadRequestAlertException("A new classreport cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ClassreportDTO result = classreportService.save(classreportDTO);
        return ResponseEntity
            .created(new URI("/api/classreports/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /classreports/:id} : Updates an existing classreport.
     *
     * @param id the id of the classreportDTO to save.
     * @param classreportDTO the classreportDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated classreportDTO,
     * or with status {@code 400 (Bad Request)} if the classreportDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the classreportDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/classreports/{id}")
    public ResponseEntity<ClassreportDTO> updateClassreport(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody ClassreportDTO classreportDTO
    ) throws URISyntaxException {
        log.debug("REST request to update Classreport : {}, {}", id, classreportDTO);
        if (classreportDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, classreportDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!classreportRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        ClassreportDTO result = classreportService.save(classreportDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, classreportDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /classreports/:id} : Partial updates given fields of an existing classreport, field will ignore if it is null
     *
     * @param id the id of the classreportDTO to save.
     * @param classreportDTO the classreportDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated classreportDTO,
     * or with status {@code 400 (Bad Request)} if the classreportDTO is not valid,
     * or with status {@code 404 (Not Found)} if the classreportDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the classreportDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/classreports/{id}", consumes = "application/merge-patch+json")
    public ResponseEntity<ClassreportDTO> partialUpdateClassreport(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody ClassreportDTO classreportDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update Classreport partially : {}, {}", id, classreportDTO);
        if (classreportDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, classreportDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!classreportRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<ClassreportDTO> result = classreportService.partialUpdate(classreportDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, classreportDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /classreports} : get all the classreports.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of classreports in body.
     */
    @GetMapping("/classreports")
    public ResponseEntity<List<ClassreportDTO>> getAllClassreports(ClassreportCriteria criteria, Pageable pageable) {
        log.debug("REST request to get Classreports by criteria: {}", criteria);
        Page<ClassreportDTO> page = classreportQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /classreports/count} : count all the classreports.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/classreports/count")
    public ResponseEntity<Long> countClassreports(ClassreportCriteria criteria) {
        log.debug("REST request to count Classreports by criteria: {}", criteria);
        return ResponseEntity.ok().body(classreportQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /classreports/:id} : get the "id" classreport.
     *
     * @param id the id of the classreportDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the classreportDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/classreports/{id}")
    public ResponseEntity<ClassreportDTO> getClassreport(@PathVariable Long id) {
        log.debug("REST request to get Classreport : {}", id);
        Optional<ClassreportDTO> classreportDTO = classreportService.findOne(id);
        return ResponseUtil.wrapOrNotFound(classreportDTO);
    }

    /**
     * {@code DELETE  /classreports/:id} : delete the "id" classreport.
     *
     * @param id the id of the classreportDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/classreports/{id}")
    public ResponseEntity<Void> deleteClassreport(@PathVariable Long id) {
        log.debug("REST request to delete Classreport : {}", id);
        classreportService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }

    /**
     * {@code SEARCH  /_search/classreports?query=:query} : search for the classreport corresponding
     * to the query.
     *
     * @param query the query of the classreport search.
     * @param pageable the pagination information.
     * @return the result of the search.
     */
    @GetMapping("/_search/classreports")
    public ResponseEntity<List<ClassreportDTO>> searchClassreports(@RequestParam String query, Pageable pageable) {
        log.debug("REST request to search for a page of Classreports for query {}", query);
        Page<ClassreportDTO> page = classreportService.search(query, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }
}
