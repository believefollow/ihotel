package ihotel.app.service.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class DxSedMapperTest {

    private DxSedMapper dxSedMapper;

    @BeforeEach
    public void setUp() {
        dxSedMapper = new DxSedMapperImpl();
    }
}
