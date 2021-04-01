package ihotel.app.repository.search;

import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Configuration;

/**
 * Configure a Mock version of {@link ClassreportSearchRepository} to test the
 * application without starting Elasticsearch.
 */
@Configuration
public class ClassreportSearchRepositoryMockConfiguration {

    @MockBean
    private ClassreportSearchRepository mockClassreportSearchRepository;
}
