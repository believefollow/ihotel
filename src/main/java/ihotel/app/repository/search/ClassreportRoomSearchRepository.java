package ihotel.app.repository.search;

import ihotel.app.domain.ClassreportRoom;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the {@link ClassreportRoom} entity.
 */
public interface ClassreportRoomSearchRepository extends ElasticsearchRepository<ClassreportRoom, Long> {}
