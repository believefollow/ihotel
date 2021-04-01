package ihotel.app.service;

import static org.elasticsearch.index.query.QueryBuilders.*;

import ihotel.app.domain.Crinfo;
import ihotel.app.repository.CrinfoRepository;
import ihotel.app.repository.search.CrinfoSearchRepository;
import ihotel.app.service.dto.CrinfoDTO;
import ihotel.app.service.mapper.CrinfoMapper;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link Crinfo}.
 */
@Service
@Transactional
public class CrinfoService {

    private final Logger log = LoggerFactory.getLogger(CrinfoService.class);

    private final CrinfoRepository crinfoRepository;

    private final CrinfoMapper crinfoMapper;

    private final CrinfoSearchRepository crinfoSearchRepository;

    public CrinfoService(CrinfoRepository crinfoRepository, CrinfoMapper crinfoMapper, CrinfoSearchRepository crinfoSearchRepository) {
        this.crinfoRepository = crinfoRepository;
        this.crinfoMapper = crinfoMapper;
        this.crinfoSearchRepository = crinfoSearchRepository;
    }

    /**
     * Save a crinfo.
     *
     * @param crinfoDTO the entity to save.
     * @return the persisted entity.
     */
    public CrinfoDTO save(CrinfoDTO crinfoDTO) {
        log.debug("Request to save Crinfo : {}", crinfoDTO);
        Crinfo crinfo = crinfoMapper.toEntity(crinfoDTO);
        crinfo = crinfoRepository.save(crinfo);
        CrinfoDTO result = crinfoMapper.toDto(crinfo);
        crinfoSearchRepository.save(crinfo);
        return result;
    }

    /**
     * Partially update a crinfo.
     *
     * @param crinfoDTO the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<CrinfoDTO> partialUpdate(CrinfoDTO crinfoDTO) {
        log.debug("Request to partially update Crinfo : {}", crinfoDTO);

        return crinfoRepository
            .findById(crinfoDTO.getId())
            .map(
                existingCrinfo -> {
                    crinfoMapper.partialUpdate(existingCrinfo, crinfoDTO);
                    return existingCrinfo;
                }
            )
            .map(crinfoRepository::save)
            .map(
                savedCrinfo -> {
                    crinfoSearchRepository.save(savedCrinfo);

                    return savedCrinfo;
                }
            )
            .map(crinfoMapper::toDto);
    }

    /**
     * Get all the crinfos.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<CrinfoDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Crinfos");
        return crinfoRepository.findAll(pageable).map(crinfoMapper::toDto);
    }

    /**
     * Get one crinfo by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<CrinfoDTO> findOne(Long id) {
        log.debug("Request to get Crinfo : {}", id);
        return crinfoRepository.findById(id).map(crinfoMapper::toDto);
    }

    /**
     * Delete the crinfo by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete Crinfo : {}", id);
        crinfoRepository.deleteById(id);
        crinfoSearchRepository.deleteById(id);
    }

    /**
     * Search for the crinfo corresponding to the query.
     *
     * @param query the query of the search.
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<CrinfoDTO> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of Crinfos for query {}", query);
        return crinfoSearchRepository.search(queryStringQuery(query), pageable).map(crinfoMapper::toDto);
    }
}
