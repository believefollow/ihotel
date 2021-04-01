package ihotel.app.repository.search;

import ihotel.app.domain.DCk;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the {@link DCk} entity.
 */
public interface DCkSearchRepository extends ElasticsearchRepository<DCk, Long> {}
