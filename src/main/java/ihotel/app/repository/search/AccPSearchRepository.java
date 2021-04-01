package ihotel.app.repository.search;

import ihotel.app.domain.AccP;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the {@link AccP} entity.
 */
public interface AccPSearchRepository extends ElasticsearchRepository<AccP, Long> {}
