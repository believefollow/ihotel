package ihotel.app.service.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class DxSedinfoMapperTest {

    private DxSedinfoMapper dxSedinfoMapper;

    @BeforeEach
    public void setUp() {
        dxSedinfoMapper = new DxSedinfoMapperImpl();
    }
}
