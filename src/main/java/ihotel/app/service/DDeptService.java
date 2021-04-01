package ihotel.app.service;

import static org.elasticsearch.index.query.QueryBuilders.*;

import ihotel.app.domain.DDept;
import ihotel.app.repository.DDeptRepository;
import ihotel.app.repository.search.DDeptSearchRepository;
import ihotel.app.service.dto.DDeptDTO;
import ihotel.app.service.mapper.DDeptMapper;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link DDept}.
 */
@Service
@Transactional
public class DDeptService {

    private final Logger log = LoggerFactory.getLogger(DDeptService.class);

    private final DDeptRepository dDeptRepository;

    private final DDeptMapper dDeptMapper;

    private final DDeptSearchRepository dDeptSearchRepository;

    public DDeptService(DDeptRepository dDeptRepository, DDeptMapper dDeptMapper, DDeptSearchRepository dDeptSearchRepository) {
        this.dDeptRepository = dDeptRepository;
        this.dDeptMapper = dDeptMapper;
        this.dDeptSearchRepository = dDeptSearchRepository;
    }

    /**
     * Save a dDept.
     *
     * @param dDeptDTO the entity to save.
     * @return the persisted entity.
     */
    public DDeptDTO save(DDeptDTO dDeptDTO) {
        log.debug("Request to save DDept : {}", dDeptDTO);
        DDept dDept = dDeptMapper.toEntity(dDeptDTO);
        dDept = dDeptRepository.save(dDept);
        DDeptDTO result = dDeptMapper.toDto(dDept);
        dDeptSearchRepository.save(dDept);
        return result;
    }

    /**
     * Partially update a dDept.
     *
     * @param dDeptDTO the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<DDeptDTO> partialUpdate(DDeptDTO dDeptDTO) {
        log.debug("Request to partially update DDept : {}", dDeptDTO);

        return dDeptRepository
            .findById(dDeptDTO.getId())
            .map(
                existingDDept -> {
                    dDeptMapper.partialUpdate(existingDDept, dDeptDTO);
                    return existingDDept;
                }
            )
            .map(dDeptRepository::save)
            .map(
                savedDDept -> {
                    dDeptSearchRepository.save(savedDDept);

                    return savedDDept;
                }
            )
            .map(dDeptMapper::toDto);
    }

    /**
     * Get all the dDepts.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<DDeptDTO> findAll(Pageable pageable) {
        log.debug("Request to get all DDepts");
        return dDeptRepository.findAll(pageable).map(dDeptMapper::toDto);
    }

    /**
     * Get one dDept by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<DDeptDTO> findOne(Long id) {
        log.debug("Request to get DDept : {}", id);
        return dDeptRepository.findById(id).map(dDeptMapper::toDto);
    }

    /**
     * Delete the dDept by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete DDept : {}", id);
        dDeptRepository.deleteById(id);
        dDeptSearchRepository.deleteById(id);
    }

    /**
     * Search for the dDept corresponding to the query.
     *
     * @param query the query of the search.
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<DDeptDTO> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of DDepts for query {}", query);
        return dDeptSearchRepository.search(queryStringQuery(query), pageable).map(dDeptMapper::toDto);
    }
}
