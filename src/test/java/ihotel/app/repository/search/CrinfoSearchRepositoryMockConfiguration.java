package ihotel.app.repository.search;

import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Configuration;

/**
 * Configure a Mock version of {@link CrinfoSearchRepository} to test the
 * application without starting Elasticsearch.
 */
@Configuration
public class CrinfoSearchRepositoryMockConfiguration {

    @MockBean
    private CrinfoSearchRepository mockCrinfoSearchRepository;
}
