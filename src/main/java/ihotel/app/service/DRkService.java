package ihotel.app.service;

import static org.elasticsearch.index.query.QueryBuilders.*;

import ihotel.app.domain.DRk;
import ihotel.app.repository.DRkRepository;
import ihotel.app.repository.search.DRkSearchRepository;
import ihotel.app.service.dto.DRkDTO;
import ihotel.app.service.mapper.DRkMapper;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link DRk}.
 */
@Service
@Transactional
public class DRkService {

    private final Logger log = LoggerFactory.getLogger(DRkService.class);

    private final DRkRepository dRkRepository;

    private final DRkMapper dRkMapper;

    private final DRkSearchRepository dRkSearchRepository;

    public DRkService(DRkRepository dRkRepository, DRkMapper dRkMapper, DRkSearchRepository dRkSearchRepository) {
        this.dRkRepository = dRkRepository;
        this.dRkMapper = dRkMapper;
        this.dRkSearchRepository = dRkSearchRepository;
    }

    /**
     * Save a dRk.
     *
     * @param dRkDTO the entity to save.
     * @return the persisted entity.
     */
    public DRkDTO save(DRkDTO dRkDTO) {
        log.debug("Request to save DRk : {}", dRkDTO);
        DRk dRk = dRkMapper.toEntity(dRkDTO);
        dRk = dRkRepository.save(dRk);
        DRkDTO result = dRkMapper.toDto(dRk);
        dRkSearchRepository.save(dRk);
        return result;
    }

    /**
     * Partially update a dRk.
     *
     * @param dRkDTO the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<DRkDTO> partialUpdate(DRkDTO dRkDTO) {
        log.debug("Request to partially update DRk : {}", dRkDTO);

        return dRkRepository
            .findById(dRkDTO.getId())
            .map(
                existingDRk -> {
                    dRkMapper.partialUpdate(existingDRk, dRkDTO);
                    return existingDRk;
                }
            )
            .map(dRkRepository::save)
            .map(
                savedDRk -> {
                    dRkSearchRepository.save(savedDRk);

                    return savedDRk;
                }
            )
            .map(dRkMapper::toDto);
    }

    /**
     * Get all the dRks.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<DRkDTO> findAll(Pageable pageable) {
        log.debug("Request to get all DRks");
        return dRkRepository.findAll(pageable).map(dRkMapper::toDto);
    }

    /**
     * Get one dRk by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<DRkDTO> findOne(Long id) {
        log.debug("Request to get DRk : {}", id);
        return dRkRepository.findById(id).map(dRkMapper::toDto);
    }

    /**
     * Delete the dRk by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete DRk : {}", id);
        dRkRepository.deleteById(id);
        dRkSearchRepository.deleteById(id);
    }

    /**
     * Search for the dRk corresponding to the query.
     *
     * @param query the query of the search.
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<DRkDTO> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of DRks for query {}", query);
        return dRkSearchRepository.search(queryStringQuery(query), pageable).map(dRkMapper::toDto);
    }
}
