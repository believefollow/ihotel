package ihotel.app.web.rest;

import static org.elasticsearch.index.query.QueryBuilders.*;

import ihotel.app.repository.CheckinAccountRepository;
import ihotel.app.service.CheckinAccountQueryService;
import ihotel.app.service.CheckinAccountService;
import ihotel.app.service.criteria.CheckinAccountCriteria;
import ihotel.app.service.dto.CheckinAccountDTO;
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
 * REST controller for managing {@link ihotel.app.domain.CheckinAccount}.
 */
@RestController
@RequestMapping("/api")
public class CheckinAccountResource {

    private final Logger log = LoggerFactory.getLogger(CheckinAccountResource.class);

    private static final String ENTITY_NAME = "checkinAccount";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final CheckinAccountService checkinAccountService;

    private final CheckinAccountRepository checkinAccountRepository;

    private final CheckinAccountQueryService checkinAccountQueryService;

    public CheckinAccountResource(
        CheckinAccountService checkinAccountService,
        CheckinAccountRepository checkinAccountRepository,
        CheckinAccountQueryService checkinAccountQueryService
    ) {
        this.checkinAccountService = checkinAccountService;
        this.checkinAccountRepository = checkinAccountRepository;
        this.checkinAccountQueryService = checkinAccountQueryService;
    }

    /**
     * {@code POST  /checkin-accounts} : Create a new checkinAccount.
     *
     * @param checkinAccountDTO the checkinAccountDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new checkinAccountDTO, or with status {@code 400 (Bad Request)} if the checkinAccount has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/checkin-accounts")
    public ResponseEntity<CheckinAccountDTO> createCheckinAccount(@Valid @RequestBody CheckinAccountDTO checkinAccountDTO)
        throws URISyntaxException {
        log.debug("REST request to save CheckinAccount : {}", checkinAccountDTO);
        if (checkinAccountDTO.getId() != null) {
            throw new BadRequestAlertException("A new checkinAccount cannot already have an ID", ENTITY_NAME, "idexists");
        }
        CheckinAccountDTO result = checkinAccountService.save(checkinAccountDTO);
        return ResponseEntity
            .created(new URI("/api/checkin-accounts/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /checkin-accounts/:id} : Updates an existing checkinAccount.
     *
     * @param id the id of the checkinAccountDTO to save.
     * @param checkinAccountDTO the checkinAccountDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated checkinAccountDTO,
     * or with status {@code 400 (Bad Request)} if the checkinAccountDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the checkinAccountDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/checkin-accounts/{id}")
    public ResponseEntity<CheckinAccountDTO> updateCheckinAccount(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody CheckinAccountDTO checkinAccountDTO
    ) throws URISyntaxException {
        log.debug("REST request to update CheckinAccount : {}, {}", id, checkinAccountDTO);
        if (checkinAccountDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, checkinAccountDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!checkinAccountRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        CheckinAccountDTO result = checkinAccountService.save(checkinAccountDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, checkinAccountDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /checkin-accounts/:id} : Partial updates given fields of an existing checkinAccount, field will ignore if it is null
     *
     * @param id the id of the checkinAccountDTO to save.
     * @param checkinAccountDTO the checkinAccountDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated checkinAccountDTO,
     * or with status {@code 400 (Bad Request)} if the checkinAccountDTO is not valid,
     * or with status {@code 404 (Not Found)} if the checkinAccountDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the checkinAccountDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/checkin-accounts/{id}", consumes = "application/merge-patch+json")
    public ResponseEntity<CheckinAccountDTO> partialUpdateCheckinAccount(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody CheckinAccountDTO checkinAccountDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update CheckinAccount partially : {}, {}", id, checkinAccountDTO);
        if (checkinAccountDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, checkinAccountDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!checkinAccountRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<CheckinAccountDTO> result = checkinAccountService.partialUpdate(checkinAccountDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, checkinAccountDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /checkin-accounts} : get all the checkinAccounts.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of checkinAccounts in body.
     */
    @GetMapping("/checkin-accounts")
    public ResponseEntity<List<CheckinAccountDTO>> getAllCheckinAccounts(CheckinAccountCriteria criteria, Pageable pageable) {
        log.debug("REST request to get CheckinAccounts by criteria: {}", criteria);
        Page<CheckinAccountDTO> page = checkinAccountQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /checkin-accounts/count} : count all the checkinAccounts.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/checkin-accounts/count")
    public ResponseEntity<Long> countCheckinAccounts(CheckinAccountCriteria criteria) {
        log.debug("REST request to count CheckinAccounts by criteria: {}", criteria);
        return ResponseEntity.ok().body(checkinAccountQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /checkin-accounts/:id} : get the "id" checkinAccount.
     *
     * @param id the id of the checkinAccountDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the checkinAccountDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/checkin-accounts/{id}")
    public ResponseEntity<CheckinAccountDTO> getCheckinAccount(@PathVariable Long id) {
        log.debug("REST request to get CheckinAccount : {}", id);
        Optional<CheckinAccountDTO> checkinAccountDTO = checkinAccountService.findOne(id);
        return ResponseUtil.wrapOrNotFound(checkinAccountDTO);
    }

    /**
     * {@code DELETE  /checkin-accounts/:id} : delete the "id" checkinAccount.
     *
     * @param id the id of the checkinAccountDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/checkin-accounts/{id}")
    public ResponseEntity<Void> deleteCheckinAccount(@PathVariable Long id) {
        log.debug("REST request to delete CheckinAccount : {}", id);
        checkinAccountService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }

    /**
     * {@code SEARCH  /_search/checkin-accounts?query=:query} : search for the checkinAccount corresponding
     * to the query.
     *
     * @param query the query of the checkinAccount search.
     * @param pageable the pagination information.
     * @return the result of the search.
     */
    @GetMapping("/_search/checkin-accounts")
    public ResponseEntity<List<CheckinAccountDTO>> searchCheckinAccounts(@RequestParam String query, Pageable pageable) {
        log.debug("REST request to search for a page of CheckinAccounts for query {}", query);
        Page<CheckinAccountDTO> page = checkinAccountService.search(query, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }
}
