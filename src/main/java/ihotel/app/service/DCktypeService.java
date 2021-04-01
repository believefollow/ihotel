package ihotel.app.service;

import static org.elasticsearch.index.query.QueryBuilders.*;

import ihotel.app.domain.DCktype;
import ihotel.app.repository.DCktypeRepository;
import ihotel.app.repository.search.DCktypeSearchRepository;
import ihotel.app.service.dto.DCktypeDTO;
import ihotel.app.service.mapper.DCktypeMapper;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link DCktype}.
 */
@Service
@Transactional
public class DCktypeService {

    private final Logger log = LoggerFactory.getLogger(DCktypeService.class);

    private final DCktypeRepository dCktypeRepository;

    private final DCktypeMapper dCktypeMapper;

    private final DCktypeSearchRepository dCktypeSearchRepository;

    public DCktypeService(
        DCktypeRepository dCktypeRepository,
        DCktypeMapper dCktypeMapper,
        DCktypeSearchRepository dCktypeSearchRepository
    ) {
        this.dCktypeRepository = dCktypeRepository;
        this.dCktypeMapper = dCktypeMapper;
        this.dCktypeSearchRepository = dCktypeSearchRepository;
    }

    /**
     * Save a dCktype.
     *
     * @param dCktypeDTO the entity to save.
     * @return the persisted entity.
     */
    public DCktypeDTO save(DCktypeDTO dCktypeDTO) {
        log.debug("Request to save DCktype : {}", dCktypeDTO);
        DCktype dCktype = dCktypeMapper.toEntity(dCktypeDTO);
        dCktype = dCktypeRepository.save(dCktype);
        DCktypeDTO result = dCktypeMapper.toDto(dCktype);
        dCktypeSearchRepository.save(dCktype);
        return result;
    }

    /**
     * Partially update a dCktype.
     *
     * @param dCktypeDTO the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<DCktypeDTO> partialUpdate(DCktypeDTO dCktypeDTO) {
        log.debug("Request to partially update DCktype : {}", dCktypeDTO);

        return dCktypeRepository
            .findById(dCktypeDTO.getId())
            .map(
                existingDCktype -> {
                    dCktypeMapper.partialUpdate(existingDCktype, dCktypeDTO);
                    return existingDCktype;
                }
            )
            .map(dCktypeRepository::save)
            .map(
                savedDCktype -> {
                    dCktypeSearchRepository.save(savedDCktype);

                    return savedDCktype;
                }
            )
            .map(dCktypeMapper::toDto);
    }

    /**
     * Get all the dCktypes.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<DCktypeDTO> findAll(Pageable pageable) {
        log.debug("Request to get all DCktypes");
        return dCktypeRepository.findAll(pageable).map(dCktypeMapper::toDto);
    }

    /**
     * Get one dCktype by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<DCktypeDTO> findOne(Long id) {
        log.debug("Request to get DCktype : {}", id);
        return dCktypeRepository.findById(id).map(dCktypeMapper::toDto);
    }

    /**
     * Delete the dCktype by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete DCktype : {}", id);
        dCktypeRepository.deleteById(id);
        dCktypeSearchRepository.deleteById(id);
    }

    /**
     * Search for the dCktype corresponding to the query.
     *
     * @param query the query of the search.
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<DCktypeDTO> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of DCktypes for query {}", query);
        return dCktypeSearchRepository.search(queryStringQuery(query), pageable).map(dCktypeMapper::toDto);
    }
}
