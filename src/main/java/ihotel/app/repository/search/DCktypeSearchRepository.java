package ihotel.app.repository.search;

import ihotel.app.domain.DCktype;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the {@link DCktype} entity.
 */
public interface DCktypeSearchRepository extends ElasticsearchRepository<DCktype, Long> {}
