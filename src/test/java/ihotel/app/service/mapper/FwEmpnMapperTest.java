package ihotel.app.service.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class FwEmpnMapperTest {

    private FwEmpnMapper fwEmpnMapper;

    @BeforeEach
    public void setUp() {
        fwEmpnMapper = new FwEmpnMapperImpl();
    }
}
