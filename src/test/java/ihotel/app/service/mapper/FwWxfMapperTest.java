package ihotel.app.service.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class FwWxfMapperTest {

    private FwWxfMapper fwWxfMapper;

    @BeforeEach
    public void setUp() {
        fwWxfMapper = new FwWxfMapperImpl();
    }
}
