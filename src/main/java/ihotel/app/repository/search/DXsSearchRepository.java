package ihotel.app.repository.search;

import ihotel.app.domain.DXs;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the {@link DXs} entity.
 */
public interface DXsSearchRepository extends ElasticsearchRepository<DXs, Long> {}
