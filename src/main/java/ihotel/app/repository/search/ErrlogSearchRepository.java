package ihotel.app.repository.search;

import ihotel.app.domain.Errlog;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the {@link Errlog} entity.
 */
public interface ErrlogSearchRepository extends ElasticsearchRepository<Errlog, Long> {}
