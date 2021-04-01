package ihotel.app.service;

import static org.elasticsearch.index.query.QueryBuilders.*;

import ihotel.app.domain.DDepot;
import ihotel.app.repository.DDepotRepository;
import ihotel.app.repository.search.DDepotSearchRepository;
import ihotel.app.service.dto.DDepotDTO;
import ihotel.app.service.mapper.DDepotMapper;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link DDepot}.
 */
@Service
@Transactional
public class DDepotService {

    private final Logger log = LoggerFactory.getLogger(DDepotService.class);

    private final DDepotRepository dDepotRepository;

    private final DDepotMapper dDepotMapper;

    private final DDepotSearchRepository dDepotSearchRepository;

    public DDepotService(DDepotRepository dDepotRepository, DDepotMapper dDepotMapper, DDepotSearchRepository dDepotSearchRepository) {
        this.dDepotRepository = dDepotRepository;
        this.dDepotMapper = dDepotMapper;
        this.dDepotSearchRepository = dDepotSearchRepository;
    }

    /**
     * Save a dDepot.
     *
     * @param dDepotDTO the entity to save.
     * @return the persisted entity.
     */
    public DDepotDTO save(DDepotDTO dDepotDTO) {
        log.debug("Request to save DDepot : {}", dDepotDTO);
        DDepot dDepot = dDepotMapper.toEntity(dDepotDTO);
        dDepot = dDepotRepository.save(dDepot);
        DDepotDTO result = dDepotMapper.toDto(dDepot);
        dDepotSearchRepository.save(dDepot);
        return result;
    }

    /**
     * Partially update a dDepot.
     *
     * @param dDepotDTO the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<DDepotDTO> partialUpdate(DDepotDTO dDepotDTO) {
        log.debug("Request to partially update DDepot : {}", dDepotDTO);

        return dDepotRepository
            .findById(dDepotDTO.getId())
            .map(
                existingDDepot -> {
                    dDepotMapper.partialUpdate(existingDDepot, dDepotDTO);
                    return existingDDepot;
                }
            )
            .map(dDepotRepository::save)
            .map(
                savedDDepot -> {
                    dDepotSearchRepository.save(savedDDepot);

                    return savedDDepot;
                }
            )
            .map(dDepotMapper::toDto);
    }

    /**
     * Get all the dDepots.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<DDepotDTO> findAll(Pageable pageable) {
        log.debug("Request to get all DDepots");
        return dDepotRepository.findAll(pageable).map(dDepotMapper::toDto);
    }

    /**
     * Get one dDepot by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<DDepotDTO> findOne(Long id) {
        log.debug("Request to get DDepot : {}", id);
        return dDepotRepository.findById(id).map(dDepotMapper::toDto);
    }

    /**
     * Delete the dDepot by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete DDepot : {}", id);
        dDepotRepository.deleteById(id);
        dDepotSearchRepository.deleteById(id);
    }

    /**
     * Search for the dDepot corresponding to the query.
     *
     * @param query the query of the search.
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<DDepotDTO> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of DDepots for query {}", query);
        return dDepotSearchRepository.search(queryStringQuery(query), pageable).map(dDepotMapper::toDto);
    }
}
