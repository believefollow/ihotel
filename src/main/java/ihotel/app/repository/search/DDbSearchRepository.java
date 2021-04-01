package ihotel.app.repository.search;

import ihotel.app.domain.DDb;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the {@link DDb} entity.
 */
public interface DDbSearchRepository extends ElasticsearchRepository<DDb, Long> {}
