package ihotel.app.service;

import static org.elasticsearch.index.query.QueryBuilders.*;

import ihotel.app.domain.DSpcz;
import ihotel.app.repository.DSpczRepository;
import ihotel.app.repository.search.DSpczSearchRepository;
import ihotel.app.service.dto.DSpczDTO;
import ihotel.app.service.mapper.DSpczMapper;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link DSpcz}.
 */
@Service
@Transactional
public class DSpczService {

    private final Logger log = LoggerFactory.getLogger(DSpczService.class);

    private final DSpczRepository dSpczRepository;

    private final DSpczMapper dSpczMapper;

    private final DSpczSearchRepository dSpczSearchRepository;

    public DSpczService(DSpczRepository dSpczRepository, DSpczMapper dSpczMapper, DSpczSearchRepository dSpczSearchRepository) {
        this.dSpczRepository = dSpczRepository;
        this.dSpczMapper = dSpczMapper;
        this.dSpczSearchRepository = dSpczSearchRepository;
    }

    /**
     * Save a dSpcz.
     *
     * @param dSpczDTO the entity to save.
     * @return the persisted entity.
     */
    public DSpczDTO save(DSpczDTO dSpczDTO) {
        log.debug("Request to save DSpcz : {}", dSpczDTO);
        DSpcz dSpcz = dSpczMapper.toEntity(dSpczDTO);
        dSpcz = dSpczRepository.save(dSpcz);
        DSpczDTO result = dSpczMapper.toDto(dSpcz);
        dSpczSearchRepository.save(dSpcz);
        return result;
    }

    /**
     * Partially update a dSpcz.
     *
     * @param dSpczDTO the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<DSpczDTO> partialUpdate(DSpczDTO dSpczDTO) {
        log.debug("Request to partially update DSpcz : {}", dSpczDTO);

        return dSpczRepository
            .findById(dSpczDTO.getId())
            .map(
                existingDSpcz -> {
                    dSpczMapper.partialUpdate(existingDSpcz, dSpczDTO);
                    return existingDSpcz;
                }
            )
            .map(dSpczRepository::save)
            .map(
                savedDSpcz -> {
                    dSpczSearchRepository.save(savedDSpcz);

                    return savedDSpcz;
                }
            )
            .map(dSpczMapper::toDto);
    }

    /**
     * Get all the dSpczs.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<DSpczDTO> findAll(Pageable pageable) {
        log.debug("Request to get all DSpczs");
        return dSpczRepository.findAll(pageable).map(dSpczMapper::toDto);
    }

    /**
     * Get one dSpcz by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<DSpczDTO> findOne(Long id) {
        log.debug("Request to get DSpcz : {}", id);
        return dSpczRepository.findById(id).map(dSpczMapper::toDto);
    }

    /**
     * Delete the dSpcz by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete DSpcz : {}", id);
        dSpczRepository.deleteById(id);
        dSpczSearchRepository.deleteById(id);
    }

    /**
     * Search for the dSpcz corresponding to the query.
     *
     * @param query the query of the search.
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<DSpczDTO> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of DSpczs for query {}", query);
        return dSpczSearchRepository.search(queryStringQuery(query), pageable).map(dSpczMapper::toDto);
    }
}
