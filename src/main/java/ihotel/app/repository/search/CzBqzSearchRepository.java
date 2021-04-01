package ihotel.app.repository.search;

import ihotel.app.domain.CzBqz;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the {@link CzBqz} entity.
 */
public interface CzBqzSearchRepository extends ElasticsearchRepository<CzBqz, Long> {}
