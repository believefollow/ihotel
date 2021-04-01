package ihotel.app.service;

import static org.elasticsearch.index.query.QueryBuilders.*;

import ihotel.app.domain.Feetype;
import ihotel.app.repository.FeetypeRepository;
import ihotel.app.repository.search.FeetypeSearchRepository;
import ihotel.app.service.dto.FeetypeDTO;
import ihotel.app.service.mapper.FeetypeMapper;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link Feetype}.
 */
@Service
@Transactional
public class FeetypeService {

    private final Logger log = LoggerFactory.getLogger(FeetypeService.class);

    private final FeetypeRepository feetypeRepository;

    private final FeetypeMapper feetypeMapper;

    private final FeetypeSearchRepository feetypeSearchRepository;

    public FeetypeService(
        FeetypeRepository feetypeRepository,
        FeetypeMapper feetypeMapper,
        FeetypeSearchRepository feetypeSearchRepository
    ) {
        this.feetypeRepository = feetypeRepository;
        this.feetypeMapper = feetypeMapper;
        this.feetypeSearchRepository = feetypeSearchRepository;
    }

    /**
     * Save a feetype.
     *
     * @param feetypeDTO the entity to save.
     * @return the persisted entity.
     */
    public FeetypeDTO save(FeetypeDTO feetypeDTO) {
        log.debug("Request to save Feetype : {}", feetypeDTO);
        Feetype feetype = feetypeMapper.toEntity(feetypeDTO);
        feetype = feetypeRepository.save(feetype);
        FeetypeDTO result = feetypeMapper.toDto(feetype);
        feetypeSearchRepository.save(feetype);
        return result;
    }

    /**
     * Partially update a feetype.
     *
     * @param feetypeDTO the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<FeetypeDTO> partialUpdate(FeetypeDTO feetypeDTO) {
        log.debug("Request to partially update Feetype : {}", feetypeDTO);

        return feetypeRepository
            .findById(feetypeDTO.getId())
            .map(
                existingFeetype -> {
                    feetypeMapper.partialUpdate(existingFeetype, feetypeDTO);
                    return existingFeetype;
                }
            )
            .map(feetypeRepository::save)
            .map(
                savedFeetype -> {
                    feetypeSearchRepository.save(savedFeetype);

                    return savedFeetype;
                }
            )
            .map(feetypeMapper::toDto);
    }

    /**
     * Get all the feetypes.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<FeetypeDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Feetypes");
        return feetypeRepository.findAll(pageable).map(feetypeMapper::toDto);
    }

    /**
     * Get one feetype by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<FeetypeDTO> findOne(Long id) {
        log.debug("Request to get Feetype : {}", id);
        return feetypeRepository.findById(id).map(feetypeMapper::toDto);
    }

    /**
     * Delete the feetype by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete Feetype : {}", id);
        feetypeRepository.deleteById(id);
        feetypeSearchRepository.deleteById(id);
    }

    /**
     * Search for the feetype corresponding to the query.
     *
     * @param query the query of the search.
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<FeetypeDTO> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of Feetypes for query {}", query);
        return feetypeSearchRepository.search(queryStringQuery(query), pageable).map(feetypeMapper::toDto);
    }
}
