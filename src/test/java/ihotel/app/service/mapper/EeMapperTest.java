package ihotel.app.service.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class EeMapperTest {

    private EeMapper eeMapper;

    @BeforeEach
    public void setUp() {
        eeMapper = new EeMapperImpl();
    }
}
