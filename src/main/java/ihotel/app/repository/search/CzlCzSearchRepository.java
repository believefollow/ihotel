package ihotel.app.repository.search;

import ihotel.app.domain.CzlCz;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the {@link CzlCz} entity.
 */
public interface CzlCzSearchRepository extends ElasticsearchRepository<CzlCz, Long> {}
