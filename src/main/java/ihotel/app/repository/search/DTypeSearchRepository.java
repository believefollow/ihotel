package ihotel.app.repository.search;

import ihotel.app.domain.DType;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the {@link DType} entity.
 */
public interface DTypeSearchRepository extends ElasticsearchRepository<DType, Long> {}
