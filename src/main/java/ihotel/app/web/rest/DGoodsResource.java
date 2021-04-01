package ihotel.app.web.rest;

import static org.elasticsearch.index.query.QueryBuilders.*;

import ihotel.app.repository.DGoodsRepository;
import ihotel.app.service.DGoodsQueryService;
import ihotel.app.service.DGoodsService;
import ihotel.app.service.criteria.DGoodsCriteria;
import ihotel.app.service.dto.DGoodsDTO;
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
 * REST controller for managing {@link ihotel.app.domain.DGoods}.
 */
@RestController
@RequestMapping("/api")
public class DGoodsResource {

    private final Logger log = LoggerFactory.getLogger(DGoodsResource.class);

    private static final String ENTITY_NAME = "dGoods";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final DGoodsService dGoodsService;

    private final DGoodsRepository dGoodsRepository;

    private final DGoodsQueryService dGoodsQueryService;

    public DGoodsResource(DGoodsService dGoodsService, DGoodsRepository dGoodsRepository, DGoodsQueryService dGoodsQueryService) {
        this.dGoodsService = dGoodsService;
        this.dGoodsRepository = dGoodsRepository;
        this.dGoodsQueryService = dGoodsQueryService;
    }

    /**
     * {@code POST  /d-goods} : Create a new dGoods.
     *
     * @param dGoodsDTO the dGoodsDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new dGoodsDTO, or with status {@code 400 (Bad Request)} if the dGoods has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/d-goods")
    public ResponseEntity<DGoodsDTO> createDGoods(@Valid @RequestBody DGoodsDTO dGoodsDTO) throws URISyntaxException {
        log.debug("REST request to save DGoods : {}", dGoodsDTO);
        if (dGoodsDTO.getId() != null) {
            throw new BadRequestAlertException("A new dGoods cannot already have an ID", ENTITY_NAME, "idexists");
        }
        DGoodsDTO result = dGoodsService.save(dGoodsDTO);
        return ResponseEntity
            .created(new URI("/api/d-goods/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /d-goods/:id} : Updates an existing dGoods.
     *
     * @param id the id of the dGoodsDTO to save.
     * @param dGoodsDTO the dGoodsDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated dGoodsDTO,
     * or with status {@code 400 (Bad Request)} if the dGoodsDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the dGoodsDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/d-goods/{id}")
    public ResponseEntity<DGoodsDTO> updateDGoods(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody DGoodsDTO dGoodsDTO
    ) throws URISyntaxException {
        log.debug("REST request to update DGoods : {}, {}", id, dGoodsDTO);
        if (dGoodsDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, dGoodsDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!dGoodsRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        DGoodsDTO result = dGoodsService.save(dGoodsDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, dGoodsDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /d-goods/:id} : Partial updates given fields of an existing dGoods, field will ignore if it is null
     *
     * @param id the id of the dGoodsDTO to save.
     * @param dGoodsDTO the dGoodsDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated dGoodsDTO,
     * or with status {@code 400 (Bad Request)} if the dGoodsDTO is not valid,
     * or with status {@code 404 (Not Found)} if the dGoodsDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the dGoodsDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/d-goods/{id}", consumes = "application/merge-patch+json")
    public ResponseEntity<DGoodsDTO> partialUpdateDGoods(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody DGoodsDTO dGoodsDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update DGoods partially : {}, {}", id, dGoodsDTO);
        if (dGoodsDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, dGoodsDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!dGoodsRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<DGoodsDTO> result = dGoodsService.partialUpdate(dGoodsDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, dGoodsDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /d-goods} : get all the dGoods.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of dGoods in body.
     */
    @GetMapping("/d-goods")
    public ResponseEntity<List<DGoodsDTO>> getAllDGoods(DGoodsCriteria criteria, Pageable pageable) {
        log.debug("REST request to get DGoods by criteria: {}", criteria);
        Page<DGoodsDTO> page = dGoodsQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /d-goods/count} : count all the dGoods.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/d-goods/count")
    public ResponseEntity<Long> countDGoods(DGoodsCriteria criteria) {
        log.debug("REST request to count DGoods by criteria: {}", criteria);
        return ResponseEntity.ok().body(dGoodsQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /d-goods/:id} : get the "id" dGoods.
     *
     * @param id the id of the dGoodsDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the dGoodsDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/d-goods/{id}")
    public ResponseEntity<DGoodsDTO> getDGoods(@PathVariable Long id) {
        log.debug("REST request to get DGoods : {}", id);
        Optional<DGoodsDTO> dGoodsDTO = dGoodsService.findOne(id);
        return ResponseUtil.wrapOrNotFound(dGoodsDTO);
    }

    /**
     * {@code DELETE  /d-goods/:id} : delete the "id" dGoods.
     *
     * @param id the id of the dGoodsDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/d-goods/{id}")
    public ResponseEntity<Void> deleteDGoods(@PathVariable Long id) {
        log.debug("REST request to delete DGoods : {}", id);
        dGoodsService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }

    /**
     * {@code SEARCH  /_search/d-goods?query=:query} : search for the dGoods corresponding
     * to the query.
     *
     * @param query the query of the dGoods search.
     * @param pageable the pagination information.
     * @return the result of the search.
     */
    @GetMapping("/_search/d-goods")
    public ResponseEntity<List<DGoodsDTO>> searchDGoods(@RequestParam String query, Pageable pageable) {
        log.debug("REST request to search for a page of DGoods for query {}", query);
        Page<DGoodsDTO> page = dGoodsService.search(query, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }
}
