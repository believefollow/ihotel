package ihotel.app.service;

import static org.elasticsearch.index.query.QueryBuilders.*;

import ihotel.app.domain.FwWxf;
import ihotel.app.repository.FwWxfRepository;
import ihotel.app.repository.search.FwWxfSearchRepository;
import ihotel.app.service.dto.FwWxfDTO;
import ihotel.app.service.mapper.FwWxfMapper;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link FwWxf}.
 */
@Service
@Transactional
public class FwWxfService {

    private final Logger log = LoggerFactory.getLogger(FwWxfService.class);

    private final FwWxfRepository fwWxfRepository;

    private final FwWxfMapper fwWxfMapper;

    private final FwWxfSearchRepository fwWxfSearchRepository;

    public FwWxfService(FwWxfRepository fwWxfRepository, FwWxfMapper fwWxfMapper, FwWxfSearchRepository fwWxfSearchRepository) {
        this.fwWxfRepository = fwWxfRepository;
        this.fwWxfMapper = fwWxfMapper;
        this.fwWxfSearchRepository = fwWxfSearchRepository;
    }

    /**
     * Save a fwWxf.
     *
     * @param fwWxfDTO the entity to save.
     * @return the persisted entity.
     */
    public FwWxfDTO save(FwWxfDTO fwWxfDTO) {
        log.debug("Request to save FwWxf : {}", fwWxfDTO);
        FwWxf fwWxf = fwWxfMapper.toEntity(fwWxfDTO);
        fwWxf = fwWxfRepository.save(fwWxf);
        FwWxfDTO result = fwWxfMapper.toDto(fwWxf);
        fwWxfSearchRepository.save(fwWxf);
        return result;
    }

    /**
     * Partially update a fwWxf.
     *
     * @param fwWxfDTO the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<FwWxfDTO> partialUpdate(FwWxfDTO fwWxfDTO) {
        log.debug("Request to partially update FwWxf : {}", fwWxfDTO);

        return fwWxfRepository
            .findById(fwWxfDTO.getId())
            .map(
                existingFwWxf -> {
                    fwWxfMapper.partialUpdate(existingFwWxf, fwWxfDTO);
                    return existingFwWxf;
                }
            )
            .map(fwWxfRepository::save)
            .map(
                savedFwWxf -> {
                    fwWxfSearchRepository.save(savedFwWxf);

                    return savedFwWxf;
                }
            )
            .map(fwWxfMapper::toDto);
    }

    /**
     * Get all the fwWxfs.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<FwWxfDTO> findAll(Pageable pageable) {
        log.debug("Request to get all FwWxfs");
        return fwWxfRepository.findAll(pageable).map(fwWxfMapper::toDto);
    }

    /**
     * Get one fwWxf by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<FwWxfDTO> findOne(Long id) {
        log.debug("Request to get FwWxf : {}", id);
        return fwWxfRepository.findById(id).map(fwWxfMapper::toDto);
    }

    /**
     * Delete the fwWxf by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete FwWxf : {}", id);
        fwWxfRepository.deleteById(id);
        fwWxfSearchRepository.deleteById(id);
    }

    /**
     * Search for the fwWxf corresponding to the query.
     *
     * @param query the query of the search.
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<FwWxfDTO> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of FwWxfs for query {}", query);
        return fwWxfSearchRepository.search(queryStringQuery(query), pageable).map(fwWxfMapper::toDto);
    }
}
