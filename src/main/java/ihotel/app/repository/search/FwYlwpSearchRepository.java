package ihotel.app.repository.search;

import ihotel.app.domain.FwYlwp;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the {@link FwYlwp} entity.
 */
public interface FwYlwpSearchRepository extends ElasticsearchRepository<FwYlwp, Long> {}
