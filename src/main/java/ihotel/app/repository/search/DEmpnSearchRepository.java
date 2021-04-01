package ihotel.app.repository.search;

import ihotel.app.domain.DEmpn;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the {@link DEmpn} entity.
 */
public interface DEmpnSearchRepository extends ElasticsearchRepository<DEmpn, Long> {}
