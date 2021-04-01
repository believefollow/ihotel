package ihotel.app.service;

import static org.elasticsearch.index.query.QueryBuilders.*;

import ihotel.app.domain.ClassBak;
import ihotel.app.repository.ClassBakRepository;
import ihotel.app.repository.search.ClassBakSearchRepository;
import ihotel.app.service.dto.ClassBakDTO;
import ihotel.app.service.mapper.ClassBakMapper;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link ClassBak}.
 */
@Service
@Transactional
public class ClassBakService {

    private final Logger log = LoggerFactory.getLogger(ClassBakService.class);

    private final ClassBakRepository classBakRepository;

    private final ClassBakMapper classBakMapper;

    private final ClassBakSearchRepository classBakSearchRepository;

    public ClassBakService(
        ClassBakRepository classBakRepository,
        ClassBakMapper classBakMapper,
        ClassBakSearchRepository classBakSearchRepository
    ) {
        this.classBakRepository = classBakRepository;
        this.classBakMapper = classBakMapper;
        this.classBakSearchRepository = classBakSearchRepository;
    }

    /**
     * Save a classBak.
     *
     * @param classBakDTO the entity to save.
     * @return the persisted entity.
     */
    public ClassBakDTO save(ClassBakDTO classBakDTO) {
        log.debug("Request to save ClassBak : {}", classBakDTO);
        ClassBak classBak = classBakMapper.toEntity(classBakDTO);
        classBak = classBakRepository.save(classBak);
        ClassBakDTO result = classBakMapper.toDto(classBak);
        classBakSearchRepository.save(classBak);
        return result;
    }

    /**
     * Partially update a classBak.
     *
     * @param classBakDTO the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<ClassBakDTO> partialUpdate(ClassBakDTO classBakDTO) {
        log.debug("Request to partially update ClassBak : {}", classBakDTO);

        return classBakRepository
            .findById(classBakDTO.getId())
            .map(
                existingClassBak -> {
                    classBakMapper.partialUpdate(existingClassBak, classBakDTO);
                    return existingClassBak;
                }
            )
            .map(classBakRepository::save)
            .map(
                savedClassBak -> {
                    classBakSearchRepository.save(savedClassBak);

                    return savedClassBak;
                }
            )
            .map(classBakMapper::toDto);
    }

    /**
     * Get all the classBaks.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<ClassBakDTO> findAll(Pageable pageable) {
        log.debug("Request to get all ClassBaks");
        return classBakRepository.findAll(pageable).map(classBakMapper::toDto);
    }

    /**
     * Get one classBak by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<ClassBakDTO> findOne(Long id) {
        log.debug("Request to get ClassBak : {}", id);
        return classBakRepository.findById(id).map(classBakMapper::toDto);
    }

    /**
     * Delete the classBak by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete ClassBak : {}", id);
        classBakRepository.deleteById(id);
        classBakSearchRepository.deleteById(id);
    }

    /**
     * Search for the classBak corresponding to the query.
     *
     * @param query the query of the search.
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<ClassBakDTO> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of ClassBaks for query {}", query);
        return classBakSearchRepository.search(queryStringQuery(query), pageable).map(classBakMapper::toDto);
    }
}
