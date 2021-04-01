package ihotel.app.service;

import static org.elasticsearch.index.query.QueryBuilders.*;

import ihotel.app.domain.Dayearndetail;
import ihotel.app.repository.DayearndetailRepository;
import ihotel.app.repository.search.DayearndetailSearchRepository;
import ihotel.app.service.dto.DayearndetailDTO;
import ihotel.app.service.mapper.DayearndetailMapper;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link Dayearndetail}.
 */
@Service
@Transactional
public class DayearndetailService {

    private final Logger log = LoggerFactory.getLogger(DayearndetailService.class);

    private final DayearndetailRepository dayearndetailRepository;

    private final DayearndetailMapper dayearndetailMapper;

    private final DayearndetailSearchRepository dayearndetailSearchRepository;

    public DayearndetailService(
        DayearndetailRepository dayearndetailRepository,
        DayearndetailMapper dayearndetailMapper,
        DayearndetailSearchRepository dayearndetailSearchRepository
    ) {
        this.dayearndetailRepository = dayearndetailRepository;
        this.dayearndetailMapper = dayearndetailMapper;
        this.dayearndetailSearchRepository = dayearndetailSearchRepository;
    }

    /**
     * Save a dayearndetail.
     *
     * @param dayearndetailDTO the entity to save.
     * @return the persisted entity.
     */
    public DayearndetailDTO save(DayearndetailDTO dayearndetailDTO) {
        log.debug("Request to save Dayearndetail : {}", dayearndetailDTO);
        Dayearndetail dayearndetail = dayearndetailMapper.toEntity(dayearndetailDTO);
        dayearndetail = dayearndetailRepository.save(dayearndetail);
        DayearndetailDTO result = dayearndetailMapper.toDto(dayearndetail);
        dayearndetailSearchRepository.save(dayearndetail);
        return result;
    }

    /**
     * Partially update a dayearndetail.
     *
     * @param dayearndetailDTO the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<DayearndetailDTO> partialUpdate(DayearndetailDTO dayearndetailDTO) {
        log.debug("Request to partially update Dayearndetail : {}", dayearndetailDTO);

        return dayearndetailRepository
            .findById(dayearndetailDTO.getId())
            .map(
                existingDayearndetail -> {
                    dayearndetailMapper.partialUpdate(existingDayearndetail, dayearndetailDTO);
                    return existingDayearndetail;
                }
            )
            .map(dayearndetailRepository::save)
            .map(
                savedDayearndetail -> {
                    dayearndetailSearchRepository.save(savedDayearndetail);

                    return savedDayearndetail;
                }
            )
            .map(dayearndetailMapper::toDto);
    }

    /**
     * Get all the dayearndetails.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<DayearndetailDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Dayearndetails");
        return dayearndetailRepository.findAll(pageable).map(dayearndetailMapper::toDto);
    }

    /**
     * Get one dayearndetail by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<DayearndetailDTO> findOne(Long id) {
        log.debug("Request to get Dayearndetail : {}", id);
        return dayearndetailRepository.findById(id).map(dayearndetailMapper::toDto);
    }

    /**
     * Delete the dayearndetail by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete Dayearndetail : {}", id);
        dayearndetailRepository.deleteById(id);
        dayearndetailSearchRepository.deleteById(id);
    }

    /**
     * Search for the dayearndetail corresponding to the query.
     *
     * @param query the query of the search.
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<DayearndetailDTO> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of Dayearndetails for query {}", query);
        return dayearndetailSearchRepository.search(queryStringQuery(query), pageable).map(dayearndetailMapper::toDto);
    }
}
