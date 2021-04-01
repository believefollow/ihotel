package ihotel.app.service;

import static org.elasticsearch.index.query.QueryBuilders.*;

import ihotel.app.domain.Errlog;
import ihotel.app.repository.ErrlogRepository;
import ihotel.app.repository.search.ErrlogSearchRepository;
import ihotel.app.service.dto.ErrlogDTO;
import ihotel.app.service.mapper.ErrlogMapper;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link Errlog}.
 */
@Service
@Transactional
public class ErrlogService {

    private final Logger log = LoggerFactory.getLogger(ErrlogService.class);

    private final ErrlogRepository errlogRepository;

    private final ErrlogMapper errlogMapper;

    private final ErrlogSearchRepository errlogSearchRepository;

    public ErrlogService(ErrlogRepository errlogRepository, ErrlogMapper errlogMapper, ErrlogSearchRepository errlogSearchRepository) {
        this.errlogRepository = errlogRepository;
        this.errlogMapper = errlogMapper;
        this.errlogSearchRepository = errlogSearchRepository;
    }

    /**
     * Save a errlog.
     *
     * @param errlogDTO the entity to save.
     * @return the persisted entity.
     */
    public ErrlogDTO save(ErrlogDTO errlogDTO) {
        log.debug("Request to save Errlog : {}", errlogDTO);
        Errlog errlog = errlogMapper.toEntity(errlogDTO);
        errlog = errlogRepository.save(errlog);
        ErrlogDTO result = errlogMapper.toDto(errlog);
        errlogSearchRepository.save(errlog);
        return result;
    }

    /**
     * Partially update a errlog.
     *
     * @param errlogDTO the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<ErrlogDTO> partialUpdate(ErrlogDTO errlogDTO) {
        log.debug("Request to partially update Errlog : {}", errlogDTO);

        return errlogRepository
            .findById(errlogDTO.getId())
            .map(
                existingErrlog -> {
                    errlogMapper.partialUpdate(existingErrlog, errlogDTO);
                    return existingErrlog;
                }
            )
            .map(errlogRepository::save)
            .map(
                savedErrlog -> {
                    errlogSearchRepository.save(savedErrlog);

                    return savedErrlog;
                }
            )
            .map(errlogMapper::toDto);
    }

    /**
     * Get all the errlogs.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<ErrlogDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Errlogs");
        return errlogRepository.findAll(pageable).map(errlogMapper::toDto);
    }

    /**
     * Get one errlog by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<ErrlogDTO> findOne(Long id) {
        log.debug("Request to get Errlog : {}", id);
        return errlogRepository.findById(id).map(errlogMapper::toDto);
    }

    /**
     * Delete the errlog by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete Errlog : {}", id);
        errlogRepository.deleteById(id);
        errlogSearchRepository.deleteById(id);
    }

    /**
     * Search for the errlog corresponding to the query.
     *
     * @param query the query of the search.
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<ErrlogDTO> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of Errlogs for query {}", query);
        return errlogSearchRepository.search(queryStringQuery(query), pageable).map(errlogMapper::toDto);
    }
}
