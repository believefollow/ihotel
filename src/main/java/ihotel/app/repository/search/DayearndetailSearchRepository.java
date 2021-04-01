package ihotel.app.repository.search;

import ihotel.app.domain.Dayearndetail;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the {@link Dayearndetail} entity.
 */
public interface DayearndetailSearchRepository extends ElasticsearchRepository<Dayearndetail, Long> {}
