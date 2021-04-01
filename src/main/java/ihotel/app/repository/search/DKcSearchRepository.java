package ihotel.app.repository.search;

import ihotel.app.domain.DKc;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the {@link DKc} entity.
 */
public interface DKcSearchRepository extends ElasticsearchRepository<DKc, Long> {}
