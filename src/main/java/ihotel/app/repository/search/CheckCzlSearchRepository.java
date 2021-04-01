package ihotel.app.repository.search;

import ihotel.app.domain.CheckCzl;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the {@link CheckCzl} entity.
 */
public interface CheckCzlSearchRepository extends ElasticsearchRepository<CheckCzl, Long> {}
