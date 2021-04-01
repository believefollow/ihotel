package ihotel.app.repository.search;

import ihotel.app.domain.CzCzl3;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the {@link CzCzl3} entity.
 */
public interface CzCzl3SearchRepository extends ElasticsearchRepository<CzCzl3, Long> {}
