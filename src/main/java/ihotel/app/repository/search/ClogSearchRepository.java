package ihotel.app.repository.search;

import ihotel.app.domain.Clog;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the {@link Clog} entity.
 */
public interface ClogSearchRepository extends ElasticsearchRepository<Clog, Long> {}
