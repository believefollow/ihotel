package ihotel.app.repository.search;

import ihotel.app.domain.Ee;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the {@link Ee} entity.
 */
public interface EeSearchRepository extends ElasticsearchRepository<Ee, Long> {}
