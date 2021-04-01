package ihotel.app.service;

import static org.elasticsearch.index.query.QueryBuilders.*;

import ihotel.app.domain.Auditinfo;
import ihotel.app.repository.AuditinfoRepository;
import ihotel.app.repository.search.AuditinfoSearchRepository;
import ihotel.app.service.dto.AuditinfoDTO;
import ihotel.app.service.mapper.AuditinfoMapper;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link Auditinfo}.
 */
@Service
@Transactional
public class AuditinfoService {

    private final Logger log = LoggerFactory.getLogger(AuditinfoService.class);

    private final AuditinfoRepository auditinfoRepository;

    private final AuditinfoMapper auditinfoMapper;

    private final AuditinfoSearchRepository auditinfoSearchRepository;

    public AuditinfoService(
        AuditinfoRepository auditinfoRepository,
        AuditinfoMapper auditinfoMapper,
        AuditinfoSearchRepository auditinfoSearchRepository
    ) {
        this.auditinfoRepository = auditinfoRepository;
        this.auditinfoMapper = auditinfoMapper;
        this.auditinfoSearchRepository = auditinfoSearchRepository;
    }

    /**
     * Save a auditinfo.
     *
     * @param auditinfoDTO the entity to save.
     * @return the persisted entity.
     */
    public AuditinfoDTO save(AuditinfoDTO auditinfoDTO) {
        log.debug("Request to save Auditinfo : {}", auditinfoDTO);
        Auditinfo auditinfo = auditinfoMapper.toEntity(auditinfoDTO);
        auditinfo = auditinfoRepository.save(auditinfo);
        AuditinfoDTO result = auditinfoMapper.toDto(auditinfo);
        auditinfoSearchRepository.save(auditinfo);
        return result;
    }

    /**
     * Partially update a auditinfo.
     *
     * @param auditinfoDTO the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<AuditinfoDTO> partialUpdate(AuditinfoDTO auditinfoDTO) {
        log.debug("Request to partially update Auditinfo : {}", auditinfoDTO);

        return auditinfoRepository
            .findById(auditinfoDTO.getId())
            .map(
                existingAuditinfo -> {
                    auditinfoMapper.partialUpdate(existingAuditinfo, auditinfoDTO);
                    return existingAuditinfo;
                }
            )
            .map(auditinfoRepository::save)
            .map(
                savedAuditinfo -> {
                    auditinfoSearchRepository.save(savedAuditinfo);

                    return savedAuditinfo;
                }
            )
            .map(auditinfoMapper::toDto);
    }

    /**
     * Get all the auditinfos.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<AuditinfoDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Auditinfos");
        return auditinfoRepository.findAll(pageable).map(auditinfoMapper::toDto);
    }

    /**
     * Get one auditinfo by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<AuditinfoDTO> findOne(Long id) {
        log.debug("Request to get Auditinfo : {}", id);
        return auditinfoRepository.findById(id).map(auditinfoMapper::toDto);
    }

    /**
     * Delete the auditinfo by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete Auditinfo : {}", id);
        auditinfoRepository.deleteById(id);
        auditinfoSearchRepository.deleteById(id);
    }

    /**
     * Search for the auditinfo corresponding to the query.
     *
     * @param query the query of the search.
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<AuditinfoDTO> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of Auditinfos for query {}", query);
        return auditinfoSearchRepository.search(queryStringQuery(query), pageable).map(auditinfoMapper::toDto);
    }
}
