package ihotel.app.repository.search;

import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Configuration;

/**
 * Configure a Mock version of {@link CardysqSearchRepository} to test the
 * application without starting Elasticsearch.
 */
@Configuration
public class CardysqSearchRepositoryMockConfiguration {

    @MockBean
    private CardysqSearchRepository mockCardysqSearchRepository;
}
