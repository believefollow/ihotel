package ihotel.app.service;

import static org.elasticsearch.index.query.QueryBuilders.*;

import ihotel.app.domain.DUnit;
import ihotel.app.repository.DUnitRepository;
import ihotel.app.repository.search.DUnitSearchRepository;
import ihotel.app.service.dto.DUnitDTO;
import ihotel.app.service.mapper.DUnitMapper;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link DUnit}.
 */
@Service
@Transactional
public class DUnitService {

    private final Logger log = LoggerFactory.getLogger(DUnitService.class);

    private final DUnitRepository dUnitRepository;

    private final DUnitMapper dUnitMapper;

    private final DUnitSearchRepository dUnitSearchRepository;

    public DUnitService(DUnitRepository dUnitRepository, DUnitMapper dUnitMapper, DUnitSearchRepository dUnitSearchRepository) {
        this.dUnitRepository = dUnitRepository;
        this.dUnitMapper = dUnitMapper;
        this.dUnitSearchRepository = dUnitSearchRepository;
    }

    /**
     * Save a dUnit.
     *
     * @param dUnitDTO the entity to save.
     * @return the persisted entity.
     */
    public DUnitDTO save(DUnitDTO dUnitDTO) {
        log.debug("Request to save DUnit : {}", dUnitDTO);
        DUnit dUnit = dUnitMapper.toEntity(dUnitDTO);
        dUnit = dUnitRepository.save(dUnit);
        DUnitDTO result = dUnitMapper.toDto(dUnit);
        dUnitSearchRepository.save(dUnit);
        return result;
    }

    /**
     * Partially update a dUnit.
     *
     * @param dUnitDTO the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<DUnitDTO> partialUpdate(DUnitDTO dUnitDTO) {
        log.debug("Request to partially update DUnit : {}", dUnitDTO);

        return dUnitRepository
            .findById(dUnitDTO.getId())
            .map(
                existingDUnit -> {
                    dUnitMapper.partialUpdate(existingDUnit, dUnitDTO);
                    return existingDUnit;
                }
            )
            .map(dUnitRepository::save)
            .map(
                savedDUnit -> {
                    dUnitSearchRepository.save(savedDUnit);

                    return savedDUnit;
                }
            )
            .map(dUnitMapper::toDto);
    }

    /**
     * Get all the dUnits.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<DUnitDTO> findAll(Pageable pageable) {
        log.debug("Request to get all DUnits");
        return dUnitRepository.findAll(pageable).map(dUnitMapper::toDto);
    }

    /**
     * Get one dUnit by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<DUnitDTO> findOne(Long id) {
        log.debug("Request to get DUnit : {}", id);
        return dUnitRepository.findById(id).map(dUnitMapper::toDto);
    }

    /**
     * Delete the dUnit by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete DUnit : {}", id);
        dUnitRepository.deleteById(id);
        dUnitSearchRepository.deleteById(id);
    }

    /**
     * Search for the dUnit corresponding to the query.
     *
     * @param query the query of the search.
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<DUnitDTO> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of DUnits for query {}", query);
        return dUnitSearchRepository.search(queryStringQuery(query), pageable).map(dUnitMapper::toDto);
    }
}
