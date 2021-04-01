package ihotel.app.repository.search;

import ihotel.app.domain.DSpcz;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the {@link DSpcz} entity.
 */
public interface DSpczSearchRepository extends ElasticsearchRepository<DSpcz, Long> {}
