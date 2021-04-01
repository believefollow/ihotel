package ihotel.app.repository.search;

import ihotel.app.domain.DUnit;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the {@link DUnit} entity.
 */
public interface DUnitSearchRepository extends ElasticsearchRepository<DUnit, Long> {}
