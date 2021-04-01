package ihotel.app.repository.search;

import ihotel.app.domain.ClassBak;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the {@link ClassBak} entity.
 */
public interface ClassBakSearchRepository extends ElasticsearchRepository<ClassBak, Long> {}
