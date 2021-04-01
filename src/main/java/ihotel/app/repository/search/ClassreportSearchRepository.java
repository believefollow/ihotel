package ihotel.app.repository.search;

import ihotel.app.domain.Classreport;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the {@link Classreport} entity.
 */
public interface ClassreportSearchRepository extends ElasticsearchRepository<Classreport, Long> {}
