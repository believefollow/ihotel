package ihotel.app.repository.search;

import ihotel.app.domain.Feetype;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the {@link Feetype} entity.
 */
public interface FeetypeSearchRepository extends ElasticsearchRepository<Feetype, Long> {}
