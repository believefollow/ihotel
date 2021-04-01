package ihotel.app.service;

import static org.elasticsearch.index.query.QueryBuilders.*;

import ihotel.app.domain.Ck2xsy;
import ihotel.app.repository.Ck2xsyRepository;
import ihotel.app.repository.search.Ck2xsySearchRepository;
import ihotel.app.service.dto.Ck2xsyDTO;
import ihotel.app.service.mapper.Ck2xsyMapper;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link Ck2xsy}.
 */
@Service
@Transactional
public class Ck2xsyService {

    private final Logger log = LoggerFactory.getLogger(Ck2xsyService.class);

    private final Ck2xsyRepository ck2xsyRepository;

    private final Ck2xsyMapper ck2xsyMapper;

    private final Ck2xsySearchRepository ck2xsySearchRepository;

    public Ck2xsyService(Ck2xsyRepository ck2xsyRepository, Ck2xsyMapper ck2xsyMapper, Ck2xsySearchRepository ck2xsySearchRepository) {
        this.ck2xsyRepository = ck2xsyRepository;
        this.ck2xsyMapper = ck2xsyMapper;
        this.ck2xsySearchRepository = ck2xsySearchRepository;
    }

    /**
     * Save a ck2xsy.
     *
     * @param ck2xsyDTO the entity to save.
     * @return the persisted entity.
     */
    public Ck2xsyDTO save(Ck2xsyDTO ck2xsyDTO) {
        log.debug("Request to save Ck2xsy : {}", ck2xsyDTO);
        Ck2xsy ck2xsy = ck2xsyMapper.toEntity(ck2xsyDTO);
        ck2xsy = ck2xsyRepository.save(ck2xsy);
        Ck2xsyDTO result = ck2xsyMapper.toDto(ck2xsy);
        ck2xsySearchRepository.save(ck2xsy);
        return result;
    }

    /**
     * Partially update a ck2xsy.
     *
     * @param ck2xsyDTO the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<Ck2xsyDTO> partialUpdate(Ck2xsyDTO ck2xsyDTO) {
        log.debug("Request to partially update Ck2xsy : {}", ck2xsyDTO);

        return ck2xsyRepository
            .findById(ck2xsyDTO.getId())
            .map(
                existingCk2xsy -> {
                    ck2xsyMapper.partialUpdate(existingCk2xsy, ck2xsyDTO);
                    return existingCk2xsy;
                }
            )
            .map(ck2xsyRepository::save)
            .map(
                savedCk2xsy -> {
                    ck2xsySearchRepository.save(savedCk2xsy);

                    return savedCk2xsy;
                }
            )
            .map(ck2xsyMapper::toDto);
    }

    /**
     * Get all the ck2xsies.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<Ck2xsyDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Ck2xsies");
        return ck2xsyRepository.findAll(pageable).map(ck2xsyMapper::toDto);
    }

    /**
     * Get one ck2xsy by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<Ck2xsyDTO> findOne(Long id) {
        log.debug("Request to get Ck2xsy : {}", id);
        return ck2xsyRepository.findById(id).map(ck2xsyMapper::toDto);
    }

    /**
     * Delete the ck2xsy by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete Ck2xsy : {}", id);
        ck2xsyRepository.deleteById(id);
        ck2xsySearchRepository.deleteById(id);
    }

    /**
     * Search for the ck2xsy corresponding to the query.
     *
     * @param query the query of the search.
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<Ck2xsyDTO> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of Ck2xsies for query {}", query);
        return ck2xsySearchRepository.search(queryStringQuery(query), pageable).map(ck2xsyMapper::toDto);
    }
}
