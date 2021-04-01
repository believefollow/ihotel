package ihotel.app.repository.search;

import ihotel.app.domain.Auditinfo;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the {@link Auditinfo} entity.
 */
public interface AuditinfoSearchRepository extends ElasticsearchRepository<Auditinfo, Long> {}
