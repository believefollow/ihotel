package ihotel.app.repository.search;

import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Configuration;

/**
 * Configure a Mock version of {@link CheckCzl2SearchRepository} to test the
 * application without starting Elasticsearch.
 */
@Configuration
public class CheckCzl2SearchRepositoryMockConfiguration {

    @MockBean
    private CheckCzl2SearchRepository mockCheckCzl2SearchRepository;
}
