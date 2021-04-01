package ihotel.app.repository.search;

import ihotel.app.domain.ClassRename;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the {@link ClassRename} entity.
 */
public interface ClassRenameSearchRepository extends ElasticsearchRepository<ClassRename, Long> {}
