package ihotel.app.service;

import static org.elasticsearch.index.query.QueryBuilders.*;

import ihotel.app.domain.AccPp;
import ihotel.app.repository.AccPpRepository;
import ihotel.app.repository.search.AccPpSearchRepository;
import ihotel.app.service.dto.AccPpDTO;
import ihotel.app.service.mapper.AccPpMapper;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link AccPp}.
 */
@Service
@Transactional
public class AccPpService {

    private final Logger log = LoggerFactory.getLogger(AccPpService.class);

    private final AccPpRepository accPpRepository;

    private final AccPpMapper accPpMapper;

    private final AccPpSearchRepository accPpSearchRepository;

    public AccPpService(AccPpRepository accPpRepository, AccPpMapper accPpMapper, AccPpSearchRepository accPpSearchRepository) {
        this.accPpRepository = accPpRepository;
        this.accPpMapper = accPpMapper;
        this.accPpSearchRepository = accPpSearchRepository;
    }

    /**
     * Save a accPp.
     *
     * @param accPpDTO the entity to save.
     * @return the persisted entity.
     */
    public AccPpDTO save(AccPpDTO accPpDTO) {
        log.debug("Request to save AccPp : {}", accPpDTO);
        AccPp accPp = accPpMapper.toEntity(accPpDTO);
        accPp = accPpRepository.save(accPp);
        AccPpDTO result = accPpMapper.toDto(accPp);
        accPpSearchRepository.save(accPp);
        return result;
    }

    /**
     * Partially update a accPp.
     *
     * @param accPpDTO the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<AccPpDTO> partialUpdate(AccPpDTO accPpDTO) {
        log.debug("Request to partially update AccPp : {}", accPpDTO);

        return accPpRepository
            .findById(accPpDTO.getId())
            .map(
                existingAccPp -> {
                    accPpMapper.partialUpdate(existingAccPp, accPpDTO);
                    return existingAccPp;
                }
            )
            .map(accPpRepository::save)
            .map(
                savedAccPp -> {
                    accPpSearchRepository.save(savedAccPp);

                    return savedAccPp;
                }
            )
            .map(accPpMapper::toDto);
    }

    /**
     * Get all the accPps.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<AccPpDTO> findAll(Pageable pageable) {
        log.debug("Request to get all AccPps");
        return accPpRepository.findAll(pageable).map(accPpMapper::toDto);
    }

    /**
     * Get one accPp by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<AccPpDTO> findOne(Long id) {
        log.debug("Request to get AccPp : {}", id);
        return accPpRepository.findById(id).map(accPpMapper::toDto);
    }

    /**
     * Delete the accPp by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete AccPp : {}", id);
        accPpRepository.deleteById(id);
        accPpSearchRepository.deleteById(id);
    }

    /**
     * Search for the accPp corresponding to the query.
     *
     * @param query the query of the search.
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<AccPpDTO> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of AccPps for query {}", query);
        return accPpSearchRepository.search(queryStringQuery(query), pageable).map(accPpMapper::toDto);
    }
}
