package ihotel.app.service;

import static org.elasticsearch.index.query.QueryBuilders.*;

import ihotel.app.domain.CzlCz;
import ihotel.app.repository.CzlCzRepository;
import ihotel.app.repository.search.CzlCzSearchRepository;
import ihotel.app.service.dto.CzlCzDTO;
import ihotel.app.service.mapper.CzlCzMapper;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link CzlCz}.
 */
@Service
@Transactional
public class CzlCzService {

    private final Logger log = LoggerFactory.getLogger(CzlCzService.class);

    private final CzlCzRepository czlCzRepository;

    private final CzlCzMapper czlCzMapper;

    private final CzlCzSearchRepository czlCzSearchRepository;

    public CzlCzService(CzlCzRepository czlCzRepository, CzlCzMapper czlCzMapper, CzlCzSearchRepository czlCzSearchRepository) {
        this.czlCzRepository = czlCzRepository;
        this.czlCzMapper = czlCzMapper;
        this.czlCzSearchRepository = czlCzSearchRepository;
    }

    /**
     * Save a czlCz.
     *
     * @param czlCzDTO the entity to save.
     * @return the persisted entity.
     */
    public CzlCzDTO save(CzlCzDTO czlCzDTO) {
        log.debug("Request to save CzlCz : {}", czlCzDTO);
        CzlCz czlCz = czlCzMapper.toEntity(czlCzDTO);
        czlCz = czlCzRepository.save(czlCz);
        CzlCzDTO result = czlCzMapper.toDto(czlCz);
        czlCzSearchRepository.save(czlCz);
        return result;
    }

    /**
     * Partially update a czlCz.
     *
     * @param czlCzDTO the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<CzlCzDTO> partialUpdate(CzlCzDTO czlCzDTO) {
        log.debug("Request to partially update CzlCz : {}", czlCzDTO);

        return czlCzRepository
            .findById(czlCzDTO.getId())
            .map(
                existingCzlCz -> {
                    czlCzMapper.partialUpdate(existingCzlCz, czlCzDTO);
                    return existingCzlCz;
                }
            )
            .map(czlCzRepository::save)
            .map(
                savedCzlCz -> {
                    czlCzSearchRepository.save(savedCzlCz);

                    return savedCzlCz;
                }
            )
            .map(czlCzMapper::toDto);
    }

    /**
     * Get all the czlCzs.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<CzlCzDTO> findAll(Pageable pageable) {
        log.debug("Request to get all CzlCzs");
        return czlCzRepository.findAll(pageable).map(czlCzMapper::toDto);
    }

    /**
     * Get one czlCz by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<CzlCzDTO> findOne(Long id) {
        log.debug("Request to get CzlCz : {}", id);
        return czlCzRepository.findById(id).map(czlCzMapper::toDto);
    }

    /**
     * Delete the czlCz by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete CzlCz : {}", id);
        czlCzRepository.deleteById(id);
        czlCzSearchRepository.deleteById(id);
    }

    /**
     * Search for the czlCz corresponding to the query.
     *
     * @param query the query of the search.
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<CzlCzDTO> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of CzlCzs for query {}", query);
        return czlCzSearchRepository.search(queryStringQuery(query), pageable).map(czlCzMapper::toDto);
    }
}
