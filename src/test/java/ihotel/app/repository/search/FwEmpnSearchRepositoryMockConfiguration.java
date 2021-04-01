package ihotel.app.repository.search;

import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Configuration;

/**
 * Configure a Mock version of {@link FwEmpnSearchRepository} to test the
 * application without starting Elasticsearch.
 */
@Configuration
public class FwEmpnSearchRepositoryMockConfiguration {

    @MockBean
    private FwEmpnSearchRepository mockFwEmpnSearchRepository;
}
