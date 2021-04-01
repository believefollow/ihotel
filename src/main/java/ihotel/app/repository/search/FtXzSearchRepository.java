package ihotel.app.repository.search;

import ihotel.app.domain.FtXz;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the {@link FtXz} entity.
 */
public interface FtXzSearchRepository extends ElasticsearchRepository<FtXz, Long> {}
