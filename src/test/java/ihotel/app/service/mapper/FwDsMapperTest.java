package ihotel.app.service.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class FwDsMapperTest {

    private FwDsMapper fwDsMapper;

    @BeforeEach
    public void setUp() {
        fwDsMapper = new FwDsMapperImpl();
    }
}
