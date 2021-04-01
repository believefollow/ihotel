package ihotel.app.service;

import static org.elasticsearch.index.query.QueryBuilders.*;

import ihotel.app.domain.CzCzl2;
import ihotel.app.repository.CzCzl2Repository;
import ihotel.app.repository.search.CzCzl2SearchRepository;
import ihotel.app.service.dto.CzCzl2DTO;
import ihotel.app.service.mapper.CzCzl2Mapper;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link CzCzl2}.
 */
@Service
@Transactional
public class CzCzl2Service {

    private final Logger log = LoggerFactory.getLogger(CzCzl2Service.class);

    private final CzCzl2Repository czCzl2Repository;

    private final CzCzl2Mapper czCzl2Mapper;

    private final CzCzl2SearchRepository czCzl2SearchRepository;

    public CzCzl2Service(CzCzl2Repository czCzl2Repository, CzCzl2Mapper czCzl2Mapper, CzCzl2SearchRepository czCzl2SearchRepository) {
        this.czCzl2Repository = czCzl2Repository;
        this.czCzl2Mapper = czCzl2Mapper;
        this.czCzl2SearchRepository = czCzl2SearchRepository;
    }

    /**
     * Save a czCzl2.
     *
     * @param czCzl2DTO the entity to save.
     * @return the persisted entity.
     */
    public CzCzl2DTO save(CzCzl2DTO czCzl2DTO) {
        log.debug("Request to save CzCzl2 : {}", czCzl2DTO);
        CzCzl2 czCzl2 = czCzl2Mapper.toEntity(czCzl2DTO);
        czCzl2 = czCzl2Repository.save(czCzl2);
        CzCzl2DTO result = czCzl2Mapper.toDto(czCzl2);
        czCzl2SearchRepository.save(czCzl2);
        return result;
    }

    /**
     * Partially update a czCzl2.
     *
     * @param czCzl2DTO the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<CzCzl2DTO> partialUpdate(CzCzl2DTO czCzl2DTO) {
        log.debug("Request to partially update CzCzl2 : {}", czCzl2DTO);

        return czCzl2Repository
            .findById(czCzl2DTO.getId())
            .map(
                existingCzCzl2 -> {
                    czCzl2Mapper.partialUpdate(existingCzCzl2, czCzl2DTO);
                    return existingCzCzl2;
                }
            )
            .map(czCzl2Repository::save)
            .map(
                savedCzCzl2 -> {
                    czCzl2SearchRepository.save(savedCzCzl2);

                    return savedCzCzl2;
                }
            )
            .map(czCzl2Mapper::toDto);
    }

    /**
     * Get all the czCzl2s.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<CzCzl2DTO> findAll(Pageable pageable) {
        log.debug("Request to get all CzCzl2s");
        return czCzl2Repository.findAll(pageable).map(czCzl2Mapper::toDto);
    }

    /**
     * Get one czCzl2 by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<CzCzl2DTO> findOne(Long id) {
        log.debug("Request to get CzCzl2 : {}", id);
        return czCzl2Repository.findById(id).map(czCzl2Mapper::toDto);
    }

    /**
     * Delete the czCzl2 by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete CzCzl2 : {}", id);
        czCzl2Repository.deleteById(id);
        czCzl2SearchRepository.deleteById(id);
    }

    /**
     * Search for the czCzl2 corresponding to the query.
     *
     * @param query the query of the search.
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<CzCzl2DTO> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of CzCzl2s for query {}", query);
        return czCzl2SearchRepository.search(queryStringQuery(query), pageable).map(czCzl2Mapper::toDto);
    }
}
