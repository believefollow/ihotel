package ihotel.app.service;

import static org.elasticsearch.index.query.QueryBuilders.*;

import ihotel.app.domain.Ee;
import ihotel.app.repository.EeRepository;
import ihotel.app.repository.search.EeSearchRepository;
import ihotel.app.service.dto.EeDTO;
import ihotel.app.service.mapper.EeMapper;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link Ee}.
 */
@Service
@Transactional
public class EeService {

    private final Logger log = LoggerFactory.getLogger(EeService.class);

    private final EeRepository eeRepository;

    private final EeMapper eeMapper;

    private final EeSearchRepository eeSearchRepository;

    public EeService(EeRepository eeRepository, EeMapper eeMapper, EeSearchRepository eeSearchRepository) {
        this.eeRepository = eeRepository;
        this.eeMapper = eeMapper;
        this.eeSearchRepository = eeSearchRepository;
    }

    /**
     * Save a ee.
     *
     * @param eeDTO the entity to save.
     * @return the persisted entity.
     */
    public EeDTO save(EeDTO eeDTO) {
        log.debug("Request to save Ee : {}", eeDTO);
        Ee ee = eeMapper.toEntity(eeDTO);
        ee = eeRepository.save(ee);
        EeDTO result = eeMapper.toDto(ee);
        eeSearchRepository.save(ee);
        return result;
    }

    /**
     * Partially update a ee.
     *
     * @param eeDTO the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<EeDTO> partialUpdate(EeDTO eeDTO) {
        log.debug("Request to partially update Ee : {}", eeDTO);

        return eeRepository
            .findById(eeDTO.getId())
            .map(
                existingEe -> {
                    eeMapper.partialUpdate(existingEe, eeDTO);
                    return existingEe;
                }
            )
            .map(eeRepository::save)
            .map(
                savedEe -> {
                    eeSearchRepository.save(savedEe);

                    return savedEe;
                }
            )
            .map(eeMapper::toDto);
    }

    /**
     * Get all the ees.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<EeDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Ees");
        return eeRepository.findAll(pageable).map(eeMapper::toDto);
    }

    /**
     * Get one ee by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<EeDTO> findOne(Long id) {
        log.debug("Request to get Ee : {}", id);
        return eeRepository.findById(id).map(eeMapper::toDto);
    }

    /**
     * Delete the ee by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete Ee : {}", id);
        eeRepository.deleteById(id);
        eeSearchRepository.deleteById(id);
    }

    /**
     * Search for the ee corresponding to the query.
     *
     * @param query the query of the search.
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<EeDTO> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of Ees for query {}", query);
        return eeSearchRepository.search(queryStringQuery(query), pageable).map(eeMapper::toDto);
    }
}
