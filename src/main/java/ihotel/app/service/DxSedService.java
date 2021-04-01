package ihotel.app.service;

import static org.elasticsearch.index.query.QueryBuilders.*;

import ihotel.app.domain.DxSed;
import ihotel.app.repository.DxSedRepository;
import ihotel.app.repository.search.DxSedSearchRepository;
import ihotel.app.service.dto.DxSedDTO;
import ihotel.app.service.mapper.DxSedMapper;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link DxSed}.
 */
@Service
@Transactional
public class DxSedService {

    private final Logger log = LoggerFactory.getLogger(DxSedService.class);

    private final DxSedRepository dxSedRepository;

    private final DxSedMapper dxSedMapper;

    private final DxSedSearchRepository dxSedSearchRepository;

    public DxSedService(DxSedRepository dxSedRepository, DxSedMapper dxSedMapper, DxSedSearchRepository dxSedSearchRepository) {
        this.dxSedRepository = dxSedRepository;
        this.dxSedMapper = dxSedMapper;
        this.dxSedSearchRepository = dxSedSearchRepository;
    }

    /**
     * Save a dxSed.
     *
     * @param dxSedDTO the entity to save.
     * @return the persisted entity.
     */
    public DxSedDTO save(DxSedDTO dxSedDTO) {
        log.debug("Request to save DxSed : {}", dxSedDTO);
        DxSed dxSed = dxSedMapper.toEntity(dxSedDTO);
        dxSed = dxSedRepository.save(dxSed);
        DxSedDTO result = dxSedMapper.toDto(dxSed);
        dxSedSearchRepository.save(dxSed);
        return result;
    }

    /**
     * Partially update a dxSed.
     *
     * @param dxSedDTO the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<DxSedDTO> partialUpdate(DxSedDTO dxSedDTO) {
        log.debug("Request to partially update DxSed : {}", dxSedDTO);

        return dxSedRepository
            .findById(dxSedDTO.getId())
            .map(
                existingDxSed -> {
                    dxSedMapper.partialUpdate(existingDxSed, dxSedDTO);
                    return existingDxSed;
                }
            )
            .map(dxSedRepository::save)
            .map(
                savedDxSed -> {
                    dxSedSearchRepository.save(savedDxSed);

                    return savedDxSed;
                }
            )
            .map(dxSedMapper::toDto);
    }

    /**
     * Get all the dxSeds.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<DxSedDTO> findAll(Pageable pageable) {
        log.debug("Request to get all DxSeds");
        return dxSedRepository.findAll(pageable).map(dxSedMapper::toDto);
    }

    /**
     * Get one dxSed by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<DxSedDTO> findOne(Long id) {
        log.debug("Request to get DxSed : {}", id);
        return dxSedRepository.findById(id).map(dxSedMapper::toDto);
    }

    /**
     * Delete the dxSed by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete DxSed : {}", id);
        dxSedRepository.deleteById(id);
        dxSedSearchRepository.deleteById(id);
    }

    /**
     * Search for the dxSed corresponding to the query.
     *
     * @param query the query of the search.
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<DxSedDTO> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of DxSeds for query {}", query);
        return dxSedSearchRepository.search(queryStringQuery(query), pageable).map(dxSedMapper::toDto);
    }
}
