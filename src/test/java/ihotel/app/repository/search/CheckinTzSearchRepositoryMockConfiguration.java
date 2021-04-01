package ihotel.app.repository.search;

import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Configuration;

/**
 * Configure a Mock version of {@link CheckinTzSearchRepository} to test the
 * application without starting Elasticsearch.
 */
@Configuration
public class CheckinTzSearchRepositoryMockConfiguration {

    @MockBean
    private CheckinTzSearchRepository mockCheckinTzSearchRepository;
}
