package ihotel.app.repository.search;

import ihotel.app.domain.DDept;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the {@link DDept} entity.
 */
public interface DDeptSearchRepository extends ElasticsearchRepository<DDept, Long> {}
