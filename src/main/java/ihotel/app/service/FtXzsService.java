package ihotel.app.service;

import static org.elasticsearch.index.query.QueryBuilders.*;

import ihotel.app.domain.FtXzs;
import ihotel.app.repository.FtXzsRepository;
import ihotel.app.repository.search.FtXzsSearchRepository;
import ihotel.app.service.dto.FtXzsDTO;
import ihotel.app.service.mapper.FtXzsMapper;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link FtXzs}.
 */
@Service
@Transactional
public class FtXzsService {

    private final Logger log = LoggerFactory.getLogger(FtXzsService.class);

    private final FtXzsRepository ftXzsRepository;

    private final FtXzsMapper ftXzsMapper;

    private final FtXzsSearchRepository ftXzsSearchRepository;

    public FtXzsService(FtXzsRepository ftXzsRepository, FtXzsMapper ftXzsMapper, FtXzsSearchRepository ftXzsSearchRepository) {
        this.ftXzsRepository = ftXzsRepository;
        this.ftXzsMapper = ftXzsMapper;
        this.ftXzsSearchRepository = ftXzsSearchRepository;
    }

    /**
     * Save a ftXzs.
     *
     * @param ftXzsDTO the entity to save.
     * @return the persisted entity.
     */
    public FtXzsDTO save(FtXzsDTO ftXzsDTO) {
        log.debug("Request to save FtXzs : {}", ftXzsDTO);
        FtXzs ftXzs = ftXzsMapper.toEntity(ftXzsDTO);
        ftXzs = ftXzsRepository.save(ftXzs);
        FtXzsDTO result = ftXzsMapper.toDto(ftXzs);
        ftXzsSearchRepository.save(ftXzs);
        return result;
    }

    /**
     * Partially update a ftXzs.
     *
     * @param ftXzsDTO the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<FtXzsDTO> partialUpdate(FtXzsDTO ftXzsDTO) {
        log.debug("Request to partially update FtXzs : {}", ftXzsDTO);

        return ftXzsRepository
            .findById(ftXzsDTO.getId())
            .map(
                existingFtXzs -> {
                    ftXzsMapper.partialUpdate(existingFtXzs, ftXzsDTO);
                    return existingFtXzs;
                }
            )
            .map(ftXzsRepository::save)
            .map(
                savedFtXzs -> {
                    ftXzsSearchRepository.save(savedFtXzs);

                    return savedFtXzs;
                }
            )
            .map(ftXzsMapper::toDto);
    }

    /**
     * Get all the ftXzs.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<FtXzsDTO> findAll(Pageable pageable) {
        log.debug("Request to get all FtXzs");
        return ftXzsRepository.findAll(pageable).map(ftXzsMapper::toDto);
    }

    /**
     * Get one ftXzs by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<FtXzsDTO> findOne(Long id) {
        log.debug("Request to get FtXzs : {}", id);
        return ftXzsRepository.findById(id).map(ftXzsMapper::toDto);
    }

    /**
     * Delete the ftXzs by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete FtXzs : {}", id);
        ftXzsRepository.deleteById(id);
        ftXzsSearchRepository.deleteById(id);
    }

    /**
     * Search for the ftXzs corresponding to the query.
     *
     * @param query the query of the search.
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<FtXzsDTO> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of FtXzs for query {}", query);
        return ftXzsSearchRepository.search(queryStringQuery(query), pageable).map(ftXzsMapper::toDto);
    }
}
