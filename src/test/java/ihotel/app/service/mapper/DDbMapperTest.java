package ihotel.app.service.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class DDbMapperTest {

    private DDbMapper dDbMapper;

    @BeforeEach
    public void setUp() {
        dDbMapper = new DDbMapperImpl();
    }
}
