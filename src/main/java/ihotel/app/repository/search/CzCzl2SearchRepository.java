package ihotel.app.repository.search;

import ihotel.app.domain.CzCzl2;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the {@link CzCzl2} entity.
 */
public interface CzCzl2SearchRepository extends ElasticsearchRepository<CzCzl2, Long> {}
