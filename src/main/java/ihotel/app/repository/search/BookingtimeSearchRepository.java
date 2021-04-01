package ihotel.app.repository.search;

import ihotel.app.domain.Bookingtime;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the {@link Bookingtime} entity.
 */
public interface BookingtimeSearchRepository extends ElasticsearchRepository<Bookingtime, Long> {}
