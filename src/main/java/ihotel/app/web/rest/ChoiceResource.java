package ihotel.app.web.rest;

import static org.elasticsearch.index.query.QueryBuilders.*;

import ihotel.app.repository.ChoiceRepository;
import ihotel.app.service.ChoiceQueryService;
import ihotel.app.service.ChoiceService;
import ihotel.app.service.criteria.ChoiceCriteria;
import ihotel.app.service.dto.ChoiceDTO;
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
 * REST controller for managing {@link ihotel.app.domain.Choice}.
 */
@RestController
@RequestMapping("/api")
public class ChoiceResource {

    private final Logger log = LoggerFactory.getLogger(ChoiceResource.class);

    private static final String ENTITY_NAME = "choice";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ChoiceService choiceService;

    private final ChoiceRepository choiceRepository;

    private final ChoiceQueryService choiceQueryService;

    public ChoiceResource(ChoiceService choiceService, ChoiceRepository choiceRepository, ChoiceQueryService choiceQueryService) {
        this.choiceService = choiceService;
        this.choiceRepository = choiceRepository;
        this.choiceQueryService = choiceQueryService;
    }

    /**
     * {@code POST  /choices} : Create a new choice.
     *
     * @param choiceDTO the choiceDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new choiceDTO, or with status {@code 400 (Bad Request)} if the choice has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/choices")
    public ResponseEntity<ChoiceDTO> createChoice(@Valid @RequestBody ChoiceDTO choiceDTO) throws URISyntaxException {
        log.debug("REST request to save Choice : {}", choiceDTO);
        if (choiceDTO.getId() != null) {
            throw new BadRequestAlertException("A new choice cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ChoiceDTO result = choiceService.save(choiceDTO);
        return ResponseEntity
            .created(new URI("/api/choices/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /choices/:id} : Updates an existing choice.
     *
     * @param id the id of the choiceDTO to save.
     * @param choiceDTO the choiceDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated choiceDTO,
     * or with status {@code 400 (Bad Request)} if the choiceDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the choiceDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/choices/{id}")
    public ResponseEntity<ChoiceDTO> updateChoice(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody ChoiceDTO choiceDTO
    ) throws URISyntaxException {
        log.debug("REST request to update Choice : {}, {}", id, choiceDTO);
        if (choiceDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, choiceDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!choiceRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        ChoiceDTO result = choiceService.save(choiceDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, choiceDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /choices/:id} : Partial updates given fields of an existing choice, field will ignore if it is null
     *
     * @param id the id of the choiceDTO to save.
     * @param choiceDTO the choiceDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated choiceDTO,
     * or with status {@code 400 (Bad Request)} if the choiceDTO is not valid,
     * or with status {@code 404 (Not Found)} if the choiceDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the choiceDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/choices/{id}", consumes = "application/merge-patch+json")
    public ResponseEntity<ChoiceDTO> partialUpdateChoice(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody ChoiceDTO choiceDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update Choice partially : {}, {}", id, choiceDTO);
        if (choiceDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, choiceDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!choiceRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<ChoiceDTO> result = choiceService.partialUpdate(choiceDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, choiceDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /choices} : get all the choices.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of choices in body.
     */
    @GetMapping("/choices")
    public ResponseEntity<List<ChoiceDTO>> getAllChoices(ChoiceCriteria criteria, Pageable pageable) {
        log.debug("REST request to get Choices by criteria: {}", criteria);
        Page<ChoiceDTO> page = choiceQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /choices/count} : count all the choices.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/choices/count")
    public ResponseEntity<Long> countChoices(ChoiceCriteria criteria) {
        log.debug("REST request to count Choices by criteria: {}", criteria);
        return ResponseEntity.ok().body(choiceQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /choices/:id} : get the "id" choice.
     *
     * @param id the id of the choiceDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the choiceDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/choices/{id}")
    public ResponseEntity<ChoiceDTO> getChoice(@PathVariable Long id) {
        log.debug("REST request to get Choice : {}", id);
        Optional<ChoiceDTO> choiceDTO = choiceService.findOne(id);
        return ResponseUtil.wrapOrNotFound(choiceDTO);
    }

    /**
     * {@code DELETE  /choices/:id} : delete the "id" choice.
     *
     * @param id the id of the choiceDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/choices/{id}")
    public ResponseEntity<Void> deleteChoice(@PathVariable Long id) {
        log.debug("REST request to delete Choice : {}", id);
        choiceService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }

    /**
     * {@code SEARCH  /_search/choices?query=:query} : search for the choice corresponding
     * to the query.
     *
     * @param query the query of the choice search.
     * @param pageable the pagination information.
     * @return the result of the search.
     */
    @GetMapping("/_search/choices")
    public ResponseEntity<List<ChoiceDTO>> searchChoices(@RequestParam String query, Pageable pageable) {
        log.debug("REST request to search for a page of Choices for query {}", query);
        Page<ChoiceDTO> page = choiceService.search(query, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }
}
