package ihotel.app.repository.search;

import ihotel.app.domain.DxSedinfo;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the {@link DxSedinfo} entity.
 */
public interface DxSedinfoSearchRepository extends ElasticsearchRepository<DxSedinfo, Long> {}
