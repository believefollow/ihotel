package ihotel.app.service;

import static org.elasticsearch.index.query.QueryBuilders.*;

import ihotel.app.domain.DCompany;
import ihotel.app.repository.DCompanyRepository;
import ihotel.app.repository.search.DCompanySearchRepository;
import ihotel.app.service.dto.DCompanyDTO;
import ihotel.app.service.mapper.DCompanyMapper;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link DCompany}.
 */
@Service
@Transactional
public class DCompanyService {

    private final Logger log = LoggerFactory.getLogger(DCompanyService.class);

    private final DCompanyRepository dCompanyRepository;

    private final DCompanyMapper dCompanyMapper;

    private final DCompanySearchRepository dCompanySearchRepository;

    public DCompanyService(
        DCompanyRepository dCompanyRepository,
        DCompanyMapper dCompanyMapper,
        DCompanySearchRepository dCompanySearchRepository
    ) {
        this.dCompanyRepository = dCompanyRepository;
        this.dCompanyMapper = dCompanyMapper;
        this.dCompanySearchRepository = dCompanySearchRepository;
    }

    /**
     * Save a dCompany.
     *
     * @param dCompanyDTO the entity to save.
     * @return the persisted entity.
     */
    public DCompanyDTO save(DCompanyDTO dCompanyDTO) {
        log.debug("Request to save DCompany : {}", dCompanyDTO);
        DCompany dCompany = dCompanyMapper.toEntity(dCompanyDTO);
        dCompany = dCompanyRepository.save(dCompany);
        DCompanyDTO result = dCompanyMapper.toDto(dCompany);
        dCompanySearchRepository.save(dCompany);
        return result;
    }

    /**
     * Partially update a dCompany.
     *
     * @param dCompanyDTO the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<DCompanyDTO> partialUpdate(DCompanyDTO dCompanyDTO) {
        log.debug("Request to partially update DCompany : {}", dCompanyDTO);

        return dCompanyRepository
            .findById(dCompanyDTO.getId())
            .map(
                existingDCompany -> {
                    dCompanyMapper.partialUpdate(existingDCompany, dCompanyDTO);
                    return existingDCompany;
                }
            )
            .map(dCompanyRepository::save)
            .map(
                savedDCompany -> {
                    dCompanySearchRepository.save(savedDCompany);

                    return savedDCompany;
                }
            )
            .map(dCompanyMapper::toDto);
    }

    /**
     * Get all the dCompanies.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<DCompanyDTO> findAll(Pageable pageable) {
        log.debug("Request to get all DCompanies");
        return dCompanyRepository.findAll(pageable).map(dCompanyMapper::toDto);
    }

    /**
     * Get one dCompany by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<DCompanyDTO> findOne(Long id) {
        log.debug("Request to get DCompany : {}", id);
        return dCompanyRepository.findById(id).map(dCompanyMapper::toDto);
    }

    /**
     * Delete the dCompany by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete DCompany : {}", id);
        dCompanyRepository.deleteById(id);
        dCompanySearchRepository.deleteById(id);
    }

    /**
     * Search for the dCompany corresponding to the query.
     *
     * @param query the query of the search.
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<DCompanyDTO> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of DCompanies for query {}", query);
        return dCompanySearchRepository.search(queryStringQuery(query), pageable).map(dCompanyMapper::toDto);
    }
}
