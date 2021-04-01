package ihotel.app.repository.search;

import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Configuration;

/**
 * Configure a Mock version of {@link ClogSearchRepository} to test the
 * application without starting Elasticsearch.
 */
@Configuration
public class ClogSearchRepositoryMockConfiguration {

    @MockBean
    private ClogSearchRepository mockClogSearchRepository;
}
