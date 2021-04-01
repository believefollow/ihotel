package ihotel.app.repository.search;

import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Configuration;

/**
 * Configure a Mock version of {@link CheckinSearchRepository} to test the
 * application without starting Elasticsearch.
 */
@Configuration
public class CheckinSearchRepositoryMockConfiguration {

    @MockBean
    private CheckinSearchRepository mockCheckinSearchRepository;
}
