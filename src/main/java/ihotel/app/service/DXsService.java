package ihotel.app.service;

import static org.elasticsearch.index.query.QueryBuilders.*;

import ihotel.app.domain.DXs;
import ihotel.app.repository.DXsRepository;
import ihotel.app.repository.search.DXsSearchRepository;
import ihotel.app.service.dto.DXsDTO;
import ihotel.app.service.mapper.DXsMapper;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link DXs}.
 */
@Service
@Transactional
public class DXsService {

    private final Logger log = LoggerFactory.getLogger(DXsService.class);

    private final DXsRepository dXsRepository;

    private final DXsMapper dXsMapper;

    private final DXsSearchRepository dXsSearchRepository;

    public DXsService(DXsRepository dXsRepository, DXsMapper dXsMapper, DXsSearchRepository dXsSearchRepository) {
        this.dXsRepository = dXsRepository;
        this.dXsMapper = dXsMapper;
        this.dXsSearchRepository = dXsSearchRepository;
    }

    /**
     * Save a dXs.
     *
     * @param dXsDTO the entity to save.
     * @return the persisted entity.
     */
    public DXsDTO save(DXsDTO dXsDTO) {
        log.debug("Request to save DXs : {}", dXsDTO);
        DXs dXs = dXsMapper.toEntity(dXsDTO);
        dXs = dXsRepository.save(dXs);
        DXsDTO result = dXsMapper.toDto(dXs);
        dXsSearchRepository.save(dXs);
        return result;
    }

    /**
     * Partially update a dXs.
     *
     * @param dXsDTO the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<DXsDTO> partialUpdate(DXsDTO dXsDTO) {
        log.debug("Request to partially update DXs : {}", dXsDTO);

        return dXsRepository
            .findById(dXsDTO.getId())
            .map(
                existingDXs -> {
                    dXsMapper.partialUpdate(existingDXs, dXsDTO);
                    return existingDXs;
                }
            )
            .map(dXsRepository::save)
            .map(
                savedDXs -> {
                    dXsSearchRepository.save(savedDXs);

                    return savedDXs;
                }
            )
            .map(dXsMapper::toDto);
    }

    /**
     * Get all the dXs.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<DXsDTO> findAll(Pageable pageable) {
        log.debug("Request to get all DXs");
        return dXsRepository.findAll(pageable).map(dXsMapper::toDto);
    }

    /**
     * Get one dXs by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<DXsDTO> findOne(Long id) {
        log.debug("Request to get DXs : {}", id);
        return dXsRepository.findById(id).map(dXsMapper::toDto);
    }

    /**
     * Delete the dXs by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete DXs : {}", id);
        dXsRepository.deleteById(id);
        dXsSearchRepository.deleteById(id);
    }

    /**
     * Search for the dXs corresponding to the query.
     *
     * @param query the query of the search.
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<DXsDTO> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of DXs for query {}", query);
        return dXsSearchRepository.search(queryStringQuery(query), pageable).map(dXsMapper::toDto);
    }
}
