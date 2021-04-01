package ihotel.app.repository.search;

import ihotel.app.domain.Ck2xsy;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the {@link Ck2xsy} entity.
 */
public interface Ck2xsySearchRepository extends ElasticsearchRepository<Ck2xsy, Long> {}
