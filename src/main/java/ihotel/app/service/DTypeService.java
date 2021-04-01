package ihotel.app.service;

import static org.elasticsearch.index.query.QueryBuilders.*;

import ihotel.app.domain.DType;
import ihotel.app.repository.DTypeRepository;
import ihotel.app.repository.search.DTypeSearchRepository;
import ihotel.app.service.dto.DTypeDTO;
import ihotel.app.service.mapper.DTypeMapper;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link DType}.
 */
@Service
@Transactional
public class DTypeService {

    private final Logger log = LoggerFactory.getLogger(DTypeService.class);

    private final DTypeRepository dTypeRepository;

    private final DTypeMapper dTypeMapper;

    private final DTypeSearchRepository dTypeSearchRepository;

    public DTypeService(DTypeRepository dTypeRepository, DTypeMapper dTypeMapper, DTypeSearchRepository dTypeSearchRepository) {
        this.dTypeRepository = dTypeRepository;
        this.dTypeMapper = dTypeMapper;
        this.dTypeSearchRepository = dTypeSearchRepository;
    }

    /**
     * Save a dType.
     *
     * @param dTypeDTO the entity to save.
     * @return the persisted entity.
     */
    public DTypeDTO save(DTypeDTO dTypeDTO) {
        log.debug("Request to save DType : {}", dTypeDTO);
        DType dType = dTypeMapper.toEntity(dTypeDTO);
        dType = dTypeRepository.save(dType);
        DTypeDTO result = dTypeMapper.toDto(dType);
        dTypeSearchRepository.save(dType);
        return result;
    }

    /**
     * Partially update a dType.
     *
     * @param dTypeDTO the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<DTypeDTO> partialUpdate(DTypeDTO dTypeDTO) {
        log.debug("Request to partially update DType : {}", dTypeDTO);

        return dTypeRepository
            .findById(dTypeDTO.getId())
            .map(
                existingDType -> {
                    dTypeMapper.partialUpdate(existingDType, dTypeDTO);
                    return existingDType;
                }
            )
            .map(dTypeRepository::save)
            .map(
                savedDType -> {
                    dTypeSearchRepository.save(savedDType);

                    return savedDType;
                }
            )
            .map(dTypeMapper::toDto);
    }

    /**
     * Get all the dTypes.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<DTypeDTO> findAll(Pageable pageable) {
        log.debug("Request to get all DTypes");
        return dTypeRepository.findAll(pageable).map(dTypeMapper::toDto);
    }

    /**
     * Get one dType by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<DTypeDTO> findOne(Long id) {
        log.debug("Request to get DType : {}", id);
        return dTypeRepository.findById(id).map(dTypeMapper::toDto);
    }

    /**
     * Delete the dType by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete DType : {}", id);
        dTypeRepository.deleteById(id);
        dTypeSearchRepository.deleteById(id);
    }

    /**
     * Search for the dType corresponding to the query.
     *
     * @param query the query of the search.
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<DTypeDTO> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of DTypes for query {}", query);
        return dTypeSearchRepository.search(queryStringQuery(query), pageable).map(dTypeMapper::toDto);
    }
}
