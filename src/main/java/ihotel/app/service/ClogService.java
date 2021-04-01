package ihotel.app.service;

import static org.elasticsearch.index.query.QueryBuilders.*;

import ihotel.app.domain.Clog;
import ihotel.app.repository.ClogRepository;
import ihotel.app.repository.search.ClogSearchRepository;
import ihotel.app.service.dto.ClogDTO;
import ihotel.app.service.mapper.ClogMapper;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link Clog}.
 */
@Service
@Transactional
public class ClogService {

    private final Logger log = LoggerFactory.getLogger(ClogService.class);

    private final ClogRepository clogRepository;

    private final ClogMapper clogMapper;

    private final ClogSearchRepository clogSearchRepository;

    public ClogService(ClogRepository clogRepository, ClogMapper clogMapper, ClogSearchRepository clogSearchRepository) {
        this.clogRepository = clogRepository;
        this.clogMapper = clogMapper;
        this.clogSearchRepository = clogSearchRepository;
    }

    /**
     * Save a clog.
     *
     * @param clogDTO the entity to save.
     * @return the persisted entity.
     */
    public ClogDTO save(ClogDTO clogDTO) {
        log.debug("Request to save Clog : {}", clogDTO);
        Clog clog = clogMapper.toEntity(clogDTO);
        clog = clogRepository.save(clog);
        ClogDTO result = clogMapper.toDto(clog);
        clogSearchRepository.save(clog);
        return result;
    }

    /**
     * Partially update a clog.
     *
     * @param clogDTO the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<ClogDTO> partialUpdate(ClogDTO clogDTO) {
        log.debug("Request to partially update Clog : {}", clogDTO);

        return clogRepository
            .findById(clogDTO.getId())
            .map(
                existingClog -> {
                    clogMapper.partialUpdate(existingClog, clogDTO);
                    return existingClog;
                }
            )
            .map(clogRepository::save)
            .map(
                savedClog -> {
                    clogSearchRepository.save(savedClog);

                    return savedClog;
                }
            )
            .map(clogMapper::toDto);
    }

    /**
     * Get all the clogs.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<ClogDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Clogs");
        return clogRepository.findAll(pageable).map(clogMapper::toDto);
    }

    /**
     * Get one clog by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<ClogDTO> findOne(Long id) {
        log.debug("Request to get Clog : {}", id);
        return clogRepository.findById(id).map(clogMapper::toDto);
    }

    /**
     * Delete the clog by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete Clog : {}", id);
        clogRepository.deleteById(id);
        clogSearchRepository.deleteById(id);
    }

    /**
     * Search for the clog corresponding to the query.
     *
     * @param query the query of the search.
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<ClogDTO> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of Clogs for query {}", query);
        return clogSearchRepository.search(queryStringQuery(query), pageable).map(clogMapper::toDto);
    }
}
