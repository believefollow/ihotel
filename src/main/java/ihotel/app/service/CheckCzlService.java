package ihotel.app.service;

import static org.elasticsearch.index.query.QueryBuilders.*;

import ihotel.app.domain.CheckCzl;
import ihotel.app.repository.CheckCzlRepository;
import ihotel.app.repository.search.CheckCzlSearchRepository;
import ihotel.app.service.dto.CheckCzlDTO;
import ihotel.app.service.mapper.CheckCzlMapper;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link CheckCzl}.
 */
@Service
@Transactional
public class CheckCzlService {

    private final Logger log = LoggerFactory.getLogger(CheckCzlService.class);

    private final CheckCzlRepository checkCzlRepository;

    private final CheckCzlMapper checkCzlMapper;

    private final CheckCzlSearchRepository checkCzlSearchRepository;

    public CheckCzlService(
        CheckCzlRepository checkCzlRepository,
        CheckCzlMapper checkCzlMapper,
        CheckCzlSearchRepository checkCzlSearchRepository
    ) {
        this.checkCzlRepository = checkCzlRepository;
        this.checkCzlMapper = checkCzlMapper;
        this.checkCzlSearchRepository = checkCzlSearchRepository;
    }

    /**
     * Save a checkCzl.
     *
     * @param checkCzlDTO the entity to save.
     * @return the persisted entity.
     */
    public CheckCzlDTO save(CheckCzlDTO checkCzlDTO) {
        log.debug("Request to save CheckCzl : {}", checkCzlDTO);
        CheckCzl checkCzl = checkCzlMapper.toEntity(checkCzlDTO);
        checkCzl = checkCzlRepository.save(checkCzl);
        CheckCzlDTO result = checkCzlMapper.toDto(checkCzl);
        checkCzlSearchRepository.save(checkCzl);
        return result;
    }

    /**
     * Partially update a checkCzl.
     *
     * @param checkCzlDTO the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<CheckCzlDTO> partialUpdate(CheckCzlDTO checkCzlDTO) {
        log.debug("Request to partially update CheckCzl : {}", checkCzlDTO);

        return checkCzlRepository
            .findById(checkCzlDTO.getId())
            .map(
                existingCheckCzl -> {
                    checkCzlMapper.partialUpdate(existingCheckCzl, checkCzlDTO);
                    return existingCheckCzl;
                }
            )
            .map(checkCzlRepository::save)
            .map(
                savedCheckCzl -> {
                    checkCzlSearchRepository.save(savedCheckCzl);

                    return savedCheckCzl;
                }
            )
            .map(checkCzlMapper::toDto);
    }

    /**
     * Get all the checkCzls.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<CheckCzlDTO> findAll(Pageable pageable) {
        log.debug("Request to get all CheckCzls");
        return checkCzlRepository.findAll(pageable).map(checkCzlMapper::toDto);
    }

    /**
     * Get one checkCzl by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<CheckCzlDTO> findOne(Long id) {
        log.debug("Request to get CheckCzl : {}", id);
        return checkCzlRepository.findById(id).map(checkCzlMapper::toDto);
    }

    /**
     * Delete the checkCzl by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete CheckCzl : {}", id);
        checkCzlRepository.deleteById(id);
        checkCzlSearchRepository.deleteById(id);
    }

    /**
     * Search for the checkCzl corresponding to the query.
     *
     * @param query the query of the search.
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<CheckCzlDTO> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of CheckCzls for query {}", query);
        return checkCzlSearchRepository.search(queryStringQuery(query), pageable).map(checkCzlMapper::toDto);
    }
}
