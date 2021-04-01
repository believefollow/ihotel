package ihotel.app.service;

import static org.elasticsearch.index.query.QueryBuilders.*;

import ihotel.app.domain.CtClass;
import ihotel.app.repository.CtClassRepository;
import ihotel.app.repository.search.CtClassSearchRepository;
import ihotel.app.service.dto.CtClassDTO;
import ihotel.app.service.mapper.CtClassMapper;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link CtClass}.
 */
@Service
@Transactional
public class CtClassService {

    private final Logger log = LoggerFactory.getLogger(CtClassService.class);

    private final CtClassRepository ctClassRepository;

    private final CtClassMapper ctClassMapper;

    private final CtClassSearchRepository ctClassSearchRepository;

    public CtClassService(
        CtClassRepository ctClassRepository,
        CtClassMapper ctClassMapper,
        CtClassSearchRepository ctClassSearchRepository
    ) {
        this.ctClassRepository = ctClassRepository;
        this.ctClassMapper = ctClassMapper;
        this.ctClassSearchRepository = ctClassSearchRepository;
    }

    /**
     * Save a ctClass.
     *
     * @param ctClassDTO the entity to save.
     * @return the persisted entity.
     */
    public CtClassDTO save(CtClassDTO ctClassDTO) {
        log.debug("Request to save CtClass : {}", ctClassDTO);
        CtClass ctClass = ctClassMapper.toEntity(ctClassDTO);
        ctClass = ctClassRepository.save(ctClass);
        CtClassDTO result = ctClassMapper.toDto(ctClass);
        ctClassSearchRepository.save(ctClass);
        return result;
    }

    /**
     * Partially update a ctClass.
     *
     * @param ctClassDTO the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<CtClassDTO> partialUpdate(CtClassDTO ctClassDTO) {
        log.debug("Request to partially update CtClass : {}", ctClassDTO);

        return ctClassRepository
            .findById(ctClassDTO.getId())
            .map(
                existingCtClass -> {
                    ctClassMapper.partialUpdate(existingCtClass, ctClassDTO);
                    return existingCtClass;
                }
            )
            .map(ctClassRepository::save)
            .map(
                savedCtClass -> {
                    ctClassSearchRepository.save(savedCtClass);

                    return savedCtClass;
                }
            )
            .map(ctClassMapper::toDto);
    }

    /**
     * Get all the ctClasses.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<CtClassDTO> findAll(Pageable pageable) {
        log.debug("Request to get all CtClasses");
        return ctClassRepository.findAll(pageable).map(ctClassMapper::toDto);
    }

    /**
     * Get one ctClass by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<CtClassDTO> findOne(Long id) {
        log.debug("Request to get CtClass : {}", id);
        return ctClassRepository.findById(id).map(ctClassMapper::toDto);
    }

    /**
     * Delete the ctClass by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete CtClass : {}", id);
        ctClassRepository.deleteById(id);
        ctClassSearchRepository.deleteById(id);
    }

    /**
     * Search for the ctClass corresponding to the query.
     *
     * @param query the query of the search.
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<CtClassDTO> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of CtClasses for query {}", query);
        return ctClassSearchRepository.search(queryStringQuery(query), pageable).map(ctClassMapper::toDto);
    }
}
