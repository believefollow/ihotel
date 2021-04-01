package ihotel.app.service.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class DXsMapperTest {

    private DXsMapper dXsMapper;

    @BeforeEach
    public void setUp() {
        dXsMapper = new DXsMapperImpl();
    }
}
