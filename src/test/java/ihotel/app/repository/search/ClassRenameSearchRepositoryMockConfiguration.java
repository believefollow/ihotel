package ihotel.app.repository.search;

import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Configuration;

/**
 * Configure a Mock version of {@link ClassRenameSearchRepository} to test the
 * application without starting Elasticsearch.
 */
@Configuration
public class ClassRenameSearchRepositoryMockConfiguration {

    @MockBean
    private ClassRenameSearchRepository mockClassRenameSearchRepository;
}
