package ihotel.app.service.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class DSpczMapperTest {

    private DSpczMapper dSpczMapper;

    @BeforeEach
    public void setUp() {
        dSpczMapper = new DSpczMapperImpl();
    }
}
