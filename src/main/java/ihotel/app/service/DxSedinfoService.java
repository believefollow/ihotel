package ihotel.app.service;

import static org.elasticsearch.index.query.QueryBuilders.*;

import ihotel.app.domain.DxSedinfo;
import ihotel.app.repository.DxSedinfoRepository;
import ihotel.app.repository.search.DxSedinfoSearchRepository;
import ihotel.app.service.dto.DxSedinfoDTO;
import ihotel.app.service.mapper.DxSedinfoMapper;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link DxSedinfo}.
 */
@Service
@Transactional
public class DxSedinfoService {

    private final Logger log = LoggerFactory.getLogger(DxSedinfoService.class);

    private final DxSedinfoRepository dxSedinfoRepository;

    private final DxSedinfoMapper dxSedinfoMapper;

    private final DxSedinfoSearchRepository dxSedinfoSearchRepository;

    public DxSedinfoService(
        DxSedinfoRepository dxSedinfoRepository,
        DxSedinfoMapper dxSedinfoMapper,
        DxSedinfoSearchRepository dxSedinfoSearchRepository
    ) {
        this.dxSedinfoRepository = dxSedinfoRepository;
        this.dxSedinfoMapper = dxSedinfoMapper;
        this.dxSedinfoSearchRepository = dxSedinfoSearchRepository;
    }

    /**
     * Save a dxSedinfo.
     *
     * @param dxSedinfoDTO the entity to save.
     * @return the persisted entity.
     */
    public DxSedinfoDTO save(DxSedinfoDTO dxSedinfoDTO) {
        log.debug("Request to save DxSedinfo : {}", dxSedinfoDTO);
        DxSedinfo dxSedinfo = dxSedinfoMapper.toEntity(dxSedinfoDTO);
        dxSedinfo = dxSedinfoRepository.save(dxSedinfo);
        DxSedinfoDTO result = dxSedinfoMapper.toDto(dxSedinfo);
        dxSedinfoSearchRepository.save(dxSedinfo);
        return result;
    }

    /**
     * Partially update a dxSedinfo.
     *
     * @param dxSedinfoDTO the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<DxSedinfoDTO> partialUpdate(DxSedinfoDTO dxSedinfoDTO) {
        log.debug("Request to partially update DxSedinfo : {}", dxSedinfoDTO);

        return dxSedinfoRepository
            .findById(dxSedinfoDTO.getId())
            .map(
                existingDxSedinfo -> {
                    dxSedinfoMapper.partialUpdate(existingDxSedinfo, dxSedinfoDTO);
                    return existingDxSedinfo;
                }
            )
            .map(dxSedinfoRepository::save)
            .map(
                savedDxSedinfo -> {
                    dxSedinfoSearchRepository.save(savedDxSedinfo);

                    return savedDxSedinfo;
                }
            )
            .map(dxSedinfoMapper::toDto);
    }

    /**
     * Get all the dxSedinfos.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<DxSedinfoDTO> findAll(Pageable pageable) {
        log.debug("Request to get all DxSedinfos");
        return dxSedinfoRepository.findAll(pageable).map(dxSedinfoMapper::toDto);
    }

    /**
     * Get one dxSedinfo by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<DxSedinfoDTO> findOne(Long id) {
        log.debug("Request to get DxSedinfo : {}", id);
        return dxSedinfoRepository.findById(id).map(dxSedinfoMapper::toDto);
    }

    /**
     * Delete the dxSedinfo by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete DxSedinfo : {}", id);
        dxSedinfoRepository.deleteById(id);
        dxSedinfoSearchRepository.deleteById(id);
    }

    /**
     * Search for the dxSedinfo corresponding to the query.
     *
     * @param query the query of the search.
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<DxSedinfoDTO> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of DxSedinfos for query {}", query);
        return dxSedinfoSearchRepository.search(queryStringQuery(query), pageable).map(dxSedinfoMapper::toDto);
    }
}
