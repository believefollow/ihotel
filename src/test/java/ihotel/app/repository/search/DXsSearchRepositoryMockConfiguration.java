package ihotel.app.repository.search;

import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Configuration;

/**
 * Configure a Mock version of {@link DXsSearchRepository} to test the
 * application without starting Elasticsearch.
 */
@Configuration
public class DXsSearchRepositoryMockConfiguration {

    @MockBean
    private DXsSearchRepository mockDXsSearchRepository;
}
