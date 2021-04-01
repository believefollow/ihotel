package ihotel.app.repository.search;

import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Configuration;

/**
 * Configure a Mock version of {@link CzCzl2SearchRepository} to test the
 * application without starting Elasticsearch.
 */
@Configuration
public class CzCzl2SearchRepositoryMockConfiguration {

    @MockBean
    private CzCzl2SearchRepository mockCzCzl2SearchRepository;
}
