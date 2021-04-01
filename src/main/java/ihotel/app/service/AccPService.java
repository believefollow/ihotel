package ihotel.app.service;

import static org.elasticsearch.index.query.QueryBuilders.*;

import ihotel.app.domain.AccP;
import ihotel.app.repository.AccPRepository;
import ihotel.app.repository.search.AccPSearchRepository;
import ihotel.app.service.dto.AccPDTO;
import ihotel.app.service.mapper.AccPMapper;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link AccP}.
 */
@Service
@Transactional
public class AccPService {

    private final Logger log = LoggerFactory.getLogger(AccPService.class);

    private final AccPRepository accPRepository;

    private final AccPMapper accPMapper;

    private final AccPSearchRepository accPSearchRepository;

    public AccPService(AccPRepository accPRepository, AccPMapper accPMapper, AccPSearchRepository accPSearchRepository) {
        this.accPRepository = accPRepository;
        this.accPMapper = accPMapper;
        this.accPSearchRepository = accPSearchRepository;
    }

    /**
     * Save a accP.
     *
     * @param accPDTO the entity to save.
     * @return the persisted entity.
     */
    public AccPDTO save(AccPDTO accPDTO) {
        log.debug("Request to save AccP : {}", accPDTO);
        AccP accP = accPMapper.toEntity(accPDTO);
        accP = accPRepository.save(accP);
        AccPDTO result = accPMapper.toDto(accP);
        accPSearchRepository.save(accP);
        return result;
    }

    /**
     * Partially update a accP.
     *
     * @param accPDTO the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<AccPDTO> partialUpdate(AccPDTO accPDTO) {
        log.debug("Request to partially update AccP : {}", accPDTO);

        return accPRepository
            .findById(accPDTO.getId())
            .map(
                existingAccP -> {
                    accPMapper.partialUpdate(existingAccP, accPDTO);
                    return existingAccP;
                }
            )
            .map(accPRepository::save)
            .map(
                savedAccP -> {
                    accPSearchRepository.save(savedAccP);

                    return savedAccP;
                }
            )
            .map(accPMapper::toDto);
    }

    /**
     * Get all the accPS.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<AccPDTO> findAll(Pageable pageable) {
        log.debug("Request to get all AccPS");
        return accPRepository.findAll(pageable).map(accPMapper::toDto);
    }

    /**
     * Get one accP by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<AccPDTO> findOne(Long id) {
        log.debug("Request to get AccP : {}", id);
        return accPRepository.findById(id).map(accPMapper::toDto);
    }

    /**
     * Delete the accP by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete AccP : {}", id);
        accPRepository.deleteById(id);
        accPSearchRepository.deleteById(id);
    }

    /**
     * Search for the accP corresponding to the query.
     *
     * @param query the query of the search.
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<AccPDTO> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of AccPS for query {}", query);
        return accPSearchRepository.search(queryStringQuery(query), pageable).map(accPMapper::toDto);
    }
}
