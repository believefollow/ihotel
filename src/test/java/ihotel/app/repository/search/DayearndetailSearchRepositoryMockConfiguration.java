package ihotel.app.repository.search;

import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Configuration;

/**
 * Configure a Mock version of {@link DayearndetailSearchRepository} to test the
 * application without starting Elasticsearch.
 */
@Configuration
public class DayearndetailSearchRepositoryMockConfiguration {

    @MockBean
    private DayearndetailSearchRepository mockDayearndetailSearchRepository;
}
