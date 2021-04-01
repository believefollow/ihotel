package ihotel.app.service;

import static org.elasticsearch.index.query.QueryBuilders.*;

import ihotel.app.domain.FtXz;
import ihotel.app.repository.FtXzRepository;
import ihotel.app.repository.search.FtXzSearchRepository;
import ihotel.app.service.dto.FtXzDTO;
import ihotel.app.service.mapper.FtXzMapper;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link FtXz}.
 */
@Service
@Transactional
public class FtXzService {

    private final Logger log = LoggerFactory.getLogger(FtXzService.class);

    private final FtXzRepository ftXzRepository;

    private final FtXzMapper ftXzMapper;

    private final FtXzSearchRepository ftXzSearchRepository;

    public FtXzService(FtXzRepository ftXzRepository, FtXzMapper ftXzMapper, FtXzSearchRepository ftXzSearchRepository) {
        this.ftXzRepository = ftXzRepository;
        this.ftXzMapper = ftXzMapper;
        this.ftXzSearchRepository = ftXzSearchRepository;
    }

    /**
     * Save a ftXz.
     *
     * @param ftXzDTO the entity to save.
     * @return the persisted entity.
     */
    public FtXzDTO save(FtXzDTO ftXzDTO) {
        log.debug("Request to save FtXz : {}", ftXzDTO);
        FtXz ftXz = ftXzMapper.toEntity(ftXzDTO);
        ftXz = ftXzRepository.save(ftXz);
        FtXzDTO result = ftXzMapper.toDto(ftXz);
        ftXzSearchRepository.save(ftXz);
        return result;
    }

    /**
     * Partially update a ftXz.
     *
     * @param ftXzDTO the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<FtXzDTO> partialUpdate(FtXzDTO ftXzDTO) {
        log.debug("Request to partially update FtXz : {}", ftXzDTO);

        return ftXzRepository
            .findById(ftXzDTO.getId())
            .map(
                existingFtXz -> {
                    ftXzMapper.partialUpdate(existingFtXz, ftXzDTO);
                    return existingFtXz;
                }
            )
            .map(ftXzRepository::save)
            .map(
                savedFtXz -> {
                    ftXzSearchRepository.save(savedFtXz);

                    return savedFtXz;
                }
            )
            .map(ftXzMapper::toDto);
    }

    /**
     * Get all the ftXzs.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<FtXzDTO> findAll(Pageable pageable) {
        log.debug("Request to get all FtXzs");
        return ftXzRepository.findAll(pageable).map(ftXzMapper::toDto);
    }

    /**
     * Get one ftXz by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<FtXzDTO> findOne(Long id) {
        log.debug("Request to get FtXz : {}", id);
        return ftXzRepository.findById(id).map(ftXzMapper::toDto);
    }

    /**
     * Delete the ftXz by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete FtXz : {}", id);
        ftXzRepository.deleteById(id);
        ftXzSearchRepository.deleteById(id);
    }

    /**
     * Search for the ftXz corresponding to the query.
     *
     * @param query the query of the search.
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<FtXzDTO> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of FtXzs for query {}", query);
        return ftXzSearchRepository.search(queryStringQuery(query), pageable).map(ftXzMapper::toDto);
    }
}
