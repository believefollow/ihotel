package ihotel.app.repository.search;

import ihotel.app.domain.FtXzs;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the {@link FtXzs} entity.
 */
public interface FtXzsSearchRepository extends ElasticsearchRepository<FtXzs, Long> {}
