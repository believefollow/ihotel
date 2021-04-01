package ihotel.app.service;

import static org.elasticsearch.index.query.QueryBuilders.*;

import ihotel.app.domain.DCk;
import ihotel.app.repository.DCkRepository;
import ihotel.app.repository.search.DCkSearchRepository;
import ihotel.app.service.dto.DCkDTO;
import ihotel.app.service.mapper.DCkMapper;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link DCk}.
 */
@Service
@Transactional
public class DCkService {

    private final Logger log = LoggerFactory.getLogger(DCkService.class);

    private final DCkRepository dCkRepository;

    private final DCkMapper dCkMapper;

    private final DCkSearchRepository dCkSearchRepository;

    public DCkService(DCkRepository dCkRepository, DCkMapper dCkMapper, DCkSearchRepository dCkSearchRepository) {
        this.dCkRepository = dCkRepository;
        this.dCkMapper = dCkMapper;
        this.dCkSearchRepository = dCkSearchRepository;
    }

    /**
     * Save a dCk.
     *
     * @param dCkDTO the entity to save.
     * @return the persisted entity.
     */
    public DCkDTO save(DCkDTO dCkDTO) {
        log.debug("Request to save DCk : {}", dCkDTO);
        DCk dCk = dCkMapper.toEntity(dCkDTO);
        dCk = dCkRepository.save(dCk);
        DCkDTO result = dCkMapper.toDto(dCk);
        dCkSearchRepository.save(dCk);
        return result;
    }

    /**
     * Partially update a dCk.
     *
     * @param dCkDTO the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<DCkDTO> partialUpdate(DCkDTO dCkDTO) {
        log.debug("Request to partially update DCk : {}", dCkDTO);

        return dCkRepository
            .findById(dCkDTO.getId())
            .map(
                existingDCk -> {
                    dCkMapper.partialUpdate(existingDCk, dCkDTO);
                    return existingDCk;
                }
            )
            .map(dCkRepository::save)
            .map(
                savedDCk -> {
                    dCkSearchRepository.save(savedDCk);

                    return savedDCk;
                }
            )
            .map(dCkMapper::toDto);
    }

    /**
     * Get all the dCks.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<DCkDTO> findAll(Pageable pageable) {
        log.debug("Request to get all DCks");
        return dCkRepository.findAll(pageable).map(dCkMapper::toDto);
    }

    /**
     * Get one dCk by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<DCkDTO> findOne(Long id) {
        log.debug("Request to get DCk : {}", id);
        return dCkRepository.findById(id).map(dCkMapper::toDto);
    }

    /**
     * Delete the dCk by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete DCk : {}", id);
        dCkRepository.deleteById(id);
        dCkSearchRepository.deleteById(id);
    }

    /**
     * Search for the dCk corresponding to the query.
     *
     * @param query the query of the search.
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<DCkDTO> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of DCks for query {}", query);
        return dCkSearchRepository.search(queryStringQuery(query), pageable).map(dCkMapper::toDto);
    }
}
