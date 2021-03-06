package ihotel.app.repository.search;

import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Configuration;

/**
 * Configure a Mock version of {@link DUnitSearchRepository} to test the
 * application without starting Elasticsearch.
 */
@Configuration
public class DUnitSearchRepositoryMockConfiguration {

    @MockBean
    private DUnitSearchRepository mockDUnitSearchRepository;
}
