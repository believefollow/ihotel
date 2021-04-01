package ihotel.app.service;

import static org.elasticsearch.index.query.QueryBuilders.*;

import ihotel.app.domain.CzCzl3;
import ihotel.app.repository.CzCzl3Repository;
import ihotel.app.repository.search.CzCzl3SearchRepository;
import ihotel.app.service.dto.CzCzl3DTO;
import ihotel.app.service.mapper.CzCzl3Mapper;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link CzCzl3}.
 */
@Service
@Transactional
public class CzCzl3Service {

    private final Logger log = LoggerFactory.getLogger(CzCzl3Service.class);

    private final CzCzl3Repository czCzl3Repository;

    private final CzCzl3Mapper czCzl3Mapper;

    private final CzCzl3SearchRepository czCzl3SearchRepository;

    public CzCzl3Service(CzCzl3Repository czCzl3Repository, CzCzl3Mapper czCzl3Mapper, CzCzl3SearchRepository czCzl3SearchRepository) {
        this.czCzl3Repository = czCzl3Repository;
        this.czCzl3Mapper = czCzl3Mapper;
        this.czCzl3SearchRepository = czCzl3SearchRepository;
    }

    /**
     * Save a czCzl3.
     *
     * @param czCzl3DTO the entity to save.
     * @return the persisted entity.
     */
    public CzCzl3DTO save(CzCzl3DTO czCzl3DTO) {
        log.debug("Request to save CzCzl3 : {}", czCzl3DTO);
        CzCzl3 czCzl3 = czCzl3Mapper.toEntity(czCzl3DTO);
        czCzl3 = czCzl3Repository.save(czCzl3);
        CzCzl3DTO result = czCzl3Mapper.toDto(czCzl3);
        czCzl3SearchRepository.save(czCzl3);
        return result;
    }

    /**
     * Partially update a czCzl3.
     *
     * @param czCzl3DTO the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<CzCzl3DTO> partialUpdate(CzCzl3DTO czCzl3DTO) {
        log.debug("Request to partially update CzCzl3 : {}", czCzl3DTO);

        return czCzl3Repository
            .findById(czCzl3DTO.getId())
            .map(
                existingCzCzl3 -> {
                    czCzl3Mapper.partialUpdate(existingCzCzl3, czCzl3DTO);
                    return existingCzCzl3;
                }
            )
            .map(czCzl3Repository::save)
            .map(
                savedCzCzl3 -> {
                    czCzl3SearchRepository.save(savedCzCzl3);

                    return savedCzCzl3;
                }
            )
            .map(czCzl3Mapper::toDto);
    }

    /**
     * Get all the czCzl3s.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<CzCzl3DTO> findAll(Pageable pageable) {
        log.debug("Request to get all CzCzl3s");
        return czCzl3Repository.findAll(pageable).map(czCzl3Mapper::toDto);
    }

    /**
     * Get one czCzl3 by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<CzCzl3DTO> findOne(Long id) {
        log.debug("Request to get CzCzl3 : {}", id);
        return czCzl3Repository.findById(id).map(czCzl3Mapper::toDto);
    }

    /**
     * Delete the czCzl3 by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete CzCzl3 : {}", id);
        czCzl3Repository.deleteById(id);
        czCzl3SearchRepository.deleteById(id);
    }

    /**
     * Search for the czCzl3 corresponding to the query.
     *
     * @param query the query of the search.
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<CzCzl3DTO> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of CzCzl3s for query {}", query);
        return czCzl3SearchRepository.search(queryStringQuery(query), pageable).map(czCzl3Mapper::toDto);
    }
}
