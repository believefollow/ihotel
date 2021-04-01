package ihotel.app.repository.search;

import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Configuration;

/**
 * Configure a Mock version of {@link AuditinfoSearchRepository} to test the
 * application without starting Elasticsearch.
 */
@Configuration
public class AuditinfoSearchRepositoryMockConfiguration {

    @MockBean
    private AuditinfoSearchRepository mockAuditinfoSearchRepository;
}
