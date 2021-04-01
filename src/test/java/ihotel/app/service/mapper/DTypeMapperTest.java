package ihotel.app.service.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class DTypeMapperTest {

    private DTypeMapper dTypeMapper;

    @BeforeEach
    public void setUp() {
        dTypeMapper = new DTypeMapperImpl();
    }
}
