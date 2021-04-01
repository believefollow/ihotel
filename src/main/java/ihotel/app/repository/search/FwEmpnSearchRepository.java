package ihotel.app.repository.search;

import ihotel.app.domain.FwEmpn;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the {@link FwEmpn} entity.
 */
public interface FwEmpnSearchRepository extends ElasticsearchRepository<FwEmpn, Long> {}
