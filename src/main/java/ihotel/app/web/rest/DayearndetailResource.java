package ihotel.app.web.rest;

import static org.elasticsearch.index.query.QueryBuilders.*;

import ihotel.app.repository.DayearndetailRepository;
import ihotel.app.service.DayearndetailQueryService;
import ihotel.app.service.DayearndetailService;
import ihotel.app.service.criteria.DayearndetailCriteria;
import ihotel.app.service.dto.DayearndetailDTO;
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
 * REST controller for managing {@link ihotel.app.domain.Dayearndetail}.
 */
@RestController
@RequestMapping("/api")
public class DayearndetailResource {

    private final Logger log = LoggerFactory.getLogger(DayearndetailResource.class);

    private static final String ENTITY_NAME = "dayearndetail";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final DayearndetailService dayearndetailService;

    private final DayearndetailRepository dayearndetailRepository;

    private final DayearndetailQueryService dayearndetailQueryService;

    public DayearndetailResource(
        DayearndetailService dayearndetailService,
        DayearndetailRepository dayearndetailRepository,
        DayearndetailQueryService dayearndetailQueryService
    ) {
        this.dayearndetailService = dayearndetailService;
        this.dayearndetailRepository = dayearndetailRepository;
        this.dayearndetailQueryService = dayearndetailQueryService;
    }

    /**
     * {@code POST  /dayearndetails} : Create a new dayearndetail.
     *
     * @param dayearndetailDTO the dayearndetailDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new dayearndetailDTO, or with status {@code 400 (Bad Request)} if the dayearndetail has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/dayearndetails")
    public ResponseEntity<DayearndetailDTO> createDayearndetail(@Valid @RequestBody DayearndetailDTO dayearndetailDTO)
        throws URISyntaxException {
        log.debug("REST request to save Dayearndetail : {}", dayearndetailDTO);
        if (dayearndetailDTO.getId() != null) {
            throw new BadRequestAlertException("A new dayearndetail cannot already have an ID", ENTITY_NAME, "idexists");
        }
        DayearndetailDTO result = dayearndetailService.save(dayearndetailDTO);
        return ResponseEntity
            .created(new URI("/api/dayearndetails/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /dayearndetails/:id} : Updates an existing dayearndetail.
     *
     * @param id the id of the dayearndetailDTO to save.
     * @param dayearndetailDTO the dayearndetailDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated dayearndetailDTO,
     * or with status {@code 400 (Bad Request)} if the dayearndetailDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the dayearndetailDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/dayearndetails/{id}")
    public ResponseEntity<DayearndetailDTO> updateDayearndetail(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody DayearndetailDTO dayearndetailDTO
    ) throws URISyntaxException {
        log.debug("REST request to update Dayearndetail : {}, {}", id, dayearndetailDTO);
        if (dayearndetailDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, dayearndetailDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!dayearndetailRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        DayearndetailDTO result = dayearndetailService.save(dayearndetailDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, dayearndetailDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /dayearndetails/:id} : Partial updates given fields of an existing dayearndetail, field will ignore if it is null
     *
     * @param id the id of the dayearndetailDTO to save.
     * @param dayearndetailDTO the dayearndetailDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated dayearndetailDTO,
     * or with status {@code 400 (Bad Request)} if the dayearndetailDTO is not valid,
     * or with status {@code 404 (Not Found)} if the dayearndetailDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the dayearndetailDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/dayearndetails/{id}", consumes = "application/merge-patch+json")
    public ResponseEntity<DayearndetailDTO> partialUpdateDayearndetail(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody DayearndetailDTO dayearndetailDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update Dayearndetail partially : {}, {}", id, dayearndetailDTO);
        if (dayearndetailDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, dayearndetailDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!dayearndetailRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<DayearndetailDTO> result = dayearndetailService.partialUpdate(dayearndetailDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, dayearndetailDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /dayearndetails} : get all the dayearndetails.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of dayearndetails in body.
     */
    @GetMapping("/dayearndetails")
    public ResponseEntity<List<DayearndetailDTO>> getAllDayearndetails(DayearndetailCriteria criteria, Pageable pageable) {
        log.debug("REST request to get Dayearndetails by criteria: {}", criteria);
        Page<DayearndetailDTO> page = dayearndetailQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /dayearndetails/count} : count all the dayearndetails.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/dayearndetails/count")
    public ResponseEntity<Long> countDayearndetails(DayearndetailCriteria criteria) {
        log.debug("REST request to count Dayearndetails by criteria: {}", criteria);
        return ResponseEntity.ok().body(dayearndetailQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /dayearndetails/:id} : get the "id" dayearndetail.
     *
     * @param id the id of the dayearndetailDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the dayearndetailDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/dayearndetails/{id}")
    public ResponseEntity<DayearndetailDTO> getDayearndetail(@PathVariable Long id) {
        log.debug("REST request to get Dayearndetail : {}", id);
        Optional<DayearndetailDTO> dayearndetailDTO = dayearndetailService.findOne(id);
        return ResponseUtil.wrapOrNotFound(dayearndetailDTO);
    }

    /**
     * {@code DELETE  /dayearndetails/:id} : delete the "id" dayearndetail.
     *
     * @param id the id of the dayearndetailDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/dayearndetails/{id}")
    public ResponseEntity<Void> deleteDayearndetail(@PathVariable Long id) {
        log.debug("REST request to delete Dayearndetail : {}", id);
        dayearndetailService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }

    /**
     * {@code SEARCH  /_search/dayearndetails?query=:query} : search for the dayearndetail corresponding
     * to the query.
     *
     * @param query the query of the dayearndetail search.
     * @param pageable the pagination information.
     * @return the result of the search.
     */
    @GetMapping("/_search/dayearndetails")
    public ResponseEntity<List<DayearndetailDTO>> searchDayearndetails(@RequestParam String query, Pageable pageable) {
        log.debug("REST request to search for a page of Dayearndetails for query {}", query);
        Page<DayearndetailDTO> page = dayearndetailService.search(query, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }
}
