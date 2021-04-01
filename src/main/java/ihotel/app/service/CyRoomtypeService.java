package ihotel.app.service;

import static org.elasticsearch.index.query.QueryBuilders.*;

import ihotel.app.domain.CyRoomtype;
import ihotel.app.repository.CyRoomtypeRepository;
import ihotel.app.repository.search.CyRoomtypeSearchRepository;
import ihotel.app.service.dto.CyRoomtypeDTO;
import ihotel.app.service.mapper.CyRoomtypeMapper;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link CyRoomtype}.
 */
@Service
@Transactional
public class CyRoomtypeService {

    private final Logger log = LoggerFactory.getLogger(CyRoomtypeService.class);

    private final CyRoomtypeRepository cyRoomtypeRepository;

    private final CyRoomtypeMapper cyRoomtypeMapper;

    private final CyRoomtypeSearchRepository cyRoomtypeSearchRepository;

    public CyRoomtypeService(
        CyRoomtypeRepository cyRoomtypeRepository,
        CyRoomtypeMapper cyRoomtypeMapper,
        CyRoomtypeSearchRepository cyRoomtypeSearchRepository
    ) {
        this.cyRoomtypeRepository = cyRoomtypeRepository;
        this.cyRoomtypeMapper = cyRoomtypeMapper;
        this.cyRoomtypeSearchRepository = cyRoomtypeSearchRepository;
    }

    /**
     * Save a cyRoomtype.
     *
     * @param cyRoomtypeDTO the entity to save.
     * @return the persisted entity.
     */
    public CyRoomtypeDTO save(CyRoomtypeDTO cyRoomtypeDTO) {
        log.debug("Request to save CyRoomtype : {}", cyRoomtypeDTO);
        CyRoomtype cyRoomtype = cyRoomtypeMapper.toEntity(cyRoomtypeDTO);
        cyRoomtype = cyRoomtypeRepository.save(cyRoomtype);
        CyRoomtypeDTO result = cyRoomtypeMapper.toDto(cyRoomtype);
        cyRoomtypeSearchRepository.save(cyRoomtype);
        return result;
    }

    /**
     * Partially update a cyRoomtype.
     *
     * @param cyRoomtypeDTO the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<CyRoomtypeDTO> partialUpdate(CyRoomtypeDTO cyRoomtypeDTO) {
        log.debug("Request to partially update CyRoomtype : {}", cyRoomtypeDTO);

        return cyRoomtypeRepository
            .findById(cyRoomtypeDTO.getId())
            .map(
                existingCyRoomtype -> {
                    cyRoomtypeMapper.partialUpdate(existingCyRoomtype, cyRoomtypeDTO);
                    return existingCyRoomtype;
                }
            )
            .map(cyRoomtypeRepository::save)
            .map(
                savedCyRoomtype -> {
                    cyRoomtypeSearchRepository.save(savedCyRoomtype);

                    return savedCyRoomtype;
                }
            )
            .map(cyRoomtypeMapper::toDto);
    }

    /**
     * Get all the cyRoomtypes.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<CyRoomtypeDTO> findAll(Pageable pageable) {
        log.debug("Request to get all CyRoomtypes");
        return cyRoomtypeRepository.findAll(pageable).map(cyRoomtypeMapper::toDto);
    }

    /**
     * Get one cyRoomtype by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<CyRoomtypeDTO> findOne(Long id) {
        log.debug("Request to get CyRoomtype : {}", id);
        return cyRoomtypeRepository.findById(id).map(cyRoomtypeMapper::toDto);
    }

    /**
     * Delete the cyRoomtype by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete CyRoomtype : {}", id);
        cyRoomtypeRepository.deleteById(id);
        cyRoomtypeSearchRepository.deleteById(id);
    }

    /**
     * Search for the cyRoomtype corresponding to the query.
     *
     * @param query the query of the search.
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<CyRoomtypeDTO> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of CyRoomtypes for query {}", query);
        return cyRoomtypeSearchRepository.search(queryStringQuery(query), pageable).map(cyRoomtypeMapper::toDto);
    }
}
