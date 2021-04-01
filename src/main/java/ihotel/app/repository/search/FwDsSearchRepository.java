package ihotel.app.repository.search;

import ihotel.app.domain.FwDs;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the {@link FwDs} entity.
 */
public interface FwDsSearchRepository extends ElasticsearchRepository<FwDs, Long> {}
