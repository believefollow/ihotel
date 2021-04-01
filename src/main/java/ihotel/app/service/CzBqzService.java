package ihotel.app.service;

import static org.elasticsearch.index.query.QueryBuilders.*;

import ihotel.app.domain.CzBqz;
import ihotel.app.repository.CzBqzRepository;
import ihotel.app.repository.search.CzBqzSearchRepository;
import ihotel.app.service.dto.CzBqzDTO;
import ihotel.app.service.mapper.CzBqzMapper;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link CzBqz}.
 */
@Service
@Transactional
public class CzBqzService {

    private final Logger log = LoggerFactory.getLogger(CzBqzService.class);

    private final CzBqzRepository czBqzRepository;

    private final CzBqzMapper czBqzMapper;

    private final CzBqzSearchRepository czBqzSearchRepository;

    public CzBqzService(CzBqzRepository czBqzRepository, CzBqzMapper czBqzMapper, CzBqzSearchRepository czBqzSearchRepository) {
        this.czBqzRepository = czBqzRepository;
        this.czBqzMapper = czBqzMapper;
        this.czBqzSearchRepository = czBqzSearchRepository;
    }

    /**
     * Save a czBqz.
     *
     * @param czBqzDTO the entity to save.
     * @return the persisted entity.
     */
    public CzBqzDTO save(CzBqzDTO czBqzDTO) {
        log.debug("Request to save CzBqz : {}", czBqzDTO);
        CzBqz czBqz = czBqzMapper.toEntity(czBqzDTO);
        czBqz = czBqzRepository.save(czBqz);
        CzBqzDTO result = czBqzMapper.toDto(czBqz);
        czBqzSearchRepository.save(czBqz);
        return result;
    }

    /**
     * Partially update a czBqz.
     *
     * @param czBqzDTO the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<CzBqzDTO> partialUpdate(CzBqzDTO czBqzDTO) {
        log.debug("Request to partially update CzBqz : {}", czBqzDTO);

        return czBqzRepository
            .findById(czBqzDTO.getId())
            .map(
                existingCzBqz -> {
                    czBqzMapper.partialUpdate(existingCzBqz, czBqzDTO);
                    return existingCzBqz;
                }
            )
            .map(czBqzRepository::save)
            .map(
                savedCzBqz -> {
                    czBqzSearchRepository.save(savedCzBqz);

                    return savedCzBqz;
                }
            )
            .map(czBqzMapper::toDto);
    }

    /**
     * Get all the czBqzs.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<CzBqzDTO> findAll(Pageable pageable) {
        log.debug("Request to get all CzBqzs");
        return czBqzRepository.findAll(pageable).map(czBqzMapper::toDto);
    }

    /**
     * Get one czBqz by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<CzBqzDTO> findOne(Long id) {
        log.debug("Request to get CzBqz : {}", id);
        return czBqzRepository.findById(id).map(czBqzMapper::toDto);
    }

    /**
     * Delete the czBqz by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete CzBqz : {}", id);
        czBqzRepository.deleteById(id);
        czBqzSearchRepository.deleteById(id);
    }

    /**
     * Search for the czBqz corresponding to the query.
     *
     * @param query the query of the search.
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<CzBqzDTO> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of CzBqzs for query {}", query);
        return czBqzSearchRepository.search(queryStringQuery(query), pageable).map(czBqzMapper::toDto);
    }
}
