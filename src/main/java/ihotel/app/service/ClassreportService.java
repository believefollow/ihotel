package ihotel.app.service;

import static org.elasticsearch.index.query.QueryBuilders.*;

import ihotel.app.domain.Classreport;
import ihotel.app.repository.ClassreportRepository;
import ihotel.app.repository.search.ClassreportSearchRepository;
import ihotel.app.service.dto.ClassreportDTO;
import ihotel.app.service.mapper.ClassreportMapper;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link Classreport}.
 */
@Service
@Transactional
public class ClassreportService {

    private final Logger log = LoggerFactory.getLogger(ClassreportService.class);

    private final ClassreportRepository classreportRepository;

    private final ClassreportMapper classreportMapper;

    private final ClassreportSearchRepository classreportSearchRepository;

    public ClassreportService(
        ClassreportRepository classreportRepository,
        ClassreportMapper classreportMapper,
        ClassreportSearchRepository classreportSearchRepository
    ) {
        this.classreportRepository = classreportRepository;
        this.classreportMapper = classreportMapper;
        this.classreportSearchRepository = classreportSearchRepository;
    }

    /**
     * Save a classreport.
     *
     * @param classreportDTO the entity to save.
     * @return the persisted entity.
     */
    public ClassreportDTO save(ClassreportDTO classreportDTO) {
        log.debug("Request to save Classreport : {}", classreportDTO);
        Classreport classreport = classreportMapper.toEntity(classreportDTO);
        classreport = classreportRepository.save(classreport);
        ClassreportDTO result = classreportMapper.toDto(classreport);
        classreportSearchRepository.save(classreport);
        return result;
    }

    /**
     * Partially update a classreport.
     *
     * @param classreportDTO the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<ClassreportDTO> partialUpdate(ClassreportDTO classreportDTO) {
        log.debug("Request to partially update Classreport : {}", classreportDTO);

        return classreportRepository
            .findById(classreportDTO.getId())
            .map(
                existingClassreport -> {
                    classreportMapper.partialUpdate(existingClassreport, classreportDTO);
                    return existingClassreport;
                }
            )
            .map(classreportRepository::save)
            .map(
                savedClassreport -> {
                    classreportSearchRepository.save(savedClassreport);

                    return savedClassreport;
                }
            )
            .map(classreportMapper::toDto);
    }

    /**
     * Get all the classreports.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<ClassreportDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Classreports");
        return classreportRepository.findAll(pageable).map(classreportMapper::toDto);
    }

    /**
     * Get one classreport by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<ClassreportDTO> findOne(Long id) {
        log.debug("Request to get Classreport : {}", id);
        return classreportRepository.findById(id).map(classreportMapper::toDto);
    }

    /**
     * Delete the classreport by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete Classreport : {}", id);
        classreportRepository.deleteById(id);
        classreportSearchRepository.deleteById(id);
    }

    /**
     * Search for the classreport corresponding to the query.
     *
     * @param query the query of the search.
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<ClassreportDTO> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of Classreports for query {}", query);
        return classreportSearchRepository.search(queryStringQuery(query), pageable).map(classreportMapper::toDto);
    }
}
