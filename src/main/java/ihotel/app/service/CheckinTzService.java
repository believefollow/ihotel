package ihotel.app.service;

import static org.elasticsearch.index.query.QueryBuilders.*;

import ihotel.app.domain.CheckinTz;
import ihotel.app.repository.CheckinTzRepository;
import ihotel.app.repository.search.CheckinTzSearchRepository;
import ihotel.app.service.dto.CheckinTzDTO;
import ihotel.app.service.mapper.CheckinTzMapper;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link CheckinTz}.
 */
@Service
@Transactional
public class CheckinTzService {

    private final Logger log = LoggerFactory.getLogger(CheckinTzService.class);

    private final CheckinTzRepository checkinTzRepository;

    private final CheckinTzMapper checkinTzMapper;

    private final CheckinTzSearchRepository checkinTzSearchRepository;

    public CheckinTzService(
        CheckinTzRepository checkinTzRepository,
        CheckinTzMapper checkinTzMapper,
        CheckinTzSearchRepository checkinTzSearchRepository
    ) {
        this.checkinTzRepository = checkinTzRepository;
        this.checkinTzMapper = checkinTzMapper;
        this.checkinTzSearchRepository = checkinTzSearchRepository;
    }

    /**
     * Save a checkinTz.
     *
     * @param checkinTzDTO the entity to save.
     * @return the persisted entity.
     */
    public CheckinTzDTO save(CheckinTzDTO checkinTzDTO) {
        log.debug("Request to save CheckinTz : {}", checkinTzDTO);
        CheckinTz checkinTz = checkinTzMapper.toEntity(checkinTzDTO);
        checkinTz = checkinTzRepository.save(checkinTz);
        CheckinTzDTO result = checkinTzMapper.toDto(checkinTz);
        checkinTzSearchRepository.save(checkinTz);
        return result;
    }

    /**
     * Partially update a checkinTz.
     *
     * @param checkinTzDTO the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<CheckinTzDTO> partialUpdate(CheckinTzDTO checkinTzDTO) {
        log.debug("Request to partially update CheckinTz : {}", checkinTzDTO);

        return checkinTzRepository
            .findById(checkinTzDTO.getId())
            .map(
                existingCheckinTz -> {
                    checkinTzMapper.partialUpdate(existingCheckinTz, checkinTzDTO);
                    return existingCheckinTz;
                }
            )
            .map(checkinTzRepository::save)
            .map(
                savedCheckinTz -> {
                    checkinTzSearchRepository.save(savedCheckinTz);

                    return savedCheckinTz;
                }
            )
            .map(checkinTzMapper::toDto);
    }

    /**
     * Get all the checkinTzs.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<CheckinTzDTO> findAll(Pageable pageable) {
        log.debug("Request to get all CheckinTzs");
        return checkinTzRepository.findAll(pageable).map(checkinTzMapper::toDto);
    }

    /**
     * Get one checkinTz by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<CheckinTzDTO> findOne(Long id) {
        log.debug("Request to get CheckinTz : {}", id);
        return checkinTzRepository.findById(id).map(checkinTzMapper::toDto);
    }

    /**
     * Delete the checkinTz by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete CheckinTz : {}", id);
        checkinTzRepository.deleteById(id);
        checkinTzSearchRepository.deleteById(id);
    }

    /**
     * Search for the checkinTz corresponding to the query.
     *
     * @param query the query of the search.
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<CheckinTzDTO> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of CheckinTzs for query {}", query);
        return checkinTzSearchRepository.search(queryStringQuery(query), pageable).map(checkinTzMapper::toDto);
    }
}
