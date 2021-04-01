package ihotel.app.repository.search;

import ihotel.app.domain.Adhoc;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the {@link Adhoc} entity.
 */
public interface AdhocSearchRepository extends ElasticsearchRepository<Adhoc, String> {}
