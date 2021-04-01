package ihotel.app.service;

import static org.elasticsearch.index.query.QueryBuilders.*;

import ihotel.app.domain.Acc;
import ihotel.app.repository.AccRepository;
import ihotel.app.repository.search.AccSearchRepository;
import ihotel.app.service.dto.AccDTO;
import ihotel.app.service.mapper.AccMapper;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link Acc}.
 */
@Service
@Transactional
public class AccService {

    private final Logger log = LoggerFactory.getLogger(AccService.class);

    private final AccRepository accRepository;

    private final AccMapper accMapper;

    private final AccSearchRepository accSearchRepository;

    public AccService(AccRepository accRepository, AccMapper accMapper, AccSearchRepository accSearchRepository) {
        this.accRepository = accRepository;
        this.accMapper = accMapper;
        this.accSearchRepository = accSearchRepository;
    }

    /**
     * Save a acc.
     *
     * @param accDTO the entity to save.
     * @return the persisted entity.
     */
    public AccDTO save(AccDTO accDTO) {
        log.debug("Request to save Acc : {}", accDTO);
        Acc acc = accMapper.toEntity(accDTO);
        acc = accRepository.save(acc);
        AccDTO result = accMapper.toDto(acc);
        accSearchRepository.save(acc);
        return result;
    }

    /**
     * Partially update a acc.
     *
     * @param accDTO the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<AccDTO> partialUpdate(AccDTO accDTO) {
        log.debug("Request to partially update Acc : {}", accDTO);

        return accRepository
            .findById(accDTO.getId())
            .map(
                existingAcc -> {
                    accMapper.partialUpdate(existingAcc, accDTO);
                    return existingAcc;
                }
            )
            .map(accRepository::save)
            .map(
                savedAcc -> {
                    accSearchRepository.save(savedAcc);

                    return savedAcc;
                }
            )
            .map(accMapper::toDto);
    }

    /**
     * Get all the accs.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<AccDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Accs");
        return accRepository.findAll(pageable).map(accMapper::toDto);
    }

    /**
     * Get one acc by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<AccDTO> findOne(Long id) {
        log.debug("Request to get Acc : {}", id);
        return accRepository.findById(id).map(accMapper::toDto);
    }

    /**
     * Delete the acc by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete Acc : {}", id);
        accRepository.deleteById(id);
        accSearchRepository.deleteById(id);
    }

    /**
     * Search for the acc corresponding to the query.
     *
     * @param query the query of the search.
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<AccDTO> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of Accs for query {}", query);
        return accSearchRepository.search(queryStringQuery(query), pageable).map(accMapper::toDto);
    }
}
