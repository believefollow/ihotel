package ihotel.app.service;

import static org.elasticsearch.index.query.QueryBuilders.*;

import ihotel.app.domain.CheckCzl2;
import ihotel.app.repository.CheckCzl2Repository;
import ihotel.app.repository.search.CheckCzl2SearchRepository;
import ihotel.app.service.dto.CheckCzl2DTO;
import ihotel.app.service.mapper.CheckCzl2Mapper;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link CheckCzl2}.
 */
@Service
@Transactional
public class CheckCzl2Service {

    private final Logger log = LoggerFactory.getLogger(CheckCzl2Service.class);

    private final CheckCzl2Repository checkCzl2Repository;

    private final CheckCzl2Mapper checkCzl2Mapper;

    private final CheckCzl2SearchRepository checkCzl2SearchRepository;

    public CheckCzl2Service(
        CheckCzl2Repository checkCzl2Repository,
        CheckCzl2Mapper checkCzl2Mapper,
        CheckCzl2SearchRepository checkCzl2SearchRepository
    ) {
        this.checkCzl2Repository = checkCzl2Repository;
        this.checkCzl2Mapper = checkCzl2Mapper;
        this.checkCzl2SearchRepository = checkCzl2SearchRepository;
    }

    /**
     * Save a checkCzl2.
     *
     * @param checkCzl2DTO the entity to save.
     * @return the persisted entity.
     */
    public CheckCzl2DTO save(CheckCzl2DTO checkCzl2DTO) {
        log.debug("Request to save CheckCzl2 : {}", checkCzl2DTO);
        CheckCzl2 checkCzl2 = checkCzl2Mapper.toEntity(checkCzl2DTO);
        checkCzl2 = checkCzl2Repository.save(checkCzl2);
        CheckCzl2DTO result = checkCzl2Mapper.toDto(checkCzl2);
        checkCzl2SearchRepository.save(checkCzl2);
        return result;
    }

    /**
     * Partially update a checkCzl2.
     *
     * @param checkCzl2DTO the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<CheckCzl2DTO> partialUpdate(CheckCzl2DTO checkCzl2DTO) {
        log.debug("Request to partially update CheckCzl2 : {}", checkCzl2DTO);

        return checkCzl2Repository
            .findById(checkCzl2DTO.getId())
            .map(
                existingCheckCzl2 -> {
                    checkCzl2Mapper.partialUpdate(existingCheckCzl2, checkCzl2DTO);
                    return existingCheckCzl2;
                }
            )
            .map(checkCzl2Repository::save)
            .map(
                savedCheckCzl2 -> {
                    checkCzl2SearchRepository.save(savedCheckCzl2);

                    return savedCheckCzl2;
                }
            )
            .map(checkCzl2Mapper::toDto);
    }

    /**
     * Get all the checkCzl2s.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<CheckCzl2DTO> findAll(Pageable pageable) {
        log.debug("Request to get all CheckCzl2s");
        return checkCzl2Repository.findAll(pageable).map(checkCzl2Mapper::toDto);
    }

    /**
     * Get one checkCzl2 by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<CheckCzl2DTO> findOne(Long id) {
        log.debug("Request to get CheckCzl2 : {}", id);
        return checkCzl2Repository.findById(id).map(checkCzl2Mapper::toDto);
    }

    /**
     * Delete the checkCzl2 by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete CheckCzl2 : {}", id);
        checkCzl2Repository.deleteById(id);
        checkCzl2SearchRepository.deleteById(id);
    }

    /**
     * Search for the checkCzl2 corresponding to the query.
     *
     * @param query the query of the search.
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<CheckCzl2DTO> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of CheckCzl2s for query {}", query);
        return checkCzl2SearchRepository.search(queryStringQuery(query), pageable).map(checkCzl2Mapper::toDto);
    }
}
