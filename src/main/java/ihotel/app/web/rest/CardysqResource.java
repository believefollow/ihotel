package ihotel.app.web.rest;

import static org.elasticsearch.index.query.QueryBuilders.*;

import ihotel.app.repository.CardysqRepository;
import ihotel.app.service.CardysqQueryService;
import ihotel.app.service.CardysqService;
import ihotel.app.service.criteria.CardysqCriteria;
import ihotel.app.service.dto.CardysqDTO;
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
 * REST controller for managing {@link ihotel.app.domain.Cardysq}.
 */
@RestController
@RequestMapping("/api")
public class CardysqResource {

    private final Logger log = LoggerFactory.getLogger(CardysqResource.class);

    private static final String ENTITY_NAME = "cardysq";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final CardysqService cardysqService;

    private final CardysqRepository cardysqRepository;

    private final CardysqQueryService cardysqQueryService;

    public CardysqResource(CardysqService cardysqService, CardysqRepository cardysqRepository, CardysqQueryService cardysqQueryService) {
        this.cardysqService = cardysqService;
        this.cardysqRepository = cardysqRepository;
        this.cardysqQueryService = cardysqQueryService;
    }

    /**
     * {@code POST  /cardysqs} : Create a new cardysq.
     *
     * @param cardysqDTO the cardysqDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new cardysqDTO, or with status {@code 400 (Bad Request)} if the cardysq has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/cardysqs")
    public ResponseEntity<CardysqDTO> createCardysq(@Valid @RequestBody CardysqDTO cardysqDTO) throws URISyntaxException {
        log.debug("REST request to save Cardysq : {}", cardysqDTO);
        if (cardysqDTO.getId() != null) {
            throw new BadRequestAlertException("A new cardysq cannot already have an ID", ENTITY_NAME, "idexists");
        }
        CardysqDTO result = cardysqService.save(cardysqDTO);
        return ResponseEntity
            .created(new URI("/api/cardysqs/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /cardysqs/:id} : Updates an existing cardysq.
     *
     * @param id the id of the cardysqDTO to save.
     * @param cardysqDTO the cardysqDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated cardysqDTO,
     * or with status {@code 400 (Bad Request)} if the cardysqDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the cardysqDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/cardysqs/{id}")
    public ResponseEntity<CardysqDTO> updateCardysq(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody CardysqDTO cardysqDTO
    ) throws URISyntaxException {
        log.debug("REST request to update Cardysq : {}, {}", id, cardysqDTO);
        if (cardysqDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, cardysqDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!cardysqRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        CardysqDTO result = cardysqService.save(cardysqDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, cardysqDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /cardysqs/:id} : Partial updates given fields of an existing cardysq, field will ignore if it is null
     *
     * @param id the id of the cardysqDTO to save.
     * @param cardysqDTO the cardysqDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated cardysqDTO,
     * or with status {@code 400 (Bad Request)} if the cardysqDTO is not valid,
     * or with status {@code 404 (Not Found)} if the cardysqDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the cardysqDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/cardysqs/{id}", consumes = "application/merge-patch+json")
    public ResponseEntity<CardysqDTO> partialUpdateCardysq(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody CardysqDTO cardysqDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update Cardysq partially : {}, {}", id, cardysqDTO);
        if (cardysqDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, cardysqDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!cardysqRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<CardysqDTO> result = cardysqService.partialUpdate(cardysqDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, cardysqDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /cardysqs} : get all the cardysqs.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of cardysqs in body.
     */
    @GetMapping("/cardysqs")
    public ResponseEntity<List<CardysqDTO>> getAllCardysqs(CardysqCriteria criteria, Pageable pageable) {
        log.debug("REST request to get Cardysqs by criteria: {}", criteria);
        Page<CardysqDTO> page = cardysqQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /cardysqs/count} : count all the cardysqs.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/cardysqs/count")
    public ResponseEntity<Long> countCardysqs(CardysqCriteria criteria) {
        log.debug("REST request to count Cardysqs by criteria: {}", criteria);
        return ResponseEntity.ok().body(cardysqQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /cardysqs/:id} : get the "id" cardysq.
     *
     * @param id the id of the cardysqDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the cardysqDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/cardysqs/{id}")
    public ResponseEntity<CardysqDTO> getCardysq(@PathVariable Long id) {
        log.debug("REST request to get Cardysq : {}", id);
        Optional<CardysqDTO> cardysqDTO = cardysqService.findOne(id);
        return ResponseUtil.wrapOrNotFound(cardysqDTO);
    }

    /**
     * {@code DELETE  /cardysqs/:id} : delete the "id" cardysq.
     *
     * @param id the id of the cardysqDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/cardysqs/{id}")
    public ResponseEntity<Void> deleteCardysq(@PathVariable Long id) {
        log.debug("REST request to delete Cardysq : {}", id);
        cardysqService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }

    /**
     * {@code SEARCH  /_search/cardysqs?query=:query} : search for the cardysq corresponding
     * to the query.
     *
     * @param query the query of the cardysq search.
     * @param pageable the pagination information.
     * @return the result of the search.
     */
    @GetMapping("/_search/cardysqs")
    public ResponseEntity<List<CardysqDTO>> searchCardysqs(@RequestParam String query, Pageable pageable) {
        log.debug("REST request to search for a page of Cardysqs for query {}", query);
        Page<CardysqDTO> page = cardysqService.search(query, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }
}
