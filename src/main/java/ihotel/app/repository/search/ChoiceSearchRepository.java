package ihotel.app.repository.search;

import ihotel.app.domain.Choice;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the {@link Choice} entity.
 */
public interface ChoiceSearchRepository extends ElasticsearchRepository<Choice, Long> {}
