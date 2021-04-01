package ihotel.app.repository.search;

import ihotel.app.domain.DDepot;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the {@link DDepot} entity.
 */
public interface DDepotSearchRepository extends ElasticsearchRepository<DDepot, Long> {}
