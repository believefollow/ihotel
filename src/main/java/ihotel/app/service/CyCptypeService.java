package ihotel.app.service;

import static org.elasticsearch.index.query.QueryBuilders.*;

import ihotel.app.domain.CyCptype;
import ihotel.app.repository.CyCptypeRepository;
import ihotel.app.repository.search.CyCptypeSearchRepository;
import ihotel.app.service.dto.CyCptypeDTO;
import ihotel.app.service.mapper.CyCptypeMapper;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link CyCptype}.
 */
@Service
@Transactional
public class CyCptypeService {

    private final Logger log = LoggerFactory.getLogger(CyCptypeService.class);

    private final CyCptypeRepository cyCptypeRepository;

    private final CyCptypeMapper cyCptypeMapper;

    private final CyCptypeSearchRepository cyCptypeSearchRepository;

    public CyCptypeService(
        CyCptypeRepository cyCptypeRepository,
        CyCptypeMapper cyCptypeMapper,
        CyCptypeSearchRepository cyCptypeSearchRepository
    ) {
        this.cyCptypeRepository = cyCptypeRepository;
        this.cyCptypeMapper = cyCptypeMapper;
        this.cyCptypeSearchRepository = cyCptypeSearchRepository;
    }

    /**
     * Save a cyCptype.
     *
     * @param cyCptypeDTO the entity to save.
     * @return the persisted entity.
     */
    public CyCptypeDTO save(CyCptypeDTO cyCptypeDTO) {
        log.debug("Request to save CyCptype : {}", cyCptypeDTO);
        CyCptype cyCptype = cyCptypeMapper.toEntity(cyCptypeDTO);
        cyCptype = cyCptypeRepository.save(cyCptype);
        CyCptypeDTO result = cyCptypeMapper.toDto(cyCptype);
        cyCptypeSearchRepository.save(cyCptype);
        return result;
    }

    /**
     * Partially update a cyCptype.
     *
     * @param cyCptypeDTO the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<CyCptypeDTO> partialUpdate(CyCptypeDTO cyCptypeDTO) {
        log.debug("Request to partially update CyCptype : {}", cyCptypeDTO);

        return cyCptypeRepository
            .findById(cyCptypeDTO.getId())
            .map(
                existingCyCptype -> {
                    cyCptypeMapper.partialUpdate(existingCyCptype, cyCptypeDTO);
                    return existingCyCptype;
                }
            )
            .map(cyCptypeRepository::save)
            .map(
                savedCyCptype -> {
                    cyCptypeSearchRepository.save(savedCyCptype);

                    return savedCyCptype;
                }
            )
            .map(cyCptypeMapper::toDto);
    }

    /**
     * Get all the cyCptypes.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<CyCptypeDTO> findAll(Pageable pageable) {
        log.debug("Request to get all CyCptypes");
        return cyCptypeRepository.findAll(pageable).map(cyCptypeMapper::toDto);
    }

    /**
     * Get one cyCptype by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<CyCptypeDTO> findOne(Long id) {
        log.debug("Request to get CyCptype : {}", id);
        return cyCptypeRepository.findById(id).map(cyCptypeMapper::toDto);
    }

    /**
     * Delete the cyCptype by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete CyCptype : {}", id);
        cyCptypeRepository.deleteById(id);
        cyCptypeSearchRepository.deleteById(id);
    }

    /**
     * Search for the cyCptype corresponding to the query.
     *
     * @param query the query of the search.
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<CyCptypeDTO> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of CyCptypes for query {}", query);
        return cyCptypeSearchRepository.search(queryStringQuery(query), pageable).map(cyCptypeMapper::toDto);
    }
}
