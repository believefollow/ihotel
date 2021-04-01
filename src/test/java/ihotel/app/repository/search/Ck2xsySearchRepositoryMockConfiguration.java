package ihotel.app.repository.search;

import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Configuration;

/**
 * Configure a Mock version of {@link Ck2xsySearchRepository} to test the
 * application without starting Elasticsearch.
 */
@Configuration
public class Ck2xsySearchRepositoryMockConfiguration {

    @MockBean
    private Ck2xsySearchRepository mockCk2xsySearchRepository;
}
