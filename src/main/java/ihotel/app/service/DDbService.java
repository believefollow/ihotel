package ihotel.app.service;

import static org.elasticsearch.index.query.QueryBuilders.*;

import ihotel.app.domain.DDb;
import ihotel.app.repository.DDbRepository;
import ihotel.app.repository.search.DDbSearchRepository;
import ihotel.app.service.dto.DDbDTO;
import ihotel.app.service.mapper.DDbMapper;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link DDb}.
 */
@Service
@Transactional
public class DDbService {

    private final Logger log = LoggerFactory.getLogger(DDbService.class);

    private final DDbRepository dDbRepository;

    private final DDbMapper dDbMapper;

    private final DDbSearchRepository dDbSearchRepository;

    public DDbService(DDbRepository dDbRepository, DDbMapper dDbMapper, DDbSearchRepository dDbSearchRepository) {
        this.dDbRepository = dDbRepository;
        this.dDbMapper = dDbMapper;
        this.dDbSearchRepository = dDbSearchRepository;
    }

    /**
     * Save a dDb.
     *
     * @param dDbDTO the entity to save.
     * @return the persisted entity.
     */
    public DDbDTO save(DDbDTO dDbDTO) {
        log.debug("Request to save DDb : {}", dDbDTO);
        DDb dDb = dDbMapper.toEntity(dDbDTO);
        dDb = dDbRepository.save(dDb);
        DDbDTO result = dDbMapper.toDto(dDb);
        dDbSearchRepository.save(dDb);
        return result;
    }

    /**
     * Partially update a dDb.
     *
     * @param dDbDTO the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<DDbDTO> partialUpdate(DDbDTO dDbDTO) {
        log.debug("Request to partially update DDb : {}", dDbDTO);

        return dDbRepository
            .findById(dDbDTO.getId())
            .map(
                existingDDb -> {
                    dDbMapper.partialUpdate(existingDDb, dDbDTO);
                    return existingDDb;
                }
            )
            .map(dDbRepository::save)
            .map(
                savedDDb -> {
                    dDbSearchRepository.save(savedDDb);

                    return savedDDb;
                }
            )
            .map(dDbMapper::toDto);
    }

    /**
     * Get all the dDbs.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<DDbDTO> findAll(Pageable pageable) {
        log.debug("Request to get all DDbs");
        return dDbRepository.findAll(pageable).map(dDbMapper::toDto);
    }

    /**
     * Get one dDb by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<DDbDTO> findOne(Long id) {
        log.debug("Request to get DDb : {}", id);
        return dDbRepository.findById(id).map(dDbMapper::toDto);
    }

    /**
     * Delete the dDb by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete DDb : {}", id);
        dDbRepository.deleteById(id);
        dDbSearchRepository.deleteById(id);
    }

    /**
     * Search for the dDb corresponding to the query.
     *
     * @param query the query of the search.
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<DDbDTO> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of DDbs for query {}", query);
        return dDbSearchRepository.search(queryStringQuery(query), pageable).map(dDbMapper::toDto);
    }
}
