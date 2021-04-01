package ihotel.app.repository.search;

import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Configuration;

/**
 * Configure a Mock version of {@link CzlCzSearchRepository} to test the
 * application without starting Elasticsearch.
 */
@Configuration
public class CzlCzSearchRepositoryMockConfiguration {

    @MockBean
    private CzlCzSearchRepository mockCzlCzSearchRepository;
}
