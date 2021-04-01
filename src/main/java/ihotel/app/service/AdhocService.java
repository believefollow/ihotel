package ihotel.app.service;

import static org.elasticsearch.index.query.QueryBuilders.*;

import ihotel.app.domain.Adhoc;
import ihotel.app.repository.AdhocRepository;
import ihotel.app.repository.search.AdhocSearchRepository;
import ihotel.app.service.dto.AdhocDTO;
import ihotel.app.service.mapper.AdhocMapper;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link Adhoc}.
 */
@Service
@Transactional
public class AdhocService {

    private final Logger log = LoggerFactory.getLogger(AdhocService.class);

    private final AdhocRepository adhocRepository;

    private final AdhocMapper adhocMapper;

    private final AdhocSearchRepository adhocSearchRepository;

    public AdhocService(AdhocRepository adhocRepository, AdhocMapper adhocMapper, AdhocSearchRepository adhocSearchRepository) {
        this.adhocRepository = adhocRepository;
        this.adhocMapper = adhocMapper;
        this.adhocSearchRepository = adhocSearchRepository;
    }

    /**
     * Save a adhoc.
     *
     * @param adhocDTO the entity to save.
     * @return the persisted entity.
     */
    public AdhocDTO save(AdhocDTO adhocDTO) {
        log.debug("Request to save Adhoc : {}", adhocDTO);
        Adhoc adhoc = adhocMapper.toEntity(adhocDTO);
        adhoc = adhocRepository.save(adhoc);
        AdhocDTO result = adhocMapper.toDto(adhoc);
        adhocSearchRepository.save(adhoc);
        return result;
    }

    /**
     * Partially update a adhoc.
     *
     * @param adhocDTO the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<AdhocDTO> partialUpdate(AdhocDTO adhocDTO) {
        log.debug("Request to partially update Adhoc : {}", adhocDTO);

        return adhocRepository
            .findById(adhocDTO.getId())
            .map(
                existingAdhoc -> {
                    adhocMapper.partialUpdate(existingAdhoc, adhocDTO);
                    return existingAdhoc;
                }
            )
            .map(adhocRepository::save)
            .map(
                savedAdhoc -> {
                    adhocSearchRepository.save(savedAdhoc);

                    return savedAdhoc;
                }
            )
            .map(adhocMapper::toDto);
    }

    /**
     * Get all the adhocs.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<AdhocDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Adhocs");
        return adhocRepository.findAll(pageable).map(adhocMapper::toDto);
    }

    /**
     * Get one adhoc by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<AdhocDTO> findOne(String id) {
        log.debug("Request to get Adhoc : {}", id);
        return adhocRepository.findById(id).map(adhocMapper::toDto);
    }

    /**
     * Delete the adhoc by id.
     *
     * @param id the id of the entity.
     */
    public void delete(String id) {
        log.debug("Request to delete Adhoc : {}", id);
        adhocRepository.deleteById(id);
        adhocSearchRepository.deleteById(id);
    }

    /**
     * Search for the adhoc corresponding to the query.
     *
     * @param query the query of the search.
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<AdhocDTO> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of Adhocs for query {}", query);
        return adhocSearchRepository.search(queryStringQuery(query), pageable).map(adhocMapper::toDto);
    }
}
