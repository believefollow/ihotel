package ihotel.app.repository.search;

import ihotel.app.domain.Checkin;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the {@link Checkin} entity.
 */
public interface CheckinSearchRepository extends ElasticsearchRepository<Checkin, Long> {}
