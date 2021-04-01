package ihotel.app.service;

import static org.elasticsearch.index.query.QueryBuilders.*;

import ihotel.app.domain.ClassreportRoom;
import ihotel.app.repository.ClassreportRoomRepository;
import ihotel.app.repository.search.ClassreportRoomSearchRepository;
import ihotel.app.service.dto.ClassreportRoomDTO;
import ihotel.app.service.mapper.ClassreportRoomMapper;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link ClassreportRoom}.
 */
@Service
@Transactional
public class ClassreportRoomService {

    private final Logger log = LoggerFactory.getLogger(ClassreportRoomService.class);

    private final ClassreportRoomRepository classreportRoomRepository;

    private final ClassreportRoomMapper classreportRoomMapper;

    private final ClassreportRoomSearchRepository classreportRoomSearchRepository;

    public ClassreportRoomService(
        ClassreportRoomRepository classreportRoomRepository,
        ClassreportRoomMapper classreportRoomMapper,
        ClassreportRoomSearchRepository classreportRoomSearchRepository
    ) {
        this.classreportRoomRepository = classreportRoomRepository;
        this.classreportRoomMapper = classreportRoomMapper;
        this.classreportRoomSearchRepository = classreportRoomSearchRepository;
    }

    /**
     * Save a classreportRoom.
     *
     * @param classreportRoomDTO the entity to save.
     * @return the persisted entity.
     */
    public ClassreportRoomDTO save(ClassreportRoomDTO classreportRoomDTO) {
        log.debug("Request to save ClassreportRoom : {}", classreportRoomDTO);
        ClassreportRoom classreportRoom = classreportRoomMapper.toEntity(classreportRoomDTO);
        classreportRoom = classreportRoomRepository.save(classreportRoom);
        ClassreportRoomDTO result = classreportRoomMapper.toDto(classreportRoom);
        classreportRoomSearchRepository.save(classreportRoom);
        return result;
    }

    /**
     * Partially update a classreportRoom.
     *
     * @param classreportRoomDTO the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<ClassreportRoomDTO> partialUpdate(ClassreportRoomDTO classreportRoomDTO) {
        log.debug("Request to partially update ClassreportRoom : {}", classreportRoomDTO);

        return classreportRoomRepository
            .findById(classreportRoomDTO.getId())
            .map(
                existingClassreportRoom -> {
                    classreportRoomMapper.partialUpdate(existingClassreportRoom, classreportRoomDTO);
                    return existingClassreportRoom;
                }
            )
            .map(classreportRoomRepository::save)
            .map(
                savedClassreportRoom -> {
                    classreportRoomSearchRepository.save(savedClassreportRoom);

                    return savedClassreportRoom;
                }
            )
            .map(classreportRoomMapper::toDto);
    }

    /**
     * Get all the classreportRooms.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<ClassreportRoomDTO> findAll(Pageable pageable) {
        log.debug("Request to get all ClassreportRooms");
        return classreportRoomRepository.findAll(pageable).map(classreportRoomMapper::toDto);
    }

    /**
     * Get one classreportRoom by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<ClassreportRoomDTO> findOne(Long id) {
        log.debug("Request to get ClassreportRoom : {}", id);
        return classreportRoomRepository.findById(id).map(classreportRoomMapper::toDto);
    }

    /**
     * Delete the classreportRoom by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete ClassreportRoom : {}", id);
        classreportRoomRepository.deleteById(id);
        classreportRoomSearchRepository.deleteById(id);
    }

    /**
     * Search for the classreportRoom corresponding to the query.
     *
     * @param query the query of the search.
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<ClassreportRoomDTO> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of ClassreportRooms for query {}", query);
        return classreportRoomSearchRepository.search(queryStringQuery(query), pageable).map(classreportRoomMapper::toDto);
    }
}
