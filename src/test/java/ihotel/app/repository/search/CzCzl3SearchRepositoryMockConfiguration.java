package ihotel.app.repository.search;

import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Configuration;

/**
 * Configure a Mock version of {@link CzCzl3SearchRepository} to test the
 * application without starting Elasticsearch.
 */
@Configuration
public class CzCzl3SearchRepositoryMockConfiguration {

    @MockBean
    private CzCzl3SearchRepository mockCzCzl3SearchRepository;
}
