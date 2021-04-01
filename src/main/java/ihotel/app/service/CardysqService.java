package ihotel.app.service;

import static org.elasticsearch.index.query.QueryBuilders.*;

import ihotel.app.domain.Cardysq;
import ihotel.app.repository.CardysqRepository;
import ihotel.app.repository.search.CardysqSearchRepository;
import ihotel.app.service.dto.CardysqDTO;
import ihotel.app.service.mapper.CardysqMapper;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link Cardysq}.
 */
@Service
@Transactional
public class CardysqService {

    private final Logger log = LoggerFactory.getLogger(CardysqService.class);

    private final CardysqRepository cardysqRepository;

    private final CardysqMapper cardysqMapper;

    private final CardysqSearchRepository cardysqSearchRepository;

    public CardysqService(
        CardysqRepository cardysqRepository,
        CardysqMapper cardysqMapper,
        CardysqSearchRepository cardysqSearchRepository
    ) {
        this.cardysqRepository = cardysqRepository;
        this.cardysqMapper = cardysqMapper;
        this.cardysqSearchRepository = cardysqSearchRepository;
    }

    /**
     * Save a cardysq.
     *
     * @param cardysqDTO the entity to save.
     * @return the persisted entity.
     */
    public CardysqDTO save(CardysqDTO cardysqDTO) {
        log.debug("Request to save Cardysq : {}", cardysqDTO);
        Cardysq cardysq = cardysqMapper.toEntity(cardysqDTO);
        cardysq = cardysqRepository.save(cardysq);
        CardysqDTO result = cardysqMapper.toDto(cardysq);
        cardysqSearchRepository.save(cardysq);
        return result;
    }

    /**
     * Partially update a cardysq.
     *
     * @param cardysqDTO the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<CardysqDTO> partialUpdate(CardysqDTO cardysqDTO) {
        log.debug("Request to partially update Cardysq : {}", cardysqDTO);

        return cardysqRepository
            .findById(cardysqDTO.getId())
            .map(
                existingCardysq -> {
                    cardysqMapper.partialUpdate(existingCardysq, cardysqDTO);
                    return existingCardysq;
                }
            )
            .map(cardysqRepository::save)
            .map(
                savedCardysq -> {
                    cardysqSearchRepository.save(savedCardysq);

                    return savedCardysq;
                }
            )
            .map(cardysqMapper::toDto);
    }

    /**
     * Get all the cardysqs.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<CardysqDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Cardysqs");
        return cardysqRepository.findAll(pageable).map(cardysqMapper::toDto);
    }

    /**
     * Get one cardysq by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<CardysqDTO> findOne(Long id) {
        log.debug("Request to get Cardysq : {}", id);
        return cardysqRepository.findById(id).map(cardysqMapper::toDto);
    }

    /**
     * Delete the cardysq by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete Cardysq : {}", id);
        cardysqRepository.deleteById(id);
        cardysqSearchRepository.deleteById(id);
    }

    /**
     * Search for the cardysq corresponding to the query.
     *
     * @param query the query of the search.
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<CardysqDTO> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of Cardysqs for query {}", query);
        return cardysqSearchRepository.search(queryStringQuery(query), pageable).map(cardysqMapper::toDto);
    }
}
