package ihotel.app.service;

import static org.elasticsearch.index.query.QueryBuilders.*;

import ihotel.app.domain.FkCz;
import ihotel.app.repository.FkCzRepository;
import ihotel.app.repository.search.FkCzSearchRepository;
import ihotel.app.service.dto.FkCzDTO;
import ihotel.app.service.mapper.FkCzMapper;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link FkCz}.
 */
@Service
@Transactional
public class FkCzService {

    private final Logger log = LoggerFactory.getLogger(FkCzService.class);

    private final FkCzRepository fkCzRepository;

    private final FkCzMapper fkCzMapper;

    private final FkCzSearchRepository fkCzSearchRepository;

    public FkCzService(FkCzRepository fkCzRepository, FkCzMapper fkCzMapper, FkCzSearchRepository fkCzSearchRepository) {
        this.fkCzRepository = fkCzRepository;
        this.fkCzMapper = fkCzMapper;
        this.fkCzSearchRepository = fkCzSearchRepository;
    }

    /**
     * Save a fkCz.
     *
     * @param fkCzDTO the entity to save.
     * @return the persisted entity.
     */
    public FkCzDTO save(FkCzDTO fkCzDTO) {
        log.debug("Request to save FkCz : {}", fkCzDTO);
        FkCz fkCz = fkCzMapper.toEntity(fkCzDTO);
        fkCz = fkCzRepository.save(fkCz);
        FkCzDTO result = fkCzMapper.toDto(fkCz);
        fkCzSearchRepository.save(fkCz);
        return result;
    }

    /**
     * Partially update a fkCz.
     *
     * @param fkCzDTO the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<FkCzDTO> partialUpdate(FkCzDTO fkCzDTO) {
        log.debug("Request to partially update FkCz : {}", fkCzDTO);

        return fkCzRepository
            .findById(fkCzDTO.getId())
            .map(
                existingFkCz -> {
                    fkCzMapper.partialUpdate(existingFkCz, fkCzDTO);
                    return existingFkCz;
                }
            )
            .map(fkCzRepository::save)
            .map(
                savedFkCz -> {
                    fkCzSearchRepository.save(savedFkCz);

                    return savedFkCz;
                }
            )
            .map(fkCzMapper::toDto);
    }

    /**
     * Get all the fkCzs.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<FkCzDTO> findAll(Pageable pageable) {
        log.debug("Request to get all FkCzs");
        return fkCzRepository.findAll(pageable).map(fkCzMapper::toDto);
    }

    /**
     * Get one fkCz by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<FkCzDTO> findOne(Long id) {
        log.debug("Request to get FkCz : {}", id);
        return fkCzRepository.findById(id).map(fkCzMapper::toDto);
    }

    /**
     * Delete the fkCz by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete FkCz : {}", id);
        fkCzRepository.deleteById(id);
        fkCzSearchRepository.deleteById(id);
    }

    /**
     * Search for the fkCz corresponding to the query.
     *
     * @param query the query of the search.
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<FkCzDTO> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of FkCzs for query {}", query);
        return fkCzSearchRepository.search(queryStringQuery(query), pageable).map(fkCzMapper::toDto);
    }
}
