package ihotel.app.web.rest;

import static org.elasticsearch.index.query.QueryBuilders.*;

import ihotel.app.repository.DCompanyRepository;
import ihotel.app.service.DCompanyQueryService;
import ihotel.app.service.DCompanyService;
import ihotel.app.service.criteria.DCompanyCriteria;
import ihotel.app.service.dto.DCompanyDTO;
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
 * REST controller for managing {@link ihotel.app.domain.DCompany}.
 */
@RestController
@RequestMapping("/api")
public class DCompanyResource {

    private final Logger log = LoggerFactory.getLogger(DCompanyResource.class);

    private static final String ENTITY_NAME = "dCompany";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final DCompanyService dCompanyService;

    private final DCompanyRepository dCompanyRepository;

    private final DCompanyQueryService dCompanyQueryService;

    public DCompanyResource(
        DCompanyService dCompanyService,
        DCompanyRepository dCompanyRepository,
        DCompanyQueryService dCompanyQueryService
    ) {
        this.dCompanyService = dCompanyService;
        this.dCompanyRepository = dCompanyRepository;
        this.dCompanyQueryService = dCompanyQueryService;
    }

    /**
     * {@code POST  /d-companies} : Create a new dCompany.
     *
     * @param dCompanyDTO the dCompanyDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new dCompanyDTO, or with status {@code 400 (Bad Request)} if the dCompany has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/d-companies")
    public ResponseEntity<DCompanyDTO> createDCompany(@Valid @RequestBody DCompanyDTO dCompanyDTO) throws URISyntaxException {
        log.debug("REST request to save DCompany : {}", dCompanyDTO);
        if (dCompanyDTO.getId() != null) {
            throw new BadRequestAlertException("A new dCompany cannot already have an ID", ENTITY_NAME, "idexists");
        }
        DCompanyDTO result = dCompanyService.save(dCompanyDTO);
        return ResponseEntity
            .created(new URI("/api/d-companies/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /d-companies/:id} : Updates an existing dCompany.
     *
     * @param id the id of the dCompanyDTO to save.
     * @param dCompanyDTO the dCompanyDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated dCompanyDTO,
     * or with status {@code 400 (Bad Request)} if the dCompanyDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the dCompanyDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/d-companies/{id}")
    public ResponseEntity<DCompanyDTO> updateDCompany(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody DCompanyDTO dCompanyDTO
    ) throws URISyntaxException {
        log.debug("REST request to update DCompany : {}, {}", id, dCompanyDTO);
        if (dCompanyDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, dCompanyDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!dCompanyRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        DCompanyDTO result = dCompanyService.save(dCompanyDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, dCompanyDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /d-companies/:id} : Partial updates given fields of an existing dCompany, field will ignore if it is null
     *
     * @param id the id of the dCompanyDTO to save.
     * @param dCompanyDTO the dCompanyDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated dCompanyDTO,
     * or with status {@code 400 (Bad Request)} if the dCompanyDTO is not valid,
     * or with status {@code 404 (Not Found)} if the dCompanyDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the dCompanyDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/d-companies/{id}", consumes = "application/merge-patch+json")
    public ResponseEntity<DCompanyDTO> partialUpdateDCompany(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody DCompanyDTO dCompanyDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update DCompany partially : {}, {}", id, dCompanyDTO);
        if (dCompanyDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, dCompanyDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!dCompanyRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<DCompanyDTO> result = dCompanyService.partialUpdate(dCompanyDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, dCompanyDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /d-companies} : get all the dCompanies.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of dCompanies in body.
     */
    @GetMapping("/d-companies")
    public ResponseEntity<List<DCompanyDTO>> getAllDCompanies(DCompanyCriteria criteria, Pageable pageable) {
        log.debug("REST request to get DCompanies by criteria: {}", criteria);
        Page<DCompanyDTO> page = dCompanyQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /d-companies/count} : count all the dCompanies.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/d-companies/count")
    public ResponseEntity<Long> countDCompanies(DCompanyCriteria criteria) {
        log.debug("REST request to count DCompanies by criteria: {}", criteria);
        return ResponseEntity.ok().body(dCompanyQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /d-companies/:id} : get the "id" dCompany.
     *
     * @param id the id of the dCompanyDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the dCompanyDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/d-companies/{id}")
    public ResponseEntity<DCompanyDTO> getDCompany(@PathVariable Long id) {
        log.debug("REST request to get DCompany : {}", id);
        Optional<DCompanyDTO> dCompanyDTO = dCompanyService.findOne(id);
        return ResponseUtil.wrapOrNotFound(dCompanyDTO);
    }

    /**
     * {@code DELETE  /d-companies/:id} : delete the "id" dCompany.
     *
     * @param id the id of the dCompanyDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/d-companies/{id}")
    public ResponseEntity<Void> deleteDCompany(@PathVariable Long id) {
        log.debug("REST request to delete DCompany : {}", id);
        dCompanyService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }

    /**
     * {@code SEARCH  /_search/d-companies?query=:query} : search for the dCompany corresponding
     * to the query.
     *
     * @param query the query of the dCompany search.
     * @param pageable the pagination information.
     * @return the result of the search.
     */
    @GetMapping("/_search/d-companies")
    public ResponseEntity<List<DCompanyDTO>> searchDCompanies(@RequestParam String query, Pageable pageable) {
        log.debug("REST request to search for a page of DCompanies for query {}", query);
        Page<DCompanyDTO> page = dCompanyService.search(query, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }
}
