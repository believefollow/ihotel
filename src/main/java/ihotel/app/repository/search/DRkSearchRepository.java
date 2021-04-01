package ihotel.app.repository.search;

import ihotel.app.domain.DRk;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the {@link DRk} entity.
 */
public interface DRkSearchRepository extends ElasticsearchRepository<DRk, Long> {}
