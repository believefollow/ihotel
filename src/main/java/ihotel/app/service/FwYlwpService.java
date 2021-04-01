package ihotel.app.service;

import static org.elasticsearch.index.query.QueryBuilders.*;

import ihotel.app.domain.FwYlwp;
import ihotel.app.repository.FwYlwpRepository;
import ihotel.app.repository.search.FwYlwpSearchRepository;
import ihotel.app.service.dto.FwYlwpDTO;
import ihotel.app.service.mapper.FwYlwpMapper;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link FwYlwp}.
 */
@Service
@Transactional
public class FwYlwpService {

    private final Logger log = LoggerFactory.getLogger(FwYlwpService.class);

    private final FwYlwpRepository fwYlwpRepository;

    private final FwYlwpMapper fwYlwpMapper;

    private final FwYlwpSearchRepository fwYlwpSearchRepository;

    public FwYlwpService(FwYlwpRepository fwYlwpRepository, FwYlwpMapper fwYlwpMapper, FwYlwpSearchRepository fwYlwpSearchRepository) {
        this.fwYlwpRepository = fwYlwpRepository;
        this.fwYlwpMapper = fwYlwpMapper;
        this.fwYlwpSearchRepository = fwYlwpSearchRepository;
    }

    /**
     * Save a fwYlwp.
     *
     * @param fwYlwpDTO the entity to save.
     * @return the persisted entity.
     */
    public FwYlwpDTO save(FwYlwpDTO fwYlwpDTO) {
        log.debug("Request to save FwYlwp : {}", fwYlwpDTO);
        FwYlwp fwYlwp = fwYlwpMapper.toEntity(fwYlwpDTO);
        fwYlwp = fwYlwpRepository.save(fwYlwp);
        FwYlwpDTO result = fwYlwpMapper.toDto(fwYlwp);
        fwYlwpSearchRepository.save(fwYlwp);
        return result;
    }

    /**
     * Partially update a fwYlwp.
     *
     * @param fwYlwpDTO the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<FwYlwpDTO> partialUpdate(FwYlwpDTO fwYlwpDTO) {
        log.debug("Request to partially update FwYlwp : {}", fwYlwpDTO);

        return fwYlwpRepository
            .findById(fwYlwpDTO.getId())
            .map(
                existingFwYlwp -> {
                    fwYlwpMapper.partialUpdate(existingFwYlwp, fwYlwpDTO);
                    return existingFwYlwp;
                }
            )
            .map(fwYlwpRepository::save)
            .map(
                savedFwYlwp -> {
                    fwYlwpSearchRepository.save(savedFwYlwp);

                    return savedFwYlwp;
                }
            )
            .map(fwYlwpMapper::toDto);
    }

    /**
     * Get all the fwYlwps.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<FwYlwpDTO> findAll(Pageable pageable) {
        log.debug("Request to get all FwYlwps");
        return fwYlwpRepository.findAll(pageable).map(fwYlwpMapper::toDto);
    }

    /**
     * Get one fwYlwp by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<FwYlwpDTO> findOne(Long id) {
        log.debug("Request to get FwYlwp : {}", id);
        return fwYlwpRepository.findById(id).map(fwYlwpMapper::toDto);
    }

    /**
     * Delete the fwYlwp by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete FwYlwp : {}", id);
        fwYlwpRepository.deleteById(id);
        fwYlwpSearchRepository.deleteById(id);
    }

    /**
     * Search for the fwYlwp corresponding to the query.
     *
     * @param query the query of the search.
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<FwYlwpDTO> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of FwYlwps for query {}", query);
        return fwYlwpSearchRepository.search(queryStringQuery(query), pageable).map(fwYlwpMapper::toDto);
    }
}
