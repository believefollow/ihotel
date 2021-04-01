package ihotel.app.service;

import static org.elasticsearch.index.query.QueryBuilders.*;

import ihotel.app.domain.DKc;
import ihotel.app.repository.DKcRepository;
import ihotel.app.repository.search.DKcSearchRepository;
import ihotel.app.service.dto.DKcDTO;
import ihotel.app.service.mapper.DKcMapper;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link DKc}.
 */
@Service
@Transactional
public class DKcService {

    private final Logger log = LoggerFactory.getLogger(DKcService.class);

    private final DKcRepository dKcRepository;

    private final DKcMapper dKcMapper;

    private final DKcSearchRepository dKcSearchRepository;

    public DKcService(DKcRepository dKcRepository, DKcMapper dKcMapper, DKcSearchRepository dKcSearchRepository) {
        this.dKcRepository = dKcRepository;
        this.dKcMapper = dKcMapper;
        this.dKcSearchRepository = dKcSearchRepository;
    }

    /**
     * Save a dKc.
     *
     * @param dKcDTO the entity to save.
     * @return the persisted entity.
     */
    public DKcDTO save(DKcDTO dKcDTO) {
        log.debug("Request to save DKc : {}", dKcDTO);
        DKc dKc = dKcMapper.toEntity(dKcDTO);
        dKc = dKcRepository.save(dKc);
        DKcDTO result = dKcMapper.toDto(dKc);
        dKcSearchRepository.save(dKc);
        return result;
    }

    /**
     * Partially update a dKc.
     *
     * @param dKcDTO the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<DKcDTO> partialUpdate(DKcDTO dKcDTO) {
        log.debug("Request to partially update DKc : {}", dKcDTO);

        return dKcRepository
            .findById(dKcDTO.getId())
            .map(
                existingDKc -> {
                    dKcMapper.partialUpdate(existingDKc, dKcDTO);
                    return existingDKc;
                }
            )
            .map(dKcRepository::save)
            .map(
                savedDKc -> {
                    dKcSearchRepository.save(savedDKc);

                    return savedDKc;
                }
            )
            .map(dKcMapper::toDto);
    }

    /**
     * Get all the dKcs.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<DKcDTO> findAll(Pageable pageable) {
        log.debug("Request to get all DKcs");
        return dKcRepository.findAll(pageable).map(dKcMapper::toDto);
    }

    /**
     * Get one dKc by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<DKcDTO> findOne(Long id) {
        log.debug("Request to get DKc : {}", id);
        return dKcRepository.findById(id).map(dKcMapper::toDto);
    }

    /**
     * Delete the dKc by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete DKc : {}", id);
        dKcRepository.deleteById(id);
        dKcSearchRepository.deleteById(id);
    }

    /**
     * Search for the dKc corresponding to the query.
     *
     * @param query the query of the search.
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<DKcDTO> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of DKcs for query {}", query);
        return dKcSearchRepository.search(queryStringQuery(query), pageable).map(dKcMapper::toDto);
    }
}
