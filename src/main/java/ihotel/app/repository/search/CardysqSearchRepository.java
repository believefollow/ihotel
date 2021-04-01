package ihotel.app.repository.search;

import ihotel.app.domain.Cardysq;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the {@link Cardysq} entity.
 */
public interface CardysqSearchRepository extends ElasticsearchRepository<Cardysq, Long> {}
