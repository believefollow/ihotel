package ihotel.app.repository.search;

import ihotel.app.domain.DCompany;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the {@link DCompany} entity.
 */
public interface DCompanySearchRepository extends ElasticsearchRepository<DCompany, Long> {}
