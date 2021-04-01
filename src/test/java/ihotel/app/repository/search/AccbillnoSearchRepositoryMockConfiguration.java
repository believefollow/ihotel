package ihotel.app.repository.search;

import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Configuration;

/**
 * Configure a Mock version of {@link AccbillnoSearchRepository} to test the
 * application without starting Elasticsearch.
 */
@Configuration
public class AccbillnoSearchRepositoryMockConfiguration {

    @MockBean
    private AccbillnoSearchRepository mockAccbillnoSearchRepository;
}
