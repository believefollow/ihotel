package ihotel.app.service;

import static org.elasticsearch.index.query.QueryBuilders.*;

import ihotel.app.domain.FwJywp;
import ihotel.app.repository.FwJywpRepository;
import ihotel.app.repository.search.FwJywpSearchRepository;
import ihotel.app.service.dto.FwJywpDTO;
import ihotel.app.service.mapper.FwJywpMapper;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link FwJywp}.
 */
@Service
@Transactional
public class FwJywpService {

    private final Logger log = LoggerFactory.getLogger(FwJywpService.class);

    private final FwJywpRepository fwJywpRepository;

    private final FwJywpMapper fwJywpMapper;

    private final FwJywpSearchRepository fwJywpSearchRepository;

    public FwJywpService(FwJywpRepository fwJywpRepository, FwJywpMapper fwJywpMapper, FwJywpSearchRepository fwJywpSearchRepository) {
        this.fwJywpRepository = fwJywpRepository;
        this.fwJywpMapper = fwJywpMapper;
        this.fwJywpSearchRepository = fwJywpSearchRepository;
    }

    /**
     * Save a fwJywp.
     *
     * @param fwJywpDTO the entity to save.
     * @return the persisted entity.
     */
    public FwJywpDTO save(FwJywpDTO fwJywpDTO) {
        log.debug("Request to save FwJywp : {}", fwJywpDTO);
        FwJywp fwJywp = fwJywpMapper.toEntity(fwJywpDTO);
        fwJywp = fwJywpRepository.save(fwJywp);
        FwJywpDTO result = fwJywpMapper.toDto(fwJywp);
        fwJywpSearchRepository.save(fwJywp);
        return result;
    }

    /**
     * Partially update a fwJywp.
     *
     * @param fwJywpDTO the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<FwJywpDTO> partialUpdate(FwJywpDTO fwJywpDTO) {
        log.debug("Request to partially update FwJywp : {}", fwJywpDTO);

        return fwJywpRepository
            .findById(fwJywpDTO.getId())
            .map(
                existingFwJywp -> {
                    fwJywpMapper.partialUpdate(existingFwJywp, fwJywpDTO);
                    return existingFwJywp;
                }
            )
            .map(fwJywpRepository::save)
            .map(
                savedFwJywp -> {
                    fwJywpSearchRepository.save(savedFwJywp);

                    return savedFwJywp;
                }
            )
            .map(fwJywpMapper::toDto);
    }

    /**
     * Get all the fwJywps.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<FwJywpDTO> findAll(Pageable pageable) {
        log.debug("Request to get all FwJywps");
        return fwJywpRepository.findAll(pageable).map(fwJywpMapper::toDto);
    }

    /**
     * Get one fwJywp by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<FwJywpDTO> findOne(Long id) {
        log.debug("Request to get FwJywp : {}", id);
        return fwJywpRepository.findById(id).map(fwJywpMapper::toDto);
    }

    /**
     * Delete the fwJywp by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete FwJywp : {}", id);
        fwJywpRepository.deleteById(id);
        fwJywpSearchRepository.deleteById(id);
    }

    /**
     * Search for the fwJywp corresponding to the query.
     *
     * @param query the query of the search.
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<FwJywpDTO> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of FwJywps for query {}", query);
        return fwJywpSearchRepository.search(queryStringQuery(query), pageable).map(fwJywpMapper::toDto);
    }
}
