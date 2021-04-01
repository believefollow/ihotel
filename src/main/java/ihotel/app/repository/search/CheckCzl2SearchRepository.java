package ihotel.app.repository.search;

import ihotel.app.domain.CheckCzl2;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the {@link CheckCzl2} entity.
 */
public interface CheckCzl2SearchRepository extends ElasticsearchRepository<CheckCzl2, Long> {}
