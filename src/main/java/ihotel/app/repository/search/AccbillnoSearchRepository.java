package ihotel.app.repository.search;

import ihotel.app.domain.Accbillno;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the {@link Accbillno} entity.
 */
public interface AccbillnoSearchRepository extends ElasticsearchRepository<Accbillno, Long> {}
