package ihotel.app.service;

import static org.elasticsearch.index.query.QueryBuilders.*;

import ihotel.app.domain.DGoods;
import ihotel.app.repository.DGoodsRepository;
import ihotel.app.repository.search.DGoodsSearchRepository;
import ihotel.app.service.dto.DGoodsDTO;
import ihotel.app.service.mapper.DGoodsMapper;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link DGoods}.
 */
@Service
@Transactional
public class DGoodsService {

    private final Logger log = LoggerFactory.getLogger(DGoodsService.class);

    private final DGoodsRepository dGoodsRepository;

    private final DGoodsMapper dGoodsMapper;

    private final DGoodsSearchRepository dGoodsSearchRepository;

    public DGoodsService(DGoodsRepository dGoodsRepository, DGoodsMapper dGoodsMapper, DGoodsSearchRepository dGoodsSearchRepository) {
        this.dGoodsRepository = dGoodsRepository;
        this.dGoodsMapper = dGoodsMapper;
        this.dGoodsSearchRepository = dGoodsSearchRepository;
    }

    /**
     * Save a dGoods.
     *
     * @param dGoodsDTO the entity to save.
     * @return the persisted entity.
     */
    public DGoodsDTO save(DGoodsDTO dGoodsDTO) {
        log.debug("Request to save DGoods : {}", dGoodsDTO);
        DGoods dGoods = dGoodsMapper.toEntity(dGoodsDTO);
        dGoods = dGoodsRepository.save(dGoods);
        DGoodsDTO result = dGoodsMapper.toDto(dGoods);
        dGoodsSearchRepository.save(dGoods);
        return result;
    }

    /**
     * Partially update a dGoods.
     *
     * @param dGoodsDTO the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<DGoodsDTO> partialUpdate(DGoodsDTO dGoodsDTO) {
        log.debug("Request to partially update DGoods : {}", dGoodsDTO);

        return dGoodsRepository
            .findById(dGoodsDTO.getId())
            .map(
                existingDGoods -> {
                    dGoodsMapper.partialUpdate(existingDGoods, dGoodsDTO);
                    return existingDGoods;
                }
            )
            .map(dGoodsRepository::save)
            .map(
                savedDGoods -> {
                    dGoodsSearchRepository.save(savedDGoods);

                    return savedDGoods;
                }
            )
            .map(dGoodsMapper::toDto);
    }

    /**
     * Get all the dGoods.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<DGoodsDTO> findAll(Pageable pageable) {
        log.debug("Request to get all DGoods");
        return dGoodsRepository.findAll(pageable).map(dGoodsMapper::toDto);
    }

    /**
     * Get one dGoods by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<DGoodsDTO> findOne(Long id) {
        log.debug("Request to get DGoods : {}", id);
        return dGoodsRepository.findById(id).map(dGoodsMapper::toDto);
    }

    /**
     * Delete the dGoods by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete DGoods : {}", id);
        dGoodsRepository.deleteById(id);
        dGoodsSearchRepository.deleteById(id);
    }

    /**
     * Search for the dGoods corresponding to the query.
     *
     * @param query the query of the search.
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<DGoodsDTO> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of DGoods for query {}", query);
        return dGoodsSearchRepository.search(queryStringQuery(query), pageable).map(dGoodsMapper::toDto);
    }
}
