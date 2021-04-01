package ihotel.app.repository.search;

import ihotel.app.domain.DCktime;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the {@link DCktime} entity.
 */
public interface DCktimeSearchRepository extends ElasticsearchRepository<DCktime, Long> {}
