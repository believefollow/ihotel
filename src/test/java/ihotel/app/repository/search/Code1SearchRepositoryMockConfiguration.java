package ihotel.app.repository.search;

import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Configuration;

/**
 * Configure a Mock version of {@link Code1SearchRepository} to test the
 * application without starting Elasticsearch.
 */
@Configuration
public class Code1SearchRepositoryMockConfiguration {

    @MockBean
    private Code1SearchRepository mockCode1SearchRepository;
}
