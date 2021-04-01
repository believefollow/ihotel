package ihotel.app.repository.search;

import ihotel.app.domain.CheckinAccount;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the {@link CheckinAccount} entity.
 */
public interface CheckinAccountSearchRepository extends ElasticsearchRepository<CheckinAccount, Long> {}
