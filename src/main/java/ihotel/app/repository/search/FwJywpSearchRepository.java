package ihotel.app.repository.search;

import ihotel.app.domain.FwJywp;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the {@link FwJywp} entity.
 */
public interface FwJywpSearchRepository extends ElasticsearchRepository<FwJywp, Long> {}
