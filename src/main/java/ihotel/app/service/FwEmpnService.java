package ihotel.app.service;

import static org.elasticsearch.index.query.QueryBuilders.*;

import ihotel.app.domain.FwEmpn;
import ihotel.app.repository.FwEmpnRepository;
import ihotel.app.repository.search.FwEmpnSearchRepository;
import ihotel.app.service.dto.FwEmpnDTO;
import ihotel.app.service.mapper.FwEmpnMapper;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link FwEmpn}.
 */
@Service
@Transactional
public class FwEmpnService {

    private final Logger log = LoggerFactory.getLogger(FwEmpnService.class);

    private final FwEmpnRepository fwEmpnRepository;

    private final FwEmpnMapper fwEmpnMapper;

    private final FwEmpnSearchRepository fwEmpnSearchRepository;

    public FwEmpnService(FwEmpnRepository fwEmpnRepository, FwEmpnMapper fwEmpnMapper, FwEmpnSearchRepository fwEmpnSearchRepository) {
        this.fwEmpnRepository = fwEmpnRepository;
        this.fwEmpnMapper = fwEmpnMapper;
        this.fwEmpnSearchRepository = fwEmpnSearchRepository;
    }

    /**
     * Save a fwEmpn.
     *
     * @param fwEmpnDTO the entity to save.
     * @return the persisted entity.
     */
    public FwEmpnDTO save(FwEmpnDTO fwEmpnDTO) {
        log.debug("Request to save FwEmpn : {}", fwEmpnDTO);
        FwEmpn fwEmpn = fwEmpnMapper.toEntity(fwEmpnDTO);
        fwEmpn = fwEmpnRepository.save(fwEmpn);
        FwEmpnDTO result = fwEmpnMapper.toDto(fwEmpn);
        fwEmpnSearchRepository.save(fwEmpn);
        return result;
    }

    /**
     * Partially update a fwEmpn.
     *
     * @param fwEmpnDTO the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<FwEmpnDTO> partialUpdate(FwEmpnDTO fwEmpnDTO) {
        log.debug("Request to partially update FwEmpn : {}", fwEmpnDTO);

        return fwEmpnRepository
            .findById(fwEmpnDTO.getId())
            .map(
                existingFwEmpn -> {
                    fwEmpnMapper.partialUpdate(existingFwEmpn, fwEmpnDTO);
                    return existingFwEmpn;
                }
            )
            .map(fwEmpnRepository::save)
            .map(
                savedFwEmpn -> {
                    fwEmpnSearchRepository.save(savedFwEmpn);

                    return savedFwEmpn;
                }
            )
            .map(fwEmpnMapper::toDto);
    }

    /**
     * Get all the fwEmpns.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<FwEmpnDTO> findAll(Pageable pageable) {
        log.debug("Request to get all FwEmpns");
        return fwEmpnRepository.findAll(pageable).map(fwEmpnMapper::toDto);
    }

    /**
     * Get one fwEmpn by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<FwEmpnDTO> findOne(Long id) {
        log.debug("Request to get FwEmpn : {}", id);
        return fwEmpnRepository.findById(id).map(fwEmpnMapper::toDto);
    }

    /**
     * Delete the fwEmpn by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete FwEmpn : {}", id);
        fwEmpnRepository.deleteById(id);
        fwEmpnSearchRepository.deleteById(id);
    }

    /**
     * Search for the fwEmpn corresponding to the query.
     *
     * @param query the query of the search.
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<FwEmpnDTO> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of FwEmpns for query {}", query);
        return fwEmpnSearchRepository.search(queryStringQuery(query), pageable).map(fwEmpnMapper::toDto);
    }
}
