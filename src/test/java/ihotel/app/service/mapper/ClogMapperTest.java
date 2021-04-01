package ihotel.app.service.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class ClogMapperTest {

    private ClogMapper clogMapper;

    @BeforeEach
    public void setUp() {
        clogMapper = new ClogMapperImpl();
    }
}
