package ihotel.app.repository.search;

import ihotel.app.domain.Comset;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the {@link Comset} entity.
 */
public interface ComsetSearchRepository extends ElasticsearchRepository<Comset, Long> {}
