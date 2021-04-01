package ihotel.app.web.rest;

import static org.elasticsearch.index.query.QueryBuilders.*;

import ihotel.app.repository.ClassreportRoomRepository;
import ihotel.app.service.ClassreportRoomQueryService;
import ihotel.app.service.ClassreportRoomService;
import ihotel.app.service.criteria.ClassreportRoomCriteria;
import ihotel.app.service.dto.ClassreportRoomDTO;
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
 * REST controller for managing {@link ihotel.app.domain.ClassreportRoom}.
 */
@RestController
@RequestMapping("/api")
public class ClassreportRoomResource {

    private final Logger log = LoggerFactory.getLogger(ClassreportRoomResource.class);

    private static final String ENTITY_NAME = "classreportRoom";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ClassreportRoomService classreportRoomService;

    private final ClassreportRoomRepository classreportRoomRepository;

    private final ClassreportRoomQueryService classreportRoomQueryService;

    public ClassreportRoomResource(
        ClassreportRoomService classreportRoomService,
        ClassreportRoomRepository classreportRoomRepository,
        ClassreportRoomQueryService classreportRoomQueryService
    ) {
        this.classreportRoomService = classreportRoomService;
        this.classreportRoomRepository = classreportRoomRepository;
        this.classreportRoomQueryService = classreportRoomQueryService;
    }

    /**
     * {@code POST  /classreport-rooms} : Create a new classreportRoom.
     *
     * @param classreportRoomDTO the classreportRoomDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new classreportRoomDTO, or with status {@code 400 (Bad Request)} if the classreportRoom has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/classreport-rooms")
    public ResponseEntity<ClassreportRoomDTO> createClassreportRoom(@Valid @RequestBody ClassreportRoomDTO classreportRoomDTO)
        throws URISyntaxException {
        log.debug("REST request to save ClassreportRoom : {}", classreportRoomDTO);
        if (classreportRoomDTO.getId() != null) {
            throw new BadRequestAlertException("A new classreportRoom cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ClassreportRoomDTO result = classreportRoomService.save(classreportRoomDTO);
        return ResponseEntity
            .created(new URI("/api/classreport-rooms/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /classreport-rooms/:id} : Updates an existing classreportRoom.
     *
     * @param id the id of the classreportRoomDTO to save.
     * @param classreportRoomDTO the classreportRoomDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated classreportRoomDTO,
     * or with status {@code 400 (Bad Request)} if the classreportRoomDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the classreportRoomDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/classreport-rooms/{id}")
    public ResponseEntity<ClassreportRoomDTO> updateClassreportRoom(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody ClassreportRoomDTO classreportRoomDTO
    ) throws URISyntaxException {
        log.debug("REST request to update ClassreportRoom : {}, {}", id, classreportRoomDTO);
        if (classreportRoomDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, classreportRoomDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!classreportRoomRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        ClassreportRoomDTO result = classreportRoomService.save(classreportRoomDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, classreportRoomDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /classreport-rooms/:id} : Partial updates given fields of an existing classreportRoom, field will ignore if it is null
     *
     * @param id the id of the classreportRoomDTO to save.
     * @param classreportRoomDTO the classreportRoomDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated classreportRoomDTO,
     * or with status {@code 400 (Bad Request)} if the classreportRoomDTO is not valid,
     * or with status {@code 404 (Not Found)} if the classreportRoomDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the classreportRoomDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/classreport-rooms/{id}", consumes = "application/merge-patch+json")
    public ResponseEntity<ClassreportRoomDTO> partialUpdateClassreportRoom(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody ClassreportRoomDTO classreportRoomDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update ClassreportRoom partially : {}, {}", id, classreportRoomDTO);
        if (classreportRoomDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, classreportRoomDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!classreportRoomRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<ClassreportRoomDTO> result = classreportRoomService.partialUpdate(classreportRoomDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, classreportRoomDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /classreport-rooms} : get all the classreportRooms.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of classreportRooms in body.
     */
    @GetMapping("/classreport-rooms")
    public ResponseEntity<List<ClassreportRoomDTO>> getAllClassreportRooms(ClassreportRoomCriteria criteria, Pageable pageable) {
        log.debug("REST request to get ClassreportRooms by criteria: {}", criteria);
        Page<ClassreportRoomDTO> page = classreportRoomQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /classreport-rooms/count} : count all the classreportRooms.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/classreport-rooms/count")
    public ResponseEntity<Long> countClassreportRooms(ClassreportRoomCriteria criteria) {
        log.debug("REST request to count ClassreportRooms by criteria: {}", criteria);
        return ResponseEntity.ok().body(classreportRoomQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /classreport-rooms/:id} : get the "id" classreportRoom.
     *
     * @param id the id of the classreportRoomDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the classreportRoomDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/classreport-rooms/{id}")
    public ResponseEntity<ClassreportRoomDTO> getClassreportRoom(@PathVariable Long id) {
        log.debug("REST request to get ClassreportRoom : {}", id);
        Optional<ClassreportRoomDTO> classreportRoomDTO = classreportRoomService.findOne(id);
        return ResponseUtil.wrapOrNotFound(classreportRoomDTO);
    }

    /**
     * {@code DELETE  /classreport-rooms/:id} : delete the "id" classreportRoom.
     *
     * @param id the id of the classreportRoomDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/classreport-rooms/{id}")
    public ResponseEntity<Void> deleteClassreportRoom(@PathVariable Long id) {
        log.debug("REST request to delete ClassreportRoom : {}", id);
        classreportRoomService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }

    /**
     * {@code SEARCH  /_search/classreport-rooms?query=:query} : search for the classreportRoom corresponding
     * to the query.
     *
     * @param query the query of the classreportRoom search.
     * @param pageable the pagination information.
     * @return the result of the search.
     */
    @GetMapping("/_search/classreport-rooms")
    public ResponseEntity<List<ClassreportRoomDTO>> searchClassreportRooms(@RequestParam String query, Pageable pageable) {
        log.debug("REST request to search for a page of ClassreportRooms for query {}", query);
        Page<ClassreportRoomDTO> page = classreportRoomService.search(query, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }
}
