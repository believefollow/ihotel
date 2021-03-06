package ihotel.app.repository.search;

import ihotel.app.domain.Accounts;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the {@link Accounts} entity.
 */
public interface AccountsSearchRepository extends ElasticsearchRepository<Accounts, Long> {}
