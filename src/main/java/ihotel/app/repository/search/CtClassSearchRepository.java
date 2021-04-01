package ihotel.app.repository.search;

import ihotel.app.domain.CtClass;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the {@link CtClass} entity.
 */
public interface CtClassSearchRepository extends ElasticsearchRepository<CtClass, Long> {}
