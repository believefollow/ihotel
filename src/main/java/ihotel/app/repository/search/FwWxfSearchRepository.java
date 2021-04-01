package ihotel.app.repository.search;

import ihotel.app.domain.FwWxf;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the {@link FwWxf} entity.
 */
public interface FwWxfSearchRepository extends ElasticsearchRepository<FwWxf, Long> {}
