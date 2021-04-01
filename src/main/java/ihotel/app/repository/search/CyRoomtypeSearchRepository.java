package ihotel.app.repository.search;

import ihotel.app.domain.CyRoomtype;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the {@link CyRoomtype} entity.
 */
public interface CyRoomtypeSearchRepository extends ElasticsearchRepository<CyRoomtype, Long> {}
