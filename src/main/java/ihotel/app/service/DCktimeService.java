package ihotel.app.service;

import static org.elasticsearch.index.query.QueryBuilders.*;

import ihotel.app.domain.DCktime;
import ihotel.app.repository.DCktimeRepository;
import ihotel.app.repository.search.DCktimeSearchRepository;
import ihotel.app.service.dto.DCktimeDTO;
import ihotel.app.service.mapper.DCktimeMapper;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link DCktime}.
 */
@Service
@Transactional
public class DCktimeService {

    private final Logger log = LoggerFactory.getLogger(DCktimeService.class);

    private final DCktimeRepository dCktimeRepository;

    private final DCktimeMapper dCktimeMapper;

    private final DCktimeSearchRepository dCktimeSearchRepository;

    public DCktimeService(
        DCktimeRepository dCktimeRepository,
        DCktimeMapper dCktimeMapper,
        DCktimeSearchRepository dCktimeSearchRepository
    ) {
        this.dCktimeRepository = dCktimeRepository;
        this.dCktimeMapper = dCktimeMapper;
        this.dCktimeSearchRepository = dCktimeSearchRepository;
    }

    /**
     * Save a dCktime.
     *
     * @param dCktimeDTO the entity to save.
     * @return the persisted entity.
     */
    public DCktimeDTO save(DCktimeDTO dCktimeDTO) {
        log.debug("Request to save DCktime : {}", dCktimeDTO);
        DCktime dCktime = dCktimeMapper.toEntity(dCktimeDTO);
        dCktime = dCktimeRepository.save(dCktime);
        DCktimeDTO result = dCktimeMapper.toDto(dCktime);
        dCktimeSearchRepository.save(dCktime);
        return result;
    }

    /**
     * Partially update a dCktime.
     *
     * @param dCktimeDTO the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<DCktimeDTO> partialUpdate(DCktimeDTO dCktimeDTO) {
        log.debug("Request to partially update DCktime : {}", dCktimeDTO);

        return dCktimeRepository
            .findById(dCktimeDTO.getId())
            .map(
                existingDCktime -> {
                    dCktimeMapper.partialUpdate(existingDCktime, dCktimeDTO);
                    return existingDCktime;
                }
            )
            .map(dCktimeRepository::save)
            .map(
                savedDCktime -> {
                    dCktimeSearchRepository.save(savedDCktime);

                    return savedDCktime;
                }
            )
            .map(dCktimeMapper::toDto);
    }

    /**
     * Get all the dCktimes.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<DCktimeDTO> findAll(Pageable pageable) {
        log.debug("Request to get all DCktimes");
        return dCktimeRepository.findAll(pageable).map(dCktimeMapper::toDto);
    }

    /**
     * Get one dCktime by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<DCktimeDTO> findOne(Long id) {
        log.debug("Request to get DCktime : {}", id);
        return dCktimeRepository.findById(id).map(dCktimeMapper::toDto);
    }

    /**
     * Delete the dCktime by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete DCktime : {}", id);
        dCktimeRepository.deleteById(id);
        dCktimeSearchRepository.deleteById(id);
    }

    /**
     * Search for the dCktime corresponding to the query.
     *
     * @param query the query of the search.
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<DCktimeDTO> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of DCktimes for query {}", query);
        return dCktimeSearchRepository.search(queryStringQuery(query), pageable).map(dCktimeMapper::toDto);
    }
}
