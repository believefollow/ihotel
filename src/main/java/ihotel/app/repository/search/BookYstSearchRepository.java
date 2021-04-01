package ihotel.app.repository.search;

import ihotel.app.domain.BookYst;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the {@link BookYst} entity.
 */
public interface BookYstSearchRepository extends ElasticsearchRepository<BookYst, Long> {}
