package ihotel.app.repository.search;

import ihotel.app.domain.CheckinBak;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the {@link CheckinBak} entity.
 */
public interface CheckinBakSearchRepository extends ElasticsearchRepository<CheckinBak, Long> {}
