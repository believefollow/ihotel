package ihotel.app.service;

import static org.elasticsearch.index.query.QueryBuilders.*;

import ihotel.app.domain.ClassRename;
import ihotel.app.repository.ClassRenameRepository;
import ihotel.app.repository.search.ClassRenameSearchRepository;
import ihotel.app.service.dto.ClassRenameDTO;
import ihotel.app.service.mapper.ClassRenameMapper;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link ClassRename}.
 */
@Service
@Transactional
public class ClassRenameService {

    private final Logger log = LoggerFactory.getLogger(ClassRenameService.class);

    private final ClassRenameRepository classRenameRepository;

    private final ClassRenameMapper classRenameMapper;

    private final ClassRenameSearchRepository classRenameSearchRepository;

    public ClassRenameService(
        ClassRenameRepository classRenameRepository,
        ClassRenameMapper classRenameMapper,
        ClassRenameSearchRepository classRenameSearchRepository
    ) {
        this.classRenameRepository = classRenameRepository;
        this.classRenameMapper = classRenameMapper;
        this.classRenameSearchRepository = classRenameSearchRepository;
    }

    /**
     * Save a classRename.
     *
     * @param classRenameDTO the entity to save.
     * @return the persisted entity.
     */
    public ClassRenameDTO save(ClassRenameDTO classRenameDTO) {
        log.debug("Request to save ClassRename : {}", classRenameDTO);
        ClassRename classRename = classRenameMapper.toEntity(classRenameDTO);
        classRename = classRenameRepository.save(classRename);
        ClassRenameDTO result = classRenameMapper.toDto(classRename);
        classRenameSearchRepository.save(classRename);
        return result;
    }

    /**
     * Partially update a classRename.
     *
     * @param classRenameDTO the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<ClassRenameDTO> partialUpdate(ClassRenameDTO classRenameDTO) {
        log.debug("Request to partially update ClassRename : {}", classRenameDTO);

        return classRenameRepository
            .findById(classRenameDTO.getId())
            .map(
                existingClassRename -> {
                    classRenameMapper.partialUpdate(existingClassRename, classRenameDTO);
                    return existingClassRename;
                }
            )
            .map(classRenameRepository::save)
            .map(
                savedClassRename -> {
                    classRenameSearchRepository.save(savedClassRename);

                    return savedClassRename;
                }
            )
            .map(classRenameMapper::toDto);
    }

    /**
     * Get all the classRenames.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<ClassRenameDTO> findAll(Pageable pageable) {
        log.debug("Request to get all ClassRenames");
        return classRenameRepository.findAll(pageable).map(classRenameMapper::toDto);
    }

    /**
     * Get one classRename by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<ClassRenameDTO> findOne(Long id) {
        log.debug("Request to get ClassRename : {}", id);
        return classRenameRepository.findById(id).map(classRenameMapper::toDto);
    }

    /**
     * Delete the classRename by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete ClassRename : {}", id);
        classRenameRepository.deleteById(id);
        classRenameSearchRepository.deleteById(id);
    }

    /**
     * Search for the classRename corresponding to the query.
     *
     * @param query the query of the search.
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<ClassRenameDTO> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of ClassRenames for query {}", query);
        return classRenameSearchRepository.search(queryStringQuery(query), pageable).map(classRenameMapper::toDto);
    }
}
