package ihotel.app.repository.search;

import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Configuration;

/**
 * Configure a Mock version of {@link DKcSearchRepository} to test the
 * application without starting Elasticsearch.
 */
@Configuration
public class DKcSearchRepositoryMockConfiguration {

    @MockBean
    private DKcSearchRepository mockDKcSearchRepository;
}
