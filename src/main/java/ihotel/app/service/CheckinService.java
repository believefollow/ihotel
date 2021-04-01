package ihotel.app.service;

import static org.elasticsearch.index.query.QueryBuilders.*;

import ihotel.app.domain.Checkin;
import ihotel.app.repository.CheckinRepository;
import ihotel.app.repository.search.CheckinSearchRepository;
import ihotel.app.service.dto.CheckinDTO;
import ihotel.app.service.mapper.CheckinMapper;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link Checkin}.
 */
@Service
@Transactional
public class CheckinService {

    private final Logger log = LoggerFactory.getLogger(CheckinService.class);

    private final CheckinRepository checkinRepository;

    private final CheckinMapper checkinMapper;

    private final CheckinSearchRepository checkinSearchRepository;

    public CheckinService(
        CheckinRepository checkinRepository,
        CheckinMapper checkinMapper,
        CheckinSearchRepository checkinSearchRepository
    ) {
        this.checkinRepository = checkinRepository;
        this.checkinMapper = checkinMapper;
        this.checkinSearchRepository = checkinSearchRepository;
    }

    /**
     * Save a checkin.
     *
     * @param checkinDTO the entity to save.
     * @return the persisted entity.
     */
    public CheckinDTO save(CheckinDTO checkinDTO) {
        log.debug("Request to save Checkin : {}", checkinDTO);
        Checkin checkin = checkinMapper.toEntity(checkinDTO);
        checkin = checkinRepository.save(checkin);
        CheckinDTO result = checkinMapper.toDto(checkin);
        checkinSearchRepository.save(checkin);
        return result;
    }

    /**
     * Partially update a checkin.
     *
     * @param checkinDTO the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<CheckinDTO> partialUpdate(CheckinDTO checkinDTO) {
        log.debug("Request to partially update Checkin : {}", checkinDTO);

        return checkinRepository
            .findById(checkinDTO.getId())
            .map(
                existingCheckin -> {
                    checkinMapper.partialUpdate(existingCheckin, checkinDTO);
                    return existingCheckin;
                }
            )
            .map(checkinRepository::save)
            .map(
                savedCheckin -> {
                    checkinSearchRepository.save(savedCheckin);

                    return savedCheckin;
                }
            )
            .map(checkinMapper::toDto);
    }

    /**
     * Get all the checkins.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<CheckinDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Checkins");
        return checkinRepository.findAll(pageable).map(checkinMapper::toDto);
    }

    /**
     * Get one checkin by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<CheckinDTO> findOne(Long id) {
        log.debug("Request to get Checkin : {}", id);
        return checkinRepository.findById(id).map(checkinMapper::toDto);
    }

    /**
     * Delete the checkin by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete Checkin : {}", id);
        checkinRepository.deleteById(id);
        checkinSearchRepository.deleteById(id);
    }

    /**
     * Search for the checkin corresponding to the query.
     *
     * @param query the query of the search.
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<CheckinDTO> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of Checkins for query {}", query);
        return checkinSearchRepository.search(queryStringQuery(query), pageable).map(checkinMapper::toDto);
    }
}
