package ihotel.app.service;

import static org.elasticsearch.index.query.QueryBuilders.*;

import ihotel.app.domain.Code1;
import ihotel.app.repository.Code1Repository;
import ihotel.app.repository.search.Code1SearchRepository;
import ihotel.app.service.dto.Code1DTO;
import ihotel.app.service.mapper.Code1Mapper;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link Code1}.
 */
@Service
@Transactional
public class Code1Service {

    private final Logger log = LoggerFactory.getLogger(Code1Service.class);

    private final Code1Repository code1Repository;

    private final Code1Mapper code1Mapper;

    private final Code1SearchRepository code1SearchRepository;

    public Code1Service(Code1Repository code1Repository, Code1Mapper code1Mapper, Code1SearchRepository code1SearchRepository) {
        this.code1Repository = code1Repository;
        this.code1Mapper = code1Mapper;
        this.code1SearchRepository = code1SearchRepository;
    }

    /**
     * Save a code1.
     *
     * @param code1DTO the entity to save.
     * @return the persisted entity.
     */
    public Code1DTO save(Code1DTO code1DTO) {
        log.debug("Request to save Code1 : {}", code1DTO);
        Code1 code1 = code1Mapper.toEntity(code1DTO);
        code1 = code1Repository.save(code1);
        Code1DTO result = code1Mapper.toDto(code1);
        code1SearchRepository.save(code1);
        return result;
    }

    /**
     * Partially update a code1.
     *
     * @param code1DTO the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<Code1DTO> partialUpdate(Code1DTO code1DTO) {
        log.debug("Request to partially update Code1 : {}", code1DTO);

        return code1Repository
            .findById(code1DTO.getId())
            .map(
                existingCode1 -> {
                    code1Mapper.partialUpdate(existingCode1, code1DTO);
                    return existingCode1;
                }
            )
            .map(code1Repository::save)
            .map(
                savedCode1 -> {
                    code1SearchRepository.save(savedCode1);

                    return savedCode1;
                }
            )
            .map(code1Mapper::toDto);
    }

    /**
     * Get all the code1s.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<Code1DTO> findAll(Pageable pageable) {
        log.debug("Request to get all Code1s");
        return code1Repository.findAll(pageable).map(code1Mapper::toDto);
    }

    /**
     * Get one code1 by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<Code1DTO> findOne(Long id) {
        log.debug("Request to get Code1 : {}", id);
        return code1Repository.findById(id).map(code1Mapper::toDto);
    }

    /**
     * Delete the code1 by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete Code1 : {}", id);
        code1Repository.deleteById(id);
        code1SearchRepository.deleteById(id);
    }

    /**
     * Search for the code1 corresponding to the query.
     *
     * @param query the query of the search.
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<Code1DTO> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of Code1s for query {}", query);
        return code1SearchRepository.search(queryStringQuery(query), pageable).map(code1Mapper::toDto);
    }
}
