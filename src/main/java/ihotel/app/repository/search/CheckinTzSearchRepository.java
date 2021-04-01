package ihotel.app.repository.search;

import ihotel.app.domain.CheckinTz;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the {@link CheckinTz} entity.
 */
public interface CheckinTzSearchRepository extends ElasticsearchRepository<CheckinTz, Long> {}
