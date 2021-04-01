package ihotel.app.repository.search;

import ihotel.app.domain.Acc;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the {@link Acc} entity.
 */
public interface AccSearchRepository extends ElasticsearchRepository<Acc, Long> {}
