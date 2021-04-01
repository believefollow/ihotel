package ihotel.app.repository.search;

import ihotel.app.domain.Crinfo;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the {@link Crinfo} entity.
 */
public interface CrinfoSearchRepository extends ElasticsearchRepository<Crinfo, Long> {}
