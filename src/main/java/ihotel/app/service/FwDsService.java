package ihotel.app.service;

import static org.elasticsearch.index.query.QueryBuilders.*;

import ihotel.app.domain.FwDs;
import ihotel.app.repository.FwDsRepository;
import ihotel.app.repository.search.FwDsSearchRepository;
import ihotel.app.service.dto.FwDsDTO;
import ihotel.app.service.mapper.FwDsMapper;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link FwDs}.
 */
@Service
@Transactional
public class FwDsService {

    private final Logger log = LoggerFactory.getLogger(FwDsService.class);

    private final FwDsRepository fwDsRepository;

    private final FwDsMapper fwDsMapper;

    private final FwDsSearchRepository fwDsSearchRepository;

    public FwDsService(FwDsRepository fwDsRepository, FwDsMapper fwDsMapper, FwDsSearchRepository fwDsSearchRepository) {
        this.fwDsRepository = fwDsRepository;
        this.fwDsMapper = fwDsMapper;
        this.fwDsSearchRepository = fwDsSearchRepository;
    }

    /**
     * Save a fwDs.
     *
     * @param fwDsDTO the entity to save.
     * @return the persisted entity.
     */
    public FwDsDTO save(FwDsDTO fwDsDTO) {
        log.debug("Request to save FwDs : {}", fwDsDTO);
        FwDs fwDs = fwDsMapper.toEntity(fwDsDTO);
        fwDs = fwDsRepository.save(fwDs);
        FwDsDTO result = fwDsMapper.toDto(fwDs);
        fwDsSearchRepository.save(fwDs);
        return result;
    }

    /**
     * Partially update a fwDs.
     *
     * @param fwDsDTO the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<FwDsDTO> partialUpdate(FwDsDTO fwDsDTO) {
        log.debug("Request to partially update FwDs : {}", fwDsDTO);

        return fwDsRepository
            .findById(fwDsDTO.getId())
            .map(
                existingFwDs -> {
                    fwDsMapper.partialUpdate(existingFwDs, fwDsDTO);
                    return existingFwDs;
                }
            )
            .map(fwDsRepository::save)
            .map(
                savedFwDs -> {
                    fwDsSearchRepository.save(savedFwDs);

                    return savedFwDs;
                }
            )
            .map(fwDsMapper::toDto);
    }

    /**
     * Get all the fwDs.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<FwDsDTO> findAll(Pageable pageable) {
        log.debug("Request to get all FwDs");
        return fwDsRepository.findAll(pageable).map(fwDsMapper::toDto);
    }

    /**
     * Get one fwDs by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<FwDsDTO> findOne(Long id) {
        log.debug("Request to get FwDs : {}", id);
        return fwDsRepository.findById(id).map(fwDsMapper::toDto);
    }

    /**
     * Delete the fwDs by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete FwDs : {}", id);
        fwDsRepository.deleteById(id);
        fwDsSearchRepository.deleteById(id);
    }

    /**
     * Search for the fwDs corresponding to the query.
     *
     * @param query the query of the search.
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<FwDsDTO> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of FwDs for query {}", query);
        return fwDsSearchRepository.search(queryStringQuery(query), pageable).map(fwDsMapper::toDto);
    }
}
