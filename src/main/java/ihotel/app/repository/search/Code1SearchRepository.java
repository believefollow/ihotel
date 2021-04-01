package ihotel.app.repository.search;

import ihotel.app.domain.Code1;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the {@link Code1} entity.
 */
public interface Code1SearchRepository extends ElasticsearchRepository<Code1, Long> {}
