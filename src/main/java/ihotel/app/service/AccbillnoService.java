package ihotel.app.service;

import static org.elasticsearch.index.query.QueryBuilders.*;

import ihotel.app.domain.Accbillno;
import ihotel.app.repository.AccbillnoRepository;
import ihotel.app.repository.search.AccbillnoSearchRepository;
import ihotel.app.service.dto.AccbillnoDTO;
import ihotel.app.service.mapper.AccbillnoMapper;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link Accbillno}.
 */
@Service
@Transactional
public class AccbillnoService {

    private final Logger log = LoggerFactory.getLogger(AccbillnoService.class);

    private final AccbillnoRepository accbillnoRepository;

    private final AccbillnoMapper accbillnoMapper;

    private final AccbillnoSearchRepository accbillnoSearchRepository;

    public AccbillnoService(
        AccbillnoRepository accbillnoRepository,
        AccbillnoMapper accbillnoMapper,
        AccbillnoSearchRepository accbillnoSearchRepository
    ) {
        this.accbillnoRepository = accbillnoRepository;
        this.accbillnoMapper = accbillnoMapper;
        this.accbillnoSearchRepository = accbillnoSearchRepository;
    }

    /**
     * Save a accbillno.
     *
     * @param accbillnoDTO the entity to save.
     * @return the persisted entity.
     */
    public AccbillnoDTO save(AccbillnoDTO accbillnoDTO) {
        log.debug("Request to save Accbillno : {}", accbillnoDTO);
        Accbillno accbillno = accbillnoMapper.toEntity(accbillnoDTO);
        accbillno = accbillnoRepository.save(accbillno);
        AccbillnoDTO result = accbillnoMapper.toDto(accbillno);
        accbillnoSearchRepository.save(accbillno);
        return result;
    }

    /**
     * Partially update a accbillno.
     *
     * @param accbillnoDTO the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<AccbillnoDTO> partialUpdate(AccbillnoDTO accbillnoDTO) {
        log.debug("Request to partially update Accbillno : {}", accbillnoDTO);

        return accbillnoRepository
            .findById(accbillnoDTO.getId())
            .map(
                existingAccbillno -> {
                    accbillnoMapper.partialUpdate(existingAccbillno, accbillnoDTO);
                    return existingAccbillno;
                }
            )
            .map(accbillnoRepository::save)
            .map(
                savedAccbillno -> {
                    accbillnoSearchRepository.save(savedAccbillno);

                    return savedAccbillno;
                }
            )
            .map(accbillnoMapper::toDto);
    }

    /**
     * Get all the accbillnos.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<AccbillnoDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Accbillnos");
        return accbillnoRepository.findAll(pageable).map(accbillnoMapper::toDto);
    }

    /**
     * Get one accbillno by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<AccbillnoDTO> findOne(Long id) {
        log.debug("Request to get Accbillno : {}", id);
        return accbillnoRepository.findById(id).map(accbillnoMapper::toDto);
    }

    /**
     * Delete the accbillno by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete Accbillno : {}", id);
        accbillnoRepository.deleteById(id);
        accbillnoSearchRepository.deleteById(id);
    }

    /**
     * Search for the accbillno corresponding to the query.
     *
     * @param query the query of the search.
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<AccbillnoDTO> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of Accbillnos for query {}", query);
        return accbillnoSearchRepository.search(queryStringQuery(query), pageable).map(accbillnoMapper::toDto);
    }
}
