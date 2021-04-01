package ihotel.app.repository.search;

import ihotel.app.domain.AccPp;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the {@link AccPp} entity.
 */
public interface AccPpSearchRepository extends ElasticsearchRepository<AccPp, Long> {}
