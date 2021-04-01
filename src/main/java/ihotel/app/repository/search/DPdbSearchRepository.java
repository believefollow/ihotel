package ihotel.app.repository.search;

import ihotel.app.domain.DPdb;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the {@link DPdb} entity.
 */
public interface DPdbSearchRepository extends ElasticsearchRepository<DPdb, Long> {}
