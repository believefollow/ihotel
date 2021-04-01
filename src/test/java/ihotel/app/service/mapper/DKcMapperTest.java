package ihotel.app.service.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class DKcMapperTest {

    private DKcMapper dKcMapper;

    @BeforeEach
    public void setUp() {
        dKcMapper = new DKcMapperImpl();
    }
}
