package ihotel.app.repository.search;

import ihotel.app.domain.DxSed;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the {@link DxSed} entity.
 */
public interface DxSedSearchRepository extends ElasticsearchRepository<DxSed, Long> {}
