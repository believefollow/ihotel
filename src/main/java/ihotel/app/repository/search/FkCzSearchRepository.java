package ihotel.app.repository.search;

import ihotel.app.domain.FkCz;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the {@link FkCz} entity.
 */
public interface FkCzSearchRepository extends ElasticsearchRepository<FkCz, Long> {}
