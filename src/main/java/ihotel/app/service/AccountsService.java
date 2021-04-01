package ihotel.app.service;

import static org.elasticsearch.index.query.QueryBuilders.*;

import ihotel.app.domain.Accounts;
import ihotel.app.repository.AccountsRepository;
import ihotel.app.repository.search.AccountsSearchRepository;
import ihotel.app.service.dto.AccountsDTO;
import ihotel.app.service.mapper.AccountsMapper;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link Accounts}.
 */
@Service
@Transactional
public class AccountsService {

    private final Logger log = LoggerFactory.getLogger(AccountsService.class);

    private final AccountsRepository accountsRepository;

    private final AccountsMapper accountsMapper;

    private final AccountsSearchRepository accountsSearchRepository;

    public AccountsService(
        AccountsRepository accountsRepository,
        AccountsMapper accountsMapper,
        AccountsSearchRepository accountsSearchRepository
    ) {
        this.accountsRepository = accountsRepository;
        this.accountsMapper = accountsMapper;
        this.accountsSearchRepository = accountsSearchRepository;
    }

    /**
     * Save a accounts.
     *
     * @param accountsDTO the entity to save.
     * @return the persisted entity.
     */
    public AccountsDTO save(AccountsDTO accountsDTO) {
        log.debug("Request to save Accounts : {}", accountsDTO);
        Accounts accounts = accountsMapper.toEntity(accountsDTO);
        accounts = accountsRepository.save(accounts);
        AccountsDTO result = accountsMapper.toDto(accounts);
        accountsSearchRepository.save(accounts);
        return result;
    }

    /**
     * Partially update a accounts.
     *
     * @param accountsDTO the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<AccountsDTO> partialUpdate(AccountsDTO accountsDTO) {
        log.debug("Request to partially update Accounts : {}", accountsDTO);

        return accountsRepository
            .findById(accountsDTO.getId())
            .map(
                existingAccounts -> {
                    accountsMapper.partialUpdate(existingAccounts, accountsDTO);
                    return existingAccounts;
                }
            )
            .map(accountsRepository::save)
            .map(
                savedAccounts -> {
                    accountsSearchRepository.save(savedAccounts);

                    return savedAccounts;
                }
            )
            .map(accountsMapper::toDto);
    }

    /**
     * Get all the accounts.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<AccountsDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Accounts");
        return accountsRepository.findAll(pageable).map(accountsMapper::toDto);
    }

    /**
     * Get one accounts by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<AccountsDTO> findOne(Long id) {
        log.debug("Request to get Accounts : {}", id);
        return accountsRepository.findById(id).map(accountsMapper::toDto);
    }

    /**
     * Delete the accounts by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete Accounts : {}", id);
        accountsRepository.deleteById(id);
        accountsSearchRepository.deleteById(id);
    }

    /**
     * Search for the accounts corresponding to the query.
     *
     * @param query the query of the search.
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<AccountsDTO> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of Accounts for query {}", query);
        return accountsSearchRepository.search(queryStringQuery(query), pageable).map(accountsMapper::toDto);
    }
}
