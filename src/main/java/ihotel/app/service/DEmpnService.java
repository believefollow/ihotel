package ihotel.app.service;

import static org.elasticsearch.index.query.QueryBuilders.*;

import ihotel.app.domain.DEmpn;
import ihotel.app.repository.DEmpnRepository;
import ihotel.app.repository.search.DEmpnSearchRepository;
import ihotel.app.service.dto.DEmpnDTO;
import ihotel.app.service.mapper.DEmpnMapper;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link DEmpn}.
 */
@Service
@Transactional
public class DEmpnService {

    private final Logger log = LoggerFactory.getLogger(DEmpnService.class);

    private final DEmpnRepository dEmpnRepository;

    private final DEmpnMapper dEmpnMapper;

    private final DEmpnSearchRepository dEmpnSearchRepository;

    public DEmpnService(DEmpnRepository dEmpnRepository, DEmpnMapper dEmpnMapper, DEmpnSearchRepository dEmpnSearchRepository) {
        this.dEmpnRepository = dEmpnRepository;
        this.dEmpnMapper = dEmpnMapper;
        this.dEmpnSearchRepository = dEmpnSearchRepository;
    }

    /**
     * Save a dEmpn.
     *
     * @param dEmpnDTO the entity to save.
     * @return the persisted entity.
     */
    public DEmpnDTO save(DEmpnDTO dEmpnDTO) {
        log.debug("Request to save DEmpn : {}", dEmpnDTO);
        DEmpn dEmpn = dEmpnMapper.toEntity(dEmpnDTO);
        dEmpn = dEmpnRepository.save(dEmpn);
        DEmpnDTO result = dEmpnMapper.toDto(dEmpn);
        dEmpnSearchRepository.save(dEmpn);
        return result;
    }

    /**
     * Partially update a dEmpn.
     *
     * @param dEmpnDTO the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<DEmpnDTO> partialUpdate(DEmpnDTO dEmpnDTO) {
        log.debug("Request to partially update DEmpn : {}", dEmpnDTO);

        return dEmpnRepository
            .findById(dEmpnDTO.getId())
            .map(
                existingDEmpn -> {
                    dEmpnMapper.partialUpdate(existingDEmpn, dEmpnDTO);
                    return existingDEmpn;
                }
            )
            .map(dEmpnRepository::save)
            .map(
                savedDEmpn -> {
                    dEmpnSearchRepository.save(savedDEmpn);

                    return savedDEmpn;
                }
            )
            .map(dEmpnMapper::toDto);
    }

    /**
     * Get all the dEmpns.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<DEmpnDTO> findAll(Pageable pageable) {
        log.debug("Request to get all DEmpns");
        return dEmpnRepository.findAll(pageable).map(dEmpnMapper::toDto);
    }

    /**
     * Get one dEmpn by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<DEmpnDTO> findOne(Long id) {
        log.debug("Request to get DEmpn : {}", id);
        return dEmpnRepository.findById(id).map(dEmpnMapper::toDto);
    }

    /**
     * Delete the dEmpn by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete DEmpn : {}", id);
        dEmpnRepository.deleteById(id);
        dEmpnSearchRepository.deleteById(id);
    }

    /**
     * Search for the dEmpn corresponding to the query.
     *
     * @param query the query of the search.
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<DEmpnDTO> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of DEmpns for query {}", query);
        return dEmpnSearchRepository.search(queryStringQuery(query), pageable).map(dEmpnMapper::toDto);
    }
}
