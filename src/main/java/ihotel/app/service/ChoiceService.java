package ihotel.app.service;

import static org.elasticsearch.index.query.QueryBuilders.*;

import ihotel.app.domain.Choice;
import ihotel.app.repository.ChoiceRepository;
import ihotel.app.repository.search.ChoiceSearchRepository;
import ihotel.app.service.dto.ChoiceDTO;
import ihotel.app.service.mapper.ChoiceMapper;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link Choice}.
 */
@Service
@Transactional
public class ChoiceService {

    private final Logger log = LoggerFactory.getLogger(ChoiceService.class);

    private final ChoiceRepository choiceRepository;

    private final ChoiceMapper choiceMapper;

    private final ChoiceSearchRepository choiceSearchRepository;

    public ChoiceService(ChoiceRepository choiceRepository, ChoiceMapper choiceMapper, ChoiceSearchRepository choiceSearchRepository) {
        this.choiceRepository = choiceRepository;
        this.choiceMapper = choiceMapper;
        this.choiceSearchRepository = choiceSearchRepository;
    }

    /**
     * Save a choice.
     *
     * @param choiceDTO the entity to save.
     * @return the persisted entity.
     */
    public ChoiceDTO save(ChoiceDTO choiceDTO) {
        log.debug("Request to save Choice : {}", choiceDTO);
        Choice choice = choiceMapper.toEntity(choiceDTO);
        choice = choiceRepository.save(choice);
        ChoiceDTO result = choiceMapper.toDto(choice);
        choiceSearchRepository.save(choice);
        return result;
    }

    /**
     * Partially update a choice.
     *
     * @param choiceDTO the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<ChoiceDTO> partialUpdate(ChoiceDTO choiceDTO) {
        log.debug("Request to partially update Choice : {}", choiceDTO);

        return choiceRepository
            .findById(choiceDTO.getId())
            .map(
                existingChoice -> {
                    choiceMapper.partialUpdate(existingChoice, choiceDTO);
                    return existingChoice;
                }
            )
            .map(choiceRepository::save)
            .map(
                savedChoice -> {
                    choiceSearchRepository.save(savedChoice);

                    return savedChoice;
                }
            )
            .map(choiceMapper::toDto);
    }

    /**
     * Get all the choices.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<ChoiceDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Choices");
        return choiceRepository.findAll(pageable).map(choiceMapper::toDto);
    }

    /**
     * Get one choice by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<ChoiceDTO> findOne(Long id) {
        log.debug("Request to get Choice : {}", id);
        return choiceRepository.findById(id).map(choiceMapper::toDto);
    }

    /**
     * Delete the choice by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete Choice : {}", id);
        choiceRepository.deleteById(id);
        choiceSearchRepository.deleteById(id);
    }

    /**
     * Search for the choice corresponding to the query.
     *
     * @param query the query of the search.
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<ChoiceDTO> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of Choices for query {}", query);
        return choiceSearchRepository.search(queryStringQuery(query), pageable).map(choiceMapper::toDto);
    }
}
