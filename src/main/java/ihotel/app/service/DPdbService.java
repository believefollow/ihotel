package ihotel.app.service;

import static org.elasticsearch.index.query.QueryBuilders.*;

import ihotel.app.domain.DPdb;
import ihotel.app.repository.DPdbRepository;
import ihotel.app.repository.search.DPdbSearchRepository;
import ihotel.app.service.dto.DPdbDTO;
import ihotel.app.service.mapper.DPdbMapper;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link DPdb}.
 */
@Service
@Transactional
public class DPdbService {

    private final Logger log = LoggerFactory.getLogger(DPdbService.class);

    private final DPdbRepository dPdbRepository;

    private final DPdbMapper dPdbMapper;

    private final DPdbSearchRepository dPdbSearchRepository;

    public DPdbService(DPdbRepository dPdbRepository, DPdbMapper dPdbMapper, DPdbSearchRepository dPdbSearchRepository) {
        this.dPdbRepository = dPdbRepository;
        this.dPdbMapper = dPdbMapper;
        this.dPdbSearchRepository = dPdbSearchRepository;
    }

    /**
     * Save a dPdb.
     *
     * @param dPdbDTO the entity to save.
     * @return the persisted entity.
     */
    public DPdbDTO save(DPdbDTO dPdbDTO) {
        log.debug("Request to save DPdb : {}", dPdbDTO);
        DPdb dPdb = dPdbMapper.toEntity(dPdbDTO);
        dPdb = dPdbRepository.save(dPdb);
        DPdbDTO result = dPdbMapper.toDto(dPdb);
        dPdbSearchRepository.save(dPdb);
        return result;
    }

    /**
     * Partially update a dPdb.
     *
     * @param dPdbDTO the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<DPdbDTO> partialUpdate(DPdbDTO dPdbDTO) {
        log.debug("Request to partially update DPdb : {}", dPdbDTO);

        return dPdbRepository
            .findById(dPdbDTO.getId())
            .map(
                existingDPdb -> {
                    dPdbMapper.partialUpdate(existingDPdb, dPdbDTO);
                    return existingDPdb;
                }
            )
            .map(dPdbRepository::save)
            .map(
                savedDPdb -> {
                    dPdbSearchRepository.save(savedDPdb);

                    return savedDPdb;
                }
            )
            .map(dPdbMapper::toDto);
    }

    /**
     * Get all the dPdbs.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<DPdbDTO> findAll(Pageable pageable) {
        log.debug("Request to get all DPdbs");
        return dPdbRepository.findAll(pageable).map(dPdbMapper::toDto);
    }

    /**
     * Get one dPdb by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<DPdbDTO> findOne(Long id) {
        log.debug("Request to get DPdb : {}", id);
        return dPdbRepository.findById(id).map(dPdbMapper::toDto);
    }

    /**
     * Delete the dPdb by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete DPdb : {}", id);
        dPdbRepository.deleteById(id);
        dPdbSearchRepository.deleteById(id);
    }

    /**
     * Search for the dPdb corresponding to the query.
     *
     * @param query the query of the search.
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<DPdbDTO> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of DPdbs for query {}", query);
        return dPdbSearchRepository.search(queryStringQuery(query), pageable).map(dPdbMapper::toDto);
    }
}
