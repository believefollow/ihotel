package ihotel.app.service;

import static org.elasticsearch.index.query.QueryBuilders.*;

import ihotel.app.domain.CheckinBak;
import ihotel.app.repository.CheckinBakRepository;
import ihotel.app.repository.search.CheckinBakSearchRepository;
import ihotel.app.service.dto.CheckinBakDTO;
import ihotel.app.service.mapper.CheckinBakMapper;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link CheckinBak}.
 */
@Service
@Transactional
public class CheckinBakService {

    private final Logger log = LoggerFactory.getLogger(CheckinBakService.class);

    private final CheckinBakRepository checkinBakRepository;

    private final CheckinBakMapper checkinBakMapper;

    private final CheckinBakSearchRepository checkinBakSearchRepository;

    public CheckinBakService(
        CheckinBakRepository checkinBakRepository,
        CheckinBakMapper checkinBakMapper,
        CheckinBakSearchRepository checkinBakSearchRepository
    ) {
        this.checkinBakRepository = checkinBakRepository;
        this.checkinBakMapper = checkinBakMapper;
        this.checkinBakSearchRepository = checkinBakSearchRepository;
    }

    /**
     * Save a checkinBak.
     *
     * @param checkinBakDTO the entity to save.
     * @return the persisted entity.
     */
    public CheckinBakDTO save(CheckinBakDTO checkinBakDTO) {
        log.debug("Request to save CheckinBak : {}", checkinBakDTO);
        CheckinBak checkinBak = checkinBakMapper.toEntity(checkinBakDTO);
        checkinBak = checkinBakRepository.save(checkinBak);
        CheckinBakDTO result = checkinBakMapper.toDto(checkinBak);
        checkinBakSearchRepository.save(checkinBak);
        return result;
    }

    /**
     * Partially update a checkinBak.
     *
     * @param checkinBakDTO the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<CheckinBakDTO> partialUpdate(CheckinBakDTO checkinBakDTO) {
        log.debug("Request to partially update CheckinBak : {}", checkinBakDTO);

        return checkinBakRepository
            .findById(checkinBakDTO.getId())
            .map(
                existingCheckinBak -> {
                    checkinBakMapper.partialUpdate(existingCheckinBak, checkinBakDTO);
                    return existingCheckinBak;
                }
            )
            .map(checkinBakRepository::save)
            .map(
                savedCheckinBak -> {
                    checkinBakSearchRepository.save(savedCheckinBak);

                    return savedCheckinBak;
                }
            )
            .map(checkinBakMapper::toDto);
    }

    /**
     * Get all the checkinBaks.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<CheckinBakDTO> findAll(Pageable pageable) {
        log.debug("Request to get all CheckinBaks");
        return checkinBakRepository.findAll(pageable).map(checkinBakMapper::toDto);
    }

    /**
     * Get one checkinBak by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<CheckinBakDTO> findOne(Long id) {
        log.debug("Request to get CheckinBak : {}", id);
        return checkinBakRepository.findById(id).map(checkinBakMapper::toDto);
    }

    /**
     * Delete the checkinBak by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete CheckinBak : {}", id);
        checkinBakRepository.deleteById(id);
        checkinBakSearchRepository.deleteById(id);
    }

    /**
     * Search for the checkinBak corresponding to the query.
     *
     * @param query the query of the search.
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<CheckinBakDTO> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of CheckinBaks for query {}", query);
        return checkinBakSearchRepository.search(queryStringQuery(query), pageable).map(checkinBakMapper::toDto);
    }
}
