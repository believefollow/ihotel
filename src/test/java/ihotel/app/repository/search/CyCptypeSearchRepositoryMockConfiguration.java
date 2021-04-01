package ihotel.app.repository.search;

import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Configuration;

/**
 * Configure a Mock version of {@link CyCptypeSearchRepository} to test the
 * application without starting Elasticsearch.
 */
@Configuration
public class CyCptypeSearchRepositoryMockConfiguration {

    @MockBean
    private CyCptypeSearchRepository mockCyCptypeSearchRepository;
}
