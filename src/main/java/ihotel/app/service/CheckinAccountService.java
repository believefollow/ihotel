package ihotel.app.service;

import static org.elasticsearch.index.query.QueryBuilders.*;

import ihotel.app.domain.CheckinAccount;
import ihotel.app.repository.CheckinAccountRepository;
import ihotel.app.repository.search.CheckinAccountSearchRepository;
import ihotel.app.service.dto.CheckinAccountDTO;
import ihotel.app.service.mapper.CheckinAccountMapper;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link CheckinAccount}.
 */
@Service
@Transactional
public class CheckinAccountService {

    private final Logger log = LoggerFactory.getLogger(CheckinAccountService.class);

    private final CheckinAccountRepository checkinAccountRepository;

    private final CheckinAccountMapper checkinAccountMapper;

    private final CheckinAccountSearchRepository checkinAccountSearchRepository;

    public CheckinAccountService(
        CheckinAccountRepository checkinAccountRepository,
        CheckinAccountMapper checkinAccountMapper,
        CheckinAccountSearchRepository checkinAccountSearchRepository
    ) {
        this.checkinAccountRepository = checkinAccountRepository;
        this.checkinAccountMapper = checkinAccountMapper;
        this.checkinAccountSearchRepository = checkinAccountSearchRepository;
    }

    /**
     * Save a checkinAccount.
     *
     * @param checkinAccountDTO the entity to save.
     * @return the persisted entity.
     */
    public CheckinAccountDTO save(CheckinAccountDTO checkinAccountDTO) {
        log.debug("Request to save CheckinAccount : {}", checkinAccountDTO);
        CheckinAccount checkinAccount = checkinAccountMapper.toEntity(checkinAccountDTO);
        checkinAccount = checkinAccountRepository.save(checkinAccount);
        CheckinAccountDTO result = checkinAccountMapper.toDto(checkinAccount);
        checkinAccountSearchRepository.save(checkinAccount);
        return result;
    }

    /**
     * Partially update a checkinAccount.
     *
     * @param checkinAccountDTO the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<CheckinAccountDTO> partialUpdate(CheckinAccountDTO checkinAccountDTO) {
        log.debug("Request to partially update CheckinAccount : {}", checkinAccountDTO);

        return checkinAccountRepository
            .findById(checkinAccountDTO.getId())
            .map(
                existingCheckinAccount -> {
                    checkinAccountMapper.partialUpdate(existingCheckinAccount, checkinAccountDTO);
                    return existingCheckinAccount;
                }
            )
            .map(checkinAccountRepository::save)
            .map(
                savedCheckinAccount -> {
                    checkinAccountSearchRepository.save(savedCheckinAccount);

                    return savedCheckinAccount;
                }
            )
            .map(checkinAccountMapper::toDto);
    }

    /**
     * Get all the checkinAccounts.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<CheckinAccountDTO> findAll(Pageable pageable) {
        log.debug("Request to get all CheckinAccounts");
        return checkinAccountRepository.findAll(pageable).map(checkinAccountMapper::toDto);
    }

    /**
     * Get one checkinAccount by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<CheckinAccountDTO> findOne(Long id) {
        log.debug("Request to get CheckinAccount : {}", id);
        return checkinAccountRepository.findById(id).map(checkinAccountMapper::toDto);
    }

    /**
     * Delete the checkinAccount by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete CheckinAccount : {}", id);
        checkinAccountRepository.deleteById(id);
        checkinAccountSearchRepository.deleteById(id);
    }

    /**
     * Search for the checkinAccount corresponding to the query.
     *
     * @param query the query of the search.
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<CheckinAccountDTO> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of CheckinAccounts for query {}", query);
        return checkinAccountSearchRepository.search(queryStringQuery(query), pageable).map(checkinAccountMapper::toDto);
    }
}
