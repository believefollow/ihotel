package ihotel.app.repository.search;

import ihotel.app.domain.CyCptype;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the {@link CyCptype} entity.
 */
public interface CyCptypeSearchRepository extends ElasticsearchRepository<CyCptype, Long> {}
