package ihotel.app.service;

import static org.elasticsearch.index.query.QueryBuilders.*;

import ihotel.app.domain.Comset;
import ihotel.app.repository.ComsetRepository;
import ihotel.app.repository.search.ComsetSearchRepository;
import ihotel.app.service.dto.ComsetDTO;
import ihotel.app.service.mapper.ComsetMapper;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link Comset}.
 */
@Service
@Transactional
public class ComsetService {

    private final Logger log = LoggerFactory.getLogger(ComsetService.class);

    private final ComsetRepository comsetRepository;

    private final ComsetMapper comsetMapper;

    private final ComsetSearchRepository comsetSearchRepository;

    public ComsetService(ComsetRepository comsetRepository, ComsetMapper comsetMapper, ComsetSearchRepository comsetSearchRepository) {
        this.comsetRepository = comsetRepository;
        this.comsetMapper = comsetMapper;
        this.comsetSearchRepository = comsetSearchRepository;
    }

    /**
     * Save a comset.
     *
     * @param comsetDTO the entity to save.
     * @return the persisted entity.
     */
    public ComsetDTO save(ComsetDTO comsetDTO) {
        log.debug("Request to save Comset : {}", comsetDTO);
        Comset comset = comsetMapper.toEntity(comsetDTO);
        comset = comsetRepository.save(comset);
        ComsetDTO result = comsetMapper.toDto(comset);
        comsetSearchRepository.save(comset);
        return result;
    }

    /**
     * Partially update a comset.
     *
     * @param comsetDTO the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<ComsetDTO> partialUpdate(ComsetDTO comsetDTO) {
        log.debug("Request to partially update Comset : {}", comsetDTO);

        return comsetRepository
            .findById(comsetDTO.getId())
            .map(
                existingComset -> {
                    comsetMapper.partialUpdate(existingComset, comsetDTO);
                    return existingComset;
                }
            )
            .map(comsetRepository::save)
            .map(
                savedComset -> {
                    comsetSearchRepository.save(savedComset);

                    return savedComset;
                }
            )
            .map(comsetMapper::toDto);
    }

    /**
     * Get all the comsets.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<ComsetDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Comsets");
        return comsetRepository.findAll(pageable).map(comsetMapper::toDto);
    }

    /**
     * Get one comset by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<ComsetDTO> findOne(Long id) {
        log.debug("Request to get Comset : {}", id);
        return comsetRepository.findById(id).map(comsetMapper::toDto);
    }

    /**
     * Delete the comset by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete Comset : {}", id);
        comsetRepository.deleteById(id);
        comsetSearchRepository.deleteById(id);
    }

    /**
     * Search for the comset corresponding to the query.
     *
     * @param query the query of the search.
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<ComsetDTO> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of Comsets for query {}", query);
        return comsetSearchRepository.search(queryStringQuery(query), pageable).map(comsetMapper::toDto);
    }
}
